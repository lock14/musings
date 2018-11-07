import java.util.*;
import java.util.stream.*;

/**
 * Generates primes using the Sieve of Eratosthenes
 * implemented using the Java Streams API. This turned
 * out to be significantly slower than the method used
 * in NaivePrime.java
 */
public final class PrimeSieve {
    // make utility class constructor private
    private PrimeSieve() {}
    
    public static List<Integer> primes(int n) {
        return primes().limit(n).boxed().collect(Collectors.toList());
    }

    public static IntStream primes() {
        MutableIntPredicate isPrime = new MutableIntPredicate();
        return IntStream.iterate(2, n -> n + 1)
                        .filter(isPrime)
                        .peek(p -> isPrime.combine(n -> n % p != 0));
    }

    private static final class MutableIntPredicate implements IntPredicate {
        IntPredicate predicate = n -> true;

        @Override
        public boolean test(int n) {
            return predicate.test(n);
        }

        public void combine(IntPredicate other) {
            predicate = predicate.and(other);
        }
    }
}
