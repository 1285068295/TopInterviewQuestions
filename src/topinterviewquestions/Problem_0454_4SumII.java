package topinterviewquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/20
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 *  利用分治的思想
 *  先从分别从两个数组中计算出两个和sum1 然后只需要再另外的两个数组中 找到两数之和为 target-sum1 的值即可。
 */
public class Problem_0454_4SumII {

    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        // key-两数之和  value-对应的方案数  nums1[0]+nums2[0] = nums1[0]+nums2[1]= 5 则key=5 value=2
        HashMap<Integer, Integer> sumTimes = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int sum = nums1[i] + nums2[j];
                sumTimes.put(sum, sumTimes.getOrDefault(sum, 0) + 1);
            }
        }

        int ans = 0;
        for (int i = 0; i < nums3.length; i++) {
            for (int j = 0; j < nums4.length; j++) {
                int sum = nums3[i] + nums4[j];
                ans += sumTimes.getOrDefault(-sum, 0);
            }
        }
        return ans;
    }
}
