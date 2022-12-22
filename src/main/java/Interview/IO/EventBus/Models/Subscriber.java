package Interview.IO.EventBus.Models;

import java.util.function.Function;

public class Subscriber {
    private final EntityId subscriberId;
    private final Topic topic;
    private final SubscriptionType subscriptionType;
    private final Function<Event, Void> handler;
    private final Function<Event, Boolean> preconditions;

    public Subscriber(EntityId subscriberId, Topic topic, SubscriptionType subscriptionType, Function<Event, Void> handler, Function<Event, Boolean> preconditions) {
        this.subscriberId = subscriberId;
        this.topic = topic;
        this.subscriptionType = subscriptionType;
        this.handler = handler;
        this.preconditions = preconditions;
    }

    public EntityId getSubscriberId() {
        return subscriberId;
    }

    public Topic getTopic() {
        return topic;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public Function<Event, Void> getHandler() {
        return handler;
    }

    public Function<Event, Boolean> getPreconditions() {
        return preconditions;
    }
}
