public class Heap<E extends Comparable<E>> {
    public static int DEFAULT_CAPACITY;
    public static enum HeapType {MIN, MAX}
    private Object[] heap;
    private int currentIndex;
    private HeapCondition<E> heapCondition;
    

    public Heap() {
        this(HeapType.MIN);
    }

    public Heap(HeapType type) {
        this(DEFAULT_CAPACITY, type);
    }

    public Heap(int capacity, HeapType type) {
        heap = new Object[capacity];
        currentIndex = 0;
        if (type == HeapType.MIN) {
            heapCondition = new HeapCondition<E>() {
                public boolean condition(E child, E parent) {
                    return parent.compareTo(child) < 0;
                }
            };
        } else if(type == HeapType.MAX) {
            heapCondition = new HeapCondition<E>() {
                public boolean condition(E child, E parent) {
                    return parent.compareTo(child) > 0;
                }
            };
        }
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
            if (child1 >= heap.length && child2 >= heap.length) {
                heap[index] = null;
            } else if (child1 < heap.length && child2 >= heap.length) {
                heap[index] = heap[child1];
                heap[child1] = null;
            } else if (heapCondition.condition(((E) heap[child1]), ((E) heap[child2]))) {
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
            if (!heapCondition.condition(((E) heap[index]), ((E) heap[parent]))) {
                E temp = (E) heap[parent];
                heap[parent] = heap[index];
                heap[index] = temp;
                enforceInvariant(parent);
            }
        }
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

    private interface HeapCondition<E extends Comparable<E>> {
        public boolean condition(E child, E parent);
    }
}
