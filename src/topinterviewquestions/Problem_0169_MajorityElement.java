package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/9
 * @version: V1.0
 * @slogan:
 * @description : 找到数组中 个数大于一半的元素
 *
 * 【思路】 每次消去两个不同的数 最后剩下的就是大于一半的数  妙哉呀！！！
 *  具体做法参见代码
 */
public class Problem_0169_MajorityElement {


    public int majorityElement(int[] nums) {
        int cand = nums[0];
        int HP = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == cand) {
                HP++;
            } else {
                if (HP == 0) {
                    HP++;
                    cand = nums[i];
                } else {
                    HP--;
                }
            }
        }
        return cand;
    }
}
