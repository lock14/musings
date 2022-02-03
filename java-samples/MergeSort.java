import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class MergeSort {

    public static void main(String[] args) {
        Random r = new Random();
        int[] a1 = IntStream.generate(r::nextInt)
                            .limit(100)
                            .toArray();
        int[] a2 = a1.clone();
        Arrays.sort(a1);
        a2 = mergeSort(a2);
        if (!Arrays.equals(a1, a2)) {
            System.out.println(Arrays.toString(a1));
            System.out.println(Arrays.toString(a2));
            throw new RuntimeException();
        }
        System.out.println("PASSED");
    }
    
    public static int[] mergeSort(int[] a) {
        if (a.length <= 1) {
            return a;
        }
        int mid = a.length / 2;
        return merge(
            mergeSort(Arrays.copyOfRange(a, 0, mid)), 
            mergeSort(Arrays.copyOfRange(a, mid, a.length))
        );
    }

    private static int[] merge(int[] left, int[] right) {
        int[] a = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                a[k++] = left[i++];
            } else {
                a[k++] = right[j++];
            }
        }
        if (i < left.length) {
            System.arraycopy(left, i, a, k, left.length - i);
        }
        if (j < right.length) {
            System.arraycopy(right, j, a, k, right.length - j);
        }
        return a;
    }
}
