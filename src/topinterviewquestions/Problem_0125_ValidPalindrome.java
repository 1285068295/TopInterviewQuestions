package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/4
 * @version: V1.0
 * @slogan:
 * @description :
 * 这道题就是考的coding
 * 准备两个指针L R 逐渐向中间移动
 *
 */
public class Problem_0125_ValidPalindrome {


    public static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        char[] str = s.toCharArray();
        int L = 0;
        int R = str.length - 1;

        while (L < R) {
            if (!isValid(str[L])) {
                L++;
            } else if (!isValid(str[R])) {
                R--;
            } else {
                if (!equals(str[L], str[R])) {
                    return false;
                }
                L++;
                R--;
            }
        }
        return true;

    }

    /**
     * 判断是否相等
     */
    public static boolean equals(char c1, char c2) {

        if (isNumber(c1) && isNumber(c2)) {
            return c1 == c2;
        }

        if (isNumber(c1) && isChar(c2)){
            return false;
        }


        if (isNumber(c2) && isChar(c1)){
            return false;
        }

        // 忽略大小写  这里的判断必须是同时字母的情况
        return Math.abs(c1 - c2) == 32 || c1 == c2;

    }


    public static boolean isValid(char s) {
        return isNumber(s) || isChar(s);
    }

    public static boolean isChar(char s) {
        return (s >= 'a' && s <= 'z') || (s >= 'A' && s <= 'Z');
    }


    public static void main(String[] args) {
        isPalindrome("0P");
    }












    /**
     * 老师的解法  代码写的很优秀
     */
    public static boolean isPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toCharArray();
        int L = 0;
        int R = str.length - 1;
        while (L < R) {
            if (validChar(str[L]) && validChar(str[R])) {
                if (!equal(str[L], str[R])) {
                    return false;
                }
                L++;
                R--;
            } else {
                L += validChar(str[L]) ? 0 : 1;
                R -= validChar(str[R]) ? 0 : 1;
            }
        }
        return true;
    }

    public static boolean validChar(char c) {
        return isLetter(c) || isNumber(c);
    }

    public static boolean equal(char c1, char c2) {
        if (isNumber(c1) || isNumber(c2)) {
            return c1 == c2;
        }
        return (c1 == c2) || (Math.max(c1, c2) - Math.min(c1, c2) == 32);
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }







}
