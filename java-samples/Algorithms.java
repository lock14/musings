

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class CodeAssessmentCommon {

    public static void main(String[] args) {
        testQuickSelect();
    }

    ///////////////////////////////////////////////////////////////////////////
    // BINARY SEARCH
    ///////////////////////////////////////////////////////////////////////////

    // standard, returns index of target if present, elese -1
    public static int binarySearch(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            // avoid overflow using (x + y) / 2 = y + ((x - y) / 2)
            int mid = low + ((high - low) / 2);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                low = mid  + 1;
            } else {
                // nums[mid] > target
                high = mid - 1;
            }
        }
        return -1;
    }

    // >= variant, returns index of least element >= target if present, else -1
    public static int binarySearchCeiling(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int ceil = nums.length;
        while (low <= high) {
            // avoid overflow using (x + y) / 2 = y + ((x - y) / 2)
            int mid = low + ((high - low) / 2);
            if (nums[mid] >= target) {
                ceil = mid;
                high = mid - 1;
            } else {
                // nums[mid] < target
                low = mid + 1;
            }
        }
        return ceil;
    }

    // <= variant, returns index of greatest element <= target if present, else -1
    public static int binarySearchFloor(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int floor = -1;
        while (low <= high) {
            // avoid overflow using (x + y) / 2 = y + ((x - y) / 2)
            int mid = low + ((high - low) / 2);
            if (nums[mid] <= target) {
                floor = mid;
                low = mid + 1;
            } else {
                // nums[mid] > target
                high = mid - 1;
            }
        }
        return floor;
    }

    // < variant, returns index of greatest element < target if present, else -1
    public static int binarySearchLower(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            // avoid overflow using (x + y) / 2 = y + ((x - y) / 2)
            int mid = low + ((high - low) / 2);
            if (nums[mid] < target) {
                low = mid + 1;
            } else {
                // nums[mid] >= target
                high = mid - 1;
            }
        }
        return high;
    }

    // > variant, returns index of least element > target if present, else -1
    public static int binarySearchHigher(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            // avoid overflow using (x + y) / 2 = y + ((x - y) / 2)
            int mid = low + ((high - low) / 2);
            if (nums[mid] > target) {
                high = mid - 1;
            } else {
                // nums[mid] <= target
                low = mid + 1;
            }
        }
        return low;
    }

    ///////////////////////////////////////////////////////////////////////////
    // QUICK SORT
    ///////////////////////////////////////////////////////////////////////////

    public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }
    
    public static void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int pivotIndex = randomPartition(a, low, high);
            quickSort(a, low, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, high);
        }
    }

    public static int randomPartition(int[] a, int low, int high) {
        Random r = new Random();
        int index = low + r.nextInt(high - low + 1);
        swap(a, index, high);
        return partition(a, low, high);
    }

    public static int partition(int[] a, int low, int high) {
        int pivot = a[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (a[j] < pivot) {
                swap(a, i++, j);
            }
        }
        swap(a, i, high);
        return i;
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    ///////////////////////////////////////////////////////////////////////////
    // QUICK SELECT
    ///////////////////////////////////////////////////////////////////////////

    public static int quickSelect(int[] a, int k) {
        return quickSelect(a, k, 0, a.length - 1);
    }

    // k: the kth min we want to choose
    public static int quickSelect(int[] a, int k, int low, int high) {
        int pivotIndex = randomPartition(a, low, high);
        if (pivotIndex < k - 1) {
            return quickSelect(a, k, pivotIndex + 1, high);
        } else if (pivotIndex > k - 1) {
            return quickSelect(a, k, low, pivotIndex - 1);
        } else {
            return pivotIndex;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MINIMUM SPANNING TREE
    ///////////////////////////////////////////////////////////////////////////

    // O(|E|log|V|)
    public static Map<Integer, Map<Integer, Integer>> prims(Map<Integer, Map<Integer, Integer>> graph, int start) {
        // result tree
        Map<Integer, Map<Integer, Integer>> tree = new HashMap<>();
        if (graph.containsKey(start)) {
            // set up auxiliary data structures
            Queue<Map.Entry<Integer, Map.Entry<Integer, Integer>>> queue = new PriorityQueue<>(Comparator.comparing(e -> e.getValue().getValue()));
            Set<Integer> visited = new HashSet<>();
            // add start to tree
            tree.put(start, new HashMap<>());
            // mark start as visited
            visited.add(start);
            // add all adjacent edges of start to queue
            graph.get(start)
                 .entrySet()
                 .stream()
                 .map(e -> Map.entry(start, e))
                 .forEach(queue::add);
            while (!queue.isEmpty()) {
                Map.Entry<Integer, Map.Entry<Integer, Integer>> edge = queue.remove();
                // extract components of edge {u,v}:w
                Integer u = edge.getKey();
                Integer v = edge.getValue().getKey();
                Integer weight = edge.getValue().getValue();
                if (!visited.contains(v)) {
                    // mark v as visited
                    visited.add(v);
                    // add edge to tree
                    tree.put(v, new HashMap<>());
                    tree.get(u).put(v, weight);
                    tree.get(v).put(u, weight);
                    // add all adjacent edges of v to queue
                    graph.get(v)
                         .entrySet()
                         .stream()
                         .map(e -> Map.entry(v, e))
                         .forEach(queue::add);
                }
            }
        }
        return tree;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Shortest Path
    ///////////////////////////////////////////////////////////////////////////

    // O(|E|log|V|)
    public static List<Integer> dijkstra(Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
        // set up auxilliary data structures
        Map<Integer, Integer> predecessors = new HashMap<>();
        Map<Integer, Integer> distances = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Map.Entry<Integer, Integer>> fringe = new PriorityQueue<>(Comparator.comparing(Map.Entry::getValue));
        // set distance to start as zero
        distances.put(start, 0);
        // add start to the fringe
        fringe.add(Map.entry(start, 0));
        while (!fringe.isEmpty()) {
            Map.Entry<Integer, Integer> entry =  fringe.remove();
            int u = entry.getKey();
            if (u == end) {
                break;
            }
            if (!visited.contains(u)) {
                // mark u as visited
                visited.add(u);
                // existing distance to u
                int uDistance = entry.getValue();
                for (Map.Entry<Integer, Integer> edge : graph.get(u).entrySet()) {
                    int v = edge.getKey();
                    int weight = edge.getValue();
                    // existing distance to v
                    int vDistance = distances.getOrDefault(v, Integer.MAX_VALUE);
                    // distance to v by going through u
                    int vNewDistance = uDistance + weight;
                    if (vNewDistance < vDistance) {
                        // update target distance
                        distances.put(v, vNewDistance);
                        predecessors.put(v, u);
                        fringe.add(Map.entry(v, vNewDistance));
                    }
                }
            }
        }
        Deque<Integer> stack = new ArrayDeque<>();
        Integer cur = end;
        while (cur != null) {
            stack.push(end);
            cur = predecessors.get(cur);
        }
        return new ArrayList<>(stack);
    }

    ///////////////////////////////////////////////////////////////////////////
    // LEVENSHTEIN DISTANCE
    ///////////////////////////////////////////////////////////////////////////
    public static int levensteinDistance(String a, String b) {
        int[][] matrix = new int[a.length() + 1][b.length() + 1];

        for (int i = 1; i <= a.length(); i++) {
            matrix[i][0] = i;
        }
        for (int j = 1; j <= b.length(); j++) {
            matrix[0][j] = j;
        }
        for (int j = 1; j <= b.length(); j++) {
            for (int i = 1; i <= a.length(); i++) {
                int substitutionCost = 0;
                if (a.charAt(i - 1) != b.charAt(j - 1)) {
                    substitutionCost = 1;
                }
                matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1), matrix[i - 1][j - 1] + substitutionCost);
            }
        }
        return matrix[a.length()][b.length()];
    }

    ///////////////////////////////////////////////////////////////////////////
    // Tests
    ///////////////////////////////////////////////////////////////////////////

    public static void testBinarySearchAndVariants() {
        testSearch(100);
        testFloor(100);
        testCeiling(100);
        testLower(100);
        testHigher(100);
    }

    // test relies on the correctness of java.util.TreeSet
    public static void testSearch(int numberOfTests) {
        Random r = new Random();
        for (int test = 0; test < numberOfTests; test++) {
            int limit = r.nextInt(1001);
            TreeSet<Integer> treeSet = new TreeSet<>();
            IntStream.generate(() -> r.nextInt())
                     .limit(limit)
                     .forEach(treeSet::add);
            int[] a = new int[treeSet.size()];
            int i = 0;
            for (int n : treeSet) {
                a[i++] = n;
            }

            for (int j = 0; j < limit; j++) {
                int n = r.nextInt();
                Boolean found = treeSet.contains(n);
                int index = binarySearch(a, n);
                if (found && (index == -1 || a[index] != n)) {
                    System.out.println(n);
                    System.out.println(treeSet);
                    System.out.println(Arrays.toString(a));
                    throw new RuntimeException(String.format("treeSet retured %b, but binarySearch returned %d", found, index));
                } else if (!found && index != -1) {
                    System.out.println(n);
                    System.out.println(treeSet);
                    System.out.println(Arrays.toString(a));
                    throw new RuntimeException(String.format("treeSet retured %b, but binarySearch returned %d", found, index));
                }
            }
        }
        System.out.println("binarySearch passed all tests");
    }

    // test relies on the correctness of java.util.TreeSet
    public static void testFloor(int numberOfTests) {
        Random r = new Random();
        for (int test = 0; test < numberOfTests; test++) {
            int limit = r.nextInt(1001);
            TreeSet<Integer> treeSet = new TreeSet<>();
            IntStream.generate(() -> r.nextInt())
                     .limit(limit)
                     .forEach(treeSet::add);
            int[] a = new int[treeSet.size()];
            int i = 0;
            for (int n : treeSet) {
                a[i++] = n;
            }

            for (int j = 0; j < limit; j++) {
                int n = r.nextInt();
                Integer val = treeSet.floor(n);
                int index = binarySearchFloor(a, n);
                if (val == null) {
                    if (index != -1) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchFloor returned %d", val, index));
                    }
                } else {
                    if (index == -1 || val.intValue() != a[index]) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchFloor returned %d", val, index));
                    }
                }
            }
        }
        System.out.println("binarySearchFloor passed all tests");
    }

    // test relies on the correctness of java.util.TreeSet
    public static void testCeiling(int numberOfTests) {
        Random r = new Random();
        for (int test = 0; test < numberOfTests; test++) {
            int limit = r.nextInt(1001);
            TreeSet<Integer> treeSet = new TreeSet<>();
            IntStream.generate(() -> r.nextInt())
                     .limit(limit)
                     .forEach(treeSet::add);
            int[] a = new int[treeSet.size()];
            int i = 0;
            for (int n : treeSet) {
                a[i++] = n;
            }

            for (int j = 0; j < limit; j++) {
                int n = r.nextInt();
                Integer val = treeSet.ceiling(n);
                int index = binarySearchCeiling(a, n);
                if (val == null) {
                    if (index != a.length) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchCeiling returned %d", val, index));
                    }
                } else {
                    if (index == a.length || val.intValue() != a[index]) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchCeiling returned %d", val, index));
                    }
                }
            }
        }
        System.out.println("binarySearchCeiling passed all tests");
    }

    // test relies on the correctness of java.util.TreeSet
    public static void testLower(int numberOfTests) {
        Random r = new Random();
        for (int test = 0; test < numberOfTests; test++) {
            int limit = r.nextInt(1001);
            TreeSet<Integer> treeSet = new TreeSet<>();
            IntStream.generate(() -> r.nextInt())
                     .limit(limit)
                     .forEach(treeSet::add);
            int[] a = new int[treeSet.size()];
            int i = 0;
            for (int n : treeSet) {
                a[i++] = n;
            }

            for (int j = 0; j < limit; j++) {
                int n = r.nextInt();
                Integer val = treeSet.lower(n);
                int index = binarySearchLower(a, n);
                if (val == null) {
                    if (index != -1) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchLower returned %d", val, index));
                    }
                } else {
                    if (index == -1 || val.intValue() != a[index]) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchLower returned %d", val, index));
                    }
                }
            }
        }
        System.out.println("binarySearchLower passed all tests");
    }

    public static void testHigher(int numberOfTests) {
        Random r = new Random();
        for (int test = 0; test < numberOfTests; test++) {
            int limit = r.nextInt(1001);
            TreeSet<Integer> treeSet = new TreeSet<>();
            IntStream.generate(() -> r.nextInt())
                     .limit(limit)
                     .forEach(treeSet::add);
            int[] a = new int[treeSet.size()];
            int i = 0;
            for (int n : treeSet) {
                a[i++] = n;
            }

            for (int j = 0; j < limit; j++) {
                int n = r.nextInt();
                Integer val = treeSet.higher(n);
                int index = binarySearchHigher(a, n);
                if (val == null) {
                    if (index != a.length) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchHigher returned %d", val, index));
                    }
                } else {
                    if (index == a.length || val.intValue() != a[index]) {
                        System.out.println(n);
                        System.out.println(treeSet);
                        System.out.println(Arrays.toString(a));
                        throw new RuntimeException(String.format("treeSet retured %d, but binarySearchHigher returned %d", val, index));
                    }
                }
            }
        }
        System.out.println("binarySearchHigher passed all tests");
    }

    private static void testQuickSelect() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int[] a = IntStream.generate(random::nextInt)
                               .limit(random.nextInt(100) + 1)
                               .toArray();
            int k = random.nextInt(a.length) + 1;
            int[] a2 = a.clone();
            int index = quickSelect(a, k);
            Arrays.sort(a2);
            if (a[index] != a2[k - 1]) {
                throw new RuntimeException();
            }
        }
        System.out.println("PASSED");
    }
}
