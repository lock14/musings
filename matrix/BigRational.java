import java.math.BigInteger; 
import java.math.BigDecimal; 
/**
 * Immutable object to represent rational numbers.
 * BigBigRational objects are always in reduced form.
 * BigRational objects always have denominator greater than or equal to 1.
 * negative BigRational objects are always represented with the numerator 
 * being negative.
 *
 * @author Brian Bechtel
 * @version 1.2
 */
public class BigRational implements Comparable<BigRational> {
    /**
     * The BigRational constant zero
     **/
    public static final BigRational ZERO = valueOf(0);
    /**
     * The BigRational constant one
     **/
    public static final BigRational ONE = valueOf(1);
    /**
     * The BigRational constant ten
     **/
    public static final BigRational TEN = valueOf(10);
    /**
     * The BigRational constant representing positive infinity (1/0) 
     **/
    public static final BigRational POSITIVE_INFINITY = new BigRational("Infinity");
    /**
     * The BigRational constant representing positive infinity (-1/0) 
     **/
    public static final BigRational NEGATIVE_INFINITY = new BigRational("-Infinity");
    /**
     * The BigRational constant representing 'not a number' (0/0) 
     **/
    public static final BigRational NaN = new BigRational("NaN");
    
    private final BigInteger numerator;
    private final BigInteger denominator;


    private BigRational(String keyword) {
        switch (keyword) {
            case "Infinity":
                numerator = BigInteger.ONE;
                denominator = BigInteger.ZERO;
                break;
            case "-Infinity":
                numerator = BigInteger.ONE.negate();
                denominator = BigInteger.ZERO;
                break;
            case "NaN":
                numerator = BigInteger.ZERO;
                denominator = BigInteger.ZERO;
                break;
            default:
                throw new IllegalArgumentException("bad keyword: " + keyword);
        }
    }

    public BigRational(int numerator) {
        this((long) numerator);
    }

    public BigRational(int numerator, int denominator) {
        this((long) numerator, (long) denominator);
    }

    public BigRational(long numerator) {
        this(BigInteger.valueOf(numerator));
    }

    public BigRational(long numerator, long denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public BigRational(BigInteger numerator) {
        this(numerator, BigInteger.ONE);
    }

    public BigRational(BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("denominator cannot be zero: " 
                                              + numerator  + "/" + denominator);
        }
        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            denominator = denominator.negate();
            numerator = numerator.negate();
        }
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Returns the numerator of this BigRational
     *  
     * @return numerator of this BigRational
     **/
    public BigInteger numerator() {
        return numerator;
    }
    
    /**
     * Returns the denominator of this BigRational
     *  
     * @return denominator of this BigRational
     **/
    public BigInteger denominator() {
        return denominator;
    }

