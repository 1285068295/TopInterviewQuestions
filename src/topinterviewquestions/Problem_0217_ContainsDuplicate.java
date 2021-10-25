package topinterviewquestions;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @author ：Lisp
 * @date： 2021/10/13
 * @version: V1.0
 * @slogan:
 * @description :给定一个整数数组，判断是否存在重复元素。
 *
 * 暴力解答  没有空间要求使用HashSet
 * 堆排序   有空间要求  空间复杂度为O(1) 原地排序  排序后再找重复的数字
 *
 */
public class Problem_0217_ContainsDuplicate {


    /**
     * 手写堆排序  自底向上的 heapify
     */
    public boolean containsDuplicate3(int[] nums) {


        return false;
    }


    /**
     * 堆排序
     * @param arr
     * @param i
     */
    public static void heapSort(int[] arr, int i){



    }




    public boolean containsDuplicate1(int[] nums) {

        int N= nums.length;
        PriorityQueue<Integer> heap = new PriorityQueue<>(N);
        for (int i = 0; i < N; i++) {
            heap.add(nums[i]);

        }

        int pre = heap.poll();
        while (!heap.isEmpty()){
            if (pre == heap.peek()){
                return true;
            }
            pre = heap.poll();

        }
        return false;
    }


    public boolean containsDuplicate2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }
        return false;
    }
}
