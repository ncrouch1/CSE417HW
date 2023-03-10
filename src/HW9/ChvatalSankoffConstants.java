package HW9;

import java.util.Random;

public class ChvatalSankoffConstants {

    /* Given the information from the prompt we are trying to find the values n and k such that
        gamma * k is less than 0.4. gamma * k is equal to the Expected value of lambda_n_k divided by n
        as the limit of n increases towards infinity. Something to note here is that the Weak law of large
        numbers states that as n increases the expected value approaches the mean. So in our case we are
        experimenting to find a mean divided by a sample size. The WLLN requires a sample size greater than or equal
        to 30.
    */
    public static void main(String[] args) {
        Random random = new Random();
        double[] constant = new double[26];
        int[] n = {2000, 5000, 10000, 20000, 50000, 100000};
        int trials = 100;
        for (int nInd = 0; nInd < n.length; nInd++) {
            int nusing = n[nInd];
            System.out.printf("Using n = %d\n", nusing);
            int k = 0;
            double sum = 0;
            for (int i = 0; i < trials; i++) {
                String a = randomStringGenerator(nusing, k);
                String b = randomStringGenerator(nusing, k);
                sum += longestCommonSubsequence(a, b);
            }
            double mean = sum / trials;
            constant[k] = mean / nusing;
            k++;
            while (k < 26 && constant[k - 1] < 0.4) {
                sum = 0;
                for (int i = 0; i < trials; i++) {
                    String a = randomStringGenerator(nusing, k);
                    String b = randomStringGenerator(nusing, k);
                    sum += longestCommonSubsequence(a, b);
                }
                mean = sum / trials;
                constant[k] = mean / nusing;
                k++;
            }
            printConstants(constant);
        }
    }



    public static int longestCommonSubsequence(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[] prevRow = new int[m+1];
        int[] currRow = new int[m+1];

        for (int i = 0; i <= m; i++)
            prevRow[i] = 0;

        for (int i = 1; i <= n; i++) {
            currRow[0] = 0;
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i-1) == b.charAt(j-1))
                    currRow[j] = prevRow[j-1] + 1;
                else if (prevRow[j] >= currRow[j-1])
                    currRow[j] = prevRow[j];
                else
                    currRow[j] = currRow[j-1];
            }
        }

        return currRow[m];
    }
    public static String randomStringGenerator(int n, int k) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int rInt = random.nextInt(0, k+1);
            salt.append(alphabet.charAt(rInt));
        }
        String string = salt.toString();
        return string;
    }

    public static void printConstants(double[] constant) {
        System.out.printf("[%f5, ", constant[0]);
        for (int i = 1; i < 25; i++) {
            System.out.printf("%f5, ", constant[i]);
        }
        System.out.printf("%f5]\n", constant[25]);
    }
}
