package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/12
 * @version: V1.0
 * @slogan:
 * @description :
 * topK问题 【题目】在无序数组中求第K小的数
 *
 * 数组中第k小和 数组中排序后索引为k处的数 不一定是同一个数  因为有重复数
 *
 * 参考com.zuo.improve03.class02.Code07_KthMinPair.kthMinPair4(int[], int)方法中
 * 找到有序数组中 index位置的数
 *
 * 解题过程
 * 1 根据快排去划分数组，每次划分完成后返回数组  等于pivot的区域左边界  equalLeft  和右边界equalRight
 *   如果  equalLeft <= K <= equalRight  这直接返回arr[equalLeft]   找的是等于区域的数据
 * 2 如果 K < equalLeft   只在arr[L~equalLeft-1]范围上去划分
 * 	   如果 K > equalRight  只在arr[equalRight+1, R]范围上去划分
 *
 * 找无序数组中最小的10个数，利用堆结构  时间复杂度为 O(nlogK)
 * 如果时快排改进版本的时间复杂度为O(N)
 * 快排时间复杂度跟选取的pivot有关，如果pivot选取随机的话，把所有pivot选取的情况累加起来求平均的话，快排的时间复杂度为O(NlogN)
 * 所以对于topK问题  使用快排的改良班的话，时间复杂度在概率上求解得到时间复杂度为O(N)
 * 而对于bfprt算法的时间复杂度是严格的O(N)
 *
 * bfprt算法过程     BFPRT算法，接收一个数组和一个K值，返回数组中的一个数
 * 1 bfprt算法与快排最大的不同就是选取pivot的过程不同，快排的版本是随机的选取pivot，而bfptt每次都是精心选择pivot
 * 2. 数组每5个数据一部分，被划分为了 N/5 个小组，每个小组的5个数排序需要 O(1) ，所有小组排完需要 O(N/5)=O(N) 组内排序可用插入排序，常数项系数小
 * 3. 取出每个小组的中位数，一共有 N/5 个，递归调用BFPRT算法得到这些数中中位数   第 (N/5)/2 小的数，记为 pivot
 * 4. 以 pivot 作为比较，将整个数组划分为 <pivot , =pivot , >pivot 三个区域
 * 5. 判断第K小的数在哪个区域，如果在 = 区域则直接返回 pivot ，如果在 < 或 > 区域，则将这个区域的数递 归调用BFPRT算法
 * 6. base case ：在某次递归调用BFPRT算法时发现这个区域只有一个数，那么这个数就是我们要找的数
 *
 *
 * 【重点】
 * 找无序数组中最小的10个数，可以先找到第K小的的数据  x，然后遍历一边数组，找到小于 x 的所有数据，如果直到到4个小于 x 的数据，则数组中的其余数据全部为 x  ！！！
 * 我们需要背过快排怎么把数组按照pivot进行partition的，很简单哦！！！
 *
 *
 *
 * 求解topK问题
 * 思路一  利用堆结构
 * 思路二  快排改进版来找（笔试用这个求解）
 * 思路三   bfprt算法（面试装逼必备）
 *
 *
 *
 *
 */
public class Problem_0215_KthLargestElementInAnArray {


    /**
     * 快排改进版本找
     */
    public int findKthLargest(int[] nums, int k) {

        return process(nums, 0, nums.length - 1, k);

    }


