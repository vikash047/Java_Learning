package CodingQuestions;

public class MySortedCollection {
   /* private int size;
    private int slidingMeanValue = 20;
    private TreeMap<Integer, TIntHashSet> map;

    public MySortedCollection(int size) {
        map = new TreeMap<Integer, TIntHashSet>();
    }

    void remove(int key, int value) {
        TIntHashSet set = map.get(value);
        if (set == null || !set.remove(key))
            throw new IllegalStateException("cannot remove key " + key + " with value " + value
                    + " - did you insert " + key + "," + value + " before?");
        size--;
        if (set.isEmpty())
            map.remove(value);
    }

    public void update(int key, int oldValue, int value) {
        remove(key, oldValue);
        insert(key, value);
    }

    public void insert(int key, int value) {
        TIntHashSet set = map.get(value);
        if (set == null)
            map.put(value, set = new TIntHashSet(slidingMeanValue));
//        else
//            slidingMeanValue = Math.max(5, (slidingMeanValue + set.size()) / 2);
        if (!set.add(key))
            throw new IllegalStateException("use update if you want to update " + key);
        size++;
    }

    public int peekValue() {
        if (size == 0)
            throw new IllegalStateException("collection is already empty!?");
        Entry<Integer, TIntHashSet> e = map.firstEntry();
        if (e.getValue().isEmpty())
            throw new IllegalStateException("internal set is already empty!?");
        return map.firstEntry().getKey();
    }

    public int peekKey() {
        if (size == 0)
            throw new IllegalStateException("collection is already empty!?");
        TIntHashSet set = map.firstEntry().getValue();
        if (set.isEmpty())
            throw new IllegalStateException("internal set is already empty!?");
        return set.iterator().next();
    }

    public int pollKey() {
        size--;
        if (size < 0)
            throw new IllegalStateException("collection is already empty!?");
        Entry<Integer, TIntHashSet> e = map.firstEntry();
        TIntHashSet set = e.getValue();
        TIntIterator iter = set.iterator();
        if (set.isEmpty())
            throw new IllegalStateException("internal set is already empty!?");
        int val = iter.next();
        iter.remove();
        if (set.isEmpty())
            map.remove(e.getKey());
        return val;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSlidingMeanValue() {
        return slidingMeanValue;
    }

    @Override
    public String toString() {
        return "size " + size + " min=(" + peekKey() + "=>" + peekValue() + ")";
    }*/
}
