package HW4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import HW3.*;

public class GreedyGraphColoring {
    public static void main(String[] args) {
        // Set up for tests
            // Basically runs ten tests for each probability p from 0.02 to 0.002 with method depending on method call
        double p = 0.02;
        double decrement = 0.002;
        int n = 1000;
        while (p >= 0.002) {
            System.out.printf("Testing ten graphs with %d n and %.3f p\n", n, p);
            double average_num_of_colors = 0.0;

            int size = 0;
            for (int i = 0; i < 10; i++) {
                ArrayList<HashSet<Integer>> map = RandomGraphGenerator.createGraph(n, p);
                int k = find_max_degree(map);
                average_num_of_colors += find_max_colors(map, k);
            }
            System.out.printf("Average num of colors in ten trials %f \n", average_num_of_colors / 10);
            p -= decrement;
        }

    }

    // finds the maximum degree in the graph
    public static int find_max_degree(ArrayList<HashSet<Integer>> am) {
        int max_deg = 0;
        for (HashSet<Integer> vertex : am) {
            max_deg = Math.max(max_deg, vertex.size());
        }
        return max_deg;
    }

    // original greedy algorithm for Q4 to find the max number of colors in a graph
    public static int find_max_colors(ArrayList<HashSet<Integer>> am, int k) {
        int n = am.size();
        int num_colors = 0;
        int[] colors = new int[n];
        for (int j = 0; j < n; j++) {
            colors[j] = -1;
        }
        for (int vertex = 0; vertex < n; vertex++) {
            HashSet<Integer> edges = am.get(vertex);
            HashSet<Integer> adj_colors = new HashSet<>();
            for (int av : edges) {
                if (colors[av] != -1) {
                    adj_colors.add(colors[av]);
                }
            }
            for (int a : colors) {
                if (a < 0) {
                    continue;
                }
                if (!adj_colors.contains(a)) {
                    colors[vertex] = a;
                }
            }
            if (colors[vertex] == -1) {
                for (int i = 0; i < k + 1; i++) {
                    if (!adj_colors.contains(i)) {
                        colors[vertex] = i;
                    }
                }
                num_colors++;
            }
        }
        return num_colors;
    }

    // Sorts graph vertices in increasing order of degreethen tests using find max colors
    public static int increasingDegree(ArrayList<HashSet<Integer>> am, int k) {
        Collections.sort(am, new FindGraphDiameter.SortBySize());
        return find_max_colors(am, k);
    }

    // Sorts graph vertices in decreasing order of degree then tests using find max colors
    public static int decreasingDegree(ArrayList<HashSet<Integer>> am, int k) {
        Collections.sort(am, new FindGraphDiameter.SortBySize());
        Collections.reverse(am);
        return find_max_colors(am, k);
    }
}
