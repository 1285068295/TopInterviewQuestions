package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/9/14
 * @version: V1.0
 * @slogan:
 * @description :电话号码的字母组合
 *
 * 【解题思路】直接暴力的深度优先遍历然后收集每个答案即可，因为按下的数字是已知的所以生成的每个结果的长度必然是固定的
 * 所以使用数组即可  不需要用ArrayList 可以节省空间
 *
 */
public class Problem_0017_LetterCombinationsOfAPhoneNumber {
    public static char[][] phone = {
            { 'a', 'b', 'c' },      // 2    0
            { 'd', 'e', 'f' },      // 3    1
            { 'g', 'h', 'i' },      // 4    2
            { 'j', 'k', 'l' },      // 5    3
            { 'm', 'n', 'o' },      // 6
            { 'p', 'q', 'r', 's' }, // 7
            { 't', 'u', 'v' },      // 8
            { 'w', 'x', 'y', 'z' }, // 9
    };

    public static List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return ans;
        }
        int length = digits.length();
        char[] chs = digits.toCharArray();
        char[] path = new char[length];
        process(chs,0, path, ans);
        return ans;
    }


    /**
     * 深度优先遍历  直接暴力求解即可
     * @param str  按键数字
     * @param index 第几个按键
     * @param path  沿途的路径
     * @param ans   最终的结果集
     */
    private static void process(char[] str, int index, char[] path, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(path));
        } else {
            char[] cands = phone[str[index] - '2'];
            for (char cur : cands) {
                path[index] = cur;
                // 需要注意的是 不能使用index++来处理index的加1操作
                process(str, index + 1, path, ans);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("2"));
        System.out.println(letterCombinations(""));
    }


}
