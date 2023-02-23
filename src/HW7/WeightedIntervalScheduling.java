package HW7;

import java.util.ArrayList;
import java.util.Random;

import static HW7.WeightedIntervalGenerator.*;

public class WeightedIntervalScheduling {
    public static void main(String[] args) {
        int n = 100, l = 10, r = 10, v = 10;
        Random ra = new Random();
        ArrayList<Interval> intervals = generateIntervals(n, l, r, v, ra);
        ArrayList<Interval> est = earliestStartTime(intervals);
        ArrayList<Interval> mvf = maximumValueFirst(intervals);
        System.out.println("Intervals");
        printIntervalsByStartTime(intervals);
        System.out.println("Earliest Start Time First");
        printIntervals(est);
        System.out.println("Maximum Value First");
        printIntervals(mvf);
    }

    public static ArrayList<Interval> earliestStartTime(ArrayList<Interval> intervals) {
        ArrayList<Interval> result = new ArrayList<>(intervals.size());
        result.add(intervals.get(0));
        for (int index = 1; index < intervals.size(); index++) {
            Interval considering = intervals.get(index);
            if (considering.start >= result.get(result.size()- 1).end) {
                result.add(considering);
            }
        }
        return result;
    }

    public static ArrayList<Interval> maximumValueFirst(ArrayList<Interval> intervals) {
        ArrayList<Interval> result = new ArrayList<>(intervals.size());
        int ptr = 0;
        while (ptr < intervals.size()) {
            Interval compare = intervals.get(ptr);
            ArrayList<Interval> set = new ArrayList<>(intervals.size() - ptr);
            set.add(compare);
            ptr++;
            while (ptr < intervals.size() && intervals.get(ptr).start == compare.start) {
                set.add(intervals.get(ptr));
                ptr++;
            }
            int maxPtr = 0;
            for (int index = 1; index < set.size(); index++) {
                if (set.get(index).value > set.get(maxPtr).value) {
                    maxPtr = index;
                }
            }
            result.add(set.get(maxPtr));
            while (ptr < intervals.size() && intervals.get(ptr).start < compare.end) {
                ptr++;
            }
        }
        return result;
    }

}
