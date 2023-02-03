package Utilities;

import java.util.ArrayList;

public class DisjointSet {
    private int[] components;

    public DisjointSet(int v) {
        components = new int[v];
        for (int index = 0; index < v; index++) {
            components[index] = -1;
        }
    }

    public boolean join(int a, int b) {
        int findA = find(a);
        int findB = find(b);
        if (findA != findB) {
            union(findA, findB);
            return true;
        }
        return false;
    }

    public int find(int v) {
        int parentPointer = components[v];
        int parentIndex = v;
        ArrayList<Integer> path_compression = new ArrayList<>();
        while (parentPointer > 0 ) {
            path_compression.add(parentPointer);
            parentPointer = components[parentPointer];
            parentIndex = parentPointer;
        }
        for (int child : path_compression) {
            components[child] = parentIndex;
        }
        return parentIndex;
    }

    public void union(int rootA, int rootB) {
        int childrenA = components[rootA];
        int childrenB = components[rootB];
        if (childrenA <= childrenB) {
            components[rootA] += components[rootB];
            components[rootB] = rootA;
        } else {
            components[rootB] += components[rootA];
            components[rootA] = rootB;
        }
    }
}
