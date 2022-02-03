import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.awt.Color;

/**
 * Graphs
 */
public class Graphs {

    public static void main(String[] args) {
        var graph = directedGraph(
            vertices(0, 1, 2, 3, 4, 5),
            edge(0, 1, 1),
            edge(0, 2, 1),
            edge(1, 3, 1),
            edge(3, 2, 1),
            edge(4, 0, 1),
            edge(5, 0, 1),
            edge(0, 5, 1)
        );
        var list = topologicalSort(graph);
        System.out.println(hasCycle(graph));
        System.out.println(directedGraphString(graph));
        System.out.println(list);

    }

    public static <V, W> boolean hasCycle(Map<V, Map<V, W>> graph) {
        return !graph.isEmpty() && topologicalSort(graph).isEmpty();
    }

    public static <V, W> List<V> topologicalSort(Map<V, Map<V, W>> graph) {
        Set<V> visiting = new HashSet<>(((graph.size() *  4) / 3) + 1);
        Set<V> visited = new HashSet<>(((graph.size() *  4) / 3) + 1);
        Deque<V> stack = new ArrayDeque<>(graph.size());
        for (V vertex : graph.keySet()) {
            if (dfs(graph, vertex, visiting, visited, v -> stack.push(v))) {
                return List.of();
            }
        }
        return new ArrayList<>(stack);
    }

    public static <V, W> boolean dfs(Map<V, Map<V, W>> graph, V vertex, Set<V> visiting, Set<V> visited, Consumer<V> visitedConsumer) {
        if (!visited.contains(vertex)) {
            if (visiting.contains(vertex)) {
                // has cycle
                return true;
            }
            visiting.add(vertex);
            for (Map.Entry<V, W> edge : graph.get(vertex).entrySet()) {
                if (dfs(graph, edge.getKey(), visiting, visited, visitedConsumer)) {
                    return true;
                }
            }
            visiting.remove(vertex);
            visited.add(vertex);
            visitedConsumer.accept(vertex);
        }
        return false;
    }

