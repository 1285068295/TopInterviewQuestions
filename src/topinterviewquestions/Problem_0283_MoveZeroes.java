package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/17
 * @version: V1.0
 * @slogan:
 * @description :给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 【解题思路】
 *  arr[R] 不为0时 与arr[L] 的下一个数交换
 *
 */
public class Problem_0283_MoveZeroes {


    /**
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     */
    public void moveZeroes(int[] nums) {

        int L = -1;
        int R = 0;
        while (R < nums.length) {
            if (nums[R] != 0) {
                swap(nums, ++L, R);
            }
            R++;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;

    }
}
