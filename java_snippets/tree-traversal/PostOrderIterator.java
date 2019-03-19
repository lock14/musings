import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;

public class PostOrderIterator<E> implements Iterator<E> {
    private Deque<TreeNode<E>> stack;

    public PostOrderIterator(TreeNode<E> root) {
        stack = new ArrayDeque<>();
        while (root != null) {
            if (root.right != null) {
                stack.push(root.right);
            }
            stack.push(root);
            root = root.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public E next() {
        TreeNode<E> next = stack.pop();
        if (next.right == stack.peek()) {
            stack.pop();
            stack.push(next);
            next = next.right;
        }
        return next.data;
    }
}
