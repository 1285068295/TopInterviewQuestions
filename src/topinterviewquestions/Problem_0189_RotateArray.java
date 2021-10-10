package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description :
 * 【思路1】 左半部分逆序 右半部分逆序 整体逆序
 *   a b c d   e f g
 *   d c b a   g f e
 *   e f g a   b c d
 *
 * 【思路2】
 *  a b c d e f | g h i j
 *  a-g  b-h c-i d-j
 *
 *  g h i j [e f| a b c d]
 *  左半部分g h i j 已经调整好了
 *  继续调整剩下的 e f | a b c d   -> [ c d | a b ]e f
 *  继续调整 c d | a b 左右两半部分长度相等  直接互换位置   a b c d
 *
 */
public class Problem_0189_RotateArray {

    public void rotate1(int[] nums, int k) {
        int N = nums.length;
        k = k % N;
        reverse(nums, 0, N - k - 1);
        reverse(nums, N - k, N - 1);
        reverse(nums, 0, N - 1);
    }

    public static void reverse(int[] nums, int L, int R) {
        while (L < R) {
            int tmp = nums[L];
            nums[L++] = nums[R];
            nums[R--] = tmp;
        }
    }



    public static void rotate2(int[] nums, int k) {
        int N = nums.length;
        k = k % N;
        if (k == 0) {
            return;
        }
        int L = 0;
        int R = N - 1;
        int lpart = N - k;
        int rpart = k;
        int same = Math.min(lpart, rpart);
        int diff = lpart - rpart;
        exchange(nums, L, R, same);
        while (diff != 0) {
            if (diff > 0) {
                L += same;
                lpart = diff;
            } else {
                R -= same;
                rpart = -diff;
            }
            same = Math.min(lpart, rpart);
            diff = lpart - rpart;
            exchange(nums, L, R, same);
        }
    }

    public static void exchange(int[] nums, int start, int end, int size) {
        int i = end - size + 1;
        int tmp = 0;
        while (size-- != 0) {
            tmp = nums[start];
            nums[start] = nums[i];
            nums[i] = tmp;
            start++;
            i++;
        }
    }


}