    /**
     * Returns BigDecimal form of this BigRational
     * 
     * @return decimal (i.e BigDecimal) form of this Raitonal
     **/
    public BigDecimal toBigDecimal() {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator));
    }

    /**
     * Returns a BigRational whos value is this + other
     *
     * @param other BigRational to be added to this BigRational
     * @return this + other
     *  
     **/
    public BigRational add(BigRational other) {
        BigInteger numerator = this.numerator.multiply(other.denominator)
                               .add(this.denominator.multiply(other.numerator));
        BigInteger denominator = this.denominator.multiply(other.denominator);
        return new BigRational(numerator, denominator);
    }
    
    /**
     * Returns a BigRational whos value is this - other
     *
     * @param other BigRational to be subtracted from this BigRational
     * @return this - other
     *  
     **/
    public BigRational subtract(BigRational other) {
        return add(new BigRational(other.numerator.negate(),other.denominator));
    }

    /**
     * Returns a BigRational whos value is this * other
     *
     * @param other BigRational to be multiplied by this BigRational
     * @return this * other
     *  
     **/
    public BigRational multiply(BigRational other) {
        BigInteger numerator = this.numerator.multiply(other.numerator);
        BigInteger denominator = this.denominator.multiply(other.denominator);
        return new BigRational(numerator, denominator);
    }

    /**
     * Returns a BigRational whos value is this / other
     *
     * @param other BigRational by which this BigRational is to be divided
     * @return this / other
     *  
     **/
    public BigRational divide(BigRational other) {
        return multiply(other.reciprocol()); 
    }
    
    /**
     * Returns the recipricol of this BigRational
     *  
     * @return BigRational b/a where a is the numerator and b is the denominator
     *         of this BigRational
     **/
    public BigRational reciprocol() {
        return new BigRational(this.denominator, this.numerator);
    }

     /**
      * Returns a BigRational whose value is this^n
      *
      * @return a BigRational whose value is (a/b)^n where a and b are 
      *         the numerator and denominator of this BigRaitonal
      **/
     public BigRational pow(int n) {
         BigInteger numeratorPow = null; 
         BigInteger denominatorPow = null; 
         if (n < 0) {
             numeratorPow = this.denominator.pow(-n);
             denominatorPow = this.numerator.pow(-n);
         } else {
             numeratorPow = this.numerator.pow(n);
             denominatorPow = this.denominator.pow(n);
         }
         return new BigRational(numeratorPow, denominatorPow);
     }

    /**
     * Returns the BigRational that is represented by the given String
     *
     * @param rational String representation of the BigRational to be returned
     * @return BigRational represented by the given String
     * @throws NumberFormatException if the string does not contain a parsable BigRational 
     **/
    public static BigRational parseBigRational(String rational) throws NumberFormatException {
        String[] parts = rational.trim().split("/") ;
        if (parts.length == 2) {
            BigInteger numerator = BigInteger.valueOf(Long.parseLong(parts[0].trim()));
            BigInteger denominator = BigInteger.valueOf(Long.parseLong(parts[1].trim()));
            return new BigRational(numerator, denominator);
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Translates an in value into a BigRational, this method is equivalent to
     * calling BigRational.valueOf(int, 1).
     *
     * @param numerator int value used as the numerator of a BigRational
     * @return A BigRational whose numerator value is equal to the int value
               provided and whoe denominator value is equal to 1.
     **/
    public static BigRational valueOf(int numerator) {
        return valueOf((long) numerator, 1L);
    }

    /**
     * Transaltes an ordered pair of int values into a BigRational.
     *
     * @param numerator int value used as the numerator of a BigRaitonal
     * @param denominator int value used as the denominator of a BigRaitonal
     * @return A BigRational whose numerator and denominator values are equal
     *         to the given int values
     **/
    public static BigRational valueOf(int numerator, int denominator) {
        return valueOf((long) numerator, (long) denominator);
    }
    
    /**
     * Translates a long value into a BigRational, this method is equivalent to
     * calling BigRational.valueOf(long, 1L).
     *
     * @param numerator long value used as the numerator of a BigRational
     * @return A BigRational whose numerator value is equal to the long value
               provided and whoe denominator value is equal to 1.
     **/
    public static BigRational valueOf(long numerator) {
        return valueOf(numerator, 1L);
    }
    
    /**
     * Transaltes an ordered pair of long values into a BigRational.
     *
     * @param numerator long value used as the numerator of a BigRaitonal
     * @param denominator long value used as the denominator of a BigRaitonal
     * @return A BigRational whose numerator and denominator values are equal
     *         to the given long values
     **/
    public static BigRational valueOf(long numerator, long denominator) {
        return new BigRational(numerator, denominator);
    }
    
    /**
     * Translates a double value into a BigRational
     * 
     * @param val double value to be converted to a BigRational
     * @return A BigRatiional whose value is equal or aproximately equal 
     *         to the value of val.
     **/
    public static BigRational valueOf(double val) {
        String s = Double.toString(val);
        if (s.equals("Infinity") || s.equals("-Infinity") || s.equals("NaN")) {
            throw new IllegalArgumentException("cannot convert double: " + val);
        }
        return valueOf(BigDecimal.valueOf(val));
    }

    /**
     * Translates a BigDecimal value into a BigRational
     * 
     * @param val BigDecimal value to be converted to a BigRational
     * @return A BigRatiional whose value is equal or aproximately equal 
     *         to the value of val.
     **/
    public static BigRational valueOf(BigDecimal val) {
        String s = val.toPlainString();
        String[] parts = s.split("\\.");
        String num = parts[0];
        int exp = 0;
        if (parts.length > 1) {
            exp = parts[1].length();
            num = num.concat(parts[1]);
        }
        return new BigRational(new BigInteger(num), BigInteger.TEN.pow(exp));
    }

    /**
     * Compares two BigRational object numerically
     *
     * @param other BigRational to which this BigRational is to be compared
     * @return int < 0, 0, or > 0 as this BigRational is numerically less than, equal to, or greater than other
     **/
    public int compareTo(BigRational other) {
        return this.numerator.multiply(other.denominator).subtract(this.denominator.multiply(other.numerator)).intValue();
    }

    /**
     * Compares this BigRational with the specified Object for equality
     *
     * @param obj Object to which this BigRational is to be compared
     * @return true if and only if the specified Object is a Raional whose value is numerically equal to this BigRational 
     **/
    public boolean equals(Object obj) {
        if (obj instanceof BigRational) {
            BigRational other = (BigRational) obj;
            return this.compareTo(other) == 0;
        }
        return false;
    }
    
    /**
     * Returns the hash code for this BigRational
     *  
     * @return hash code for this BigRational
     **/
    public int hashCode() {
        int hash = 23;
        hash = (hash * 37) + numerator.hashCode();
        hash = (hash * 37) + denominator.hashCode();
        return hash;
    }

    /**
     * Returns a String representation of this BigRational.
     *  
     * @return "a/b" where a is the numerator and b is the denominator of this
     *         BigRational. If the denominator of this BigRational is equal to 
     *         one, then only the String "a" will be returned where a is the 
     *         numerator of this BigRational.
     **/
    public String toString() {
        String s = "" + this.numerator;
        return (denominator.equals(BigInteger.ONE))? s : s + "/" + this.denominator;
    }
    
    /**
     * returns the fraction syntax in latex of this BigRational
     *  
     * @return "\frac{a}{b}" where a is the numerator and b is the denominator
     *         of this BigRational
     **/
    public String latexToString() {
        return "\\frac{" + this.numerator + "}{" + this.denominator + "}";        
    }
}
