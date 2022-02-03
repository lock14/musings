import java.util.Arrays;
import java.util.Random;

public class Shuffle {

    public static void main(String[] args) {
        int[] a = new int[] {1, 2, 3, 4, 5};
        shuffle(a);
        System.out.println(Arrays.toString(a));
    }

    public static void shuffle(boolean[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            boolean tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void shuffle(char[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            char tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void shuffle(byte[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            byte tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void shuffle(short[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            short tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void shuffle(int[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void shuffle(long[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            long tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void shuffle(float[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            float tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void shuffle(double[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            double tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static <T> void shuffle(T[] a) {
        Random r = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            T tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
    
}
