import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MergeKSorted {
    public ListNode mergeKLists(ListNode[] lists) {
        Queue<ListNode> queue = new PriorityQueue<>(Comparator.comparing(n -> n.val));
        for (ListNode list : lists) {
            if (list != null) {
                queue.add(list);   
            }
        }
        ListNode head = null;
        if (!queue.isEmpty()) {
            head = queue.remove();
            if (head.next != null) {
                queue.add(head.next);
            }
        }
        ListNode cur = head;
        while (!queue.isEmpty()) {
            ListNode node = queue.remove();
            if (node.next != null) {
                queue.add(node.next);
            }
            cur.next = node;
            cur = cur.next;
        }
        if (cur != null) {
            cur.next = null;
        }
        return head;
    }


    public int[] mergeKArrays(int[][] arrays) { 
        Queue<ArrayVal> queue = new PriorityQueue<>((av1, av2) -> av1.a[av1.index] - av2.a[av2.index]);
        int length = 0;
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                queue.add(new ArrayVal(arrays[i], 0));
            }
            length += arrays[i].length;
        }
        int[] merged = new int[length];
        int i = 0;
        while (!queue.isEmpty()) {
            ArrayVal arrayVal = queue.remove();
            merged[i++] = arrayVal.a[arrayVal.index];
            if (arrayVal.index < arrayVal.a.length - 1) {
                queue.add(new ArrayVal(arrayVal.a, arrayVal.index + 1));
            }
        }
        return merged;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    static record ArrayVal(int[] a, int index) {}
}
