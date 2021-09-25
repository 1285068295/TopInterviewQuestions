package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/25
 * @version: V1.0
 * @slogan:
 * @description :最大子数组累加和问题  子数组的数量规模是  N^2级别的
 * 【解题思路】  -  子数组必须是连续的！！！
 * 子数组求解套路：
 *      枚举所有的必须以 arr[i] 开头的子数组  或者  以arr[i] 结尾的子数组的情况
 *      因为这两种情况就是暴力求解的过程！！！
 *
 * 这道题采用 必须要arr[i]结尾情况下的讨论。
 * 计算arr[i]结尾的子数组的最大累加和  根据最大累加和是否包含arr[i]来讨论
 * 1) 子数组的最大累加和包含arr[i]     dp[i] = dp[i-1] + arr[i]
 * 2) 子数组的最大累加和不包含arr[i]   dp[i] = dp[i-1]
 *
 *
 */
public class Problem_0053_MaximumSubarray {

    /**
     *
     * 必须以arr[i] 结尾的子数组最大累加和   与  必须以arr[i+1]结尾的子数组最大累加和 关系如下
     * 0 1 2 3 4 5 .... i-1 i   i+1
     *   1 2 3 4 5 .... i-1 i   i+1
     *     2 3 4 5 .... i-1 i   i+1
     *       3 4 5 .... i-1 i   i+1
     *         4 5 .... i-1 i   i+1
     *           5 .... i-1 i   i+1
     *                  i-1 i   i+1
     *                      i   i+1  ----上面的最大累加和为  dp[i+1]=dp[i]+arr[i+1]
     *                          i+1  ----最后的最大累加和为  dp[i+1]=      arr[i+1]
     *
     *                          注意必须要以arr[i+1]结尾！！！ 就是说必须要包含arr[i+1]
     *                          一定不要和  子数组的子数组搞混了呀！！！
     *
     *
     * 观察可得  dp[i+1] = max{arr[i+1],  dp[i] +arr[i+1]}
     */
    public static int maxSubArray1(int[] arr) {

        int N = arr.length;
        int[] dp = new int[N];
        dp[0] = arr[0];
        int max = dp[0];
        for (int i = 1; i < N; i++) {
            dp[i] = Math.max(arr[i], dp[i - 1] + arr[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static int maxSubArray2(int[] arr) {
        int N = arr.length;
        int[] dp = new int[N];
        dp[0] = arr[0];
        int max = dp[0];
        for (int i = 1; i < N; i++) {
            dp[i] = arr[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
            max = Math.max(max, dp[i]);
        }
        return max;
    }


    public static int maxSubArray3(int[] arr) {
        int N = arr.length;
        int dp = arr[0];
        int max = arr[0];
        for (int i = 1; i < N; i++) {
            dp = arr[i] + (dp > 0 ? dp : 0);
            max = Math.max(max, dp);
        }
        return max;
    }





    /**
     * 拓展题  数组上不能选择相邻的数字  求最大的累加和
     *
     * 分以下情况
     * 0 1 2 3 4 5 .... i-1 i   i+1
     *   1 2 3 4 5 .... i-1 i   i+1
     *     2 3 4 5 .... i-1 i   i+1
     *       3 4 5 .... i-1 i   i+1
     *         4 5 .... i-1 i   i+1
     *           5 .... i-1 i   i+1
     *                  i-1 i   i+1
     *                      i   i+1  ----上面的最大累加和为  dp[i+1]=dp[i]+arr[i+1]
     *                          i+1  ----最后的最大累加和为  dp[i+1]=      arr[i+1]
     *
     * 1) 不选i+1  dp[i+1] = dp[i]
     * 2)   选i+1  dp[i+1] = max(arr[i+1],   arr[i+1]+dp[i-1]   )  选i+1时  有两种情况  只有i+1 和  dp[i-1]+arr[i+1]
     */
    public static int maxSumFollowUp(int[] arr) {
        if (arr == null) {
            return 0;
        }
        int N = arr.length;
        if (N == 0) {
            return 0;
        }
        if (N == 1) {
            return arr[0];
        }
        if (N == 2) {
            return Math.max(arr[0], arr[1]);
        }
        // N > 2
        int[] dp = new int[N];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);
        for (int i = 2; i < N; i++) {
            int p1 = arr[i];
            int p2 = dp[i - 1];
            int p3 = arr[i] + dp[i - 2];
            dp[i] = Math.max(Math.max(p1, p2), p3);
        }
        return dp[N-1];
    }


}
