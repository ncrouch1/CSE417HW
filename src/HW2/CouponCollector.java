package HW2;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class CouponCollector {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("How many coupons?    ");
        int nCoupons = input.nextInt();
        System.out.print("How many trials?     ");
        int nTrials = input.nextInt();
        Random r = new Random();
        for (int trial = 0; trial < nTrials; trial++) {
            HashSet<Integer> collected = new HashSet<>();
            int total_coupons = 0;
            while (collected.size() < nCoupons) {
                total_coupons++;
                int coupon = r.nextInt(nCoupons);
                collected.add(coupon);
            }
            System.out.printf("Total Coupons Collected: %d, Distinct Coupons: %d\n", total_coupons, nCoupons);
        }
    }
}
