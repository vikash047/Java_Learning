package Interview.IO.EventBus;

import Interview.IO.EventBus.Exceptions.RetryLimitExceedsxception;
import Interview.IO.EventBus.Models.*;
import Interview.IO.EventBus.Retry.RetryAlgorithm;
import Interview.IO.EventBus.Util.KeyedExecutor;
import Interview.IO.EventBus.Util.Timer;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class EventBus {
    private final KeyedExecutor<String> keyedExecutor;
    private final Timer timer;
    private final Map<Topic, List<Event>> buses;
    private final Map<Topic, Set<Subscriber>> subscribers;
    private final Map<Topic, Map<EntityId, EventIndex>> subscribersIndex;
    private final Map<Topic, Map<EventId, EventIndex>> eventIndex;
    private final Map<Topic, ConcurrentSkipListMap<TimeStamp, EventIndex>> timeStampIndex;
    private final RetryAlgorithm<Event, Void> retryAlgorithm;
    private final EventBus deadLetter;

    public EventBus(final int threads,
                    final EventBus deadLetter,
                    final Timer timer,
                    final RetryAlgorithm<Event, Void> retryAlgorithm) {
        this.keyedExecutor = new KeyedExecutor<>(threads);
        this.timer = timer;
        this.buses = new ConcurrentHashMap<>();
        this.subscribers = new ConcurrentHashMap<>();
        this.subscribersIndex = new ConcurrentHashMap<>();
        this.eventIndex = new ConcurrentHashMap<>();
        this.timeStampIndex = new ConcurrentHashMap<>();
        this.retryAlgorithm = retryAlgorithm;
        this.deadLetter = deadLetter;
    }

    public CompletionStage<Void> publish(Topic topic, Event event) {
        return keyedExecutor.getThreadFor(topic.getName(), () -> addToEventBus(topic, event));
    }

    public CompletionStage<Event> poll(Topic topic, EntityId subscriber) {
        return keyedExecutor.getThreadFor(topic.getName() + subscriber.getId(), () -> {
            final EventIndex index = subscribersIndex.get(topic).get(subscriber);
            try {
                final Event event = buses.get(topic).get(index.getIndex());
                setIndexAfterEvent(topic, event.getId(), subscriber).handleAsync((a, t) -> {
                    return "Hello";
                });
                return event;
            } catch (IndexOutOfBoundsException ex) {
                return null;
            }
        });
    }

    public void registerTopic(Topic topic) {
        if (buses.containsKey(topic)) {
            throw new IllegalArgumentException("Same Topic already exist");
        }
        buses.putIfAbsent(topic, new CopyOnWriteArrayList<>());
        timeStampIndex.putIfAbsent(topic, new ConcurrentSkipListMap<>());
        eventIndex.putIfAbsent(topic, new ConcurrentHashMap<>());
        subscribers.putIfAbsent(topic, Collections.newSetFromMap(new ConcurrentHashMap<>()));
        subscribersIndex.putIfAbsent(topic, new ConcurrentHashMap<>());
    }

    public CompletionStage<Void> subscribe(Subscriber subscriber) {
        return keyedExecutor.getThreadFor(subscriber.getTopic().getName(), () -> {
            subscribers.get(subscriber.getTopic()).add(subscriber);
            subscribersIndex.get(subscriber.getTopic())
                    .put(subscriber.getSubscriberId(), new EventIndex(buses.get(subscriber.getTopic()).size() - 1));
        });

    }

    public CompletionStage<Void> setIndexAfterTimeStamp(Topic topic, TimeStamp timeStamp, EntityId subscriber) {
        return keyedExecutor.getThreadFor(topic.getName() + subscriber.getId(), () -> {
            final Map.Entry<TimeStamp, EventIndex> eventIndexEntry = timeStampIndex.get(topic).higherEntry(timeStamp);
            if(eventIndexEntry == null) {
                subscribersIndex.get(topic).put(subscriber, new EventIndex(buses.get(topic).size() - 1));
            } else {
                subscribersIndex.get(topic).put(subscriber, eventIndexEntry.getValue().increment());
            }
        });
    }

    public CompletionStage<Void> setIndexAfterEvent(Topic topic, EventId event, EntityId subscriber) {
        return keyedExecutor.getThreadFor(topic.getName() + subscriber.getId(), () -> {
            final EventIndex index = eventIndex.get(topic).get(event);
            subscribersIndex.get(topic).put(subscriber, index.increment());
        });
    }

    public CompletionStage<Event> getEvent(Topic topic, EventId eventId) {
        return keyedExecutor.getThreadFor(topic.getName(), () -> {
           EventIndex index =  eventIndex.get(topic).get(eventId);
           return buses.get(topic).get(index.getIndex());
        });
    }
    private void addToEventBus(Topic topic, Event event) {
        buses.putIfAbsent(topic, new CopyOnWriteArrayList<>());
        timeStampIndex.putIfAbsent(topic, new ConcurrentSkipListMap<>());
        eventIndex.putIfAbsent(topic, new ConcurrentHashMap<>());
        EventIndex index = new EventIndex(buses.get(topic).size());
        timeStampIndex.get(topic).put(event.getTimeStamp(), index);
        eventIndex.get(topic).put(event.getId(), index);
        buses.get(topic).add(event);
        subscribers.getOrDefault(topic, Collections.newSetFromMap(new ConcurrentHashMap<>()))
                .stream().filter(sub -> sub.getSubscriptionType().equals(SubscriptionType.PUSH))
                .forEach(sub -> push(event, sub));
    }

    private void push(Event event, Subscriber sub) {
        keyedExecutor.getThreadFor(sub.getSubscriberId().getId() + sub.getTopic().getName(), () -> {
            try {
                retryAlgorithm.attempt(sub.getHandler(), event, 0);
            } catch (RetryLimitExceedsxception ex) {
                if(deadLetter != null) {
                    deadLetter.publish(sub.getTopic(), new FailureEvent(event, ex, Timer.getTimeStamp()));
                } else {
                    ex.printStackTrace();
                }
            }
        });
    }
}
