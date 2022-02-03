import java.lang.Comparable;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IteratorExamples {

    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4, 5, 6 , 7};
        TreeNode<Integer> root = createBalanced(a);
        System.out.println(toString(new InOrderIterator<>(root)));
        System.out.println(toString(new PreOrderIterator<>(root)));
        System.out.println(toString(new PostOrderIterator<>(root)));
        for (int i = 1; i <= a.length; i++) {
            System.out.println(findKthSmallest(root, i).orElse(-1));
        }
    }
    
    // pre: root is binary search tree
    // post: returns the kth smallest item in the tree if it exists
    public static <E extends Comparable<? super E>> Optional<E> findKthSmallest(TreeNode<E> root, int k) {
        Iterator<E> itr = new InOrderIterator<>(root);
        while (itr.hasNext() && k > 1) {
            itr.next();
            k--;
        }
        return Optional.ofNullable(k == 1 ? itr.next() : null);
    }

    public static <E extends Comparable<? super E>> TreeNode<E> createBalanced(E[] a) {
        return createBalanced(a, 0, a.length - 1);
    }

    public static <E extends Comparable<? super E>> TreeNode<E> createBalanced(E[] a, int low, int high) {
        if (low <= high) {
            int mid = (high + low) / 2;
            TreeNode<E> root = new TreeNode<>(a[mid]);
            root.left = createBalanced(a, low, mid - 1);
            root.right = createBalanced(a, mid + 1, high);
            return root;
        }
        return null;
    }

    public static <T> String toString(Iterator<T> iterator) {
        return String.join(stream(iterator)
                           .map(String::valueOf)
                           .collect(Collectors.joining(", ")), "[", "]");
    }
    
    public static <T> Stream<T> stream(Iterator<T> iterator) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
