import java.math.BigDecimal;

/* 
 * @author Brian Bechtel
 *
 * This small program calculates the probability an '11' will occur in a string
 * of n digits.
 *
 * Problem Description:
 * We consider an n digit string, S, indexed from 0 to n-1. The ith digit, S[i],
 * is chosen uniformly at random from the alphabet {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}.
 *
 * Our goal is to forumlate a function, F(n), that calculates the probabilty of an
 * '11' occuring between the indices 0 and n-1 of S.
 *
 * Derivation:
 * let N = the event an '11' occurs between indices 0...n-1
 *     A = the event an '11' occurs at indices 0 and 1
 *     B = the event an '11' occurs between indices 1...n-1
 *     C = the event a '1' occurs at index 2
 *     D = the event an '11' occurs at index between indices 3...n-1
 *
 * let 'v' denote set union, and '^' denote set intersection
 *
 * F(n) = P(N)
 *      = P(A v B) // by analysis
 *      = P(A) + P(B) - P(A ^ B) // Addition Rule
 *      = P(A) + P(B) - P(A)P(B | A) // Multiplication Rule
 *      = P(A) + P(B) - P(A)P(C v D | A) // C v D, given A = B, given A
 *      = P(A) + P(B) - P(A ^ (C v D)) // Multiplication Rule
 *      = P(A) + P(B) - P((A ^ C) v (A ^ D)) // Distribution of set intersection over union
 *      = P(A) + P(B) - (P(A ^ C) + P(A ^ D) - P(A ^ C ^ D)) // Addition Rule
 *      = P(A) + P(B) - P(A ^ C) - P(A ^ D) + P(A ^ C ^ D) // Simplification
 *      = P(A) + P(B) - P(A)P(C) - P(A)(D) + P(A)P(C)P(D) // Events A, C, and D are all independent from each other
 *
 * From analysis we can derive: 
 *      P(A) = 1/10 * 1/10 = 1/100
 *      P(B) = F(n-1)
 *      P(C) = 1/10
 *      P(D) = F(n-3)
 *
 * Therefore:
 * F(n) = 1/100 + F(n-1) - 1/1000 - 1/100F(n-3) + 1/1000F(n-3)
        = 9/1000 + F(n-1) - 9/1000F(n-3), where F(0) = F(1) = 0, and F(2) = 1/100
 * 
 * Finally, it should be noted that this is the probability of any two digit 
 * number occuring in an n digit string so long as the two digits are the same.
 *
 * If you are doubting the correctness of this derivation, you can compare the
 * output of the following python function with the output of this program. The
 * python function computes the proportion of strings that contain a '11' in an
 * n length string using brute force. It can only compute the proportions for
 * n <= 9 in a reasonable amount of time as result of its time complexity.
 *
 * def proportion_of_strings_containing_eleven(n):
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
                BigDecimal probability = F(n);
                System.out.println("True probability " + probability);
                System.out.printf("There is a %.2f%% chance an '11' occurs in string of length %d %n", probability.multiply(BigDecimal.TEN.pow(2)), n);
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
        if (n < 2) {
            return BigDecimal.ZERO;
        }
        BigDecimal nineThousandths = BigDecimal.TEN.subtract(BigDecimal.ONE)
                                               .divide(BigDecimal.TEN.pow(3));
        BigDecimal nMinusThree = BigDecimal.ZERO;
        BigDecimal nMinusTwo = BigDecimal.ZERO;
        BigDecimal nMinusOne = BigDecimal.ZERO;
        BigDecimal result = BigDecimal.ONE.divide(BigDecimal.TEN.pow(2));
        /* want to do n-2 interations. i.e. n=3 requires 1 iteration,
         * n=4 requires 2 iterations,  etc.
         */
        for (int i = 0; i < (n - 2); i++) {
            nMinusThree = nMinusTwo;
            nMinusTwo = nMinusOne;
            nMinusOne = result;
            result = nineThousandths.add(nMinusOne)
                                    .subtract(nineThousandths.multiply(nMinusThree))
                                    .stripTrailingZeros();
        }
        return result;
    }
}
