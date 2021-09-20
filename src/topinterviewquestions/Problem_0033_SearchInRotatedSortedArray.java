package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/19
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路 尽可能的使用二分法来找答案】
 * 这道的本质 是尽可能的使用二分法来求解答案。
 * 原理：arr[L] arr[M] arr[R]  只要三个数不完全相等  就可以使用二分法来找答案，因为部位完全相等时  可以根据三个值大小
 *      确定出哪个区间段上是单调的，因为整个数组的是根据有序数组旋转得到的  所以整个数组在整个区间上  最多是两个单调的区间如
 *      nums = [4,5,6,7,0,1,2]     [4,5,6,7] [0,1,2]
 *     当arr[L]=arr[M]=arr[R]  如   4 4 4 4  4  1 2 3 4  或则   4 1 2 3 4 4 4 4  arr[L]=arr[M]=arr[R]=4
 *     要找 1 2 3 这三个值  不能确定实在L~M还是  M~R区间上
 *     做法是  L=L+1  在L+1 ~ R 区间上做二分
 *
 *
 * 【二分法求解局部最小值问题】
 * 局部最小值定义：
 * 最左边边界处，如果最左边的数小于左边第二个数，则最左边为局部最小值。
 * 最右边边界处，如果最右边的数小于右边第二个数，则最右边为局部最小值。
 * 中间处，如果一个数小于它两边的数，则这个数就是局部最小值。
 * 说白了，就是二维坐标系中的任意一个最低点，都是局部最小值。
 * 现在，有一个数组，相邻的两个数不相等，请求出一个数组中的一个局部最小值。
 *
 */
public class Problem_0033_SearchInRotatedSortedArray {


    public static int search(int[] arr, int num) {
        int L = 0;
        int R = arr.length - 1;
        int M = 0;

        while (L <= R) {
            M = (L + R) / 2;
            if (arr[M] == num) {
                return M;
            }

            if (arr[L] == arr[M] && arr[M] == arr[R]) {
                L++;
                continue;
            }

            // 三个数不完全相等的情况
            if (arr[L] != arr[M]) {
                //  [4] 5 6 7 [1] 2 3 4 [4]
                if (arr[L] > arr[M]) {
                    // 踩雷的地方 ：num >= arr[M] && num <= arr[R]  没有处理=的情况
                    // 这种情况下  M~R区间上 一定是单调的
                    if (num >= arr[M] && num <= arr[R]) {
                        // 在M+1 ~ R 区间上做二分
                        L = M + 1;
                    } else {
                        // 在L ~ M-1区间上做二分
                        R = M - 1;
                    }
                } else {
                    //  [4] 5 6 [7] 1 2 [4]
                    // arr[L] < arr[M]
                    // 这种情况下  L~M区间上 一定是单调的
                    if (num >= arr[L] && num <= arr[M]) {
                        // 在L ~ M-1区间上做二分
                        R = M - 1;
                    } else {
                        // 在M+1 ~ R 区间上做二分
                        L = M + 1;
                    }
                }

            } else {
                // arr[M]!=num
                // arr[L]=arr[M]且三个数不完全相等  所以可知 arr[M]!=arr[R]
                // 4 4 4 5 6 7
                L = M + 1;
            }
        }
        return -1;
    }




    // teacher
    public static int search2(int[] arr, int num) {
        int L = 0;
        int R = arr.length - 1;
        int M = 0;
        while (L <= R) {
            M = (L + R) / 2;
            if (arr[M] == num) {
                return M;
            }
            // arr[M] != num
            if (arr[L] == arr[M] && arr[M] == arr[R]) {
                while (L != M && arr[L] == arr[M]) {
                    L++;
                }
                // L和M没撞上，[L]!=[M] L,.....M
                if (L == M) {
                    L = M + 1;
                    continue;
                }
            }




            // arr[M] != num
            // [L] [M] [R] 不都一样的情况
            if (arr[L] != arr[M]) {
                if (arr[M] > arr[L]) {
                    if (num >= arr[L] && num < arr[M]) {
                        R = M - 1;
                    } else {
                        L = M + 1;
                    }
                } else { //  [L]  >  [M]
                    if (num > arr[M] && num <= arr[R]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                }
            } else { // [L] === [M] ->  [M]!=[R]
                if (arr[M] < arr[R]) {
                    if (num > arr[M] && num <= arr[R]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                } else {
                    if (num >= arr[L] && num < arr[M]) {
                        R = M - 1;
                    } else {
                        L = M + 1;
                    }
                }
            }
        }
        return -1;
    }


}
