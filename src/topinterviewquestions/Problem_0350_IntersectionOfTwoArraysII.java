package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description : 思路 使用map统计词频
 */
public class Problem_0350_IntersectionOfTwoArraysII {
    /**
     * 题目要求 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2,2]
     * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出：[4,9]
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map1 = new HashMap<>();
        for (int num : nums1) {
            map1.put(num, map1.getOrDefault(num, 0) + 1);
        }


        HashMap<Integer, Integer> map2 = new HashMap<>();
        for (int num : nums2) {
            map2.put(num, map2.getOrDefault(num, 0) + 1);
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (Integer num : map1.keySet()) {
            if (map2.containsKey(num)) {
                int k = Math.min(map1.get(num), map2.get(num));
                for (int i = 0; i < k; i++) {
                    list.add(num);
                }
            }
        }

        // 方法返回值要的是数组
        int[] ans = new int[list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }


}
