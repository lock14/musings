import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * KthMin
 */
public class KthMin {
    private static Random RNG = new Random();
    public static void main(String[] args) {
        kthMinTest();
    }

    private static void kthMinTest() {
        for (int i = 0; i < 100; i++) {
            int[] a = IntStream.generate(RNG::nextInt)
                               .limit(RNG.nextInt(100) + 1)
                               .toArray();
            int k = RNG.nextInt(a.length) + 1;
            int[] a2 = a.clone();
            int val = kthMin(a, k);
            Arrays.sort(a2);
            if (val != a2[k - 1]) {
                throw new RuntimeException();
            }
        }
        System.out.println("PASSED");
    }

    public static int kthMin(int[] a, int k) {
        return kthMin(a, k, 0, a.length - 1);
    }

    public static int kthMin(int[] a, int k, int low, int high) {
        if (low <= high) {
            int pivotIndex = partition(a, low, high);
            if (k - 1 == pivotIndex) {
                return a[pivotIndex];
            } else if (k - 1 < pivotIndex) {
                return kthMin(a, k, low, pivotIndex - 1);
            } else {
                return kthMin(a, k, pivotIndex + 1, high);
            }
        }
        return -1;
    }
        

    public static int partition(int[] a, int low, int high) {
        int pivot = a[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                swap(a, i++, j);
            }
        }
        swap(a, i, high);
        return i;
    }

    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}