package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/20
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 *  1 没有 0 时, 数组的所有元素乘积得到一个结果，求除arr[i]位置 其他所有元素的乘积时 = all/arr[j]
 *  2 有1个0时, 只有0 位置对应其他的所有元素乘积不为0 其他的位置都是0
 *  3 有2或者2个以上0时, 结果都是0
 *
 *
 */
public class Problem_0238_ProductOfArrayExceptSelf {

    public int[] productExceptSelf(int[] nums) {
        int zeroNum = 0;
        int all = 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroNum++;
            } else {
                all *= nums[i];
            }
        }

        int[] ans = new int[nums.length];
        if (zeroNum == 1) {
            for (int i = 0; i < ans.length; i++) {
                ans[i] = nums[i] == 0 ? all : 0;
            }
        } else if (zeroNum == 0) {
            for (int i = 0; i < ans.length; i++) {
                ans[i] = all / nums[i];
            }
        }
        // zeroNum>=2时 结果都是0
        return ans;
    }






}
