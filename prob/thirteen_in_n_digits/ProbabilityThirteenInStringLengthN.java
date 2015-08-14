import java.math.BigDecimal;

/*
 * This small program calculates the probability a '13' will occur in a string
 * of n digits.
 *
 * Asumptions: 
 *  1. only digits 0-9 can occur in the string.
 *  2. each digit has an equal likelyhood of showing up in each slot.
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
 *       = P(A union B)
 *       = P(A) + P(B) - P(A intersect B)
 *       = P(A) + P(B) - P(A intersect C)
 *       = P(A) + P(B) - P(A)P(C)
 *
 *  Note: The set (A intersect B) is the same as (A intersect C) since if the 
 *        first two digits of an n digit string are '13', then event B = C as
 *        the first digit of the (n-1) string is a '3'. Meaning a '13' can
 *        only occur in (n-2) digits. P(A intesect B) cannot be split up due 
 *        to events A and B being dependent. However, events A and C are 
 *        independent. So P(A intersect C) can be broken up int P(A)P(C).
 *
 *  we know: 
 *       P(A) = 1/100
 *       P(B) = F(n-1)
 *       P(C) = F(n-2)
 *  so:
 *  F(n) = (1/100) + F(n-1) - (1/100)F(n-2), where F(0) = F(1) = 0
 * 
 *  Finally, it should be noted that this is the probability of any two digit 
 *  number occuring in an n digit string so long as the two digits are not the 
 *  same. For numbers like '11', '22', etc, the reasoning for replacing 
 *  (A intersect B) with (A interssect C) does not work.
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
        BigDecimal zero = BigDecimal.valueOf(0.0);
        if (n == 0 || n == 1) {
            return zero;
        }
        BigDecimal nMinusTwo = zero; 
        BigDecimal nMinusOne = nMinusTwo; 
        BigDecimal result = nMinusOne; 
        for (int i = 1; i < n; i++) {
            nMinusTwo = nMinusOne;
            nMinusOne = result;
            result = nMinusOne.subtract(BigDecimal.valueOf(0.01).multiply(nMinusTwo)).add(BigDecimal.valueOf(0.01));
        }
        return result;
    }
}
