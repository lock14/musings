import java.util.Random;

public class StackTest {
    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<Integer>();
        java.util.Stack<Integer> stack2 = new java.util.Stack<Integer>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int n = r.nextInt(20) + 1;
            stack.push(n);
            stack2.push(n);
        }
        System.out.println(stack);
        System.out.println(stack2);
        System.out.println(stack.pop());
        System.out.println(stack2.pop());
        System.out.println(stack);
        System.out.println(stack2);
    }
}
