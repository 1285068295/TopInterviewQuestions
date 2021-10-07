package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/6
 * @version: V1.0
 * @slogan:
 * @description :只出现一次的数字
 * 【解题思路】
 * 使用异或操作
 * <p>
 * 拓展 异或实现加法运算
 */
public class Problem_0136_SingleNumber {

    public int singleNumber(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            ans ^= nums[i];
        }

        return ans;
    }
}
