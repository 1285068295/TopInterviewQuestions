package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/9/22
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【全排列】
 * 输入：nums = [1,2,3]
 * 输出：[
 *          [1,2,3],[1,3,2],
 *          [2,1,3],[2,3,1],
 *          [3,1,2],[3,2,1]
 *      ]
 * 【解题思路】
 * 通过交换元素的方式 来得到所有的排列组合  注意恢复现场
 * 第1个元素与第1个元素交换
 * 第1个元素与第2个元素交换
 * 第1个元素与第3个元素交换
 *
 * 第2个元素与第2个元素交换
 * 第2个元素与第3个元素交换

 * 第3个元素与第3个元素交换
 *
 *
 */
public class Problem_0046_Permutations {

    public static List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        process(nums, 0, ans);
        return ans;

    }

    /**
     * 广度优先遍历得到结果
     * @param nums
     * @param cur
     * @param ans
     */
    private static void process(int[] nums, int cur,  List<List<Integer>> ans) {

        if (cur == nums.length){
            ArrayList<Integer> path = new ArrayList<>();
            for (int i :nums) {
                path.add(i);
            }
            ans.add(path);
        }else {
            for (int i = 0; i < nums.length - cur; i++) {
                // 因为是原地调整  所以需要恢复现场！！！
                swap(nums, cur, cur + i);
                process(nums, cur + 1, ans);
                // 恢复现场！！！
                swap(nums, cur + i, cur);
            }
        }
    }

    public static void swap(int[] arr, int i ,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

















    /**
     * 效率很低的求解方式
     */
    public static List<List<Integer>> onClass(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        HashSet<Integer> rest = new HashSet<>();
        for (int num : nums) {
            rest.add(num);
        }
        ArrayList<Integer> path = new ArrayList<>();
        f(rest, path, ans);
        return ans;
    }

    // rest中有剩余数字，已经选过的数字不在rest中，选过的数字在path里
    public static void f(HashSet<Integer> rest, ArrayList<Integer> path, List<List<Integer>> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            for (int num : rest) {
                ArrayList<Integer> curPath = new ArrayList<>(path);
                curPath.add(num);
                HashSet<Integer> clone = cloneExceptNum(rest, num);
                f(clone, curPath, ans);
            }
        }
    }

    public static HashSet<Integer> cloneExceptNum(HashSet<Integer> rest, int num) {
        HashSet<Integer> clone = new HashSet<>(rest);
        clone.remove(num);
        return clone;
    }







    public static void main(String[] args) {
        int[] arr = {1,2,3};
        List<List<Integer>> ans = permute(arr);
    }

}
