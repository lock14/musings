import java.util.Random;

public class QueueTest {
    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayQueue<Integer>();
        java.util.Queue<Integer> queue2 = new java.util.LinkedList<Integer>();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int n = r.nextInt(20) + 1;
            queue.add(n);
            queue2.add(n);
        }
        System.out.println(queue);
        System.out.println(queue2);
        System.out.println(queue.remove());
        System.out.println(queue2.remove());
        System.out.println(queue);
        System.out.println(queue2);
    }
}
