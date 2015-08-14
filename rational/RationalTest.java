import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
public class RationalTest {
    public static void main(String[] args) {
        String[] rationals = {"42/21", "3/4", "124/5", "12/60"};
        Set<Rational> set = new TreeSet<Rational>();
        for (int i = 0; i < rationals.length; i++) {
            System.out.println("adding rational " + rationals[i] + " to set");
            Rational r = Rational.parseRational(rationals[i]);
            set.add(r);
            System.out.println("added rational " + r + " to set");
        }
        System.out.println(set);
        Rational r1 = new Rational(1, 2);
        Rational r2 = null;
        System.out.println(r1.equals(r2));
        List<Rational> list = new ArrayList<Rational>();
        for (int i = 0; i < rationals.length; i++) {
            list.add(Rational.parseRational(rationals[i]));
        }
        System.out.println("list: " + list);
        int size = list.size();
        for (int i = 0; i < size - 1; i ++) {
            System.out.println(list.get(i) +  " + " + list.get(i + 1));
            list.add(list.get(i).add(list.get(i + 1)));
        }
        System.out.println("list: " + list);
    }
}
