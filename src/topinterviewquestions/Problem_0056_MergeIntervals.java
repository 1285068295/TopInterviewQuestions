package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/9/27
 * @version: V1.0
 * @slogan:
 * @description :合并区间
 * 【解题思路】
 *  按照start最区间从小到大进行排序，如下
 *  [1,4][1,3][2,5][7,8][11,14]
 *  依次的遍历区间，start=1 end=4   [1,3]用1与end比较  1<end  废弃1  再用3与end比较  3<end 废弃3
 *                                [2,5]用2与end比较  2<end  废弃2  再用5与end比较  5>end 更新end=5
 *                                [7,8]用7与end比较  7>end  之前的start end 形成区间段 [1,5]
 *                                      更新start=7 end=8
 *
 *
 */
public class Problem_0056_MergeIntervals {

    public static class Range{
        public int start;
        public int end;
        public Range(int start, int end){
            this.start =start;
            this.end = end;
        }
    }

    public static class RangeComparator implements Comparator<Range> {

        @Override
        public int compare(Range o1, Range o2) {
            return o1.start - o2.start;
        }

    }


    public static int[][] merge(int[][] intervals) {

        int N = intervals.length;
        Range[] ranges = new Range[N];
        for (int i = 0; i < N; i++) {
            ranges[i] = new Range(intervals[i][0], intervals[i][1]);
        }

        // 区间段排序  leetcode 居然不识别lambda比到达式子  ！！！日了狗了！！！
        // ！！！日了狗了！！！ Arrays.sort(ranges, (r1, r2) -> {return r1.start < r2.start ? -1 : 1;});
        Arrays.sort(ranges, new RangeComparator());

        List<Range> ans = new ArrayList<>();

        int start = ranges[0].start;
        int end = ranges[0].end;
        for (int i = 1; i < N; i++) {
            if (ranges[i].start > end) {
                Range r = new Range(start, end);
                ans.add(r);
                start = ranges[i].start;
            }
            if (ranges[i].end > end) {
                end = ranges[i].end;
            }
        }

        // 最后一个要加进来
        Range r = new Range(start, end);
        ans.add(r);

        int len = ans.size();
        int[][] arr = new int[len][2];
        for (int i = 0; i < ans.size(); i++) {
            arr[i][0] = ans.get(i).start;
            arr[i][1] = ans.get(i).end;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[][] intervals= {{1,3},{2,6},{8,10},{15,18}};

        merge(intervals);

    }
}
