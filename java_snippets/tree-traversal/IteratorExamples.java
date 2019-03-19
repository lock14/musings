import java.lang.Comparable;
import java.util.Iterator;
import java.util.Optional;

public class IteratorExamples {
    
    // pre: root is binary search tree
    // post: returns the kth smallest item in the tree if it exists
    public static <E extends Comparable<? super E>> Optional<E> findKthSmallest(TreeNode<E> root, int k) {
        Iterator<E> itr = new InOrderIterator<>(root);
        E data = null;
        while (itr.hasNext() && k > 0) {
            data = itr.next();
            k--;
        }
        return (k == 0) ? Optional.ofNullable(data) : Optional.empty();
    }
}
