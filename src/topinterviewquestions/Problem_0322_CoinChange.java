package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/26
 * @version: V1.0
 * @slogan:
 * @description :零钱兑换
 */
public class Problem_0322_CoinChange {

    /**
     * 暴力尝试
     * @param coins
     * @param amount
     * @return
     */
    public static int coinChange(int[] coins, int amount) {

        int N = coins.length;
        // dp[i][j] = 0 ~ i 上硬币随意使用 兑换出 j 元使用最少的硬币数量为多少
        int[][] dp = new int[N][amount + 1];

        // dp[i][0] = 0 0列不需要填
        // dp[0][1...] = arr[0]的整数倍，有张数，倍数，其他的格子-1（表示无方案）
        for (int j = 1; j <= amount; j++) {
            if (j % coins[0] != 0) {
                dp[0][j] = -1;
            } else {
                dp[0][j] = j / coins[0];
            }
        }

        // dp[i][0]  从0~i 位置上 自由做选择拼出0元 最少需要的硬币张数 为0张
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= amount; j++) {
                // 使用0张 1张  2张 3张 i位置的硬币时，拼出j元 最少需要的硬币数量
                // dp[i-1][j]
                // dp[i-1][j- coins[i] * 1]
                // dp[i-1][j- coins[i] * 2]
                // dp[i-1][j- coins[i] * 3]
                // dp[i-1][j- coins[i] * 4]
                // dp[i-1][j- coins[i] * 5]
                // 踩坑点 这里不能直接设置 dp[i-1][j]  因为dp[i-1][j]=-1 赋值给dp[i][j] 后面Math.min(dp[i][j], dp[i - 1][j - m * coins[i]] + m) 会得到-1
                // dp[i][j] = dp[i-1][j];
                dp[i][j] = Integer.MAX_VALUE;
                for (int m = 0; m * coins[i] <= j; m++) {
                    int tmp = dp[i - 1][j - m * coins[i]];
                    if (tmp != -1) {
                        // 当前i位置 使用了m个硬币  加上 0~i-1上使用的硬币的总数量
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - m * coins[i]] + m);
                    }
                }

                if (dp[i][j] == Integer.MAX_VALUE) {
                    // 没有找到有效方案
                    dp[i][j] = -1;
                }

            }
        }
        return dp[N - 1][amount];
    }

    /**
     * 观察省去枚举行为
     */
    public static int coinChange2(int[] coins, int amount) {

        int N = coins.length;
        // dp[i][j] = 0 ~ i 上硬币随意使用 兑换出 j 元使用最少的硬币数量为多少
        int[][] dp = new int[N][amount + 1];

        // dp[i][0] = 0 0列不需要填
        // dp[0][1...] = arr[0]的整数倍，有张数，倍数，其他的格子-1（表示无方案）
        for (int j = 1; j <= amount; j++) {
            if (j % coins[0] != 0) {
                dp[0][j] = -1;
            } else {
                dp[0][j] = j / coins[0];
            }
        }

        // dp[i][0]  从0~i 位置上 自由做选择拼出0元 最少需要的硬币张数 为0张
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= amount; j++) {
                // 使用0张 1张  2张 3张 i位置的硬币时，拼出j元 最少需要的硬币数量
                // dp[i][j]                   和  dp[i][j - coins[i]] 之间关系可以省去枚举行为
                // dp[i-1][j]               +0
                // dp[i-1][j- coins[i] * 1] +1      dp[i-1][j - coins[i]   ] +0
                // dp[i-1][j- coins[i] * 2] +2      dp[i-1][j - coins[i] *2] +1
                // dp[i-1][j- coins[i] * 3] +3      dp[i-1][j - coins[i] *3] +2
                // dp[i-1][j- coins[i] * 4] +4      dp[i-1][j - coins[i] *4] +3
                // dp[i-1][j- coins[i] * 5] +5      dp[i-1][j - coins[i] *5] +4
                dp[i][j] = dp[i - 1][j];
                if (j - coins[i] >= 0 && dp[i - 1][j - coins[i]] != -1) {
                    if (dp[i][j] != -1) {
                        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - coins[i]] + 1);
                    } else {
                        // dp[i - 1][j] 无效方案 但是  dp[i - 1][j - coins[i]] 有效
                        dp[i][j] = dp[i][j - coins[i]] + 1;
                    }
                }
            }
        }
        return dp[N - 1][amount];
    }






    // 下面代码为老师给的答案  coinChange11 coinChange22

    public static int coinChange11(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        int N = coins.length;
        int[][] dp = new int[N + 1][amount + 1];
        for (int col = 1; col <= amount; col++) {
            dp[N][col] = -1;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= amount; rest++) {
                dp[i][rest] = -1;
                if (dp[i + 1][rest] != -1) {
                    dp[i][rest] = dp[i + 1][rest];
                }
                if (rest - coins[i] >= 0 && dp[i][rest - coins[i]] != -1) {
                    if (dp[i][rest] == -1) {
                        dp[i][rest] = dp[i][rest - coins[i]] + 1;
                    } else {
                        dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - coins[i]] + 1);
                    }
                }
            }
        }
        return dp[0][amount];
    }

    public static int coinChange22(int[] coins, int aim) {
        if (coins == null || coins.length == 0 || aim < 0) {
            return -1;
        }
        int N = coins.length;
        int[][] dp = new int[N][aim + 1];
        // dp[i][0] = 0 0列不需要填
        // dp[0][1...] = arr[0]的整数倍，有张数，倍数，其他的格子-1（表示无方案）
        for (int j = 1; j <= aim; j++) {
            if (j % coins[0] != 0) {
                dp[0][j] = -1;
            } else {
                dp[0][j] = j / coins[0];
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                if (dp[i - 1][j] != -1) {
                    dp[i][j] = dp[i - 1][j];
                }
                if (j - coins[i] >= 0 && dp[i][j - coins[i]] != -1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - coins[i]] + 1);
                }
                if (dp[i][j] == Integer.MAX_VALUE) {
                    dp[i][j] = -1;
                }
            }
        }
        return dp[N - 1][aim];
    }


    public static void main(String[] args) {
        int[] cions = {1, 2, 5};
        System.out.println(coinChange(cions, 11));
    }
}
