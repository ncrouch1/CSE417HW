package HW7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class WeightedIntervalGenerator {
    public static class Interval {
        public int start, end, value;
        public Interval(int s, int e, int v) {
            start = s;
            end = e;
            value = v;
        }
    }

    public static class Sorter implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            if (o1.start == o2.start) {
                if (o1.end == o2.end) {
                    return o1.value - o2.value;
                }
                return o1.end - o2.end;
            }
            return o1.start - o2.start;
        }
    }
    public static ArrayList<Interval> generateIntervals(int n, int l, int r, int v, Random ra) {
        ArrayList<Interval> intervals = new ArrayList<>(n);
        for (int index = 0; index < n; index++) {
            int start = ra.nextInt(1, r);
            int end = start + ra.nextInt(1, l);
            int value = ra.nextInt(0, v) + 1;
            intervals.add(new Interval(start, end, value));
        }
        Collections.sort(intervals, new Sorter());
        return intervals;
    }

    public static void printIntervals(ArrayList<Interval> intervals) {
        System.out.print('[');
        for (int index = 0; index < intervals.size() - 1; index++) {
            if (index % 20 == 0 && index != 0) System.out.println();
            Interval interval = intervals.get(index);
            System.out.printf("[%d start, %d end, %d value], ", interval.start, interval.end, interval.value);
        }
        Interval last = intervals.get(intervals.size() - 1);
        System.out.printf("[%d start, %d end, %d value]]\n", last.start, last.end, last.value);
    }

    public static void printIntervalsByStartTime(ArrayList<Interval> intervals) {
        Interval compare = intervals.get(0);
        System.out.printf("[[%d start, %d end, %d value], ", compare.start, compare.end, compare.value);
        for (int index = 1; index < intervals.size() - 1; index++) {
            Interval interval = intervals.get(index);
            if (interval.start > compare.start) {
                compare = interval;
                System.out.println();
            }
            System.out.printf("[%d start, %d end, %d value], ", interval.start, interval.end, interval.value);
        }
        Interval last = intervals.get(intervals.size() - 1);
        System.out.printf("[%d start, %d end, %d value]]\n", last.start, last.end, last.value);
    }
}