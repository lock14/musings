public interface Queue<E> {
    public void add(E data);
    public E remove();
    public boolean isEmpty();
}
