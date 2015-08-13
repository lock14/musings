import java.util.Comparator;

public class Heap<E extends Comparable<E>> {
    public static int DEFAULT_CAPACITY = 100;
    private Object[] heap;
    private int currentIndex;
    private Comparator<E> comparator;

    public Heap() {
        this(new Comparator<E>(){
            public int compare(E o1, E o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public Heap(Comparator<E> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    public Heap(int capacity, Comparator<E> comparator) {
        heap = new Object[capacity];
        currentIndex = 0;
        comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    public E peek() {
        return (E) heap[0];
    }

    @SuppressWarnings("unchecked")
    public E remove() {
        E item = (E) heap[0];
        fixHeap(0);
        return item;
    }

    @SuppressWarnings("unchecked")
    private void fixHeap(int index) {
        if (heap[index] != null) {
            int child1 = 2 * index + 1;
            int child2 = 2 * index + 2;
            if (child1 >= heap.length) {
                heap[index] = null;
            } else if (child1 < heap.length && child2 >= heap.length) {
                heap[index] = heap[child1];
                heap[child1] = null;
            } else if (heapCondition(((E) heap[child1]), ((E) heap[child2]))) {
                heap[index] = heap[child2];
                fixHeap(child2);
            } else {
                heap[index] = heap[child1];
                fixHeap(child1);
            }
        }
    }

    public void insert(E item) {
        heap[currentIndex++] = item;
        if (currentIndex == heap.length) {
            resize();
        }
        enforceInvariant(currentIndex);
    }

    @SuppressWarnings("unchecked")
    private void enforceInvariant(int index) {
        if (index != 0) {
            int parent = (index - 1) / 2;
            if (!heapCondition(((E) heap[index]), ((E) heap[parent]))) {
                E temp = (E) heap[parent];
                heap[parent] = heap[index];
                heap[index] = temp;
                enforceInvariant(parent);
            }
        }
    }

    private boolean heapCondition(E e1, E e2) {
        return !(comparator.compare(e1, e2) > 0);
    }

    private void resize() {
        resize(heap.length + (heap.length / 2));
    }

    private void resize(int size) {
        if (size < heap.length) {
            throw new IllegalArgumentException("new size must be greater than current heap size");
        }
        Object[] newHeap = new Object[size];
        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
}
