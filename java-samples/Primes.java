import java.util.BitSet;
import java.util.stream.IntStream;

public class Primes {

   public static IntStream firstKPrimes(int k) {
      if (k < 1) {
         return IntStream.empty();
      }
      int bound = (int) Math.ceil(piInverse((double) k)) + 3;
      return primesLessThan(bound).limit(k);
   }

   public static IntStream primesLessThan(int n) {
      if (n < 0) {
         return IntStream.empty();
      }
      BitSet primes = new BitSet(n);
      // unset bit means prime, set bit means composite
      if (n > 2) {
         primes.set(0); // not prime
         primes.set(1); // not prime

         // handle 2 as a special case
         for (int j = 4; j > 0 && j < n; j += 2) {
            primes.set(j);
         }

         for (int i = 3; (i * i) > 0 && i * i < n; i += 2) {
            if (!primes.get(i)) {
               // ith number is prime
               for (int j = i * i; j > 0 && j < n; j += i) {
                  primes.set(j);
               }
            }
         }
         // now flip all the bits so that 1 means prime, 0 means not prime
         primes.flip(0, n);
      }
      return primes.stream();
   }

   private static double piInverse(double y) {
      double xOld = 0;
      double x = y * Math.log(y);
      double maxDiff = 0.5;
      while (x - xOld > maxDiff) {
          xOld = x;
          x = y * Math.log(x);
      }
      return x;
  }
}