    public static <V> List<V> dijkstraShortestPathByte(Map<V, Map<V, Byte>> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (a, b) -> (byte) (a + b), (byte) 0);
    }

    public static <V> List<V> dijkstraShortestPathShort(Map<V, Map<V, Short>> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, (a, b) -> (short) (a + b), (short) 0);
    }

    public static <V> List<V> dijkstraShortestPathInt(Map<V, Map<V, Integer>> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Integer::sum, 0);
    }

    public static <V> List<V> dijkstraShortestPathLong(Map<V, Map<V, Long>> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Long::sum, 0L);
    }

    public static <V> List<V> dijkstraShortestPathFloat(Map<V, Map<V, Float>> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Float::sum, (float) 0.0);
    }

    public static <V> List<V> dijkstraShortestPathDouble(Map<V, Map<V, Double>> graph, V start, V end) {
        return dijkstraShortestPath(graph, start, end, Double::sum, 0.0);
    }

    public static <V, W extends Comparable<? super W>> List<V> dijkstraShortestPath(Map<V, Map<V, W>> graph, V start, V end, 
                                                                                    BinaryOperator<W> plus, W zero) {
        if (graph.isEmpty()) {
            return List.of();
        }
        Map<V, V> parents = dijkstra(graph, start, Predicate.isEqual(end), plus, zero).getKey();
        LinkedList<V> stack = new LinkedList<>();
        V cur = end;
        while (parents.get(cur) != null) {
            stack.push(cur);
            cur = parents.get(cur);
        }
        return stack;
    }

    public static <V> Map.Entry<Map<V, V>, Map<V, Integer>> dijkstraInt(Map<V, Map<V, Integer>> graph, V start) {
        return dijkstra(graph, start, v -> false, Integer::sum, 0);
    }

    public static <V> Map.Entry<Map<V, V>, Map<V, Long>> dijkstraLong(Map<V, Map<V, Long>> graph, V start) {
        return dijkstra(graph, start, v -> false, Long::sum, 0L);
    }

    public static <V> Map.Entry<Map<V, V>, Map<V, Double>> dijkstraDouble(Map<V, Map<V, Double>> graph, V start) {
        return dijkstra(graph, start, v -> false, Double::sum, 0.0);
    }

    // O(|E|log|V|)
    public static <V, W extends Comparable<? super W>> Map.Entry<Map<V, V>, Map<V, W>> dijkstra(Map<V, Map<V, W>> graph, V start,
                                                                                                Predicate<V> stopCondition,
                                                                                                BinaryOperator<W> plus, W zero) {
        // set up auxilliary data structures
        Map<V, V> parents = new HashMap<>();
        Map<V, W> distances = new HashMap<>();
        Set<V> visited = new HashSet<>();
        Queue<Map.Entry<V, W>> fringe = new PriorityQueue<>(Comparator.comparing(Map.Entry::getValue));
        // set distance to start as zero
        distances.put(start, zero);
        // add start to the fringe
        fringe.add(Map.entry(start, zero));
        while (!fringe.isEmpty()) {
            Map.Entry<V, W> entry =  fringe.remove();
            V u = entry.getKey();
            if (stopCondition.test(u)) {
                break;
            }
            if (!visited.contains(u)) {
                // mark u as visited
                visited.add(u);
                // existing distance to u
                W uDistance = distances.get(u);
                for (Map.Entry<V, W> edge : graph.get(u).entrySet()) {
                    V v = edge.getKey();
                    W weight = edge.getValue();
                    // existing distance to v
                    W vDistance = distances.get(v);
                    // distance to v by going through u
                    W vNewDistance = plus.apply(uDistance, weight);
                    if (vDistance == null || vNewDistance.compareTo(vDistance) < 0) {
                        // update target distance
                        distances.put(v, vNewDistance);
                        parents.put(v, u);
                        fringe.add(Map.entry(v, vNewDistance));
                    }
                }
            }
        }
        return Map.entry(parents, distances);
    }

    // O(|E||V|)
    public static <V, W extends Comparable<? super W>> List<V> bellmanFord(Map<V, Map<V, W>> graph, V start, V end, W zero, BinaryOperator<W> plus) {
        Map<V, W> distances = new HashMap<>();
        Map<V, V> predecessors = new HashMap<>();
        // compute distances
        distances.put(start, zero);
        boolean changed = true;
        for (int i = 0; changed && i < graph.size() - 1; i++) {
            changed = false;
            for (Map.Entry<V, Map<V, W>> entry : graph.entrySet()) {
                V u = entry.getKey();
                for (Map.Entry<V, W> edge : entry.getValue().entrySet()) {
                    V v = edge.getKey();
                    W w = edge.getValue();
                    W uDistance = distances.get(u);
                    if (uDistance != null) {
                        W vDistance = distances.get(v);
                        W vNewDistance = plus.apply(uDistance, w);
                        if (vDistance == null || vNewDistance.compareTo(vDistance) < 0) {
                            changed = true;
                            distances.put(v, vNewDistance);
                            predecessors.put(v, u);
                        }
                    }
                }
            }
        }
        // check for negative-weight cycles (optional)
        for (Map.Entry<V, Map<V, W>> entry : graph.entrySet()) {
            V u = entry.getKey();
            for (Map.Entry<V, W> edge : entry.getValue().entrySet()) {
                V v = edge.getKey();
                W w = edge.getValue();
                W uDistance = distances.get(u);
                W vDistance = distances.get(v);
                if (plus.apply(uDistance, w).compareTo(vDistance) < 0) {
                    throw new IllegalStateException("Graph contains a negative-weight cycle");
                }
            }
        }
        Deque<V> stack = new ArrayDeque<>();
        V current = end;
        while (end != null) {
            stack.push(end);
            current = predecessors.get(current);
        }
        return new ArrayList<>(stack);
    }

    public static <V> int numberOfConnectedComponents(Map<V, Set<V>> graph) {
        // use union find
        Map<V, V> parents = new HashMap<>();
        Map<V, Integer> rank = new HashMap<>();
        for (V vertex : graph.keySet()) {
            parents.put(vertex, vertex);
            rank.put(vertex, 1);
        }
        int numberOfComponents = graph.keySet().size();
        for (V vertex : graph.keySet()) {
            for (V neighbor : graph.get(vertex)) {
                numberOfComponents -= union(parents, rank, vertex, neighbor);
            }
        }
        return numberOfComponents;
    }

    private static <V> int union(Map<V, V> parents, Map<V, Integer> rank, V v1, V v2) {
        V p1 = find(parents, v1);
        V p2 = find(parents, v2);
        if (Objects.equals(p1, p2)) {
            return 0;
        }
        if (rank.get(p2) > rank.get(p1)) {
            parents.put(p1, p2);
            rank.put(p2, rank.get(p2) + rank.get(p1));
        } else {
            parents.put(p2, p1);
            rank.put(p1, rank.get(p1) + rank.get(p2));
        }
        return 1;
    }

    private static <V> V find(Map<V, V> parents, V vertex) {
        while (!Objects.equals(vertex, parents.get(vertex))) {
            parents.put(parents.get(vertex), parents.get(parents.get(vertex)));
            vertex = parents.get(vertex);
        }
        return vertex;
    }

    public static <V, W extends Comparable<? super W>> Map<V, Map<V, W>> prims(Map<V, Map<V, W>> graph) {
        if (graph.isEmpty()) {
            return Map.of();
        }
        return prims(graph, graph.keySet().iterator().next());
    }

    // O(|E|log|V|)
    public static <V, W extends Comparable<? super W>> Map<V, Map<V, W>> prims(Map<V, Map<V, W>> graph, V start) {
        if (graph.isEmpty()) {
            return Map.of();
        }
        // set up auxiliary data structures
        Queue<Map.Entry<V, Map.Entry<V, W>>> queue = new PriorityQueue<>(Comparator.comparing(e -> e.getValue().getValue()));
        Set<V> visited = new HashSet<>();
        // result tree
        Map<V, Map<V, W>> tree = new HashMap<>();
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
            Map.Entry<V, Map.Entry<V, W>> edge = queue.remove();
            // extract components of edge {u,v}:w
            V u = edge.getKey();
            V v = edge.getValue().getKey();
            W weight = edge.getValue().getValue();
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
        return tree;
    }

    @SafeVarargs
    public static <V, W> Map<V, Map<V, W>> directedGraph(Set<V> vertices, Map.Entry<Map.Entry<V, V>, W>... edges) {
        return initGraph(true, vertices, edges);
    }

    @SafeVarargs
    public static <V, W> Map<V, Map<V, W>> unDirectedGraph(Set<V> vertices, Map.Entry<Map.Entry<V, V>, W>... edges) {
        return initGraph(false, vertices, edges);
    }

    @SafeVarargs
    public static <V, W> Map<V, Map<V, W>> initGraph(boolean directed, Set<V> vertices, Map.Entry<Map.Entry<V, V>, W>... edges) {
        Map<V, Map<V, W>> graph = new HashMap<>();
        for (V vertex : vertices) {
            graph.put(vertex, new HashMap<>());
        }
        for (Map.Entry<Map.Entry<V, V>, W> edge : edges) {
            graph.get(edge.getKey().getKey()).put(edge.getKey().getValue(), edge.getValue());
            if (!directed) {
                graph.get(edge.getKey().getValue()).put(edge.getKey().getKey(), edge.getValue());
            }
        }
        return graph;
    }

    @SafeVarargs
    public static <V> Set<V> vertices(V... vertices) {
        return Set.of(vertices);
    }

    public static <V, W> Map.Entry<Map.Entry<V, V>, W> edge(V u, V v, W w) {
        return Map.entry(Map.entry(u, v), w);
    }

    public static <V, W> String directedGraphString(Map<V, Map<V, W>> graph) {
        return graphString(graph, true);
    }

    public static <V, W> String unDirectedGraphString(Map<V, Map<V, W>> graph) {
        return graphString(graph, false);
    }

    public static <V, W> String graphString(Map<V, Map<V, W>> graph, boolean directed) {
        Predicate<Map.Entry<V, Map.Entry<V, W>>> filter = x -> true;
        if (!directed) {
            filter = new UndirectedEdgeFilter<>();
        }
        return String.valueOf(graph.keySet()) + ", [" 
             + graph.entrySet()
                    .stream()
                    .flatMap(e -> e.getValue().entrySet().stream().map(e2 -> Map.entry(e.getKey(), e2)))
                    .filter(filter)
                    .map(e -> "(" + e.getKey() + ", " + e.getValue().getKey() + "):" + e.getValue().getValue())
                    .collect(Collectors.joining(", ")) + "]";
    }

    static class UndirectedEdgeFilter<V, W> implements Predicate<Map.Entry<V, Map.Entry<V, W>>> {
        Set<UndirectedEdge<V,W>> seen = new HashSet<>();

        @Override
        public boolean test(Entry<V, Entry<V, W>> t) {
            var edge = new UndirectedEdge<>(t);
            boolean result = seen.contains(edge);
            seen.add(edge);
            return result;
        }

    }

    static class UndirectedEdge<V, W> {
        Map.Entry<V, Map.Entry<V, W>> entry;

        UndirectedEdge(Map.Entry<V, Map.Entry<V, W>> entry) {
            this.entry = entry;
        }

        @Override
        public int hashCode() {
            return Objects.hash(entry.getKey().hashCode() + entry.getValue().getKey().hashCode(), entry.getValue().getValue());
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return false;
            }
            if (!(o instanceof UndirectedEdge)) {
                return false;
            }
            UndirectedEdge<?, ?> other = (UndirectedEdge<?, ?>) o;
            return (
                (Objects.equals(this.entry.getKey(), other.entry.getKey()) && Objects.equals(this.entry.getValue().getKey(), other.entry.getValue().getKey()))
                || 
                (Objects.equals(this.entry.getKey(), other.entry.getValue().getKey()) && Objects.equals(this.entry.getValue().getKey(), other.entry.getKey()))
                )
                && Objects.equals(this.entry.getValue().getValue(), other.entry.getValue().getValue());
        }
    }

    private static class VertexProperties<V> {
        private Set<V> visited;
        private Set<V> visiting;
        private Map<V, Color> colors;

        public VertexProperties() {
            visited = new HashSet<>();
            visiting = new HashSet<>();
            colors = new HashMap<>();
        }

        private void markVisited(V vertex) {
            visited.add(vertex);
        }

        private boolean visited(V vertex) {
            return visited.contains(vertex);
        }

        private void markVisiting(V vertex) {
            visiting.add(vertex);
        }

        private boolean visiting(V vertex) {
            return visiting.contains(vertex);
        }

        private void setColor(V vertex, Color color) {
            colors.put(vertex, color);
        }

        private Color getColor(V vertex) {
            return colors.get(vertex);
        }
    }
}