package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/1
 * @version: V1.0
 * @slogan:
 * @description :子集
 *
 * 【解题思路】 深度优先遍历  每个点的位置有要和不要两种选择  需要注意的是 记得恢复现场！！
 *
 * 参见图片 Problem_0078_Subsets.jpg 递归的调用过程
 *
 */
public class Problem_0078_Subsets {

    public static List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();

        process(nums, path, 0, ans);
        return ans;
    }

    /**
     * 深度优先遍历即可
     * @param nums
     * @param ans
     * @return
     */
    private static void process(int[] nums, LinkedList<Integer> path, int i, List<List<Integer>> ans) {

        if (i == nums.length) {
            List<Integer> tmp = new ArrayList<>(path);
            ans.add(tmp);
        } else {
            // i位置的数据 有两种选择 要或者不要
            process(nums, path, i + 1, ans);
            // 保留i位置的数据
            path.add(nums[i]);
            process(nums, path, i + 1, ans);
            path.removeLast();
        }
    }

    /**
     *  1  2
     *
     *
     *    x  1
     *  x 2 x 2
     */



    public static void main(String[] args) {
        int[] nums = {1,2};
        subsets(nums);
    }

}