    /**
     * processOld代码优化版 ，把partition过程剥离出来了
     *
     * 抽离了partition 代码比processOld要清爽很多呀，嘿嘿！！！
     *
     */
    private static int process(int[] arr, int L, int R, int k) {
        if (L == R) {
            // 或者返回arr[L]
            return arr[R];
        }
        int index = L + (int) ((R - L + 1) * Math.random());
        int pivot = arr[index];

        // 分割数组
        int range[] = partition(arr, L, R, pivot);

        // 划分完成后查看等于区域是否有第K-1个数据
        // 这里有个坑就是  要用k-1来比较，或者调整process调用参数直接传k-1

        // 这里有个踩坑点  需要注意  比较的是索引位置  而不是数组的的数
        // 数组 1 2 3 4 4 4 5 6 7 8  我们用想等区域的第一个数的索引与k比较
        // 如果k等于了相等区域的第一个数的索引  则找到了答案  而不是用arr[k]与相等区域的实际数进行的比较

        // 注意题目求 第K大的数 不是第K小  partition时要把倒序排列
        if (k - 1 < range[0]) {
            // 在小于区域继续找
            return process(arr, L, range[0] - 1, k);
        } else if (k - 1 > range[1]) {
            // 在大于区域继续找
            return process(arr, range[1] + 1, R, k);
        } else {
            // k >= range[0] && k <= range[1]
            return arr[k - 1];
        }
    }



    /**
     * 分割数组 ，为了优化代码  单独把partition抽出来了
     *
     * 按照pivot，把数组在L~R范围   （<pivot）    （ =pivot）  （>pivot）      三个区域
     *
     * 返回等于区域的左右边界   range[0]等于区域左边界   range[1]等于区域右边界
     *
     * 【重点】
     *    划分的实质是找到大于pivot区域  小于pivot区域  顺着这个思路去想partition的代码实现
     *    显然我们需要使用两个变量分别表示  小于区域边界  大于区域边界
     *
     *  pivot为 arr[L~R]上随机选取的数据 划分的过程如下
     *  1 初始化小于区域边界为 less=L-1， 大于区域的边界为 more = R+1
     *  2 遍历arr[L~R] 上每一个元素 ，
     * 		如果arr[i] < pivot，把小于区域的下一个数与arr[i]交换，继续遍历下一个元素
     * 						    因为当前的arr[i]前面隔了一堆等于pivot的数，所以要与小于区域的下一个数据做交换
     * 		如果arr[i] > pivot，把大于区域的前一个数与arr[i]交换，同时保持i不动
     * 						    因为从大于区域交换过来的数据还没有与pivot进行比较，所以要保持i不动
     *
     * 		如果arr[i] = pivot，大于区域和小于区域都不动，继续遍历下一个元素
     *
     */
    public static int[] partition(int[] arr, int L, int R, int pivot) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        int more = L-1;
        int less = R+1;

        // {1,5,4,2,6,3}; k=3
        // 踩坑点 等于的情况处理
        // 简单几下思路  大于小于的时候交换  相等时不交换

