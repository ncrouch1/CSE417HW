package HW1;

import java.util.*;

public class HW1 {
    public static void main(String[] args) {
        int length = 1000;
        int[][] mpref = getRandomPrefs(length);
        boolean[][] asked = new boolean[mpref.length][mpref.length];
        int[][] wpref = getRandomPrefs(length);
        int[] mMatch = setMatches(length);
        int[] wMatch = setMatches(length);
        Stack<Integer> men = new Stack<>();
        for (int i = 0; i < length; i++) {
            men.push(i);
        }
        while (!men.isEmpty()) {
            int man = men.pop();
            int[] pref = mpref[man];
            boolean[] askedbefore = asked[man];
            for (int i = 0; i < pref.length; i++) {
                int woman = pref[i];
                if (askedbefore[i]) continue;
                System.out.printf("%d proposes to %d [%d, %d] ", man, woman, woman, wMatch[woman]);
                if (wMatch[woman] == -1){
                    wMatch[woman] = man;
                    mMatch[man] = woman;
                    askedbefore[i] = true;
                    System.out.println("Accepted");
                    break;
                }
                int[] wprefs = wpref[woman];
                boolean better = false;
                int lastMan = wMatch[woman];
                for (int j = 0; j < wprefs.length; j++) {
                    if (wprefs[j] == man) {
                        better = true;
                        break;
                    } else if (wprefs[j] == lastMan) {
                        break;
                    }
                }
                if (better) {
                    System.out.println("Accepted");
                    wMatch[woman] = man;
                    mMatch[lastMan] = -1;
                    mMatch[man] = woman;
                    men.push(lastMan);
                } else {
                    System.out.println("Rejected");
                }
                askedbefore[i] = true;
                if (better) break;
            }
        }
        System.out.print("[");
        for (int i = 0; i < length - 1; i++) {
            System.out.printf("%d, ", mMatch[i]);
            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
        }
        System.out.println(mMatch[length- 1] + "]");
//        printPrefLists(mpref, wpref);
        printGoodnessComparison(mMatch, mpref, wpref);
    }

    public static int[] setMatches(int l) {
        int[] res = new int[l];
        for (int i = 0; i < l; i++) {
            res[i] = -1;
        }
        return res;
    }

    public static int[][] getRandomPrefs(int l) {
        int[][] res = new int[l][l];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                res[i][j] = j;
            }
        }
        for (int row = 0; row < l; row++) {
            res[row] = randomize(res[row], l);
        }
        return res;
    }

    public static int[] randomize(int[] list, int l) {
        Random r = new Random();
        int[] layer = list;
        int index = 0;
        do {
            int index2swap = r.nextInt(l);
            int temp = layer[index2swap];
            layer[index2swap] = layer[index];
            layer[index] = temp;
            r = new Random(index2swap);
            index++;
        } while (index < l);
        return layer;
    }

    public static void printPrefLists(int[][] m, int[][] w) {
        System.out.println("Male Preferences:");
        for (int i = 0; i < m.length; i++) {
            System.out.print("[");
            for (int j = 0; j < m[i].length-1; j++) {
                System.out.print(m[i][j] + ", ");
            }
            System.out.println(m[i][m[i].length-1] + "]");
        }
        System.out.println("Female Preferences:");
        for (int i = 0; i < w.length; i++) {
            System.out.print("[");
            for (int j = 0; j < w[i].length-1; j++) {
                System.out.print(w[i][j] + ", ");
            }
            System.out.println(w[i][w[i].length-1] + "]");
        }
    }

    public static void printGoodnessComparison(int[] mMatches, int[][] mpref, int[][] wpref) {
        int[] mGoodnesses = new int[mMatches.length];
        int[] wGoodnesses = new int[mMatches.length];
        double mMean = 0.0;
        double wMean = 0.0;
        for (int index = 0; index < mMatches.length; index++) {
            int man = index;
            int woman = mMatches[index];
            System.out.printf("Testing goodness for match [%d, %d]\n", man, woman);
            for (int col = 0; col < mpref[man].length; col++) {
                if (mpref[man][col] == woman) {
                    mGoodnesses[man] = col;
                    mMean += col;
                    break;
                }
            }
            for (int col = 0; col < wpref[woman].length; col++) {
                if (wpref[woman][col] == man) {
                    wGoodnesses[woman] = col;
                    wMean += col;
                    break;
                }
            }
            System.out.printf("Man goodness = %d, Woman goodness = %d\n", mGoodnesses[man], wGoodnesses[woman]);
        }
        mMean = mMean / mGoodnesses.length;
        wMean = wMean / wGoodnesses.length;
        Arrays.sort(mGoodnesses);
        Arrays.sort(wGoodnesses);
        double mMedian = 0.0;
        double wMedian = 0.0;
        if (mGoodnesses.length % 2 == 0) {
            mMedian = (mGoodnesses[(mGoodnesses.length/2)] + mGoodnesses[(mGoodnesses.length/2) + 1]) / 2;
            wMedian = (wGoodnesses[(wGoodnesses.length/2)] + wGoodnesses[(wGoodnesses.length/2) + 1]) / 2;

        } else {
            int i = (mGoodnesses.length / 2) + 1;
            mMedian = mGoodnesses[i];
            wMedian = wGoodnesses[i];
        }
        System.out.printf("Average statistics, lower is better\n");
        System.out.printf("Male Goodnesses: Median = %.2f, Mean = %.2f\n", mMedian, mMean);
        System.out.printf("Female Goodnesses: Median = %.2f, Mean = %.2f\n", wMedian, wMean);
    }
}
