import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * QuickSort
 */
public class QuickSort {

    public static void main(String[] args) {
        Random r = new Random();
        int[] a1 = IntStream.generate(r::nextInt)
                            .limit(100)
                            .toArray();
        int[] a2 = a1.clone();
        Arrays.sort(a1);
        quickSort(a2);
        if (!Arrays.equals(a1, a2)) {
            System.out.println(Arrays.toString(a1));
            System.out.println(Arrays.toString(a2));
            throw new RuntimeException();
        }
        System.out.println("PASSED");
    }

    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    public static void quickSort(int[] a, int low, int high) {
        if (low <= high) {
            int pivotIndex = randomPartition(a, low, high);
            quickSort(a, low, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, high);
        }
    }

    private static int randomPartition(int[] a, int low, int high) {
        Random r = new Random();
        int index = low + r.nextInt(high - low + 1);
        swap(a, index, high);
        return partition(a, low, high);
    }

    public static int partition(int[] a, int low, int high) {
        int pivot = a[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (a[j] < pivot) {
                swap(a, i++, j);
            }
        }
        swap(a, i, high);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}