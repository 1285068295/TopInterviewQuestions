package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/9/16
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 这里利用来了只有小括号（一种括号）的情况下，如何判断是否是有效的括号的机制，即用一个数字 n 来记录沿途遍历的
 * 括号，遇到左括号就++ 遇到右括号就-- 遍历过程中不能有小于0的情况出现。
 *
 * 如果给定 n  只需要求出方案数  可以直接用 卡兰特数公式求解即可
 * 本身考察的还是深度优先遍历过程
 *
 */
public class Problem_0022_GenerateParentheses {


    /**
     * 1 <= n <= 8
     * @param n
     * @return
     */
    public static List<String> generateParenthesis(int n) {
        char[] path = new char[n << 1];
        List<String> ans = new ArrayList<>();
        process(0, 0, 0, n, path, ans);
        return ans;
    }

    /**
     * @param index  当前处理括号的位置
     * @param waitingToPair    已经使用的左括号数量 - 已经使用的右括号数量 = path中还没配对的左括号数量
     * @param usedLeft   已经使用的左括号数量
     * @param n    总的括号对数
     * @param path   沿途的路径
     * @param ans    最终答案
     */
    private static void process(int index, int waitingToPair, int usedLeft, int n, char[] path, List<String> ans) {
        if (index == n << 1) {
            ans.add(String.valueOf(path));
        } else {
            // 当前的index位置 可以取值的情况有两种 '('  ')'
            if (usedLeft < n) {
                path[index] = '(';
                process(index + 1, waitingToPair + 1, usedLeft + 1, n, path, ans);
            }
            if (waitingToPair > 0) {
                path[index] = ')';
                process(index + 1, waitingToPair - 1, usedLeft, n, path, ans);
            }
        }
    }


    /**
     * teacher
     * @param n
     * @return
     */
    public static List<String> generateParenthesis1(int n) {
        char[] path = new char[n << 1];
        List<String> ans = new ArrayList<>();
        process(path, 0, 0, n, ans);
        return ans;
    }

    // 依次在path上填写决定
    // ( ( ) ) ( )....
    // 0 1 2 3 4 5
    // path[0...index-1]决定已经做完了
    // index位置上，( )
    public static void process(char[] path, int index, int leftMinusRight, int leftRest, List<String> ans) {
        if (index == path.length) {
            ans.add(String.valueOf(path));
        } else {
            if (leftRest > 0) {
                path[index] = '(';
                process(path, index + 1, leftMinusRight + 1, leftRest - 1, ans);
            }
            if (leftMinusRight > 0) {
                path[index] = ')';
                process(path, index + 1, leftMinusRight - 1, leftRest, ans);
            }
        }
    }

    // 不剪枝的做法
    public static List<String> generateParenthesis2(int n) {
        char[] path = new char[n << 1];
        List<String> ans = new ArrayList<>();
        process2(path, 0, ans);
        return ans;
    }

    public static void process2(char[] path, int index, List<String> ans) {
        if (index == path.length) {
            if (isValid(path)) {
                ans.add(String.valueOf(path));
            }
        } else {
            path[index] = '(';
            process2(path, index + 1, ans);
            path[index] = ')';
            process2(path, index + 1, ans);
        }
    }

    public static boolean isValid(char[] path) {
        int count = 0;
        for (char cha : path) {
            if (cha == '(') {
                count++;
            } else {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }




    public static void main(String[] args) {
        generateParenthesis(3);
    }

}
