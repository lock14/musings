/**
 * Immutable object to represent rational numbers.
 * Rational objects are always in reduced form.
 * Rational objects always have denominator greater than 1.
 * negative Rational objects are always represented with the numerator being negative.
 * Rationals are protected from integer overflow and will throw an IllegalStateException if it occurs
 *
 * @author Brian Bechtel
 * @version 1.0
 */
public class Rational implements Comparable<Rational> {
    public static final int IDENTITY_DENOMINATOR = 1;
    private final int numerator;
    private final int denominator;
    
    public Rational(int numerator) {
        this(numerator, IDENTITY_DENOMINATOR);
    }

    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator cannot be zero: " + numerator  + "/" + denominator);
        }
        if (denominator < 0) {
            denominator = -denominator;
            numerator = -numerator;
        }
        if (numerator % denominator == 0) {
            numerator /= denominator;
            denominator /= denominator;
        } else if (denominator % numerator == 0) {
            denominator /= numerator;
            numerator /= numerator;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    /**
     * Returns the numerator of this Rational
     *  
     * @return numerator of this Rational
     **/
    public int numerator() {
        return numerator;
    }
    
    /**
     * Returns the denominator of this Rational
     *  
     * @return denominator of this Rational
     **/
    public int denominator() {
        return denominator;
    }

    /**
     * Returns double form of this Rational
     * 
     * @return decimal (i.e double) form of this Raitonal
     **/
    public double toDouble() {
        return this.numerator / denominator;
    }

    /**
     * Returns a Rational whos value is this + other
     * @param other Rational to be added to this Rational
     *  
     * @return this + other
     **/
    public Rational add(Rational other) {
        long numerator = (long) (this.numerator * other.denominator) + (this.denominator * other.numerator);
        long denominator = (long) this.denominator * other.denominator;
        checkOverflow(numerator);
        checkOverflow(denominator);
        return new Rational((int) numerator, (int) denominator);
    }
    
    /**
     * Returns a Rational whos value is this - other
     * @param other Rational to be subtracted from this Rational
     *  
     * @return this - other
     **/
    public Rational subtract(Rational other) {
        return add(new Rational(-other.numerator, other.denominator));
    }

    /**
     * Returns a Rational whos value is this * other
     * @param other Rational to be multiplied by this Rational
     *  
     * @return this * other
     **/
    public Rational multiply(Rational other) {
        long numerator = (long) this.numerator * other.numerator;
        long denominator = (long) this.denominator * other.denominator;
        checkOverflow(numerator);
        checkOverflow(denominator);
        return new Rational((int) numerator, (int) denominator);
    }

    /**
     * Returns a Rational whos value is this / other
     * @param other Rational by which this Rational is to be divided
     *  
     * @return this / other
     **/
    public Rational divide(Rational other) {
        return multiply(other.reciprocol()); 
    }
    
    /**
     * Returns the recipricol of this Rational
     *  
     * @return Rational b / a where a is the numerator and b is the denominator of this Rational
     **/
    public Rational reciprocol() {
        return new Rational(this.denominator, this.numerator);
    }

    /**
     * Returns the Rational that is represented by the given String
     * @param rational String representation of the Rational to be returned
     *  
     * @return Rational represented by the given String
     **/
    public static Rational parseRational(String rational) throws NumberFormatException {
        String[] parts = rational.trim().split("/") ;
        if (parts.length == 2) {
            int numerator = Integer.parseInt(parts[0].trim());
            int denominator = Integer.parseInt(parts[1].trim());
            return new Rational(numerator, denominator);
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     * Compares two Rational object numerically
     * @param other Rational to which this Rational is to be compared
     *  
     * @return int < 0, 0, or int > 0 as this Rational is numerically less than, equal to, or greater than other
     **/
    public int compareTo(Rational other) {
        return (this.numerator * other.denominator) - (this.denominator * other.numerator);
    }

    /**
     * Compares this Rational with the specified Object for equality
     * @param obj Object to which this Rational is to be compared
     *  
     * @return true if and only if the specified Object is a Raional whose value is numerically equal to this BigInteger
     **/
    public boolean equals(Object obj) {
        if (obj instanceof Rational) {
            Rational other = (Rational) obj;
            return this.compareTo(other) == 0;
        }
        return false;
    }
    
    /**
     * Returns the hash code for this Rational
     *  
     * @return hash code for this Rational
     **/
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Returns a String representation of this Rational.
     *  
     * @return "a / b" where a is the numerator and b is the denominator of this Rational
     **/
    public String toString() {
        String s = "" + this.numerator;
        return (denominator == 1)? s : s + "/" + this.denominator;
    }
    
    /**
     * returns the fraction syntax in latex of this Rational
     *  
     * @return "\frac{a}{b}" where a is the numerator and b is the denominator of this Rational
     **/
    public String latexToString() {
        return "\\frac{" + this.numerator + "}{" + this.denominator + "}";        
    }
    
    private void checkOverflow(long value) {
        if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
            throw new IllegalStateException("int overflow has occured");
        }
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
