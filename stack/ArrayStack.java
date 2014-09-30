public class ArrayStack<E> implements Stack<E> {
    public static final int DEFAULT_CAPACITY = 100;
    private Object[] stack;
    private int size;

    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayStack(int capacity) {
        stack = new Object[capacity];
        size = 0;
    }
    
    @SuppressWarnings("unchecked")
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        // actually want to null out references
        // to avoid memory leaks
        E data = (E)stack[--size];
        stack[size + 1] = null;
        return data;
    }

    public void push(E data) {
        checkSize();
        stack[size++] = data;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        StringBuilder s = new StringBuilder("[");
        if (stack[0] != null) {
            s.append(stack[0]);
        }
        for (int i = 1; i < size; i++) {
            s.append(", ").append(stack[i]);
        }
        return s.append("]").toString();
    }

    private void checkSize() {
        if (size == stack.length) {
            int newSize = stack.length + (stack.length / 2);
            Object[] newStack = new Object[newSize];
            for (int i = 0; i < stack.length; i++) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
    }
}
