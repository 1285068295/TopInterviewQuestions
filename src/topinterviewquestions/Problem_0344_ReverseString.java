package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0344_ReverseString {


    public void reverseString(char[] s) {
        int L = 0;
        int R = s.length - 1;
        while (L < R){
            char tmp = s[L];
            s[L]= s[R];
            s[R] = tmp;
            L++;
            R--;
        }
    }
}
