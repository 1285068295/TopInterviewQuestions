package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/25
 * @version: V1.0
 * @slogan:
 * @description :摆动排序 II
 *
 * 【思路】 1 找到中位数 第media大的数， 按照media进行划分   左侧小于media  中间等于media  右侧大于media   快排改进版本
 *         2 数组长度偶数情况  利用完美洗牌原地调整顺序                    L1 L2 L3  R1 R2 R3    ->        L1 R1 L2 R2 L3 R3
 *           数组长度奇数数情况 从arr[1]开始往后调整  arr[0]不动    arr[0] L1 L2 L3  R1 R2 R3   -> arr[0]  R1 L1 R2 L2 R3 L3
 *           完美洗牌要求必须是偶数的长度！！！
 *         3 完美洗牌由  L1 L2 L3 L4 L5 L6 R1 R2 R3 R4 R5 R6  -> R1 L1 R2 L2 R3 L3 R4 L4 R5 L5 R6 L6   偶数情况相邻位置两两互换 L1 R1 L2 R2 L3 R3
 *                      奇数情况 从第二个元素完美洗牌后就是结果 不需要再互换了
 *
 * 【完美洗牌】
 *  1  L1 L2 L3 L4 L5 L6 R1 R2 R3 R4 R5 R6  -> R1 L1 R2 L2 R3 L3 R4 L4 R5 L5 R6 L6
 *  2  函数f(i, N) 可以计算出任意i位置 变换后的位置
 *     下标不从0开始，从1开始  i<=len / 2      2 * i;
 *                           i> len / 2      2 * (i - (len / 2)) - 1
 *      或着  剃刀函数 (2 * i) % (len + 1)
 *  3  对于2*n =（3^k-1）这种长度的数组，恰好只有k个环，且每个环的起始位置分别是1,3,9，…3^(k-1)。
 *     当长度为2 8 26 80... 时，每个环的入口 1 3 9 27...
 *  4  长度不为（3^k-1）时  如长度为200 前80个时满足的    [1 2 3 ... 39 40][41 42 ... 100][101 102 ...140][141 142 ... 200]
 *     区间段 [101 102 ...140]  位置互换（左半部分逆序  右半部逆序  整体逆序） 得到[101 102 ...140] [41 42 ... 100]
 *     次数数组的整体数据状况为[1 2 3 ... 39 40][101 102 ...140]   [41 42 ... 100][141 142 ... 200]
 *     前80个元素满足 长度为 3^k -1 剩下的120个元素  继续利用上面的操作去处理
 */
public class Problem_0324_WiggleSortII {

    public static void wiggleSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int N = nums.length;
        findIndexNum(nums, 0, nums.length - 1, N / 2);
        if ((N & 1) == 0) {
            shuffle(nums, 0, nums.length - 1);
            reverse(nums, 0, nums.length - 1);
        } else {
            shuffle(nums, 1, nums.length - 1);
        }
    }


    public static int findIndexNum(int[] arr, int L, int R, int index) {
        int pivot = 0;
        int[] range = null;
        // 找到第 K大的数  快排改进版本进行查找  迭代版本
        while (L < R) {
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            range = partition(arr, L, R, pivot);
            if (index >= range[0] && index <= range[1]) {
                return arr[index];
            } else if (index < range[0]) {
                R = range[0] - 1;
            } else {
                L = range[1] + 1;
            }
        }
        return arr[L];
    }




    public static void shuffle(int[] arr, int L, int R) {
        // 切成一块一块的解决，每一块的长度满足(3^k)-1
        while (R - L + 1 > 0) {
            int len = R - L + 1;
            int base = 3;
            int k = 1;
            // 计算小于等于len并且是离len最近的，满足(3^k)-1的数
            // 也就是找到最大的k，满足3^k <= len+1
            while (base <= (len + 1) / 3) { // base > (N+1)/3
                base *= 3;
                k++;
            }
            // 3^k -1
            // 当前要解决长度为base-1的块，一半就是再除2
            int half = (base - 1) / 2;
            // [L..R]的中点位置
            int mid = (L + R) / 2;
            // 要旋转的左部分为[L+half...mid], 右部分为arr[mid+1..mid+half]
            // 注意在这里，arr下标是从0开始的
            rotate(arr, L + half, mid, mid + half);
            // 旋转完成后，从L开始算起，长度为base-1的部分进行下标连续推
            cycles(arr, L, base - 1, k);
            // 解决了前base-1的部分，剩下的部分继续处理
            // L ->     [] [+1...R]
            L = L + base - 1;
        }
    }



    // 从start位置开始，往右len的长度这一段，做下标连续推
    // 出发位置依次为1,3,9...
    public static void cycles(int[] arr, int start, int len, int k) {
        // 找到每一个出发位置trigger，一共k个
        // 每一个trigger都进行下标连续推
        // 出发位置是从1开始算的，而数组下标是从0开始算的。
        for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
            int preValue = arr[trigger + start - 1];
            int cur = modifyIndex2(trigger, len);
            while (cur != trigger) {
                int tmp = arr[cur + start - 1];
                arr[cur + start - 1] = preValue;
                preValue = tmp;
                cur = modifyIndex2(cur, len);
            }
            arr[cur + start - 1] = preValue;
        }
    }

    // 数组的长度为len，调整前的位置是i，返回调整之后的位置
    // 下标不从0开始，从1开始
    public static int modifyIndex1(int i, int len) {
        if (i <= len / 2) {
            return 2 * i;
        } else {
            return 2 * (i - (len / 2)) - 1;
        }
    }

    // 数组的长度为len，调整前的位置是i，返回调整之后的位置
    // 下标不从0开始，从1开始
    public static int modifyIndex2(int i, int len) {
        return (2 * i) % (len + 1);
    }


    /**
     * [L..M]为左部分，[M+1..R]为右部分，左右两部分互换
     */
    public static void rotate(int[] arr, int L, int M, int R) {
        reverse(arr, L, M);
        reverse(arr, M + 1, R);
        reverse(arr, L, R);
    }


    /**
     * [L..R]做逆序调整
     */
    public static void reverse(int[] arr, int L, int R) {
        while (L < R) {
            int tmp = arr[L];
            arr[L++] = arr[R];
            arr[R--] = tmp;
        }
    }

    /**
     * 快排划分区域
     */
    private static int[] partition(int[] nums, int L, int R, int pivot) {
        if (L > R) {
            return new int[]{-1, -1};
        }
        if (L == R) {
            return new int[]{L, R};
        }

        int less = L - 1;
        int more = R + 1;
        // 注意不能丢等于的情况
        while (L <= R) {
            if (nums[L] < pivot) {
                // 交换到小于区域
                swap(nums, L++, ++less);
            } else if (nums[L] == pivot) {
                //
                L++;
            } else {
                // 交换到大于区域 L 不要动
                swap(nums, L, --more);
                R--;
            }
        }
        return new int[]{less + 1, more - 1};
    }

//    public static int[] partition(int[] arr, int L, int R, int pivot) {
//        int less = L - 1;
//        int more = R + 1;
//        int cur = L;
//        while (cur < more) {
//            if (arr[cur] < pivot) {
//                swap(arr, ++less, cur++);
//            } else if (arr[cur] > pivot) {
//                swap(arr, cur, --more);
//            } else {
//                cur++;
//            }
//        }
//        return new int[] { less + 1, more - 1 };
//    }



    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {2, 8, 4, 9, 3, 7, 5};
        partition(arr, 0,6, 5);

        int[] nums = {1,2,3,4,5,6,7,8};
        int[] nums2 ={1,5,1,1,6,4};
        wiggleSort(nums2);
    }
}
