package topinterviewquestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author ：Lisp
 * @date： 2021/10/25
 * @version: V1.0
 * @slogan:
 * @description : 会议室 II  119 253
 *
 * 给你一个会议时间安排的数组 intervals ，每个会议时间都会包括开始和结束的时间 intervals[i] = [starti, endi] ，
 * 为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。
 *
 * 示例 1：
 * 输入：intervals = [[0,30],[5,10],[15,20]]
 * 输出：2
 *
 * 示例 2：
 * 输入：intervals = [[7,10],[2,4]]
 * 输出：1
 *
 * 【解题思路】
 *  这题的本质其实很简单，就是利用一个看最忙碌的时候会用到几个会议室，这个时候就是最大值。
 *
 */
public class Problem_0253_MeetingRoomsII {

    public static int minMeetingRooms(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        Arrays.sort(lines, new StartComparator());
        PriorityQueue<Line> heap = new PriorityQueue<>(new EndComparator());
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            // 堆顶的元素是 最早结束的会议，堆顶的会议没有结束 其余的更不可能结束
            while (!heap.isEmpty() && heap.peek().end <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i]);
            // 找到最忙碌的时候的会议室数量
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static class StartComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }

    }

    public static class EndComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.end - o2.end;
        }

    }

}
