import java.math.BigDecimal;

/* 
 * @author Brian Bechtel
 *
 * This small program calculates the probability a '13' will occur in a string
 * of n digits.
 *
 * Problem Description:
 *  We consider an n digit string, S, indexed from 0 to n-1. The ith digit, S[i],
 *  is chosen uniformly at random from the alphabet {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}.
 *
 *  Our goal is to forumlate a function, F(n), that calculates the probabilty of a
 * '13' occuring between the indices 0 and n-1 of S.
 *
 * Derivation:
 *  let N = the event a '13' occurs between indices 0...n-1 of a string
 *      A = the event a '13' occurs at indices 0 and 1 of a string
 *      B = the event a '13' occurs between indices 1...n-1 of a string
 *      C = the event a '13' occurs between indices 2...n-1 of a string
 *
 *  let 'v' denote set union, and '^' denote set intersection
 *
 *  F(n) = P(N)
 *       = P(A v B) // by analysis
 *       = P(A) + P(B) - P(A ^ B) // Addition Rule
 *       = P(A) + P(B) - P(A)P(B | A) // Multiplication Rule
 *       = P(A) + P(B) - P(A)P(C | A) // P(B | A) = P(C | A). See Note for details
 *       = P(A) + P(B) - P(A)P(C)     // A and C are independent, thus P(C | A) = P(C)    
 *
 *  Note: P(B | A) is the same as P(C | A). If A occurs, then index 0 is a '1'
 *        and index 1 is a '3'. Therefore event B = C as
 *        the digit at index 1 is a '3', meaning a '13' can
 *        only occur between indices 2...n-1.
 *
 *  From analysis we can derive: 
 *       P(A) = 1/10 * 1/10 = 1/100
 *       P(B) = F(n-1)
 *       P(C) = F(n-2)
 *
 *  Therefore:
 *  F(n) = (1/100) + F(n-1) - (1/100)F(n-2)
 *       = F(n-1) + 1/100 - F(n-2)/100
 *       = F(n-1) + (1 - F(n-2))/100, where F(0) = F(1) = 0
 * 
 * Finally, it should be noted that this is the probability of any two digit 
 * number occuring in an n digit string so long as the two digits distinct.
 * For numbers like '11', '22', etc, the reasoning for replacing 
 * P(B | A) with P(C | A) does not hold.
 *
 * If you are doubting the correctness of this derivation, you can compare the
 * output of the following python function with the output of this program. The
 * python function computes the proportion of strings that contain a '13' in an
 * n length string using brute force. It can only compute the proportions for
 * n <= 9 in a reasonable amount of time as result of its time complexity.
 * 
 * def proportion_of_strings_containing_thirteen(n):
 *     return sum(1 for x in range(10**n) if "13" in str(x)) / 10**n
 * 
 * Here is a table for the brute force solution up n=10
 * +-----+--------------+
 * |  N  |     P(N)     |
 * +-----+--------------+
 * |  0  | 0            |
 * |  1  | 0            |
 * |  2  | 0.01         |
 * |  3  | 0.02         |
 * |  4  | 0.0299       |
 * |  5  | 0.0397       |
 * |  6  | 0.049401     |
 * |  7  | 0.059004     |
 * |  8  | 0.06850999   |
 * |  9  | 0.07791995   |
 * | 10  | 0.0872348501 |
 */
public class ProbabilityThirteenInStringLengthN {
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);
            if (n >= 0) {
                BigDecimal probability = F(n);
                System.out.println("True probability " + probability);
                System.out.printf("There is a %.2f%% chance a '13' occurs in string of length %d %n", probability.multiply(BigDecimal.TEN.pow(2)), n);
            } else {
                System.err.println("Error: argument cannot be less than zero");
            }
        } catch (ArrayIndexOutOfBoundsException  e) {
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
        BigDecimal nMinusTwo = BigDecimal.ZERO; 
        BigDecimal nMinusOne = BigDecimal.ZERO;
        BigDecimal result = BigDecimal.ZERO; 
        /* want to do n-1 interations. i.e. n=0 or n=1 requires 0 iterations,
         * n=2 requires 1 iterations, n=3 requires 2 iterations etc.
         */
        for (int i = 0; i < (n - 1); i++) {
            nMinusTwo = nMinusOne;
            nMinusOne = result;
            result = oneHundreth.add(nMinusOne)
                     .subtract(oneHundreth.multiply(nMinusTwo));
        }
        return result;
    }
}
