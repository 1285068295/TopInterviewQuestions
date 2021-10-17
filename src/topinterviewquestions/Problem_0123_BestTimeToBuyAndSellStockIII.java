package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/4
 * @version: V1.0
 * @slogan:
 * @description : 股票问题第三问  最多只能交易k次
 *
 * 找多所有的上坡区间段 求一个topK问题？？？xxx 错误的想法
 * 反例：1,2,4,2,5,7,2,4,9,0  最佳的交易时机为  1买7卖出  + 2买入9卖出 = 13收益
 *
 * 当K>N/2时 等同于无限次数交易  因为在整个区间上上涨的区间数量不可能多余N/2
 *
 */
public class Problem_0123_BestTimeToBuyAndSellStockIII {



    public static int maxProfit(int[] prices) {
        return maxProfit2(2, prices);
    }


    /**
     * 交易次数大于N/2 的情况 等同与无限次数交易
     */
    public static int allTrans(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            ans += Math.max(prices[i] - prices[i - 1], 0);
        }
        return ans;
    }

    /**
     * 没有斜率优化版本
     */
    public static int maxProfit(int K, int[] prices) {
        int N = prices.length;

        if (K >= N / 2) {
            return allTrans(prices);
        }

        // dp[i][j] 表示0~i位置上股票最多交易k次情况下 最大收益
        int[][] dp = new int[N][K+1];

        // baseCase
        // dp[0][...] = 0   dp[...][0] = 0

        // 情况一 prices[i] 不参与交易  dp[i][j] = dp[i-1][j]
        // 情况二 prices[i]  参与交易  由贪心可知 [i]位置的股票参与的是最后一次的卖出时机
        //       prices[i] 参与卖出 且最多卖出K次的情况下 prices[i]一定是在第K次的卖出才能收益最大
        //     如K=3 prices={1,2,1,3,1,4} prices[5]=4 最多买卖3次
        //     如果 prices[4]是在第1次的时候就卖出  因为此时的时间点已经来到了第5天  则后面几次的交易的收益只能是i位置买入 卖出 不能产生收益
        //     为了股票利益最大化 i 位置的股票只能是参与的最后一次的交易卖出
        //     即 i 位置的股票参与了前面的卖出 那么后面的交易就都不能产生收益了


        //     第i天股票参与交易(在第i天卖出)   要使得收益最大  一定是 最后一次 (第j次交易卖出时)获得的最大收益
        //     枚举最后一次的交易的买入股票时机  从而枚举得到最后一次交易的收益
        //     dp[i][j] 可以按照第j次交易的买入时机分情况如下
        //     1 第j次的买入时机为[i]   前面0~i  位置做i-1次交易
        //     2 第j次的买入时机为[i-1] 前面0~i-1位置做i-1次交易
        //     3 第j次的买入时机为[i-2] 前面0~i-2位置做i-1次交易
        //     ...
        //    2.1)  dp[i][j] = dp[i][j-1]  + prices[i]-prices[i]   最后一次的交易为  最后一天买入 最后一天卖出
        //    2.2)  dp[i][j] = dp[i-1][j-1]+ prices[i]-prices[i-1]
        //    2.3)  dp[i][j] = dp[i-2][j-1]+ prices[i]-prices[i-2]
        //    2.4)  dp[i][j] = dp[i-3][j-1]+ prices[i]-prices[i-3] 最后一次交易的买入时机时 i-3，其收益为prices[i]-prices[i-3]
        //    2.5)  dp[i][j] = dp[i-4][j-1]+ prices[i]-prices[i-4]
        //     ...
        //     上面的所有情况中  求max
        //     举例如下：dp[10][3]
        //     dp[10][3] = dp[10][2] + prices[10]-prices[10]		dp[10][2]-prices[10]
        //     dp[10][3] = dp[9][2] + prices[10]-prices[9]			dp[9][2] -prices[9]
        //     dp[10][3] = dp[8][2] + prices[10]-prices[8]  = Max{ 	dp[8][2] -prices[8]  }+ prices[10]
        //     dp[10][3] = dp[7][2] + prices[10]-prices[7]			dp[7][2] -prices[7]
        //     dp[10][3] = dp[6][2] + prices[10]-prices[6]			dp[6][2] -prices[6]
        //     ...														...
        //
        //
        //     dp[11][3]
        //     dp[11][3] = dp[11][2] + prices[11]-prices[11]		dp[11][2] -prices[11]
        //	   dp[11][3] = dp[10][2] + prices[11]-prices[10]		dp[10][2] -prices[10]
        //     dp[11][3] = dp[9][2] + prices[11]-prices[9]			dp[9][2] -prices[9]
        //     dp[11][3] = dp[8][2] + prices[11]-prices[8]  = Max{  dp[8][2] -prices[8] } + prices[11]
        //     dp[11][3] = dp[7][2] + prices[11]-prices[7]			dp[7][2] -prices[7]
        //     dp[11][3] = dp[6][2] + prices[11]-prices[6]			dp[6][2] -prices[6]
        //     ...														...
        //
        //     观察可得  dp[11][3]的计算过程中 存在大量的dp[10][3]的重复计算过程 ，所以我们可以在  计算dp[10][3]
        //     的过程中  用临时遍历计算中间过程  以便在求解dp[11][3]时使用 大大减少重复计算
        //     需要注意的是  利用dp[i][j] 可以加速得到 dp[i+1][j] 所以for循环 i应该是内层循环
        for (int j = 1; j <= K; j++) {
            for (int i = 1; i < prices.length; i++) {
                //  斜率没有优化版本
                // [i]位置股票不参与交易
                dp[i][j] = dp[i-1][j];
                // [i]位置股票参与交易
                for (int m = 0; m <= i; m++) {
//                    dp[i][j] = dp[i][j-1]  + prices[i]-prices[i];
//                    dp[i][j] = dp[i-1][j-1]  + prices[i]-prices[i-1];
//                    dp[i][j] = dp[i-2][j-1]  + prices[i]-prices[i-2];
//                    dp[i][j] = dp[i-3][j-1]  + prices[i]-prices[i-3];
//                    dp[i][j] = dp[i-4][j-1]  + prices[i]-prices[i-4];
                    dp[i][j] = Math.max(dp[i][j], dp[i - m][j - 1] + prices[i] - prices[i - m]);
                }
            }
        }
        return dp[N-1][K];

    }


    /**
     * 在maxProfit方法的基础上 通过观察dp[i][j] 与dp[i+1][j] 关系省去枚举行为
     */
    public static int maxProfit2(int K,int[] prices) {

        int N = prices.length;
        if (K >= N / 2) {
            return allTrans(prices);
        }
        // dp[i][j] 表示0~i位置上股票最多交易k次情况下 最大收益
        int[][] dp = new int[N][K+1];
        // 不能在这里定义
//        int tmp = 0;
        for (int j = 1; j <= K; j++) {
            // 踩坑点  tmp的初始值设定！！！
            int tmp = dp[0][j - 1] - prices[0];
            for (int i = 1; i < prices.length; i++) {
                //  斜率没有优化版本
                // [i]位置股票不参与交易
                dp[i][j] = dp[i-1][j];
                // [i-1]位置股票参与交易
//                                  dp[i-1][j-1]  + prices[i-1] - prices[i-1];          dp[i-1][j-1]  - prices[i-1];
//               dp[i-1][j] =max{   dp[i-2][j-1]  + prices[i-1] - prices[i-2]; } = max{ dp[i-2][j-1]  - prices[i-2];  } + prices[i-1] =  tmp + prices[i-1]
//                                  dp[i-3][j-1]  + prices[i-1] - prices[i-3];          dp[i-3][j-1]  - prices[i-3];
//                                  dp[i-4][j-1]  + prices[i-1] - prices[i-4];          dp[i-4][j-1]  - prices[i-4];
//               ...
//                                  dp[i][j-1]    + prices[i] - prices[i];              dp[i][j-1]    - prices[i];
//                                  dp[i-1][j-1]  + prices[i] - prices[i-1];            dp[i-1][j-1]  - prices[i-1];
//               dp[i][j] = max{    dp[i-2][j-1]  + prices[i] - prices[i-2]; }   = max{ dp[i-2][j-1]  - prices[i-2]; } + prices[i]  =max(tmp,  dp[i][j-1]- prices[i]) + prices[i]
//                                  dp[i-3][j-1]  + prices[i] - prices[i-3];            dp[i-3][j-1]  - prices[i-3];
//                                  dp[i-4][j-1]  + prices[i] - prices[i-4];            dp[i-4][j-1]  - prices[i-4];
//               ...
                // dp[i+1][j] = Math.max((dp[i][j] - prices[i]) + prices[i+1], dp[i+1][j-1]  - prices[i+1]) + prices[i+1]
                // 上面的这个方程是错误的 因为  dp[i][j] 既有[i]不参与交易的情况 又有参与交易的情况！！！
//                if (i + 1 < prices.length) {
//                    int p = Math.max(dp[i][j] - prices[i], dp[i + 1][j - 1] - prices[i + 1]) + prices[i + 1];
//                    dp[i][j] = Math.max(dp[i][j], p);
//                }

                tmp = Math.max(tmp, dp[i][j-1]- prices[i]);
                // 不参与交易  与  参与交易  PK
                dp[i][j] = Math.max(dp[i][j], tmp + prices[i]);
            }
        }
        return dp[N-1][K];
    }

    /**
     * 空间压缩版本
     *
     * 实际测试下来 这个版本时间最快？？？ 讲道理时间复杂度都是一样的呀
     *
     */
    public static int maxProfit3(int K, int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int N = prices.length;
        if (K >= N / 2) {
            return allTrans(prices);
        }
        // dp一维表，做了空间压缩
        int[] dp = new int[N];
        int ans = 0;
        for (int tran = 1; tran <= K; tran++) {
            int pre = dp[0];
            int best = pre - prices[0];
            for (int index = 1; index < N; index++) {
                pre = dp[index];
                dp[index] = Math.max(dp[index - 1], prices[index] + best);
                best = Math.max(best, pre - prices[index]);
                ans = Math.max(dp[index], ans);
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int[] arr = { 3,3,5,0,0,3,1,4};
        System.out.println(maxProfit(2,arr));

        System.out.println(maxProfit2(2,arr));
        System.out.println(maxProfit3(2,arr));
    }

}
