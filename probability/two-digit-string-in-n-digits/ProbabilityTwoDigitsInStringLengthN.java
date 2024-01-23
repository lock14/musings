
import java.math.BigDecimal;

/* 
 * @author Brian Bechtel
 */
public class ProbabilityTwoDigitsInStringLengthN {
    public static void main(String[] args) {
        try {
            String digits = args[0];
            if (digits.length() != 2) {
                System.err.println("Error: the given string must have length 2");
                System.exit(1);
            }
            if (!digits.chars().allMatch(Character::isDigit)) {
                System.err.println("Error: the given string must be numeric");
                System.exit(1);
            }
            int n = Integer.parseInt(args[1]);
            if (n >= 0) {
                BigDecimal probability;
                if (digits.charAt(0) == digits.charAt(1)) {
                   probability = ProbabilityElevenInStringLengthN.F(n);
                } else {
                   probability = ProbabilityThirteenInStringLengthN.F(n);
                }
                System.out.println("True probability " + probability);
                System.out.printf("There is a %.2f%% chance a '%s' occurs in string of length %d %n", probability.multiply(BigDecimal.TEN.pow(2)), digits, n);
            } else {
                System.err.println("Error: argument cannot be less than zero");
                System.exit(1);
            }
        } catch (ArrayIndexOutOfBoundsException  e) {
            System.err.println("Error: not enough arguments given");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Error: " + args[1] + " is not an integer");
            System.exit(1);
        }
    }
}

