package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/4
 * @version: V1.0
 * @slogan:
 * @description :带冷却时间的股票交易
 *
 * 无限次数交易 但是需要冷却一天
 *
 * 指导性的思路 就把所有的i位置的交易的收益算出来
 *
 * 参考第188题的解题思路  分两种大情况 i位置参加交易  和  不参加交易两种情况
 *
 * 根本不需要按照老师讲的来搞  我自己的思路来搞  一样可以搞定！！！
 *
 *
 */
public class Problem_0309_BestTimeToBuyAndSellStockWithCooldown_V {

    /**
     * 暴力枚举
     * @param prices
     * @return
     */
    public static int maxProfit1(int[] prices) {
        int N = prices.length;

        int[] dp = new int[N];
        if (N <= 1) {
            return 0;
        }

        if (N == 2) {
            return prices[1] - prices[0] > 0 ? prices[1] - prices[0] : 0;
        }

        // baseCase
        dp[1] = prices[1] - prices[0] > 0 ? prices[1] - prices[0] : 0;
//        int best = 0 - prices[1];
        for (int i = 2; i < N; i++) {
            dp[i] = dp[i-1];
            // 枚举所有的买入时机
            for (int j = 0; j <= i; j++) {
                int pre = i - j - 2 < 0 ? 0 : i - j - 2;
                dp[i] = Math.max(dp[i], dp[pre] - prices[i - j] + prices[i]);
            }
            int best = dp[0] - prices[0];
            // i位置不参与交易  可以合并
//            dp[i] = dp[i-1];
            // i位置参与交易  分情况套路i的买入时机
//          dp[i] = dp[i-2] - prices[i]   + prices[i];
//          dp[i] = dp[i-3] - prices[i-1] + prices[i];
//          dp[i] = dp[i-4] - prices[i-2] + prices[i];     = best + prices[i]
//          dp[i] = dp[i-5] - prices[i-3] + prices[i];
//          dp[i] = dp[i-6] - prices[i-4] + prices[i];
//          ...

//          dp[i-1] = dp[i-3] - prices[i-1] + prices[i-1];
//          dp[i-1] = dp[i-4] - prices[i-2] + prices[i-1]; = best + prices[i-1]
//          dp[i-1] = dp[i-5] - prices[i-3] + prices[i-1];
//          dp[i-1] = dp[i-6] - prices[i-4] + prices[i-1];
//          ...
//            best = Math.max(best,  dp[i-2] - prices[i] );
//            dp[i] = best +  prices[i] ;
        }



        return dp[N-1];
    }


    /**
     * 斜率优化版本
     *
     * 无敌啦！！！
     *
     */
    public static int maxProfit2(int[] prices) {
        int N = prices.length;
        int[] dp = new int[N];
        if (N <= 1) {
            return 0;
        }

        if (N == 2) {
            return prices[1] - prices[0] > 0 ? prices[1] - prices[0] : 0;
        }

        // baseCase
        dp[1] = prices[1] - prices[0] > 0 ? prices[1] - prices[0] : 0;

        //  i=0 i=1
        int best = Math.max(0 - prices[0], 0 - prices[1]);
        for (int i = 2; i < N; i++) {
            dp[i] = dp[i-1];

            // i位置不参与交易  可以合并
//            dp[i] = dp[i-1];
            // i位置参与交易  分情况套路i的买入时机
//          dp[i] = dp[i-2] - prices[i]   + prices[i];
//          dp[i] = dp[i-3] - prices[i-1] + prices[i];
//          dp[i] = dp[i-4] - prices[i-2] + prices[i];     = best + prices[i]
//          dp[i] = dp[i-5] - prices[i-3] + prices[i];
//          dp[i] = dp[i-6] - prices[i-4] + prices[i];
//          ...

//          dp[i-1] = dp[i-3] - prices[i-1] + prices[i-1];
//          dp[i-1] = dp[i-4] - prices[i-2] + prices[i-1]; = best + prices[i-1]
//          dp[i-1] = dp[i-5] - prices[i-3] + prices[i-1];
//          dp[i-1] = dp[i-6] - prices[i-4] + prices[i-1];
//          ...
//          省去枚举所有的买入时机
            best = Math.max(best, dp[i - 2] - prices[i]);
            // 参与交易情况  和  不参与交易情况PK
            dp[i] = Math.max(dp[i], best + prices[i]);
        }
        return dp[N-1];
    }


    public static void main(String[] args) {
        int[] arr1 = {1,2,4};
        int[] arr2 = {1,2,3,0,2};
        int[] arr3 ={1,2,4,2,5,7,2,4,9,0 };

//        System.out.println(maxProfit(arr1));
//        System.out.println(maxProfit2(arr2));
        System.out.println(maxProfit2(arr3));
    }


}
