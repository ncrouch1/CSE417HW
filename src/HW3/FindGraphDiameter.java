package HW3;

import java.util.*;

public class FindGraphDiameter {
    /*
        Goal Runtime: O(nm) = num vertices * num edges, if all vertices are connected that means that the
        runtime will be O(n^2), which is the worst case.
            Plan:
                Run DFS on graph once to get number of components. O(n+m)
                Next run BFS on each vertex to find max distance. O(n * (c + m))
                    Breaking that down:
                        O(n) since we are touching each vertex

     */
    public static void main(String[] args) {
        // Set up
        int n = 100;
        double p = 1;
        System.out.printf("Testing graph with %d vertices and %.4f edge chance\n", n, p);
        ArrayList<HashSet<Integer>> am = RandomGraphGenerator.createGraph(n, p);
        // finish set up

        //

    }
}
