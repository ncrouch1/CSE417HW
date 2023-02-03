package Utilities;

import java.util.ArrayList;
import java.util.HashMap;

public class RandomDirectedGraph {
    private static ArrayList<HashMap<Integer, Integer>> am;

    public static ArrayList<HashMap<Integer, Integer>> createMap(int n, double p, int max_edge_weight) {
        am = new ArrayList<HashMap<Integer,Integer>>(n);
        for (int index = 0; index < n; index++) {
            HashMap<Integer, Integer> empty_edges = new HashMap<>(n);
            am.add(index, empty_edges);
        }
        for (int vertice = 0; vertice < n; vertice++) {
            HashMap<Integer, Integer> edges = new HashMap<>(n);
            for (int connect = 0; connect < n; connect++) {
                if (connect == vertice) {
                    continue;
                }
                if (Math.random() <= p) {
                    int edge_weight = (int) Math.round(Math.random() * max_edge_weight);
                    edges.put(connect, edge_weight);
                }
            }
        }
        return am;
    }
}
