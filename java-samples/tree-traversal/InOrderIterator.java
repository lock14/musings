import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;

public class InOrderIterator<E> implements Iterator<E> {
    private Deque<TreeNode<E>> stack;

    public InOrderIterator(TreeNode<E> root) {
        stack = new ArrayDeque<>();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public E next() {
        TreeNode<E> next = stack.pop();
        TreeNode<E> cur = next.right;
        while(cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        return next.data;
    }
}
