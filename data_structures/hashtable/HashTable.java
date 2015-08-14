public class HashTable<E> {
    public static final int DEFAULT_SIZE = 100;
    private static final double LOAD_FACTOR = 0.7;
    private HashNode[] hashTable;
    private int size;
    
    public HashTable() {
        this(DEFAULT_SIZE);
    }

    public HashTable(int size) {
        hashTable = new HashNode[size];
    }
    
    @SuppressWarnings("unchecked")    
    public void insert(E data) {
        if (((double) size) / ((double) hashTable.length) >= LOAD_FACTOR) {
            resize();
        }
        int hash = fixHash(data.hashCode());
        hashTable[hash] = new HashNode<E>(data, hashTable[hash]);
        size++;
    }
    
    @SuppressWarnings("unchecked")    
    public boolean contains(E data) {
        int hash = fixHash(data.hashCode());
        HashNode<E> current = hashTable[hash];
        while (current != null) {
            if (data.equals(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
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
            HashNode<E> current = oldHashTable[i];
            while (current != null) {
                insert(current.data);
                current = current.next;
            }
        }
    }

    private static final class HashNode<E> {
        E data;
        HashNode<E> next;

        HashNode(E data) {
            this(data, null);
        }

        HashNode(E data, HashNode<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}
