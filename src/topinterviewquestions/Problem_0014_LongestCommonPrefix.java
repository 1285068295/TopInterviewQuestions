package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/13
 * @version: V1.0
 * @slogan:
 * @description :最大公共前缀
 * 【解题思路】 取出第一个字符串 一个一个字符进行比较即可
 *
 */
public class Problem_0014_LongestCommonPrefix {


    /**
     * 这种for循环不好  因为如果 strs的长度很长的情况下 如全是"abcde" "abcde" "abc"  "abcde"
     * 外层循环为chs 效率很低  不明白为什么leetcode 给出的效率很低
     *
     * 老师的做法是一个字符串一个字符串进行遍历
     */
    public static String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0){
            return "";
        }

        char[] chs = strs[0].toCharArray();

        int index = 0;
        for (; index < chs.length; index++) {
            for (String str : strs) {
                char[] tmp = str.toCharArray();
                if (index == tmp.length || tmp[index] != chs[index]) {
                        return strs[0].substring(0, index);
                }
            }
        }
        return strs[0].substring(0, index);
    }




    /**
     * teacher
     * 想来想去  与我的方案效率应该是一样的
     * "flower",
     * "flow",
     * "flight"
     *  必须要所有的字符遍历一遍  找到最短的公共前缀才行  就是说必要过一遍所有的字符串
     */
    public static String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        char[] chs = strs[0].toCharArray();
        int min = Integer.MAX_VALUE;
        for (String str : strs) {
            char[] tmp = str.toCharArray();
            int index = 0;
            while (index < tmp.length && index < chs.length) {
                if (chs[index] != tmp[index]) {
                    break;
                }
                index++;
            }
            min = Math.min(index, min);
            if (min == 0) {
                return "";
            }
        }
        return strs[0].substring(0, min);
    }

    public static void main(String[] args) {

        String[] s = {"flower","flow","flight"};
//        String[] s = {"ab", "a"};
        System.out.println(longestCommonPrefix2(s));

    }
}
