package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/19
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 *      二分过程中 需要一个额外的值 记录二分的边界 L R
 * 【二分法使用范围】
 *  1) 在一个有序数组中，找某个数是否存在
 *  2) 在一个有序数组中，找>=某个数最左侧的位置
 *  3) 在一个有序数组中，找<=某个数最右侧的位置
 *  4) 局部最小值问题
 *
 */
public class Problem_0034_FindFirstAndLastPositionOfElementInSortedArray {

    public static int[] searchRange(int[] nums, int target) {
        int[] ans = { -1, -1 };
        if (nums == null || nums.length == 0) {
            return ans;
        }
        ans[0] = findFirst(nums, target);
        ans[1] = findLast(nums, target);
        return ans;
    }

    /**
     * 注意 二分过程中 需要一个额外的值 记录二分的边界L
     * @param arr
     * @param num
     * @return
     */
    public static int findFirst(int[] arr, int num) {
        int L = 0;
        int R = arr.length - 1;
        int ans = -1;
        int mid = 0;
        while (L <= R) {
            mid = L + ((R - L) >> 1);
            if (arr[mid] < num) {
                L = mid + 1;
            } else if (arr[mid] > num) {
                R = mid - 1;
            } else {
                ans = mid;
                R = mid - 1;
            }
        }
        return ans;
    }


    /**
     * 注意 二分过程中 需要一个额外的值 记录二分的边界R
     * @param arr
     * @param num
     * @return
     */
    public static int findLast(int[] arr, int num) {
        int L = 0;
        int R = arr.length - 1;
        int ans = -1;
        int mid = 0;
        while (L <= R) {
            mid = L + ((R - L) >> 1);
            if (arr[mid] < num) {
                L = mid + 1;
            } else if (arr[mid] > num) {
                R = mid - 1;
            } else {
                ans = mid;
                L = mid + 1;
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int[] arr = {1 ,2,3,4,5,6,6,6,7};
        System.out.println(searchRange(arr, 6));
    }




}
