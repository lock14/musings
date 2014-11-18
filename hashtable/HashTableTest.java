public class HashTableTest {
    public static void main(String[] args) {
        HashTable<String> hashTable = new HashTable<String>();
        String[] array = {"Tom", "Dick", "Harry"};
        for (String s : array) {
            hashTable.insert(s);
        }
        for (String s : array) {
            System.out.println(hashTable.contains(s));
        }
    }
}
