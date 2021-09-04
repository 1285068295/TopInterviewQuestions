package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/4
 * @version: V1.0
 * @slogan:
 * @description :无重复字符的最长子串
 *
 * 【思路】 计算每一个即str[i]结尾的子串的最大不重复长度
 * 有两个限制因素
 * 1 当前字符上次出现的位置
 * 2 以str[i-1]位置结尾最大长度多少
 * 以str[i]结尾的字符 一定是上面两个条件的较小的一个
 *
 * nice 一次通过！！！
 */
public class Problem_0003_LongestSubstringWithoutRepeatingCharacters {
    public static int lengthOfLongestSubstring(String s) {
        if (s==null || s.length() ==0){
            return 0;
        }
        char[] str = s.toCharArray();
        // 以str[i-1]字符结尾的字符的最大长度的索引位置
        int pre = -1;

        // 当前遍历的字符上一次出现的位置
        int[] preIndex = new int[256];
        for (int i = 0; i < preIndex.length; i++) {
            preIndex[i] = -1;
        }

        int maxLength = 0;
        for (int i = 0; i < str.length; i++) {
           // 当前字符距离最近的不重复的字符串的位置  取出索引大的一个
           int nearest =  Math.max(pre, preIndex[str[i]]);
           maxLength = Math.max(maxLength, i - nearest);

           // 更新str[i]字符出现的位置
           preIndex[str[i]] = i;
           // str[i-1]出现重复的位置 为下一次for循环使用做准备
           pre = nearest;
        }
        return maxLength;

    }

    public static void main(String[] args) {
        String s = "pwwwkew";
        lengthOfLongestSubstring(s);
    }

}
