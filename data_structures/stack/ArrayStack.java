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
        E data = (E)stack[--size];
        stack[size + 1] = null; // avoid memory leak
        return data;
    }

    public void push(E data) {
        if (size == stack.length) {
            expandCapacity();
        }
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

    private void expandCapacity() {
        Object[] newStack = new Object[stack.length + (stack.length / 2)];
        for (int i = 0; i < size; i++) {
            newStack[i] = stack[i];
        }
        stack = newStack;
    }
}
