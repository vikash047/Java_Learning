package CodingQuestions;

import com.sun.security.jgss.GSSUtil;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CustomSet {

    class Entry {
        public int key;
        public int value;
        public Entry next;
        public Entry(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private AtomicInteger size;
    private int count;

    private int eleCount;
    private Entry[] entries;

    private ReentrantReadWriteLock readWriteLock;

    public CustomSet() {
        this.size = new AtomicInteger(16);
        this.count = 0;
        entries = new Entry[this.size.get()];
        readWriteLock = new ReentrantReadWriteLock(true);
    }

    private int getIndex(int i) {
        return i%this.size.get();
    }

    private void add(Entry[] entries,int index, Entry entry) {
        this.eleCount++;
        if(entries[index] == null) {
            this.count++;
        } else {
            entry.next = entries[index];
        }
        entries[index] = entry;
    }

    private boolean contains(int index, int val) {
        readWriteLock.readLock().lock();
        try {
            Entry temp = this.entries[index];
            while (temp != null) {
                if(temp.value == val) {
                    return true;
                }
                temp = temp.next;
            }
            return false;
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    private boolean remove(int index, int val) {
        Entry temp = this.entries[index];
        Entry prev = null;
        while (temp != null) {
            if(temp.value == val) {
                if(prev == null) {
                    entries[index] = temp.next;
                } else {
                    prev.next = temp.next;
                }
                this.eleCount--;
                return true;
            }
            prev = temp;
            temp = temp.next;
        }
        return false;
    }
    private void resize() {

        if(count < this.size.get()*0.70) {
            return;
        }
        int newSize = this.size.get()*2;
        Entry[] newEntries = new Entry[newSize];
        this.count = 0;
        this.size = new AtomicInteger(newSize);
        this.eleCount = 0;
        for(int i = 0; i < entries.length; i++) {
            Entry temp = entries[i];
            Entry next;
            while (temp != null) {
                next = temp.next;
                int index = getIndex(temp.value);
                this.add(newEntries, index, temp);
                temp = next;
            }
        }
        this.entries = newEntries;
    }
    public boolean add(int i){
        if(count >= size.get()*0.70) {
            readWriteLock.writeLock().lock();
            try {
                if(count >= size.get()*0.70) {
                    resize();
                }
            } finally {
                readWriteLock.writeLock().unlock();
            }

        }
        int index = getIndex(i);
        readWriteLock.writeLock().lock();
        try {
            this.add(this.entries, index, new Entry(index, i));
        } finally {
            readWriteLock.writeLock().unlock();
        }

        return true;
    }

    public boolean remove(int i){
        int index = getIndex(i);
        readWriteLock.writeLock().lock();
        try {
            return this.remove(index, i);
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public boolean contains(int i){
        int index = getIndex(i);
        readWriteLock.readLock().lock();
        try {
            if(this.entries[index] != null) {
                return this.contains(index, i);
            }
            return false;
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    public Iterator<Integer> iterator(){
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<Integer> {
        private int pos;
        private Integer remEle;

        private Entry currentNode;

        private Entry[] copy;

        public CustomIterator() {
            this.pos = 0;
            this.remEle = eleCount;
            this.currentNode = null;
            copy = new Entry[size.get()];
            readWriteLock.writeLock().lock();
            try {
                System.arraycopy(entries, 0, copy, 0, size.get());
            } finally {
                readWriteLock.writeLock().unlock();
            }

        }
        @Override
        public boolean hasNext() {
            //System.out.println(this.remEle);
            return (this.remEle > 0);
        }

        @Override
        public Integer next() {
            if(this.hasNext()) {
                if(currentNode == null) {
                    while (pos < size.get() && copy[pos] == null) pos++;
                    currentNode = copy[pos];
                    pos++;
                }
                int value = currentNode.value;
                currentNode = currentNode.next;
                this.remEle--;
                return value;
            }
            throw new UnsupportedOperationException("No more elements exits");
        }
    }

    public void printAll(){
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + ", ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        CustomSet set = new CustomSet();
        for(int i = 1; i <= 20; i++) {
            set.add(i);
        }
        set.printAll();
        System.out.println(set.contains(10));
        set.remove(19);
        System.out.println(set.contains(19));
        System.out.println(set.remove(100));
        System.out.println(set.contains(30));
        set.printAll();
        for(int i = 21; i <= 100; i++) {
            set.add(i);
        }
        set.printAll();
    }

}
