import java.math.BigInteger; 
import java.math.BigDecimal; 
/**
 * Immutable object to represent rational numbers.
 * BigBigRational objects are always in reduced form.
 * BigRational objects always have denominator greater than or equal to 1.
 * negative BigRational objects are always represented with the numerator being negative.
 *
 * @author Brian Bechtel
 * @version 1.1
 */
public class BigRational implements Number<BigRational>, Comparable<BigRational> {
    public static final BigInteger ZERO = BigInteger.valueOf(0);
    public static final BigInteger ONE = BigInteger.valueOf(1);
    private final BigInteger numerator;
    private final BigInteger denominator;
    
    public BigRational(int numerator) {
        this(BigInteger.valueOf(numerator));
    }

    public BigRational(int numerator, int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public BigRational(BigInteger numerator) {
        this(numerator, ONE);
    }

    public BigRational(BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(ZERO)) {
            throw new IllegalArgumentException("denominator cannot be zero: " + numerator  + "/" + denominator);
        }
        if (denominator.compareTo(ZERO) < 0) {
            denominator = denominator.negate();
            numerator = numerator.negate();
        }
        BigInteger gcd = gcd(numerator, denominator);
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
     * @param other BigRational to be added to this BigRational
     *  
     * @return this + other
     *  
     **/
    public BigRational add(BigRational other) {
        BigInteger numerator = this.numerator.multiply(other.denominator).add(this.denominator.multiply(other.numerator));
        BigInteger denominator = this.denominator.multiply(other.denominator);
        return new BigRational(numerator, denominator);
    }
    
    /**
     * Returns a BigRational whos value is this - other
     * @param other BigRational to be subtracted from this BigRational
     *  
     * @return this - other
     *  
     **/
    public BigRational subtract(BigRational other) {
        return add(new BigRational(other.numerator.negate(), other.denominator));
    }

    /**
     * Returns a BigRational whos value is this * other
     * @param other BigRational to be multiplied by this BigRational
     *  
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
     * @param other BigRational by which this BigRational is to be divided
     *  
     * @return this / other
     *  
     **/
    public BigRational divide(BigRational other) {
        return multiply(other.reciprocol()); 
    }
    
    /**
     * Returns the recipricol of this BigRational
     *  
     * @return BigRational b/a where a is the numerator and b is the denominator of this BigRational
     **/
    public BigRational reciprocol() {
        return new BigRational(this.denominator, this.numerator);
    }

    /**
     * Returns the BigRational that is represented by the given String
     * @param rational String representation of the BigRational to be returned
     *  
     * @return BigRational represented by the given String
     *  
     * @throws NumberFormatException - if the string does not contain a parsable BigRational 
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
     * Compares two BigRational object numerically
     * @param other BigRational to which this BigRational is to be compared
     *  
     * @return BigInteger < 0, 0, or BigInteger > 0 as this BigRational is numerically less than, equal to, or greater than other
     **/
    public int compareTo(BigRational other) {
        return this.numerator.multiply(other.denominator).subtract(this.denominator.multiply(other.numerator)).intValue();
    }

    /**
     * Compares this BigRational with the specified Object for equality
     * @param obj Object to which this BigRational is to be compared
     *  
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
     * @return "a/b" where a is the numerator and b is the denominator of this BigRational
     **/
    public String toString() {
        String s = "" + this.numerator;
        return (denominator.equals(ONE))? s : s + "/" + this.denominator;
    }
    
    /**
     * returns the fraction syntax in latex of this BigRational
     *  
     * @return "\frac{a}{b}" where a is the numerator and b is the denominator of this BigRational
     **/
    public String latexToString() {
        return "\\frac{" + this.numerator + "}{" + this.denominator + "}";        
    }
    
    private BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(ZERO)) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }
}
