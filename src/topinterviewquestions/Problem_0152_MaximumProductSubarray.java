package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/8
 * @version: V1.0
 * @slogan:
 * @description :乘积最大子数组
 *
 * 【解题思路】 参考最大子数组的累加和问题情况套路  053题
 *  情况讨论 讨论所有子数组以 arr[i]结尾的乘积
 *
 *
 */
public class Problem_0152_MaximumProductSubarray {

    public static int maxProduct(int[] nums) {

        /**
         * 所有必须以arr[i]结尾的子数组有
         * 0 1 2 3 4 5 .... i-1 i
         *   1 2 3 4 5 .... i-1 i
         *     2 3 4 5 .... i-1 i
         *       3 4 5 .... i-1 i
         *         4 5 .... i-1 i
         *           5 .... i-1 i
         *                  i-1 i
         *                      i
         *  乘积最大子数组分情况：1）单独以arr[i]
         *                      2）arr[j] ~ arr[i] 即  arr[j]~arr[i-1] arr[i]
         *  2.1 如果[i] > 0 在 "所有的" 以[i-1]结尾的累乘积中 最大正数 * arr[i] = 得到以arr[i] 结尾的最大累乘积
         *  2.2 如果[i] < 0 在 "所有的" 以[i-1]结尾的累乘积中 最小负数 * arr[i] = 得到以arr[i] 结尾的最大累乘积  （负负得正）
         *  2.3 如果[i] = 0 最大累乘积就是 0
         *
         *  从上面分析可得  dp[i] 依赖与dp[i-1]位置的最大正数 和最小负数
         *
         *  举例如下
         *         [5][6][7] = maxPositive(正数)
         *  [3][4][5][6][7]  = minNegative(负数)
         *  arr[8]>0  [3][4][5][6][7][8] = maxPositive(正数) =  最小的负数 *[8]
         *                  [5][6][7][8] = minNegative(负数) =  最大的正数 *[8]
         *
         *  综上所述  dp[i] 依赖与dp[i-1]的最大值和最小值  没有必要取细分取讨论 正负的情况
         */
        int ans = nums[0];
        int min = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 每一步都依赖于前一步的最大值和最小值
            // nums[i], min * nums[i], max * nums[i]  三者求最大值  最小值
            int curmin = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
            int curmax = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));
            min = curmin;
            max = curmax;
            ans = Math.max(ans, max);
        }
        return ans;
    }
}
