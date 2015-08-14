public class ArrayQueue<E> implements Queue<E> {
    public static final int DEFAULT_CAPACITY = 100;
    private Object[] queue;
    private int front;
    private int end;
    
    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }
    
    public ArrayQueue(int capacity) {
        queue = new Object[capacity];
        front = 0;
        end = 0;
    }

    public void add(E data) {
        int next = (end + 1) % queue.length;
        if (next == front) {
            expandCapacity();
        }
        queue[end] = data;
        end = next;
    }
    
    @SuppressWarnings("unchecked")
    public E remove() {
        E data = (E) queue[front];
        queue[front] = null; // avoid memory leak
        front = (front + 1) % queue.length;
        return data;
    }

    public boolean isEmpty() {
        return front == end;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        if (!isEmpty()){
            s.append(queue[front]);
        }
        for (int i = front + 1; i < end; i++) {
            s.append(", ").append(queue[i]);
        }
        return s.append("]").toString();
    }

    private void expandCapacity() {
        Object[] newQueue = new Object[queue.length + (queue.length / 2)];
        for (int i = front; i < end; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }
}
