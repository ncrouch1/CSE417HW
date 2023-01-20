package HW2;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ValueCollector {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.printf("How many coupons?    ");
        int nCoupons = input.nextInt();
        System.out.printf("How many trials?     ");
        int nTrials = input.nextInt();
        Random r = new Random();
        for (int trial = 0; trial < nTrials; trial++) {
            HashMap<Integer, Double> coupon_value = new HashMap<>();
            while (coupon_value.size() < nCoupons) {
                int coupon = r.nextInt(nCoupons);
                double value = r.nextDouble();
                if (!coupon_value.containsKey(coupon)) {
                    coupon_value.put(coupon, value);
                } else if (value < coupon_value.get(coupon)) {
                    coupon_value.put(coupon, value);
                }
            }
            double value = 0;
            for (int key : coupon_value.keySet()) {
                value += coupon_value.get(key);
            }
            System.out.printf("Total Value: %f, Distinct Coupons: %d\n", value, nCoupons);
        }
    }
}
