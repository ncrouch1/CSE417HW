package HW3;

import java.util.*;

public class FindGraphDiameter {
    // Comparator used to sort the adjacency matrix in terms of number of edges
    static class SortBySize implements Comparator<HashSet> {
        @Override
        public int compare(HashSet o1, HashSet o2) {
            return o1.size() - o2.size();
        }
    }
    /*
        Goal Runtime: O(nm), for every vertex, it is explored m times. Meaning that at worst the algorithm is O(n)
        time when there are n vertices and 1 edge, and worst case is O(n^n) time when there are n edges for every
        vertex.

        Plan:
            Run BFS to find the number of components and also to compute the max diameter. The max diameter should be
            the deepest level of the BFS tree. However, this only works if we are starting from the most connected
            vertex in the component. Thus, we will need to sort by number of connecting edges. Then iterate by n

     */
    public static void main(String[] args) {
        // Set up - All of this is in here since the generation algorithm is O(N^2) and is not possible to be
        // less, thus this is assumed to be omitted in the graph diameter function.
        // test parameters
        int n = 1000;
        double p = 0.0002;

        // test start output
        System.out.printf("Testing graph with %d vertices and %.6f edge chance\n", n, p);
        // test object creation
        ArrayList<HashSet<Integer>> am = RandomGraphGenerator.createGraph(n, p);
        // test object preparation, asserts that the adjacency matrix is sorted
        Collections.sort(am, new SortBySize());
        // finish set up

        // Function set up
        boolean[] visited = new boolean[n];
        int[] levels = new int[n];
        int max_level = 0;
        int components = 0;
        Queue<Integer> BFS = new LinkedList<>();
        for (int vertex = n - 1; vertex >= 0; vertex--) {
            if (!visited[vertex]) {
                components++;
                BFS.add(vertex);
                while (!BFS.isEmpty()) {
                    int vert = BFS.remove();
                    visited[vert] = true;
                    HashSet<Integer> connections = am.get(vert);
                    for (int av : connections) {
                        if (!visited[av]) {
                            BFS.add(av);
                            if (levels[av] == 0) {
                                levels[av] = levels[vert] + 1;
                            }
                            max_level = Math.max(levels[av], max_level);
                        }
                    }
                }
            }
        }
        System.out.printf("%d components, %d max diameter\nif only one component max diameter = finite diameter\n\n"
                , components, max_level);
    }
}
