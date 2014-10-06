import java.util.Set;
import java.util.TreeSet;
public class RationalTest {
    public static void main(String[] args) {
        String[] rationals = {"42 / 21", "3 / 4", "124 / 5", "12 / 60"};
        Set<Rational> set = new TreeSet<Rational>();
        for (int i = 0; i < rationals.length; i++) {
            System.out.println("adding rational " + rationals[i] + " to set");
            Rational r = new Rational(rationals[i]);
            set.add(r);
            System.out.println("added rational " + r + " to set");
        }
        System.out.println(set);
    }
}
