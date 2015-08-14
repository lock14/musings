public class HashTableTest {
    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>();
        String[] keys = {"Tom", "Dick", "Harry"};
        int[] values = {23, 27, 24};
        for (int i = 0; i < 3; i++) {
            hashTable.put(keys[i], values[i]);
        }
        for (String s : keys) {
            System.out.println(hashTable.get(s));
        }
    }
}