        // 因为要求第K大的数 所以需要倒序排列
        while (L <= R) {
            if (arr[L] > pivot) {
                swap(arr, L++, ++more);
            } else if (arr[L] == pivot) {
                L++;
            } else {
                swap(arr, L, R--);
                less--;
            }
        }
        return new int[] { more + 1, less - 1 };
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }










    /**
     * bfprt算法   这里的实现时求第k小的算法  leetcode要求的时第k大的数
     */
    public static int minKth3(int[] arr, int k) {
        return bfprt(arr, 0, arr.length - 1, k);
    }


    /**
     * 这个方法是 bfprtOld 方法的优化版本的
     *
     * @see #bfprtOld(int[], int, int, int)
     */
    private static int bfprt(int[] arr, int L, int R, int k) {
        if (L == R) {
            return arr[R]; // 或者返回arr[L]
        }

        // 这里有个坑 getPivot 返回的是pivot而不是 pivot的索引！！！！ 与快排不太相同
        int pivot = getPivot(arr, L, R);// 生成天选之子
        int range[] = partition(arr, L, R, pivot);// 分割数组

        // 划分完成后查看等于区域是否有第K-1个数据
        // 这里有个坑就是 要用k-1来比较，或者调整process调用参数直接传k-1
        if (k - 1 < range[0]) {// 在小于区域继续找
            return process(arr, L, range[0] - 1, k);
        } else if (k - 1 > range[1]) {// 在大于区域继续找
            return process(arr, range[1] + 1, R, k);
        } else {// k >= range[0] && k < range[1
            return arr[k - 1];
        }

    }



    /**
     *
     * 自己写的第一版的代码   有些臃肿
     * bfprt算法与快排最大的不同就是选取pivot的过程不同，快排的版本是随机的选取pivot，而bfptt每次都是精心选择pivot
     */
    private static int bfprtOld(int[] arr, int L, int R, int k) {
        if (L == R) {
            return arr[R]; // 或者返回arr[L]
        }
        int less = L - 1;
        int more = R + 1;

        // 这里有个坑    getPivot 返回的是pivot而不是 pivot的索引！！！！  与快排不太相同
        int pivot = getPivot(arr, L, R);// 生成天选之子

        // 下面这个for循环用来将数组arr在L~R范围上 以pivot进行划分
        for (int i = L; i < more; i++) {// i的终止条件为大于区域的前一个位置
            if (arr[i] < pivot) {// 小于时 与小于区域的后一个位置交换
                swap(arr, i, ++less);
            } else if (arr[i] > pivot) {// 大于时 与大于区域的前一个位置交换
                swap(arr, i, --more);
                i--;// 交换完后 保持i不动 因为在for循环中
            }
            // 相等时 小于区域大于区域不动
        }
        // 划分完成后查看等于区域是否有第K个数据
        // 我们提前规定了 L <= k <= R -->这个条件对我们很重要

        // 注意k-1才是对应的index呀！！！！  被坑的地方   k-1而不是k
        // small = L - 1  说明没有小于pivot的数据
        if (k - 1 <= less && less != L - 1) {// 继续在小于区域找
            return process(arr, L, less, k);
        } else if (k - 1 >= more && more != R + 1) {// 继续在大于区域找
            return process(arr, more, R, k);
        } else {
            // 上面的递归都跑完后 返回arr[k-1]
            return arr[k - 1];// 或者返回arr[big-1]
        }

    }

    /**
     * bfprt算法找到天玄之子
     * 1 先分组
     * 2 组内排序
     * 3 每组中位数
     * 4 每组中位数的数组的中位数
     */
    private static int getPivot(int[] arr, int L, int R) {
        // 组数 [0 1 2 3 4] [5 6 7]
        int n = arr.length / 5;
        int groupNum = (arr.length % 5 == 0) ? n : n + 1;// 2

        int[] marr = new int[groupNum];// 每组的中位数组成数组

        for (int i = 0; i < groupNum; i++) {
            int groupFirst = L + 5 * i;
            int groupEnd = Math.min(L + 5 * i + 4, R);
            int groupMid = groupFirst + ((groupEnd - groupFirst) >> 1);
            insertSort(arr, groupFirst, groupEnd);
            marr[i] = arr[groupMid];

        }

        // 对生成的中位数组成的数组，继续递归调用bfprt   这里存在第二个坑
        // 递归调用bfprt算法的 最后一个参数为    marr.length / 2
        return bfprtOld(marr, 0, marr.length - 1, marr.length / 2);
    }


    /**
     * 插入排序的精髓：
     * 遇到比比当前数要小的数 就交换位置，一路向前交换！！！！
     *
     * 对数组的i到j上进行插入排序
     * 常数项系数小
     * 1 2 4 5 6 3
     * 如果3直接与4交换   -> 1 2 3 5 6 4
     * 对于3的处理是  遇到比3大的数就交换   直到遇到一个比3小的数
     *
     */
    public static void insertSort(int[] arr, int i, int j) {
        for (int m = i + 1; m <= j; m++) {
            int tmp = m;// 注意数组别越界
            while (tmp - 1 >= i && arr[tmp] < arr[tmp - 1]) {
                swap(arr, tmp, tmp - 1);
                tmp--;
            }
        }
    }






    public static void main(String[] args) {

        System.out.println((Math.random() * (10)));

    }

}
