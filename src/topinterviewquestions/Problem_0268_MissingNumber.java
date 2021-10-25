package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/17
 * @version: V1.0
 * @slogan:
 * @description :给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。
 *
 * 整体解题思路参考 第41题
 * 【解题思路】
 * 整体思路时 设定一个最好的预期值  当前遍历的值过大 或者过小  或者重复  都算作是垃圾数据，都需要交换到末尾的垃圾区域！！！
 * 与
 */
public class Problem_0268_MissingNumber {

    public static int missingNumber(int[] arr) {

        // 输入：nums = [3,0,1]
        // 输出：2
        // 解释：n = 3，因为有 3 个数字，所以所有的数字都在范围 [0,3] 内。2 是丢失的数字，因为它没有出现在 nums 中。

        // 输入：nums = [0,1]
        // 输出：2
        // 解释：n = 2，因为有 2 个数字，所以所有的数字都在范围 [0,2] 内。2 是丢失的数字，因为它没有出现在 nums 中。

        // 输入：nums = [9,6,4,2,3,5,7,0,1]
        // 输出：8
        // 解释：n = 9，因为有 9 个数字，所以所有的数字都在范围 [0,9] 内。8 是丢失的数字，因为它没有出现在 nums 中。

        int l = 0;
        int r = arr.length;
        while (l < r) {
            if (arr[l] == l) {
                l++;
            } else if (arr[l] < l || arr[l] >= r || arr[arr[l]] == arr[l]) {
                swap(arr, l, --r);
            } else {
                swap(arr, l, arr[l]);
            }
        }
        return l;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void main(String[] args) {
        int[] arr1 = {3, 1,0};
        int[] arr2 = {6, 5, 4, 2, 3, 5, 1,0};
        System.out.println(missingNumber(arr1));
        System.out.println(missingNumber(arr2));
    }

}
