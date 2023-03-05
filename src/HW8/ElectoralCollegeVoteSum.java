package HW8;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class ElectoralCollegeVoteSum {
    static final String states[] = {"Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","District of Columbia","Florida",
            "Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts",
            "Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico",
            "New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina",
            "South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"};
    static final int[] electoralVotes1964 = {10,3,5,6,40,6,8,3,3,14,12,4,4,26,13,9,7,9,10,4,10,14,21,10,7,12,4,5,3,4,17,4,43,13,4,26,8,6,29,4,8,4,11,25,4,3,12,9,7,12,3};
    static final int[] electoralVotes1972 = {9,3,6,6,45,7,8,3,3,17,12,4,4,26,13,8,7,9,10,4,10,14,21,10,7,12,4,5,3,4,17,4,41,13,3,25,8,6,27,4,8,4,10,26,4,3,12,9,6,11,3};
    static final int[] electoralVotes1984 = {9,3,7,6,47,8,8,3,3,21,12,4,4,24,12,8,7,9,10,4,10,13,20,10,7,11,4,5,4,4,16,5,36,13,3,23,8,7,25,4,8,3,11,29,5,3,12,10,6,11,3};
    static final int[] electoralVotes1992 = {9,3,8,6,54,8,8,3,3,25,13,4,4,22,12,7,6,8,9,4,10,12,18,10,7,11,3,5,4,4,15,5,33,14,3,21,8,7,23,4,8,3,11,32,5,3,13,11,5,11,3};
    static final int[] electoralVotes2004 = {9,3,10,6,55,9,7,3,3,27,15,4,4,21,11,7,6,8,9,4,10,12,17,10,6,11,3,5,5,4,15,5,31,15,3,20,7,7,21,4,8,3,11,34,5,3,13,11,5,10,3};
    static final  int[] electoralVotes2012 = {9,3,11,6,55,9,7,3,3,29,16,4,4,20,11,6,6,8,8,4,10,11,16,10,6,10,3,5,6,4,14,5,29,15,3,18,7,7,20,4,9,3,11,38,6,3,13,12,5,10,3};
    static final int[] electoralVotes2024 = {9,3,11,6,54,10,7,3,3,30,16,4,4,19,11,6,6,8,8,4,10,11,15,10,6,10,4,5,6,4,14,5,28,16,3,17,7,8,19,4,9,3,11,40,6,3,13,12,4,10,3};
    static final HashSet<Integer> yearsSet = new HashSet<Integer>(Arrays.asList(1964, 1972, 1984, 1992, 2004, 2012, 2024));
    public static void main(String[] args) {
        int num = 269;
        int year = 1964;
//        long[][] sumNums = findSubsetCount(num, year);
//        printSumNums(sumNums);
        long[][] sumSizes = findSubsetSizes(num, year);
        printSumNums(sumSizes);
        long minSumSize = findMinSumSize(sumSizes);
    }

    public static long[][] findSubsetCount(int K, int year) {
        assert(yearsSet.contains(year));
        int[] nums = year == 1964 ? electoralVotes1964 :
                year == 1972 ? electoralVotes1972 :
                year == 1984 ? electoralVotes1984 :
                year == 1992 ? electoralVotes1992 :
                year == 2004 ? electoralVotes2004 :
                year == 2012 ? electoralVotes2012 :
                        electoralVotes2024;

        long[][] sumNums = new long[K + 1][nums.length + 1];
        // since arrays in Java are initated to 0 we don't need to set the first row and column
        for (int k = 1; k <= K; k++) {
            for (int index = 1; index <= nums.length; index++) {
                int num = nums[index - 1];
                if (k - num < 0) {
                    sumNums[k][index] = sumNums[k][index - 1];
                    continue;
                }
                if (k == num) {
                    sumNums[k][index] = sumNums[k][index-1] + 1;
                } else if (sumNums[k-num][index - 1] > 0) {
                    sumNums[k][index] = sumNums[k][index - 1] + sumNums[k-num][index-1];
                } else {
                    sumNums[k][index] = sumNums[k][index - 1];
                }
            }
        }
        return sumNums;
    }

    public static long[][] findSubsetSizes(int K, int year) {
        assert(yearsSet.contains(year));
        int[] nums = year == 1964 ? electoralVotes1964 :
                year == 1972 ? electoralVotes1972 :
                year == 1984 ? electoralVotes1984 :
                year == 1992 ? electoralVotes1992 :
                year == 2004 ? electoralVotes2004 :
                year == 2012 ? electoralVotes2012 :
                electoralVotes2024;
        long[][] sumSizes = new long[K+1][nums.length + 1];
        for (int k = 1; k <= K; k++) {
            for (int index = 1; index <= nums.length; index++) {
                int num = nums[index - 1];
                if (k - num < 0)
                    sumSizes[k][index] = sumSizes[k][index - 1];
                else if (k == num) {
                    sumSizes[k][index] = 1;
                } else if (sumSizes[k-num][index-1] > 0){
                    long lastSize = sumSizes[k-num][index-1];
                    sumSizes[k][index] = lastSize + 1;
                } else {
                    sumSizes[k][index] = sumSizes[k][index - 1];
                }
            }
        }
        return sumSizes;
    }

    public static long findMinSumSize(long[][] sumSizes) {
        long[] k = sumSizes[sumSizes.length - 1];
        long min = Long.MAX_VALUE;
        for (int index = 0; index < k.length; index++) {
            if (k[index] != 0) {
                min = Math.min(min, k[index]);
            }
        }
        if (min == 0) {
            System.out.println("Number not able to be made");
        } else {
            System.out.printf("Smallest subset size = %d\n", min);
        }
        return min;
    }

    public static void printSumNums(long[][] sumNums) {
        for (int row = sumNums.length - 1; row >= 0; row--) {
            System.out.print(row + "  ");
            for (int col = 0; col < sumNums[0].length; col++) {
                if (col == 0)
                    System.out.print('[');
                else if (col == sumNums[0].length - 1) {
                    System.out.printf("%d", sumNums[row][col]);
                    System.out.println(']');
                    continue;
                }
                System.out.printf("%d, ", sumNums[row][col]);
            }
        }
    }
}
