package HW9;

import java.util.Random;

public class ChvatalSankoffConstants {

    /* Given the information from the prompt we are trying to find the values n and k such that
        gamma * k is less than 0.4. gamma * k is equal to the Expected value of lambda_n_k divided by n
        as the limit of n increases towards infinity. Something to note here is that the Weak law of large
        numbers states that as n increases the expected value approaches the mean. So in our case we are
        experimenting to find a mean divided by a sample size
    */
    public static void main(String[] args) {
        Random random = new Random();
        int gammaK = 0;
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
}
