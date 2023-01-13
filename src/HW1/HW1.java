package HW1;

import java.util.*;

public class HW1 {
    public static void main(String[] args) {
        int length = 10;
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
        printPrefLists(mpref, wpref);
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
        int[] layer = new int[l];
        for (int i = 0; i < l; i++) {
            layer[i] = i;
        }
        for (int i = 0; i < l; i++) {
            res[i] = randomize(layer, l);
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
}
