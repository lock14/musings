import java.math.BigDecimal;

/* 
 * @author Brian Bechtel
 *
 * This small program calculates the probability a '13' will occur in a string
 * of n digits.
 *
 * Asumptions: 
 *  1. only digits 0-9 can occur in the string.
 *  2. each digit has an equal likelyhood of showing up in each position of the
 *     the string. i.e. for a digit n element of {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
 *     P_i(n) = 1/10 for the ith digit in the string. 
 *
 * we will try to forumlate a function F(n) that calculates the probabilty of a
 * '13' occuring in a string of n digits.
 *
 * Derivation:
 *  let N = the event a '13' occurs in a string of n digits
 *      A = the event a '13' occurs in the first two digits of a string
 *      B = the event a '13' occurs in a string of (n-1) digits
 *      C = the event a '13' occurs in a string of (n-2) digits
 *
 *  let 'union' denote set union, and 'intersect' denote set intersection
 *
 *  F(n) = P(N)
 *       {a '13' occurs in the first 2 digits or in the next n-1 digits}
 *       = P(A union B)
 *       {probability of the union of two events}
 *       = P(A) + P(B) - P(A intersect B)
 *       {B can be substitued for C. see note for justification}
 *       = P(A) + P(B) - P(A intersect C)
 *       {probability of the intersection of independent events}
 *       = P(A) + P(B) - P(A)P(C)         
 *
 *  Note: The set (A intersect B) is the same as (A intersect C) since if the 
 *        first two digits of an n digit string are '13', then event B = C as
 *        the first digit of the (n-1) string is a '3'. Meaning a '13' can
 *        only occur in (n-2) digits. P(A intesect B) cannot be split up due 
 *        to events A and B being dependent. However, events A and C are 
 *        independent. So P(A intersect C) can be broken up into P(A)P(C).
 *
 *  we know: 
 *       P(A) = 1/10 * 1/10 = 1/100
 *       P(B) = F(n-1)
 *       P(C) = F(n-2)
 *
 *  Therefore:
 *  F(n) = (1/100) + F(n-1) - (1/100)F(n-2), where F(0) = F(1) = 0
 * 
 * Finally, it should be noted that this is the probability of any two digit 
 * number occuring in an n digit string so long as the two digits are not the 
 * same. For numbers like '11', '22', etc, the reasoning for replacing 
 * (A intersect B) with (A interssect C) does not work.
 *
 * If you are doubting the correctness of this derivation you can compare the output
 * of the following python function with the output of this program. The python
 * function computes the proportion of strings that contain a '13' in an n length
 * string using brute force (e.g. it generates all possible strings and checks
 * each one). Fair warning, it is quite slow as a result of this.
 * 
 * def proportion_of_strings_containing_thirteen(n):
 *     return len([x for x in range(0, 10**n) if "13" in str(x)]) / 10**n
 */
public class ProbabilityThirteenInStringLengthN {
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);
            if (n >= 0) {
                System.out.println(F(n));
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
     * in O(2^n) time complexity, similar to calculating the Fibinacci sequence 
     * recursively. Therefore an interative approach should be taken to achieve
     * a much faster time complexity of O(n).
     */
    public static BigDecimal F(int n) {
        BigDecimal oneHundreth = BigDecimal.ONE.divide(BigDecimal.TEN.pow(2));
        BigDecimal nMinusTwo = BigDecimal.ZERO; 
        BigDecimal nMinusOne = BigDecimal.ZERO;
        BigDecimal result = BigDecimal.ZERO; 
        /* want to do n-1 interations. i.e. n=0 or n=1 requires 0 iterations,
         * n=2 requires 1 iterations, n=3 requires 2 iterations etc. */
        for (int i = 0; i < (n - 1); i++) {
            nMinusTwo = nMinusOne;
            nMinusOne = result;
            result = oneHundreth.add(nMinusOne)
                     .subtract(oneHundreth.multiply(nMinusTwo));
        }
        return result;
    }
}
