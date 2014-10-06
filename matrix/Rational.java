public class Rational implements Comparable<Rational> {
    public static final int IDENTITY_DENOMINATOR = 1;
    private final int numerator;
    private final int denominator;
    
    public Rational(String rational) throws NumberFormatException {
        this(Integer.parseInt(rational.trim().split(" ")[0]), 
             Integer.parseInt(rational.trim().split(" ")[rational.trim().split(" ").length -1]));
    }
        
    public Rational(int numerator) {
        this(numerator, IDENTITY_DENOMINATOR);
    }

    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator cannot be zero: " + numerator  + " / " + denominator);
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
     * Returns a Rational whos value is this + other
	 * @param other Rational to be added to this Rational
     *  
	 * @return this + other
     **/
    public Rational add(Rational other) {
        int num = (this.numerator * other.denominator) + (this.denominator * other.numerator);
        int denom = this.denominator * other.denominator;
        return new Rational(num, denom);
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
        return new Rational(this.numerator * other.numerator, this.denominator * other.denominator);
    }

    /**
     * Returns a Rational whos value is this / other
	 * @param other Rational by which this Rational is to be divided
     *  
	 * @return this / other
     **/
    public Rational divide(Rational other) {
        return new Rational(this.numerator * other.denominator, this.denominator * other.numerator);
    }
    
    /**
     * Returns the recipricol of this Rational
     *  
	 * @return Rational b / a where a is the numerator and b is the denominator of this Rational
     **/
    public Rational reciprocal() {
        return new Rational(this.denominator, this.numerator);
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
     * returns the fraction syntax in latex of this Raional
     *  
	 * @return "\frac{a}{b}" where a is the numerator and b is the denominator of this Rational
     **/
    public String latexToString() {
        return "\\frac{" + this.numerator + "}{" + this.denominator + "}";        
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
