package HW3;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static java.util.Arrays.sort;


public class RandomGraphGenerator {

    public static void main(String[] args) {
        int n = 10;
        double p = 0.0005;
        ArrayList<HashSet<Integer>> graph = createGraph(n, p);
        printList(graph);
    }

    // Constraints:
        // Runtime: none
        // Space: n + m, adjacency list
        // p: p >= 0 && p <= 1
        // n: n > 0
    // Goal:
        // Runtime: Not greater than n^2
            // Use HashSet to contain edges, O(1) add time, O(1) lookup time
    public static ArrayList<HashSet<Integer>> createGraph(int n, double p) {
        // Sanity Checks
        assert(p <= 1 && p >= 0);
        assert(n > 0);
        // init array list
        ArrayList<HashSet<Integer>> am = new ArrayList<>(n);
        // init hashsets to avoid resizing
        for (int i = 0; i < n; i++) {
            HashSet<Integer> set = new HashSet<>(n);
            am.add(set);
        }
        // loop over all vertices
        for (int vertex = 0; vertex < n; vertex++) {
            Random r = new Random();
            // get the connections of the vertices
            HashSet<Integer> connections = am.get(vertex);
            // loop over all possible conections
            for (int edgeTo = 0; edgeTo < n; edgeTo++) {
                // to avoid self edges skip possible edge if it is self
                if (edgeTo == vertex) {
                    continue;
                }
                // If we pass the stat check add the connection to both vertices connections
                // We can just use add since it doesn't matter if the set already contains it
                // it wont add a duplicate and will not overwrite the entry
                if (r.nextDouble(1.0) <= p) {
                    connections.add(edgeTo);
                    am.get(edgeTo).add(vertex);
                }
            }
        }
        return am;
    }

    // analysis tool method
    public static void printList(ArrayList<HashSet<Integer>> graph) {
        int n = graph.size();
        for (int vertex = 0; vertex < n; vertex++) {
            System.out.printf("[%d]: ", vertex);
            HashSet<Integer> connections = graph.get(vertex);
            Object[] asArray = Arrays.copyOf(connections.toArray(), connections.size());
            sort(asArray);
            System.out.print("->");
            for (int index = 0; index < asArray.length; index++) {
                if (index < asArray.length - 1) {
                    System.out.printf("[%d] ->", asArray[index]);
                } else {
                    System.out.printf("[%d]", asArray[index]);
                }
            }
            System.out.println();
        }
    }
}
