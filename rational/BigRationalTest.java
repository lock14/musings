public class BigRationalTest {
    public static void main(String[] args) {
        BigRational b = BigRational.valueOf(2.01);
        System.out.println(b);
        BigRational a = BigRational.valueOf(0.01);
        System.out.println(a);
        BigRational c = BigRational.valueOf(Math.PI);
        System.out.println(c);
        BigRational d = BigRational.valueOf(Math.E);
        System.out.println(d);
        BigRational e = BigRational.valueOf(3.0);
        System.out.println(e);
        BigRational f = BigRational.valueOf(0.25);
        System.out.println(f);
        BigRational g = BigRational.valueOf(0.001);
        System.out.println(g);
        BigRational h = BigRational.valueOf(0.0001);
        System.out.println(h);
        BigRational i = BigRational.valueOf(123456789123456789.0);
        System.out.println(i);
    }
}
