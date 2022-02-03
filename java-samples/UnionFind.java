import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * UnionFind
 */
public class UnionFind<V> {
    private Map<V, V> parents;
    private Map<V, Integer> rank;

    public UnionFind() {
        this.parents = new HashMap<>();
        this.rank = new HashMap<>();
    }
    
    public int union(V v1, V v2) {
        V p1 = find(v1);
        V p2 = find(v2);
        if (Objects.equals(p1, p2)) {
            return 0;
        }
        if (rank.getOrDefault(p2, 1) > rank.getOrDefault(p1, 1)) {
            parents.put(p1, p2);
            rank.put(p2, rank.getOrDefault(p2, 1) + rank.getOrDefault(p1, 1));
        } else {
            parents.put(p2, p1);
            rank.put(p1, rank.getOrDefault(p1, 1) + rank.getOrDefault(p2, 1));
        }
        return 1;
    }

    public V find(V vertex) {
        while (!Objects.equals(vertex, parents.getOrDefault(vertex, vertex))) {
            V parent = parents.getOrDefault(vertex, vertex);
            parents.put(parents.getOrDefault(vertex, vertex), parents.getOrDefault(parent, parent));
            vertex = parents.getOrDefault(vertex, vertex);
        }
        return vertex;
    }
}