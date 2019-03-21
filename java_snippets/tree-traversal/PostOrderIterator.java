import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;

public class PostOrderIterator<E> implements Iterator<E> {
    private Deque<TreeNode<E>> stack;
    private TreeNode<E> prev;

    public PostOrderIterator(TreeNode<E> root) {
        stack = new ArrayDeque<>();
        stack.push(root);
        prev = null;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public E next() {
        boolean foundNext = false;
        TreeNode<E> current = null;
        while (!foundNext) {
            current = stack.peek();
            if (prev == null || prev.left == current || prev.right == current) {
                if (current.left != null) {
                    stack.push(current.left);
                } else if (current.right != null) {
                    stack.push(current.right);
                } else {
                    stack.pop();
                    foundNext = true;
                }
            } else if (current.left == prev) {
                if (current.right != null) {
                    stack.push(current.right);
                } else {
                    stack.pop();
                    foundNext = true;
                }
            } else if (current.right == prev) {
                stack.pop();
                foundNext = true;
            }
            prev = current;
        }
        return current.data;
    }
}
