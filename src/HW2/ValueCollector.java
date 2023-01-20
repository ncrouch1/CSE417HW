package HW2;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ValueCollector {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("How many coupons?    ");
        int nCoupons = input.nextInt();
        System.out.print("How many trials?     ");
        int nTrials = input.nextInt();
        Random r = new Random();
        for (int trial = 0; trial < nTrials; trial++) {
            HashMap<Integer, Integer> coupon_value = new HashMap<>();
            while (coupon_value.size() < nCoupons) {
                int coupon = r.nextInt(nCoupons);
                int value = r.nextInt((nCoupons - 1)) + 1;
                if (!coupon_value.containsKey(coupon)) {
                    coupon_value.put(coupon, value);
                } else if (value < coupon_value.get(coupon)) {
                    coupon_value.put(coupon, value);
                }
            }
            int value = 0;
            for (int key : coupon_value.keySet()) {
                value += coupon_value.get(key);
            }
            System.out.printf("Total Value: %d, Distinct Coupons: %d\n", value, nCoupons);
        }
    }
}
