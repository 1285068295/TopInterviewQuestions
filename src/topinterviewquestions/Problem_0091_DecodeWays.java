package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/1
 * @version: V1.0
 * @slogan:
 * @description :
 * 【思路】
 *  从左到有的尝试模型
 *
 */
public class Problem_0091_DecodeWays {


    /**
     *暴力递归过程
     */
    public static int numDecodings(String s) {
        char[] chs = s.toCharArray();
        return process(chs, 0);

    }

    /**
     * 从当前i位置往后 可以解码的方案数量
     *
     * 题目说了 不能有以0开头的字符串  就是说 0 必须和前面的数字结合使用
     */
    private static int process(char[] chs, int i) {
        if (i == chs.length){
            return 1;
        }

        if (chs[i] == '0'){
            // 不能以 0 开头  0 必须和前面的字符结合使用才行
            return 0;
        }

        int ways = 0;
        if (chs[i] == '1') {
            ways = process(chs, i + 1);
            if (i != chs.length - 1) {
                // i不是倒数第一个字符  [i]单独出来  或者[i][i+1] 代表一个字母
                ways += process(chs, i + 2);
            }
            return ways;
        }

        if (chs[i] == '2') {
            ways = process(chs, i + 1);
            // 踩坑点 chs[i] <='6'
            if (i != chs.length - 1 && chs[i+1] <= '6') {
                // i不是倒数第一个字符  [i]单独出来  或者[i][i+1] 代表一个字母
                ways += process(chs, i + 2);
            }
            return ways;
        }
        // chs[i] > '2' 的情况
        return  process(chs, i+1);
    }



    /**
     * 暴力递归基础上改动态规划
     *
     * 我居然写出来了 哈哈哈！！！ 强无敌啦！！！
     */
    public static int numDecodings2(String s) {
        char[] chs = s.toCharArray();

        int N = chs.length;

        int[] dp = new int[chs.length + 1];
        dp[N] = 1;


        for (int i = N-1; i >=0 ; i--) {
            if (chs[i] == '0'){
                // 不能以 0 开头  0 必须和前面的字符结合使用才行
                dp[i] = 0;
                continue;
            }

            int ways = 0;
            if (chs[i] == '1') {
                ways = dp[ i + 1];
                if (i != chs.length - 1) {
                    // i不是倒数第一个字符  [i]单独出来  或者[i][i+1] 代表一个字母
                    ways += dp[i + 2];
                }
                dp[i] = ways;
                continue;
            }

            if (chs[i] == '2') {
                ways = dp[i + 1];
                // 踩坑点 chs[i] <='6'
                if (i != chs.length - 1 && chs[i+1] <= '6') {
                    // i不是倒数第一个字符  [i]单独出来  或者[i][i+1] 代表一个字母
                    ways += dp[i + 2];
                }
                dp[i] =  ways;
                continue;
            }
            // chs[i] > '2' 的情况
            dp[i] = dp[i+1];
        }
        return dp[0];
    }






    public static void main(String[] args) {
        System.out.println(numDecodings("27"));
    }
}
