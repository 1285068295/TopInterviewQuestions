package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/8
 * @version: V1.0
 * @slogan:
 * @description :解题思路 str[i...] 能不能用pattern[pi...]变出来
 *
 * .不能变成空字符
 * s : abcd
 * p:  .*  -> .... ->abcd
 *
 *
 */
public class Problem_0010_RegularExpressionMatching {

    public static boolean isMatch1(String s, String p) {
        if(s == null || p == null){
            return false;
        }

        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();

        return isValid(str, pattern) && process1(str, pattern, 0, 0) ;
    }

    /**
     * 前置校验
     * @param str
     * @param pattern
     * @return
     *
     * str中不能包含* .
     * 因为*必须和已知的字符结合使用
     * pattern 中不能包含连续的*
     */
    private static boolean isValid(char[] str, char[] pattern) {
        for (char s : str) {
            if(s == '*' || s == '.'){
                return false;
            }
        }
        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == '*' && (i == 0 || pattern[i - 1] == '*')) {
                return false;
            }
        }
        return true;

    }



    /**
     * 暴力递归方式
     * str[si.....] 能否被 pattern[pi...] 变出来
     * 潜台词：pi位置，pattern[pi] != '*'  是因为* 必须要和非*的字符结合才能使用
     * (这个方法调用过程中 pattern[pi] != '*')
     *
     * 大的分类方向是
     * pi不为*时 根据pi+1是否为*的情况进行分类
     *
     */
    private static boolean process1(char[] str, char[] pattern, int si, int pi) {
        if(si== str.length){
            // str此时为空串
            if (pi == pattern.length) {
                return true;
            }
            // 用pattern pi...变成空串str的情况!!!
            // 如 "a*" 可以变成""  pi的位置没有影响  pi+1位置必须为*
            if (pi + 1 < pattern.length && pattern[pi + 1] == '*') {
                return process1(str, pattern, si, pi + 2);
            }
            return false;
        }

        // si没越界  pi越界
        if(pi == pattern.length){
            return false;
        }

        // si 没越界  pi没越界
        // 大的分类情况 pi+1的位置是否为*
        if (pi + 1 == pattern.length || pattern[pi + 1] != '*') {
            // pi+1位置不为*的情况
            // si位置与pi位置必须匹配的情况想  si+1与pi+1去递归处理
            return (str[si] == pattern[pi] || pattern[pi] == '.') && process1(str, pattern, si + 1, pi + 1);
        }

        // si 没越界  pi没越界
        // pi+1位置为*的情况  pi位置与si位置不匹配  此时必须把pi和pi+1变成空串
        if (pattern[pi] != '.' && str[si] != pattern[pi]) {
            return process1(str, pattern, si, pi + 2);
        }

        //  pi+1位置为*的情况   pi位置为.或者pi位置可以与si位置匹配的情况下
        // 总的pi和pi+1位置  变成空串  si+1和pi+2匹配
        //                      1份.  si+1和pi+2匹配
        //                      2份.  si+1和pi+2匹配
        //                      3份.  si+1和pi+2匹配
        //                      4份.  si+1和pi+2匹配
        //                      .....
        //                      只要有一个能匹配上就行
        while (si < str.length && (str[si] == pattern[pi] || pattern[pi] == '.')) {
            if (process1(str, pattern, si , pi + 2)) {
                return true;
            }
            si++;
        }
        // 最后不能直接返回false 从上面的while循环出来后  依然要进行process处理判断
        // str  abcd  pattern .*e
        // while结束时  str为空串  pattern 为e
        return process1(str, pattern, si , pi + 2);
    }







    /**
     * 傻缓存的形式
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch2(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();

        int[][] dp = new int[str.length + 1][pattern.length + 1];
        for (int i = 0; i < str.length + 1; i++) {
            for (int j = 0; j < pattern.length + 1; j++) {
                dp[i][j] = -1;
            }
        }
        return isValid(str, pattern) && process2(str, pattern, 0, 0, dp);
    }


    /**
     * 傻缓存的递归方式
     * str[si.....] 能否被 pattern[pi...] 变出来
     * 潜台词：pi位置，pattern[pi] != '*'  是因为* 必须要和非*的字符结合才能使用
     * (这个方法调用过程中 pattern[pi] != '*')
     * <p>
     * 大的分类方向是
     * pi不为*时 根据pi+1是否为*的情况进行分类
     * <p>
     * dp的值为-1 表示没计算过
     * dp的值为0 表示false
     * dp的值为1 表示true
     */
    private static boolean process2(char[] str, char[] pattern, int si, int pi, int[][] dp) {
        if (dp[si][pi] != -1) {
            return dp[si][pi] == 1;
        }

        if (si == str.length) {
            // str此时为空串
            if (pi == pattern.length) {
                dp[si][pi] = 1;
                return true;
            }
            // 用pattern pi...变成空串str的情况!!!
            // 如 "a*" 可以变成""  pi的位置没有影响  pi+1位置必须为*
            if (pi + 1 < pattern.length && pattern[pi + 1] == '*') {
                boolean ans = process2(str, pattern, si, pi + 2, dp);
                dp[si][pi] = ans ? 1 : 0;
                return ans;
            }
            return false;
        }

        // si没越界  pi越界
        if (pi == pattern.length) {
            dp[si][pi] = 0;
            return false;
        }

        // si 没越界  pi没越界
        // 大的分类情况 pi+1的位置是否为*
        if (pi + 1 == pattern.length || pattern[pi + 1] != '*') {
            // pi+1位置不为*的情况
            // si位置与pi位置必须匹配的情况想  si+1与pi+1去递归处理
            boolean ans = (str[si] == pattern[pi] || pattern[pi] == '.') && process2(str, pattern, si + 1, pi + 1, dp);
            dp[si][pi] = ans ? 1 : 0;
            return ans;
        }

        // si 没越界  pi没越界
        // pi+1位置为*的情况  pi位置与si位置不匹配  此时必须把pi和pi+1变成空串
        if (pattern[pi] != '.' && str[si] != pattern[pi]) {
            boolean ans = process2(str, pattern, si, pi + 2, dp);
            dp[si][pi] = ans ? 1 : 0;
            return ans;
        }

        //  pi+1位置为*的情况   pi位置为.或者pi位置可以与si位置匹配的情况下
        // 总的pi和pi+1位置  变成空串  si+1和pi+2匹配
        //                      1份.  si+1和pi+2匹配
        //                      2份.  si+1和pi+2匹配
        //                      3份.  si+1和pi+2匹配
        //                      4份.  si+1和pi+2匹配
        //                      .....
        //                      只要有一个能匹配上就行
        while (si < str.length && (str[si] == pattern[pi] || pattern[pi] == '.')) {
            if (process2(str, pattern, si, pi + 2, dp)) {
                dp[si][pi] = 1;
                return true;
            }
            si++;
        }
        // 最后不能直接返回false 从上面的while循环出来后  依然要进行process处理判断
        // str  abcd  pattern .*e
        // while结束时  str为空串  pattern 为e
        boolean ans = process2(str, pattern, si, pi + 2, dp);
        dp[si][pi] = ans ? 1 : 0;
        return ans;
    }












    // 以下的代码是课后的优化
    // 有代码逻辑精简的优化，还包含一个重要的枚举行为优化
    // 请先理解课上的内容
    public static boolean isMatch3(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();
        return isValid(str, pattern) && process3(str, pattern, 0, 0);
    }

    // 举例说明枚举行为优化
    // 求状态(si = 3, pi = 7)时，假设状况如下
    // str : a a a b ...
    // si  : 3 4 5 6 ...
    // pat : a * ? ...
    // pi  : 7 8 9 ...
    // 状态(si = 3, pi = 7)会依赖：
    //   状态(si = 3, pi = 9)
    //   状态(si = 4, pi = 9)
    //   状态(si = 5, pi = 9)
    //   状态(si = 6, pi = 9)
    //
    // 求状态(si = 2, pi = 7)时，假设状况如下
    // str : a a a a b ...
    // si  : 2 3 4 5 6 ...
    // pat : a * ? ...
    // pi  : 7 8 9 ...
    // 状态(si = 2, pi = 7)会依赖：
    //   状态(si = 2, pi = 9)
    //   状态(si = 3, pi = 9)
    //   状态(si = 4, pi = 9)
    //   状态(si = 5, pi = 9)
    //   状态(si = 6, pi = 9)
    //
    // 注意看状态(si = 2, pi = 7)依赖的后4个，其实就是状态(si = 3, pi = 7)
    // 所以状态(si = 2, pi = 7)的依赖可以化简为：
    //   状态(si = 2, pi = 9)
    //   状态(si = 3, pi = 7)
    // 这样枚举行为就被化简成了有限两个状态，详细情况看代码
    public static boolean process3(char[] str, char[] pattern, int si, int pi) {
        if (si == str.length && pi == pattern.length) {
            return true;
        }
        if (si == str.length) {
            return (pi + 1 < pattern.length && pattern[pi + 1] == '*') && process3(str, pattern, si, pi + 2);
        }
        if (pi == pattern.length) {
            return false;
        }
        if (pi + 1 >= pattern.length || pattern[pi + 1] != '*') {
            return ((str[si] == pattern[pi]) || (pattern[pi] == '.')) && process3(str, pattern, si + 1, pi + 1);
        }
        // 此处为枚举行为优化，含义看函数注释
        if ((str[si] == pattern[pi] || pattern[pi] == '.') && process3(str, pattern, si + 1, pi)) {
            return true;
        }
        return process3(str, pattern, si, pi + 2);
    }

    // 以下的代码是枚举行为优化后的尝试函数，改成动态规划的解
    // 请先理解基础班中"暴力递归改动态规划"的内容
    // 如果str长度为N，pattern长度为M，最终时间复杂度为O(N*M)
    public static boolean isMatch4(String str, String pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        if (!isValid(s, p)) {
            return false;
        }
        int N = s.length;
        int M = p.length;
        boolean[][] dp = new boolean[N + 1][M + 1];
        dp[N][M] = true;
        for (int j = M - 1; j >= 0; j--) {
            dp[N][j] = (j + 1 < M && p[j + 1] == '*') && dp[N][j + 2];
        }
        // dp[0..N-2][M-1]都等于false，只有dp[N-1][M-1]需要讨论
        if (N > 0 && M > 0) {
            dp[N - 1][M - 1] = (s[N - 1] == p[M - 1] || p[M - 1] == '.');
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = M - 2; j >= 0; j--) {
                if (p[j + 1] != '*') {
                    dp[i][j] = ((s[i] == p[j]) || (p[j] == '.')) && dp[i + 1][j + 1];
                } else {
                    if ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }



    public static void main(String[] args) {


        System.out.println(isMatch2("a","a*"));
    }




}
