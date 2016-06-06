public class HashTable<K, V> {
    public static final int DEFAULT_SIZE = 100;
    private static final double MAX_LOAD_FACTOR = 0.7;
    private HashNode[] hashTable;
    private int size;
    
    public HashTable() {
        this(DEFAULT_SIZE);
    }

    public HashTable(int size) {
        hashTable = new HashNode[size];
    }
    
    @SuppressWarnings("unchecked")    
    public void put(K key, V value) {
        if (load_factor() > MAX_LOAD_FACTOR) {
            resize();
        }
        int hash = fixHash(key.hashCode());
        HashNode<K, V> newNode = new HashNode<K, V>(key, value, hashTable[hash]);
        hashTable[hash] = newNode;
        size++;
    }
    
    @SuppressWarnings("unchecked")    
    public V get(K key) {
        if (key != null){
            int hash = fixHash(key.hashCode());
            HashNode<K, V> current = hashTable[hash];
            while (current != null) {
                if (key.equals(current.key)) {
                    return current.value;
                }
                current = current.next;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    @SuppressWarnings("unchecked")    
    public boolean containsValue(V value) {
        for (int i = 0; i < hashTable.length; i++) {
            HashNode<K, V> current = hashTable[i];
            while (current != null) {
                if (value.equals(current.value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int fixHash(int hash) {
        hash %= hashTable.length;
        if (hash < 0) {
            hash += hashTable.length;
        }
        return hash;
    }

    @SuppressWarnings("unchecked")    
    private void resize() {
        HashNode[] newHashTable = new HashNode[hashTable.length + (hashTable.length / 2)];
        HashNode[] oldHashTable = hashTable;
        hashTable = newHashTable;
        for (int i = 0; i < oldHashTable.length; i++) {
            HashNode<K, V> current = oldHashTable[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }
    
    private double load_factor() {
        return ((double) size) / ((double) hashTable.length);
    }

    private static final class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        HashNode(K key, V value) {
            this(key, value, null);
        }

        HashNode(K key, V value, HashNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
