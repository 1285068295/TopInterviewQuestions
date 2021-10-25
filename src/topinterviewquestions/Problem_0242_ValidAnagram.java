package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 统计第一个单词的词频，再去遍历第二个单词  出现词频小于0的情况 就返回false
 */
public class Problem_0242_ValidAnagram {

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] str = s.toCharArray();
        char[] tar = t.toCharArray();


        int[] count = new int[256];
        for (int i = 0; i < str.length; i++) {
            count[str[i]]++;
        }
        for (int i = 0; i < tar.length; i++) {
            count[tar[i]]--;
            if (count[tar[i]] < 0) {
                return false;
            }
        }
        return true;
    }

}
