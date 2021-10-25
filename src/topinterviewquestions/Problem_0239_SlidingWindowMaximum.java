package topinterviewquestions;

import java.util.LinkedList;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description :滑动窗口最大值
 * 【解题思路】
 * 假设遍历到 arr[i]，qmax 的放入规则为：
 * 1．如果 qmax 为空，直接把下标 i 放进 qmax，放入过程结束。
 * 2．如果 qmax 不为空，取出当前 qmax 队尾存放的下标，假设为 j。
 *    1）如果 arr[j]>arr[i]，直接把下标 i 放进 qmax 的队尾，放入过程结束。
 *    2）如果 arr[j]<=arr[i]，把 j 从 qmax 中弹出，重复 qmax 的放入规则。
 * 也就是说，如果 qmax 是空的，就直接放入当前的位置。如果 qmax 不是空的，qmax 队尾
 * 的位置所代表的值如果不比当前的值大，将一直弹出队尾的位置，直到 qmax 队尾的位置所代
 * 表的值比当前的值大，当前的位置才放入 qmax 的队尾。
 * 假设遍历到 arr[i]，qmax 的弹出规则为：
 * 如果 qmax 队头的下标等于 i-w，说明当前 qmax 队头的下标已过期，弹出当前对头的下标即可。
 *
 * arr = [1 2 3 4 2 1 0 5 8 4]
 * qmax= [1]
 * qmax= [2]
 * qmax= [3]
 * qmax= [4]
 * qmax= [4 2 ]
 * qmax= [4 2 1]
 * qmax= [2 1 0]
 * qmax= [5]
 * qmax= [8]
 * qmax= [8 4]
 *
 * coding 有空了再写一下
 */
public class Problem_0239_SlidingWindowMaximum {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k < 1 || nums.length < k) {
            return null;
        }

        // 窗口队列中存放的是数组的索引值
        // 队列中数据为 从到小递减的
        LinkedList<Integer> qmax = new LinkedList<>();

        int[] res = new int[nums.length - k + 1];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[i]) {
                qmax.pollLast();
            }
            qmax.addLast(i);

            if (qmax.peekFirst() == i - k) {
                qmax.pollFirst();
            }
            if (i >= k - 1) {
                res[index++] = nums[qmax.peekFirst()];
            }
        }
        return res;
    }
}
