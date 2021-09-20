package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/20
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路】
 * 尝试模型：一个样本做行 一个样本做列
 *
 * 通过观察法dp数组临近的位置依赖关系省去枚举行为！！！！
 *
 * 【参考第10题  这道题比第10题要简单！！！】
 *
 *
 *
 *
 */
public class Problem_0044_WildcardMatching {

    public static boolean isMatch(String str, String pattern) {
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        return process1(s,p,0,0 );
    }

    /**
     * 暴力递归过程
     *
     * 尝试模型：一个样本做行 一个样本做列
     *
     * s[si...] 从s字符串从si位置及其往后所有的字符串 是否可以被 p[pi...] p字符串从 pi 及其往后所有的字符串匹配出来
     *
     * @param s
     * @param p
     * @param si
     * @param pi
     * @return
     */
    private static boolean process1(char[] s, char[] p, int si, int pi) {

        if(si == s.length){
            // s = "" 空字符串
            // 两种情况可以有 p字符串匹配得到 "" 空字符串
            // 1 pi=p.length
            // 2 从pi位置往后  所有的字符都必须是 * 只要有一个非* 就不能匹配  如 ***a*   题目要求的? 不可以当作空字符使用！！！
            while(pi<p.length){
                if(p[pi] != '*'){
                    return false;
                }
            }
            return true;
        }

        // si还没到头  pi就已经到头了  肯定匹配不出来
        if(pi == p.length){
            return false;
        }

        // si 还有字符  pi也还有字符 且pi位置为普通字符
        if (p[pi] != '?' && p[pi] != '*') {
            return s[si] == p[pi] && process1(s, p, si + 1, pi + 1);
        }

        // si 还有字符  pi也还有字符 且pi位置为?
        if (p[pi] == '?') {
            return process1(s, p, si + 1, pi + 1);
        }

        // si 还有字符  pi也还有字符 且pi位置为*
        if (p[pi] == '*') {
            // 用pi位置的 * 匹配  ""  零个字符  ->  剩下的   si ... 与 pi+1  取匹配
            // 用pi位置的 * 匹配  [si]  一个字符  ->  剩下的   si+1 ... 与 pi+1  取匹配
            // 用pi位置的 * 匹配  [si, si+1]  两个字符 ->  剩下的   si+2 ... 与 pi+1  取匹配
            // 用pi位置的 * 匹配  [si, si+1, si+2]  三个字符 ->  剩下的   si+3 ... 与 pi+1  取匹配
            // 用pi位置的 * 匹配  [si, si+1, si+2, si+3]   四个字符 ->  剩下的   si+4 ... 与 pi+1  取匹配
            // 用pi位置的 * 匹配  [si, si+1, si+2, si+3,..., s.length-1] ->  剩下的   si+5 ... 与 pi+1  取匹配
            // 只要上面的情况有一个匹配出来就行

            // s  ... x y z a b c
            // p ...  *    a b c

            // 直接用* 匹配了 si ~ si+len 的字符串  这里与第10题  还是由差别的  第10题 只能变成 相同的字符串 a aa aaa(前面是普通字符的情况下，不是. )
            for (int len = 0; len <= s.length - si; len++) {
                if (process1(s, p, si + len, pi + 1)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 动态规划版本的
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isMatch2(String str, String pattern) {
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();

        int N = s.length;
        int M = p.length;

        boolean[][] dp = new boolean[N + 1][M + 1];

        // baseCase  si=s.length  pi=p.length
        dp[N][M] = true;

        // baseCase si=s.length  pi!=p.length
        for (int pi = p.length - 1; pi >= 0; pi--) {
            // pi pi+1 pi+2 ... 往后只要有一个不是* 就设置为false  这是我想到的！！！哈哈哈哈！！！
            dp[N][pi] = (p[pi] == '*') && dp[N][pi + 1];
        }
        // baseCase si!=s.length  pi==p.length  dp[si][pi]=false


        // 注意计算顺序  必须要用已知的结果来推算为止的结果
        // 因为要求的是dp[0][0] 已知的是dp[N][M] 所以dp数组计算顺序是  从右下角往左上角计算
        for (int si = N-1; si >=0; si--) {
            for (int pi = M-1; pi >=0; pi--) {
                // 下面的三个if只能命中其中一个

                // si 还有字符  pi也还有字符 且pi位置为普通字符
                if (p[pi] != '?' && p[pi] != '*') {
                    dp[si][pi] = (s[si] == p[pi]) && (dp[si + 1][pi + 1]);
                }

                // si 还有字符  pi也还有字符 且pi位置为?
                if (p[pi] == '?') {
                    dp[si][pi] = dp[si + 1][pi + 1];
                }

                // si 还有字符  pi也还有字符 且pi位置为*
                if (p[pi] == '*') {
                    // 用pi位置的 * 匹配  ""  零个字符  ->  剩下的   si ... 与 pi+1  取匹配
                    // 用pi位置的 * 匹配  [si]  一个字符  ->  剩下的   si+1 ... 与 pi+1  取匹配
                    // 用pi位置的 * 匹配  [si, si+1]  两个字符 ->  剩下的   si+2 ... 与 pi+1  取匹配
                    // 用pi位置的 * 匹配  [si, si+1, si+2]  三个字符 ->  剩下的   si+3 ... 与 pi+1  取匹配
                    // 用pi位置的 * 匹配  [si, si+1, si+2, si+3]   四个字符 ->  剩下的   si+4 ... 与 pi+1  取匹配
                    // 用pi位置的 * 匹配  [si, si+1, si+2, si+3,..., s.length-1] ->  剩下的   si+5 ... 与 pi+1  取匹配
                    // 只要上面的情况有一个匹配出来就行

                    // s  ... x y z a b c
                    // p ...  *    a b c

                    // si可以取到N 所以len<= s.length-si
                    for (int len = 0; len <= s.length - si; len++) {
                        if (dp[si + len][pi + 1]) {
                            dp[si][pi] =  true;
                        }
                    }
                }
            }
        }
        return dp[0][0];
    }


    /**
     * 通过观察临近位置的依赖关系  省略枚举行为
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isMatch3(String str, String pattern) {
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        int N = s.length;
        int M = p.length;
        boolean[][] dp = new boolean[N + 1][M + 1];
        dp[N][M] = true;
        for (int pi = M - 1; pi >= 0; pi--) {
            dp[N][pi] = p[pi] == '*' && dp[N][pi + 1];
        }
        for (int si = N - 1; si >= 0; si--) {
            for (int pi = M - 1; pi >= 0; pi--) {
                if (p[pi] != '?' && p[pi] != '*') {
                    dp[si][pi] = s[si] == p[pi] && dp[si + 1][pi + 1];
                    continue;
                }
                if (p[pi] == '?') {
                    dp[si][pi] = dp[si + 1][pi + 1];
                    continue;
                }
                // p[pi] == '*' 通过观察临近位置的依赖关系  省略枚举行为
                //     0 1 2 3 4 5 6 7 8  - pi
                //si 0
                //si 1         *
                //si 2
                //si 3
                //si 4
                //si 5

                //  注意p[pi]='*' 与si位置的字符没有关系
                //  dp[si][pi]   = dp[si][pi + 1] || dp[si + 1][pi + 1] || dp[si + 2][pi + 1] || dp[si + 3][pi + 1] ... ;
                //  dp[si+1][pi] =                  dp[si + 1][pi + 1] || dp[si + 2][pi + 1] || dp[si + 3][pi + 1]... ;
                //  dp[si][pi] = dp[si][pi + 1] || dp[si + 1][pi]
                // 省去枚举行为
                dp[si][pi] = dp[si][pi + 1] || dp[si + 1][pi];
            }
        }
        return dp[0][0];
    }


    /**
     *  最终做的化简 teacher
     */
    public static boolean isMatch4(String str, String pattern) {
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        int N = s.length;
        int M = p.length;
        boolean[][] dp = new boolean[N + 1][M + 1];
        dp[N][M] = true;
        for (int pi = M - 1; pi >= 0; pi--) {
            dp[N][pi] = p[pi] == '*' && dp[N][pi + 1];
        }
        for (int si = N - 1; si >= 0; si--) {
            for (int pi = M - 1; pi >= 0; pi--) {
                if (p[pi] != '*') {
                    dp[si][pi] = (p[pi] == '?' || s[si] == p[pi]) && dp[si + 1][pi + 1];
                } else {
                    dp[si][pi] = dp[si][pi + 1] || dp[si + 1][pi];
                }
            }
        }
        return dp[0][0];
    }



    public static void main(String[] args) {
        System.out.println(isMatch2("mississippi",
                "m??*ss*?i*pi"));
    }

}
