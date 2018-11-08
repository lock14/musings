import java.util.*;
import java.util.stream.*;

/**
 * Generates primes using a naive method
 */
public final class NaivePrime {
    // make utility class constructor private
    private NaivePrime() {}
    
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
        return n > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(n))
                                 .filter(m -> n % m == 0)
                                 .count() == 0;
    }
}
