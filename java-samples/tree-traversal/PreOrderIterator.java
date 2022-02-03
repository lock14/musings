import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;

public class PreOrderIterator<E> implements Iterator<E> {
    private Deque<TreeNode<E>> stack;

    public PreOrderIterator(TreeNode<E> root) {
        stack = new ArrayDeque<>();
        if (root != null) {
            stack.push(root);
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public E next() {
        TreeNode<E> next = stack.pop();
        if (next.right != null) {
            stack.push(next.right);
        }
        if (next.left != null) {
            stack.push(next.left);
        }
        return next.data;
    }
}
