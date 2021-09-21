package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/21
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【题目】 55题的升级版：
 * 给你一个非负整数数组 nums ，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 *
 * 假设你总是可以到达数组的最后一个位置。
 *
 * 【解题思路】
 * 需要三个变量
 * step  当前跳跃多少步可以来到i位置  当i>cur时  更新step
 * cur   跳跃step步数的情况下  最远可以来到的右边界位置   next定义知  cur每次需要更新时  直接用next赋值个cur即可
 * next  跳跃step+1步数的情况下  最远可以来到的右边界位置
 * 最终返回step
 *
 */
public class Problem_0045_JumpGameII {


    public static int jump(int[] nums) {

        int step = 0;
        int cur = 0;
        int next = nums[0];

        for (int i = 0; i < nums.length; i++) {
            if (i > cur) {
                cur = next;
                step++;
            }
            // 每次都需要更新next
            next = Math.max(next, i + nums[i]);
        }

        return step;
    }


    public static void main(String[] args) {
        int[] nums = {2,3,0,1,4};
        System.out.println(jump(nums));

    }


}
