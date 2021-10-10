package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【思路】
 *  dp[i] = Max{dp[i-1] , dp[i-2]+[i]}
 *
 */
public class Problem_0198_HouseRobber {


    public static int rob(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }
        if (len == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int cur = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
            dp[i] = Math.max(dp[i] , cur   );

        }
        return dp[len - 1];
    }


    /**
     * 空间压缩
     */
    public static int rob2(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }
        if (len == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int dpi_2 = nums[0];
        int dpi_1 = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int cur = Math.max(dpi_1, dpi_2 + nums[i]);
            dpi_2 = dpi_1;
            dpi_1 = cur;
        }
        return dpi_1;
    }
    public static void main(String[] args) {
        int[] arr = {1,2,3,1};
        System.out.println(rob(arr));
    }

}
