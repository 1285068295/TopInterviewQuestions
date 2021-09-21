package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/21
 * @version: V1.0
 * @slogan:
 * @description :
 *【题目】
 * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 判断你是否能够到达最后一个下标
 *
 * 【解题思路】
 * 使用一个变量maxRight 每遍历一个位置  就更新当前位置能够到达的最右边界的位置
 * 如果存在某一处 i > maxRight 表示遍历的i位置超过了可以到达的最右边界  直接返回false
 *
 */
public class Problem_0055_JumpGame {

    public boolean canJump(int[] nums) {


        int maxRight = 0;
        for (int i = 0; i < nums.length; i++) {

            if (i > maxRight) {
                return false;
            }

            if (maxRight >= nums.length){
                return true;

            }

            maxRight = Math.max(maxRight, i + nums[i]);


        }
        return true;

    }
    
}
