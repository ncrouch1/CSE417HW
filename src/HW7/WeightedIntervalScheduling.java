package HW7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import static HW7.WeightedIntervalGenerator.*;
import static java.lang.Math.max;

public class WeightedIntervalScheduling {
    public static void main(String[] args) {
        int n = 10000, l = 1000000, r = 2000, v = 100;
        Random ra = new Random();
        for (int trial = 0; trial < 10; trial++) {
            ArrayList<Interval> intervals = generateIntervals(n, l, r, v, ra);
            ArrayList<Interval> est = earliestStartTime(intervals);
            ArrayList<Interval> mvf = maximumValueFirst(intervals);
            ArrayList<Interval> mdf = maximumDensityFirst(intervals);
            Collections.sort(intervals, new SortByEndTime());
            ArrayList<Interval> dpmv = dynamicProgrammingMaximumValue(intervals);
            int weightEst = sumValues(est), weightMvf = sumValues(mvf),
                    weightMdf = sumValues(mdf), weightDP = sumValues(dpmv);
            System.out.printf("%dth trial   %d total intervals\n EST: %d weight, %d intervals\n MVF: %d weight, %d intervals\n" +
                    " MDF: %d weight, %d intervals\n DP: %d weight, %d intervals\n", trial + 1, intervals.size(),
                    weightEst, est.size(), weightMvf, mvf.size(), weightMdf, mdf.size(), weightDP, dpmv.size());
        }
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
            while (ptr < intervals.size() && intervals.get(ptr).start < result.get(result.size() - 1).end) {
                ptr++;
            }
        }
        return result;
    }

    public static ArrayList<Interval> maximumDensityFirst(ArrayList<Interval> intervals) {
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
                Interval o1 = set.get(maxPtr);
                Interval o2 = set.get(index);
                double o1Val = o1.value;
                double o2Val = o2.value;
                double o1Density = o1Val / (o1.end - o1.start);
                double o2Density = o2Val / (o2.end - o2.start);
                if (o2Density > o1Density) {
                    maxPtr = index;
                }
            }
            result.add(set.get(maxPtr));
            while (ptr < intervals.size() && intervals.get(ptr).start < result.get(result.size() - 1).end) {
                ptr++;
            }
        }
        return result;
    }

    public static class SortByEndTime implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            if (o1.end == o2.end) {
                if (o1.start == o2.start) {
                    return o1.value - o2.value;
                }
                return o1.start - o2.start;
            }
            return o1.end - o2.end;
        }
    }
    // Precondition: Intervals sorted by end time
    public static ArrayList<Interval> dynamicProgrammingMaximumValue(ArrayList<Interval> intervals) {
        int n = intervals.size();
        ArrayList<Interval> result = new ArrayList<>();
        int m[] = new int[n+1];
        int prev[] = new int[n];
        prev[0] = -1;
        for (int index = 1; index < n; index++) {
            Interval iv = intervals.get(index);
            int currMaxValue = 0;
            int currMaxIndex = -1;
            for (int jndex = index; jndex >= 0; jndex--) {
                Interval jv = intervals.get(jndex);
                if (jv.end > iv.start) continue;
                if (jv.value > currMaxValue) {
                    currMaxValue = jv.value;
                    currMaxIndex = jndex;
                }
            }
            prev[index] = currMaxIndex;
        }

        m[0] = 0;
        for (int index = 1; index <= n; index++) {
            Interval iv = intervals.get(index - 1);
            m[index] = max(m[index - 1], m[prev[index - 1] + 1] + iv.value );
        }
        int index = n;
        int maximum = m[n];
        while (m[index] == maximum) {
            index--;
        }
        // retrieve the 'path' of intervals
        int weight = intervals.get(index).value;
        result.add(intervals.get(index));
        while (weight < m[n]) {
            index = prev[index];
            if (index == -1) index = 0;
            result.add(intervals.get(index));
            weight += intervals.get(index).value;
        }
        // reverse the list so intervals are in order
        Collections.reverse(result);
        return result;
    }

    public static int sumValues(ArrayList<Interval> intervals) {
        int sum = 0;
        for (Interval iv : intervals) {
            sum += iv.value;
        }
        return sum;
    }
}
