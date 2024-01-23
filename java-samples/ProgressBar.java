import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProgressBar<T> implements Iterable<T> {

    private final Iterator<T> iterator;
    private final int size;

    public ProgressBar(Collection<T> collection) {
        this(collection.iterator(), collection.size());
    }

    public ProgressBar(Iterator<T> iterator, int size) {
        this.iterator = iterator;
        this.size = size;
    }

    public static <T> ProgressBar<T> of(Collection<T> c) {
        return new ProgressBar<>(c);
    }

    public static <T> ProgressBar<T> of(Iterator<T> iterator, int size) {
        return new ProgressBar<>(iterator, size);
    }

    /**
     * Prints a progress to standard out. The total length of the line printed
     * is 80 characters so that the bar will fit on one line with most
     * terminals. Percentage complete displays to two decimal places.
     *
     * Example:
     *  [##################################..........................] 56.67%
     *
     * @param iteration The current iteration
     * @param total the number of iterations
     *
     **/
    public static void printProgressBar(int iteration, int total) {
        printProgressBar(iteration, total, "", "", 2, 69, "#", ".");
    }

    /**
     * Prints a progress to standard out.
     *
     * @param iteration The current iteration
     * @param total the number of iterations
     * @param prefix a string prefix that appears before the percentage marker
     * @param suffix a string suffix that apepars after the percentage marker
     * @param decimals the number of decimal places to display the percentage
     * @param length the length of the progress bar
     * @param fill the character representing filled progress
     * @param unfill the character representing unfilled progress
     *
     **/
    public static void printProgressBar(int iteration, int total, String prefix,
                                   String suffix, int decimals, int length, 
                                   String fill, String unfill) {
        String percent = String.format("%." + decimals + "f", (100.0 * iteration) / total);
        int filledLength = (length * iteration) / total;
        String filled = String.join("", Collections.nCopies(filledLength, fill));
        String unfilled = String.join("", Collections.nCopies(length - filled.length(), unfill));
        String bar = filled + unfilled;
        int pad = 4 + decimals;
        System.out.printf("\r%s [%s] %" + pad + "s%% %s", prefix, bar, percent, suffix);
        if (iteration == total) {
            System.out.println();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ProgressIterator();
    }

    private final class ProgressIterator implements Iterator<T> {
        private int iteration;

        public ProgressIterator() {
            iteration = 0;
        }

        @Override
        public boolean hasNext() {
            return ProgressBar.this.iterator.hasNext();
        }

        @Override
        public T next() {
            T item = ProgressBar.this.iterator.next();
            printProgressBar(++iteration, ProgressBar.this.size);
            return item;
        }
    }

    public static void main(String[] args) {
        // demo the progress bar
        int limit = 1000;
        List<Integer> integers = IntStream.iterate(0, n -> n + 1)
            .limit(limit)
            .boxed()
            .collect(Collectors.toList());
        for (int i : ProgressBar.of(integers)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
