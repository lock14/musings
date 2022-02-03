import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * LFUCache
 */
public class LFUCache<K, V> {
    private Map<Integer, Integer> cache;
    private Map<Integer, Integer> counts;
    private Map<Integer, Set<Integer>> lists;
    private int capacity;
    private int min;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.counts = new HashMap<>();
        this.lists = new HashMap<>();
        this.min = 1;
    }
    
    public int get(int key) {
        if (cache.containsKey(key)) {
            int count = counts.get(key);
            counts.put(key, count + 1);
            lists.computeIfAbsent(count + 1, k -> new LinkedHashSet<>()).add(key);
            lists.get(count).remove(key);
            if (count == min && lists.get(min).size() == 0) {
                min++;
            }
            return cache.get(key);
        }
        return -1;
    }
    
    public void put(int key, int val) {
        if (capacity > 0 ) {
            if (!cache.containsKey(key) && cache.size() == capacity) {
                removeLeastUsed();
            }
            insert(key, val);
        }
    }
    
    private void insert(int key, int val) {
        int count = counts.getOrDefault(key, 0);
        counts.put(key, count + 1);
        lists.computeIfAbsent(count + 1, k -> new LinkedHashSet<>()).add(key);
        if (cache.put(key, val) != null) {
            lists.get(count).remove(key);
            if (count == min && lists.get(min).size() == 0) {
                min++;
            }
        } else {
            min = 1;
        }
    }
    
    private void removeLeastUsed() {
        int key = lists.get(min).iterator().next();
        lists.get(min).remove(key);
        cache.remove(key);
        counts.remove(key);
    }
}