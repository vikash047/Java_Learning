package Adobe;


/*

Library stack any one can use

function
push to add elements.
peek top element of stack
pop  to remove top element of a stack
size -> what is current size of a stack
Empty

Generic
Limit
Exception if limit increase
OOM


 */
public class Adobe2 {
    interface CustomStack<T> {
        void push(T item);
        T peek();
        T pop();
        int getSize();
        boolean isEmpty();
    }

    class CustomStackImpl<T> implements CustomStack<T> {

        private final  int dl = 100000;
        private int limit;
        private T[] record;
        private int currSize;

        public CustomStackImpl(int limit) {
            if(this.limit <= 0) {

            }
            this.limit = limit;
            record = (T[]) new Object[this.limit];
            this.currSize = 0;
        }

        public CustomStackImpl() {
            this.limit = dl;
            record = (T[]) new Object[this.limit];
            this.currSize = 0;
        }
        @Override
        public void push(T item) {
            if(currSize > this.limit) {
                throw new RuntimeException();
            }
            record[this.currSize] = item;
            this.currSize++;
        }

        @Override
        public T peek() {
             if(isEmpty()) {
                 return null;
             }
             return record[this.currSize - 1];
        }

        @Override
        public T pop() {
            if(isEmpty()) {
                return  null;
            }
            T item = peek();
            record[this.currSize - 1] = null;
            this.currSize--;
            return item;
        }

        @Override
        public int getSize() {
            return this.currSize;
        }

        @Override
        public boolean isEmpty() {
            return (this.currSize == 0);
        }
    }
}
