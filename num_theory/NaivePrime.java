import java.util.*;
import java.util.stream.*;

/**
 * Generates primes using a naive method
 */
public class NaivePrime {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(primes(n));
    }

    public static List<Integer> primes(int n) {
        return primes().limit(n).boxed().collect(Collectors.toList());
    }

    public static IntStream primes() {
        return IntStream.iterate(2, i -> i + 1)
                        .filter(NaivePrime::isPrime);
    }

    public static boolean isPrime(int n) {
        return IntStream.range(2, (int) (Math.sqrt(n) + 1))
                        .filter(m -> n % m == 0)
                        .count() == 0 && n > 1;
    }
}
