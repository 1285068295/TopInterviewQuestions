package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/9
 * @version: V1.0
 * @slogan:
 * @description : 寻找峰值
 * 峰值元素是指其值严格大于左右相邻值的元素。
 *
 * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
 *
 * 你可以假设 nums[-1] = nums[n] = -∞ 。
 *
 * 【局部最大值】 直接二分法求解即可
 *
 */
public class Problem_0162_FindPeakElement {


    /**
     * 注意要返回的时索引值
     */
    public static int findPeakElement(int[] nums) {

        int N = nums.length;
        if (N < 2) {
            return 0;
        }
        // 排除边界值
        if (nums[0] > nums[1]) {
            return 0;
        }
        if (nums[N - 1] > nums[N - 2]) {
            return N - 1;
        }

        // 如果上面的两个if都没有返回值  则一定是  [0]<[1]... [M]...[N-2]>[N-1]  拱形的 要么左侧二分  要么右侧二分

        // 中间值的情况
        int L = 1;
        int R = N - 2;
        int M = 0;
        while (L < R) {
            M = (L + R) / 2;
            if (nums[M - 1] < nums[M] && nums[M] > nums[M + 1]) {
                return M;
            } else if (nums[M - 1] > nums[M]) {
                R = M - 1;
            } else {
                L = M + 1;
            }
        }
        // 最后返回的是L
        return L;
    }

    public static void main(String[] args) {
//        int[] arr = {1,2,1,3,5,6,4};
        int[] arr = {1,2,3,1};

        System.out.println(findPeakElement(arr));
    }
}
