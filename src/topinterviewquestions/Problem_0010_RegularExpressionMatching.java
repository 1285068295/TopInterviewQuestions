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
 * 题目指示
 * 1 0 <= s.length <= 20
 * 2 0 <= p.length <= 30
 * 3 s 可能为空，且只包含从 a-z 的小写字母。
 * 4 p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
 * 5 保证每次出现字符 * 时，前面都匹配到有效的字符
 * 由第5点  知道必须左isValid 方法的前置校验！！！
 *
 *
 *
 *
 */
public class Problem_0010_RegularExpressionMatching {

    // **********************下面代码为 20210921 学完44题后回来重写的  **************************************
    /**
     * 暴力版本  有bug  测试用例没通过！！！  日！！！
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch11(String s, String p) {
        if(s == null || p == null){
            return false;
        }

        char[] str = s.toCharArray();
        char[] pattern = p.toCharArray();

        // isValid 校验str字符换 同时校验patten字符串
        return isValid(str, pattern) && process11(str, pattern, 0, 0) ;
    }


    private static boolean process11(char[] str, char[] pattern, int si, int pi) {
        if(si == str.length){
            // baseCase  与 44题不同呀 老哥

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


        // si!=str.length  pi=pattern.length 一定是false的
        if (pi == pattern.length) {
            return false;

        }

        // si!=str.length  pi!=pattern.length
        // 因为*必须与前置字符配合使用  所以我们的大的分类方向是 pi位置看下  pi+1位置是否为*
        // 这样就能保证 我们递归函数在调用过程中 一定不会单独的面都pi位置为 * 的情况
        // 整个process11函数在调用的过程中 我们可以保证pi位置不是*  所以pi位置是 * 的情况不需要讨论！！！  这一点很重要！！！

        // 特别的注意 在isValid的方法中已经校验了 pattern中不存在连续的 * ！！！

        // pi+1 位置不是 *的情况
        if (pi + 1 == pattern.length || pattern[pi + 1] != '*') {
            // pi是.的情况 和 普通字符
            return (pattern[pi] == '.' || str[si] == pattern[pi]) && process11(str, pattern, si + 1, pi + 1);
        }


        // pi+1位置是*情况  在isValid中校验了不能存在连续的* 所以pi+1为*  pi+2 一定不是*
        // 继续分类 pi位置为普通字符  和 . 情况
        // .* --> 可以转为 .........  任意个数的.  所以要特殊讨论
        if(pattern[pi] != '.'){
            // aaa bc
            // a*  abc  ->  a aa  aaa  aaaa  aaaa ...
            // a*  可以变成  0个a  1个a  2个a

            // si与pi不相等的情况
            if (pattern[pi] != str[si]) {
                // 此时只能把  pi 与pi+1 当作空串处理
                return process11(str, pattern, si, pi + 2);
            }

            // 下面的情况是  str[si] = pattern[pi]的情况



            // str[si] = pattern[pi]
            // 计算出str中存在多个重复的字符
            int len = 1;
            while(si + len < str.length && str[si] == str[si+len]){
                len++;
            }


            for (int i = 0; i <= len; i++) {
                // pi *作为 ""  "a"  "aa"  "aaa" ...  去与str中  aaa (相同的字符)进行匹配
                if (process11(str, pattern, si + i, pi + 2)) {
                    return true;
                }
            }

            // 上面的for循环执行完 没有返回true  说明 pi作为 ""(空串)  "a" "aa"  ... 没有一个可以与str中 aaa(相同的字符串) 匹配上
            // 最后执行process11 最后代码 返回false
        }

        if (pattern[pi] == '.') {
            // pi位置的 . 与 pi+1 位置的 *  组成 .  .. ... ....  (一个点  两个点  三个点 ...) 与str后面的字符去匹配
            for (int i = 1; i <= str.length - si; i++) {
                if (process11(str, pattern, si + i, pi + 2)) {
                    return true;
                }
            }
        }

        return false;

    }



    // 动态规划版本的有时间再写！！！  TODO


    public static void main(String[] args) {
        System.out.println(isMatch4(
                "aaa",
                "ab*a"));

    }














    // ***************************上面代码为20210921 学习完成44题后  回来重写的版本************************


















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



        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                System.out.print(dp[i][j] +"\t");

            }
            System.out.println();
        }

        System.out.println();
        return dp[0][0];
    }





}
