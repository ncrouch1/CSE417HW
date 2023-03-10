package HW6;

import java.util.*;

public class RandomSelectKthElement {
    public static void main(String[] args) {
        int n = 50000;
        int increment = 50000;
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0 && i != 0 && i != 90) {
                n += increment;
                System.out.printf("]\n N = %d\n [", n);
            } else if (i == 0){
                System.out.printf("N = %d\n[", n);
            }
            int k = r.nextInt(n) + 1;
//            System.out.printf("%d numbers, %dth largest element\n", n, k);
            ArrayList<Double> A = new ArrayList<>(n);
            generateArray(A, n, r);
            double num = findKthLargestElement(A, r, k);
            int c = findC(A, r, k);
//            System.out.printf("Comparisons made %d\n", c);
            Collections.sort(A);
            assert(A.get(k) == num);
            System.out.printf("%d, ", c);
        }
        System.out.println("]");
//        System.out.printf("Kth largest element found, %f\nList: ", num);
//        for (int index = 0; index < n - 1; index++) {
//            if (index % 20 == 0 && index != 0) {
//                System.out.println();
//            }
//            System.out.printf("%f, ", A.get(index));
//        }
//        System.out.printf("%f\n", A.get(n-1));
    }


    public static void generateArray(ArrayList<Double> A, int n, Random r) {
        for (int index = 0; index < n; index++) {
            A.add(index, Math.random());
        }
    }

    public static double findKthLargestElement(ArrayList<Double> A, Random r, int k) {
        int n = A.size();
        ArrayList<Double> s1 = new ArrayList<>();
        ArrayList<Double> s2 = new ArrayList<>();
        ArrayList<Double> s3 = new ArrayList<>();
        double x = A.get(r.nextInt(n));
        for (double i : A) {
            if (i < x) {s1.add(i); continue;}
            if (i > x) {s2.add(i); continue;}
            s3.add(i);
        }
        if (s2.size() >= k) {
            return findKthLargestElement(s2, r, k);
        } else if (s3.size() + s2.size() >= k) {
            return x;
        } else {
            return findKthLargestElement(s1, r, k - s2.size() - s3.size());
        }
    }

    /*
     * Identically this is the same algorithm as the Select implementation. In order to calculate the
     * number of comparisons per n elements I added a count functionality. See below
     */
    public static int findC(ArrayList<Double> A, Random r, int k) {
        // For each round initialize a count variable
        int count = 0;
        int n = A.size();
        ArrayList<Double> s1 = new ArrayList<>();
        ArrayList<Double> s2 = new ArrayList<>();
        ArrayList<Double> s3 = new ArrayList<>();
        double x = A.get(r.nextInt(n));
        for (double i : A) {
            // If this comparison is done, only add 1
            if (i < x) {
                s1.add(i); count++; continue;
            }
            // If this comparison is done add 2, since the last one was performed
            if (i > x) {
                s2.add(i); count += 2; continue;
            }
            // If the other two comparisons are executed and they were false, then the element is equal to the
            // element we are comparing with. Thus there is no other comparison needed. Thus we add 2 to the
            // comparison count.
            s3.add(i);
            count += 2;
        }
        if (s2.size() >= k) {
            // Instead of returning a number return all the rounds counts
            return count + findC(s2, r, k);
        } else if (s3.size() + s2.size() >= k) {
            // once Kth largest has been found return the count at the bottom level
            return count;
        } else {
            // Return the counts from the levels
            return count + findC(s1, r, k - s2.size() - s3.size());
        }
    }
}
