package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/27
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 与平方一样  使用二分法来求解
 *
 * 注意平方时会越界！！！
 *
 */
public class Problem_0069_SqrtX {


    public static int mySqrt(int x) {
        if (x == 0){
            return 0;
        }

        // 题目要求  向下取整
        long mostR = 1;
        long L = 1;
        long R = x;
        while (L <= R) {
            long mid = L + ((R - L) >> 1);
            long tmp = mid * mid;
            if (tmp < x) {
                mostR = mid;
                L = mid + 1;
            } else if (tmp > x) {
                R = mid - 1;
            } else {
                return (int) mid;
            }

        }
        return (int) mostR;
    }

    public static void main(String[] args) {

        System.out.println(mySqrt(2147395599));


    }

}
