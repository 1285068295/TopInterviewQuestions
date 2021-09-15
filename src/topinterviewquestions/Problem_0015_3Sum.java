package topinterviewquestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/9/14
 * @version: V1.0
 * @slogan:
 * @description :为了保证结果唯一性，需要对数组进行排序操作，然后转换为2数之和问题
 * 计算每一个以arr[i] 开头的情况下  累计和为  sun-b-c的结果集
 *
 *
 */
public class Problem_0015_3Sum {


    public static List<List<Integer>> threeSum(int[] nums) {
        return threeSumOfK(nums, 0);
    }


    /**
     * 三个数求任何和问题
     */
    public static List<List<Integer>> threeSumOfK(int[] nums, int k) {
        List<List<Integer>> ans = new LinkedList<>();
        if(nums == null || nums.length <3){
            return ans;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int target = k - nums[i];
            List<List<Integer>> list = twoSum(nums, i + 1, target);
            // 要保证与之前的结果没有重复
            if (!list.isEmpty()) {
                if (i == 0 || (nums[i] != nums[i - 1])) {
                    for (List<Integer> twoSumList : list) {
                        ((LinkedList<Integer>) twoSumList).addFirst(nums[i]);
                        ans.add(twoSumList);
                    }
                }
            }
        }
        return ans;
    }


    /**
     * 数组中从index位置往后，找到两个数的和为target的两个数
     * 这里的数组是有序的所以可以用双指针来求解
     */
    public static List<List<Integer>> twoSum(int[] nums, int index, int target) {
        List<List<Integer>> ans = new LinkedList<>();
        if (index >= nums.length) {
            return ans;
        }
        int left = index;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] < target) {
                left++;
            } else if (nums[left] + nums[right] > target) {
                right--;
            } else {
                // 要保证之前的没有收集过结果
                if (left == index || nums[left] != nums[left - 1]) {
                    LinkedList<Integer> res = new LinkedList<>();
                    res.add(nums[left]);
                    res.add(nums[right]);
                    ans.add(res);
                }
                // 之前已经收集过答案了  没有收集答案这里也要执行++否则死循环
                left++;

            }
        }
        return ans;
    }







    // teacher1
    public static List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        // 第一个数选了i位置的数
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i - 1] != nums[i]) {
                List<List<Integer>> nexts = twoSum1(nums, i + 1, -nums[i]);
                for (List<Integer> cur : nexts) {
                    // 为了保证最后的结果集都是从小大排序的
                    cur.add(0, nums[i]);
                    ans.add(cur);
                }
            }
        }
        return ans;
    }

    // nums已经有序了
    // nums[begin......]范围上，找到累加和为target的所有二元组
    public static List<List<Integer>> twoSum1(int[] nums, int begin, int target) {
        int L = begin;
        int R = nums.length - 1;
        List<List<Integer>> ans = new ArrayList<>();
        while (L < R) {
            if (nums[L] + nums[R] > target) {
                R--;
            } else if (nums[L] + nums[R] < target) {
                L++;
            } else {
                if (L == begin || nums[L - 1] != nums[L]) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(nums[L]);
                    cur.add(nums[R]);
                    ans.add(cur);
                }
                L++;
            }
        }
        return ans;
    }



    // teacher1  为了保证最后结果集是从小到达排序的 在遍历nums时倒序的遍历
    public static List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);
        int N = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = N - 1; i > 1; i--) {
            if (i == N - 1 || nums[i] != nums[i + 1]) {
                List<List<Integer>> nexts = twoSum2(nums, i - 1, -nums[i]);
                for (List<Integer> cur : nexts) {
                    cur.add(nums[i]);
                    ans.add(cur);
                }
            }
        }
        return ans;
    }

    public static List<List<Integer>> twoSum2(int[] nums, int end, int target) {
        int L = 0;
        int R = end;
        List<List<Integer>> ans = new ArrayList<>();
        while (L < R) {
            if (nums[L] + nums[R] > target) {
                R--;
            } else if (nums[L] + nums[R] < target) {
                L++;
            } else {
                if (L == 0 || nums[L - 1] != nums[L]) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(nums[L]);
                    cur.add(nums[R]);
                    ans.add(cur);
                }
                L++;
            }
        }
        return ans;
    }




    public static void main(String[] args) {

//        int[] nums = {-1,0,1,2,-1,-4};
        int[] nums = {};
        threeSum(nums);


    }


}
