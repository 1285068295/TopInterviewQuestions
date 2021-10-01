package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/27
 * @version: V1.0
 * @slogan:
 * @description :加一
 */
public class Problem_0066_PlusOne {


    public int[] plusOne(int[] digits) {
        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        // 上面的for循环结束 没有retrun一定产生了进位
        int[] ans = new int[len + 1];
        ans[0] = 1;
        return ans;
    }
}
