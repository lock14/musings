import java.math.BigDecimal;

/* 
 * @author Brian Bechtel
 * 
 * def proportion_of_strings_containing_thirteen(n):
 *     return sum(1 for x in range(10**n) if "11" in str(x)) / 10**n
 * 
 * Here is a table for the brute force solution up n=10
 * +-----+--------------+
 * |  N  |     P(N)     |
 * +-----+--------------+
 * |  0  | 0            |
 * |  1  | 0            |
 * |  2  | 0.01         |
 * |  3  | 0.019        |
 * |  4  | 0.028        |
 * |  5  | 0.03691      |
 * |  6  | 0.045739     |
 * |  7  | 0.054487     |
 * |  8  | 0.06315481   |
 * |  9  | 0.071743159  |
 * | 10  | 0.080252776  |
 */
public class ProbabilityElevenInStringLengthN {
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);
            if (n >= 0) {
                System.out.println(F(n));
            } else {
                System.err.println("Error: argument cannot be less than zero");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: no argument given");
        } catch (NumberFormatException e) {
            System.err.println("Error: " + args[0] + " is not an integer");
        }
    }

    /* A naive implementation using the recursive nature of F(n) would result
     * in O(2^n) time complexity, similar to calculating the Fibonacci sequence
     * recursively. Therefore, an iterative approach should be taken to achieve
     * a much faster time complexity of O(n).
     */
    public static BigDecimal F(int n) {
        BigDecimal oneHundreth = BigDecimal.ONE.divide(BigDecimal.TEN.pow(2));
        BigDecimal oneThousandth = BigDecimal.ONE.divide(BigDecimal.TEN.pow(3));
        BigDecimal nine = BigDecimal.TEN.subtract(BigDecimal.ONE);
        BigDecimal nMinusThree = BigDecimal.ZERO;
        BigDecimal nMinusTwo = BigDecimal.ZERO;
        BigDecimal nMinusOne = BigDecimal.ZERO;
        BigDecimal result = BigDecimal.ZERO;
        /* want to do n-1 interations. i.e. n=0 or n=1 requires 0 iterations,
         * n=2 requires 1 iterations, n=3 requires 2 iterations etc. */
        for (int i = 0; i < (n - 1); i++) {
            nMinusThree = nMinusTwo;
            nMinusTwo = nMinusOne;
            nMinusOne = result;
            result = oneHundreth.add(nMinusOne)
                                .subtract(i > 0 ? oneThousandth : BigDecimal.ZERO)
                                .subtract(nine.multiply(nMinusThree).multiply(oneThousandth))
                                .stripTrailingZeros();
        }
        return result;
    }
}
