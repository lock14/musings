import java.util.HashMap;
import java.util.Map;

public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void add(CharSequence charSequence) {
        TrieNode cur = root;
        for (int i = 0; i < charSequence.length(); i++) {
            char c = charSequence.charAt(i);
            if (!cur.contains(c)) {
                cur.add(c);
            }
            cur = cur.get(c);
        }
        cur.markTerminal();
    }

    public boolean containsPrefix(CharSequence prefix) {
        return get(prefix) != null;
    }

    public boolean contains(CharSequence charSequence) {
        TrieNode trieNode = get(charSequence);
        return trieNode != null && trieNode.isTerminal();
    }

    private TrieNode get(CharSequence charSequence) {
        TrieNode cur = root;
        for (int i = 0; i < charSequence.length(); i++) {
            char c = charSequence.charAt(i);
            if (!cur.contains(c)) {
                return null;
            }
            cur = cur.get(c);
        }
        return cur;
    }

    private static class TrieNode {
        private boolean terminal;
        private Map<Character, TrieNode> children;

        TrieNode() {
            terminal = false;
            children = null;
        }

        boolean contains(char c) {
            return children != null && children.containsKey(c);
        }

        void add(char c) {
            if (children == null) {
                children = new HashMap<>();
            }
            children.put(c, new TrieNode());
        }

        TrieNode get(char c) {
            if (children == null) {
                return null;
            }
            return children.get(c);
        }

        void markTerminal() {
            terminal = true;
        }

        boolean isTerminal() {
            return terminal;
        }
    }
}
