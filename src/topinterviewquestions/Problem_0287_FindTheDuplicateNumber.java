package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/15
 * @version: V1.0
 * @slogan:
 * @description :
 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
 * 你设计的解决方案必须不修改数组 nums 且只用常量级 O(1) 的额外空间。
 *
 * 【题目原型】 找打有环链表的环入口点
 *  5 8 6 4 9 2 5 3 1 7
 *  0 1 2 3 4 5 6 7 8 9
 *
 *  fast = 5 -> 6 -> 5
 *  slow = 5 -> 2 -> 5
 *
 *  因为数字是 1~n 所以一定不会回到 index=0
 *
 */
public class Problem_0287_FindTheDuplicateNumber {

    public int findDuplicate(int[] nums) {
        int fast = nums[0];
        int slow = nums[0];

        while(fast != nums.length){
            fast = nums[nums[fast]];
            slow = nums[slow];
            if (fast == slow){
                break;
            }
        }

        // fast 回到原点 再次与slow相遇时就是环入口
        fast = nums[0];
        while (fast != slow){
            fast = nums[fast];
            slow = nums[slow];
        }
        return slow;
    }

}