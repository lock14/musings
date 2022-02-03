import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

class LRUCache<K, V> {
    private final Map<K, Node<K, V>> map;
    private final Node<K, V> list;
    private int capacity;

    public LRUCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.map = new HashMap<>();
        this.list = Node.sentinel();
        this.capacity = capacity;
    }

    public Optional<V> get(K key) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            unlink(node);
            addToFront(node);
            return Optional.ofNullable(node.value);
        }
        return Optional.empty();
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            unlink(node);
            addToFront(node);
            node.value = value;
        } else {
            if (map.size() == capacity) {
                map.remove(list.prev.key);
                unlink(list.prev);
            }
            Node<K, V> node = new Node<>(key, value);
            map.put(key, node);
            addToFront(node);
        }
    }

    @Override
    public String toString() {
        return map.toString();
    }

    private void unlink(Node<K,V> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        node.prev = null;
        node.next = null;
    }

    private void addToFront(Node<K, V> node) {
        node.prev = list;
        node.next = list.next;
        list.next.prev = node;
        list.next = node;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K,V> prev;
        Node<K,V> next;

        Node(K key, V value) {
            this(key, value, null, null);
        }

        Node(K key, V value, Node<K,V> prev, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        static <K, V> Node<K, V> sentinel() {
            Node<K, V> node = new Node<K,V>(null, null);
            node.next = node;
            node.prev = node;
            return node;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        cache.get(1).ifPresent(System.out::println);
        System.out.println(cache);

        System.out.println("LRU Test:");
        int maxSize = 2;
        Map<Integer, Integer> map = new LinkedHashMap<>(((maxSize * 4) / 3) + 1, 0.75f, true) {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > maxSize;
            }
        };
        map.put(1, 1);
        System.out.println(map);
        map.put(2, 2);
        System.out.println(map);
        System.out.println(Optional.ofNullable(map.get(1)).orElse(-1));
        map.put(3, 3);
        System.out.println(map);
        System.out.println(Optional.ofNullable(map.get(2)).orElse(-1));
        map.put(4, 4);
        System.out.println(map);
        System.out.println(Optional.ofNullable(map.get(1)).orElse(-1));
        System.out.println(Optional.ofNullable(map.get(3)).orElse(-1));
        System.out.println(Optional.ofNullable(map.get(4)).orElse(-1));
    }
}