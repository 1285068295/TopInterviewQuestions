package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/5
 * @version: V1.0
 * @slogan:
 * @description :
 * 【整体思路】  str = "aabaac"
 *  设计递归f函数返回从 index位置往后 可以构建的回文串
 *  str=a+f()
 *      aa+f()
 *      aab+f()
 *      aaba+f()
 */
public class Problem_0131_PalindromePartitioning {

    public static List<List<String>> partition(String s) {
        char[] str = s.toCharArray();
        // dp[L][R] -> 是不是回文
        boolean[][] dp = getDp(s.toCharArray());
        List<List<String>> ans = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        f(s, 0, dp, path, ans);
        return ans;
    }


    /**
     * str在任意区间段 L~R上是否为回文串
     * @param str
     * @return
     */
    private static boolean[][] getDp(char[] str) {

        int N = str.length;
        boolean[][] dp = new boolean[N][N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = true;
            dp[i][i + 1] = str[i] == str[i + 1];
        }
        dp[N - 1][N - 1] = true;
        for (int j = 2; j < N; j++) {
            int row = 0;
            int col = j;
            while (row < N && col < N) {
                dp[row][col] = str[row] == str[col] && dp[row + 1][col - 1];
                row++;
                col++;
            }
        }
        return dp;
    }

    /**
     * s 字符串
     * s[0...index-1] 已经做过的决定，放入了path中
     * 在index开始做属于这个位置的决定，
     * index == s.len  path之前做的决定（一种分割方法），放进总答案ans里
     */
    private static void f(String str, int index, boolean[][]dp, LinkedList<String> path, List<List<String>> ans) {
        if (index == str.length()){
            List<String> tmp = new ArrayList<>();
            for (String s : path) {
                tmp.add(s);
            }
           ans.add(tmp);
        }else {
            for (int i = index; i < str.length(); i++) {
                // 深度优先遍历  提前进行剪枝
                if (dp[index][i]) {
                    String cur = str.substring(index, i + 1);
                    path.add(cur);
                    f(str, i + 1, dp, path, ans);
                    //  踩坑点 应为path 中存在相同元素 所以不能直接调用 "path.remove(cur)"
                    path.removeLast();
                }

            }
        }
    }

    public static void main(String[] args) {
        partition("aab");
    }

}
