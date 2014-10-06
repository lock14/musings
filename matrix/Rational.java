public class Rational implements Comparable<Rational> {
    public static final int IDENTITY_DENOMINATOR = 1;
	private final int numerator;
	private final int denominator;
    
    public Rational(String rational) throws NumberFormatException {
        this(Integer.parseInt(rational.trim().substring(0, 1)), 
             Integer.parseInt(rational.trim().substring(rational.length() - 1, rational.length())));
    }
        
    public Rational(int numerator) {
        this(numerator, IDENTITY_DENOMINATOR);
    }

    public Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator cannot be zero: " + denominator);
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
        int thisMultiple = 1;
        int otherMultiple = 1;
        int gcdDenom = this.denominator;
        if (this.denominator != other.denominator) {
            gcdDenom = gcd(this.denominator, other.denominator);
            thisMultiple = gcdDenom / this.denominator;
            otherMultiple = gcdDenom / other.denominator;
        }
        return new Rational((this.numerator * thisMultiple) + (other.numerator * otherMultiple), this.denominator);
    }
    
    /**
     * Returns a Rational whos value is this - other
	 * @param other Rational to be subtracted from this Rational
     *  
	 * @return this - other
     **/
    public Rational subtract(Rational other) {
        int thisMultiple = 1;
        int otherMultiple = 1;
        int gcdDenom = this.denominator;
        if (this.denominator != other.denominator) {
            gcdDenom = gcd(this.denominator, other.denominator);
            thisMultiple = gcdDenom / this.denominator;
            otherMultiple = gcdDenom / other.denominator;
        }
        return new Rational((this.numerator * thisMultiple) - (other.numerator * otherMultiple), this.denominator);
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
     * Compares this Rational with the Specified Object for equality
	 * @param other Rational to which this Rational is to be compared
     *  
	 * @return int < 0, 0, or int > 0 as this Rational is numerically less than, equal to, or greater than other
     **/
    public int compareTo(Rational other) {
        if (this.denominator == other.denominator) {
            return this.numerator - other.numerator;
        } else {
            return -1;
        }
    }

    /**
     * Returns a String representation of this Rational.
     *  
	 * @return "a / b" where a is the numerator and b is the denominator of this Rational
     **/
    public String toString() {
        return "" + this.numerator + " / " + this.denominator;
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
