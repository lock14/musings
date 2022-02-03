public class KnapSack {
    
    public static void main(String[] args) {
        testZeroOneKnapSack();
        testUnboundedKnapSack();
    }

    public static void testZeroOneKnapSack() {
        int[] values = {60, 100, 120};
        int[] weights = {10, 20, 30};
        int capacity = 50;
        // correct answer is 220
        System.out.println(zeroOneKnapSack(values, weights, capacity));
    }

    public static void testUnboundedKnapSack() {
        int[] values = {10, 40, 50, 70};
        int[] weights = {1, 3, 4, 5};
        int capacity = 8;
        // correct answer is 110
        System.out.println(unboundedKnapSack(values, weights, capacity));
    }

    /**
     * Zero/One Knapsack problem
     * State: Given a set of n items with values v_i and weights w_i and a capacity W,
     *        find the maximum value of items where the sum of the items weights is <= W.
     *        Each item can be chosen at most once.
     * 
     * Idea: For each item, consider all possible capacities between 0 and W (inclusive).
     *       when considering the ith item and capacity w we have two situations:
     *       1. if weight[i] > w, then we cannot choose the ith item, thus the
     *          optimal value is the same as value for (i -1)th item and w
     *       2. if weight[i] <= w, then we can potentially take the item. thus
     *          we have to consider both choices. The optimal choice is the maximum of:
     *          a. taking the ith item: value[i] + value we can get for (i - 1)th item with capacity w - weight[i]
     *          b. not taking the ith item: value we can get for (i - 1)th item with capacity w
     */
    public static int zeroOneKnapSack(int[] values, int[] weights, int capacity) {
        int[][] table = new int[values.length + 1][capacity + 1];
        for (int item = 1; item <= values.length; item++) {
            for (int w = 0; w <= capacity; w++) {
                if (weights[item - 1] > w) {
                    table[item][w] = table[item - 1][w];
                } else {
                    table[item][w] = Math.max(table[item - 1][w], table[item - 1][w - weights[item - 1]] + values[item - 1]);
                }
            }
        }
        return table[values.length][capacity];
    }

    /**
     * Unbounded Knapsack problem
     * State: Given a set of n items with values v_i and weights w_i and a capacity W,
     *        find the maximum value of items where the sum of the items weights is <= W.
     *        Each item can be chosen an unlimited number of times.
     * 
     * Idea: Consider all possible capacities between 0 and W (inclusive).
     *       For each capacity w consider each item.
     *       2. if weight[item] <= w, then we can potentially take the item or not. thus
     *          we have to consider both choices. The optimal choice is the maximum of:
     *          a. taking the ith item: value[i] + value we can get for capacity w - weight[i]
     *          b. not taking the ith item: value we can get for capacity w
     */
    public static int unboundedKnapSack(int[] values, int[] weights, int capacity) {
        int[] table = new int[capacity + 1];
        for (int w = 0; w <= capacity; w++) {
            for (int item = 0; item < values.length; item++) {
                if (weights[item] <= w) {
                    table[w] = Math.max(table[w], table[w - weights[item]] + values[item]);
                }
            }
        }
        return table[capacity];
    }
}
