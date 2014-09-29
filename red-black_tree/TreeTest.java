import java.util.Random;
import java.util.TreeMap;

public class TreeTest {
    public static void main(String[] args) {
        Random r = new Random();
        RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
        //TreeMap<Integer, Integer> tree2 = new TreeMap<Integer, Integer>();
        for (int i = 0; i < 10; i++) {
            tree.insert(r.nextInt(20) + 1);
        }/*
        for (int i = 0; i < 10; i++) {
            tree2.put(r.nextInt(20) + 1, r.nextInt(20) + 1);
        }*/
        System.out.println(tree);
        //System.out.println(tree2);
    }
}
