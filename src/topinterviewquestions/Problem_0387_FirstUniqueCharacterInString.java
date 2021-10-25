package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/24
 * @version: V1.0
 * @slogan:
 * @description : 字符串中的第一个唯一字符
 *
 * 统计词频即可
 */
public class Problem_0387_FirstUniqueCharacterInString {

    public static int firstUniqChar(String s) {
        int[] chs = new int[26];

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chs[chars[i] - 'a']++;
        }
        for (int i = 0; i < chars.length; i++) {
            if (chs[chars[i] - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(firstUniqChar("leetcode"));
    }

}
