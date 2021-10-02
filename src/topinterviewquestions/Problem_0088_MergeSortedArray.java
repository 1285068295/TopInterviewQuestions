package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/1
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【思路】 依次比较 谁大就放到最后的位置
 *
 */
public class Problem_0088_MergeSortedArray {


    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = nums1.length;
        while (m > 0 && n > 0) {
            if (nums1[m - 1] >= nums2[n - 1]) {
                nums1[--index] = nums1[--m];
            } else {
                nums1[--index] = nums2[--n];
            }
        }
        while (m > 0) {
            nums1[--index] = nums1[--m];
        }
        while (n > 0) {
            nums1[--index] = nums2[--n];
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {4, 5, 6, 0, 0, 0};
        int[] nums2 = {1, 2, 3};
        merge(nums1, 3, nums2, 3);
    }

}
