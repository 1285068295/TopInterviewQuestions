package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/10/27
 * @version: V1.0
 * @slogan:
 * @description :计算右侧小于当前元素的个数
 *
 * 【解题思路】  参考小和问题！！！
 *  利用归并排序  逆序对交换的次数就是当前值右侧小的数的个数
 *  归并排序的过程如下
 *  [5 2 6 1]
 *  [5 2] [6 1]
 *  [2 5] 得到2右侧0个  5右侧1个
 *  [6 1] 得到6右侧0个  2右侧1个
 *  [2 5] [1 6] 继续归并
 *  [1 2 5 6]   得到1右侧0个  2右侧1个
 *              得到5右侧2个  6右侧1个
 */
public class Problem_0315_CountOfSmallerNumbersAfterSelf {

    public static List<Integer> countSmaller2(int[] nums) {

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // 初始时设置每个数右侧没有比它要小的数
            ans.add(0);
        }
        process2(nums, 0, nums.length - 1, ans);
        return ans;
    }

    public static void process2(int[] nums, int l, int r, List<Integer> ans){
        if (l == r){
            return;
        }

        int mid = l + ((r - l) >> 1);
        process2(nums, l, mid, ans);
        process2(nums, mid + 1, r, ans);

        // 合并[l, mid]  [mid, r]
        merge2(nums, l, r, ans);
    }

    /**
     * 自己写的缺陷的地方 数组  {5 2 6 1 3 7 2 1}
     * {1 2 5 6} {1 2 3 7} 不能直接用原数组的值来确定当前索引位置右侧小于的数量
     * 因为每次merge一次会将原来的值的位置打乱了
     * 正确答案 [5,2,4,0,2,2,1,0]  而我的答案[3,2,5,3,4,4,2,0]
     * {1 2 5 6} {1 2 3 7} merge时 [0] 位置原来为5 而现在直接用1 来比较显然是不对的
     *
     * 还有一个问题是 arr[i] > arr[j] 时 里面还有一个小循环 不好！！！
     *
     *
     * 老师的做法是讲 index 和 value 包装成一个Node
     */
    @Deprecated
    public static void merge2(int[] nums, int l, int r, List<Integer> ans){
        // 辅助数组  谁小拷贝谁
        int[] help = new int[r - l + 1];
        int index = 0;

        // 一半长度
        int mid = l + ((r - l) >> 1);
        // l...   r...
        // [5]    [2]
        int i = l;
        int j = mid + 1;

        // 两个数组的长度不一样长可能
        // l ~ mid   mid+1 ~ r
        while (i <= mid && j <= r) {
            if (nums[i] > nums[j]) {
                // 左侧的数要大
                help[index] = nums[j];
                // 从 i ~ mid 上所有的数都要比 num[j]大
                // [3 7]  [1 2]
                int tmp = i;
                while(tmp <= mid){
                    ans.set(tmp, ans.get(tmp) + 1);
                    tmp++;
                }
                j++;
            } else {
                // 右侧的数要大
                help[index] = nums[i];
                i++;
            }
            index++;
        }

        // 下面的while只会执行其中一个
        while (i <= mid) {
            help[index++] = nums[i++];
        }
        while (j <= r) {
            help[index++] = nums[j++];
        }

        // 讲辅助数组排好序的数组放入原来的数组位置
        for (int m = 0; m < help.length; m++) {
            nums[l + m] = help[m];
        }
    }





    // ********************************  下面是正确做法  原因参考 merge2方法注释 ****************************************************

    public static class Node {
        public int value;
        public int index;

        public Node(int v, int i) {
            value = v;
            index = i;
        }
    }

    public static List<Integer> countSmaller(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // 初始时设置每个数右侧没有比它要小的数
            ans.add(0);
        }
        if (nums.length < 2) {
            return ans;
        }

        Node[] arr = new Node[nums.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Node(nums[i], i);
        }
        process(arr, 0, arr.length - 1, ans);
        return ans;
    }

    public static void process(Node[] nums, int l, int r, List<Integer> ans){
        if (l == r){
            return;
        }

        int mid = l + ((r - l) >> 1);
        process(nums, l, mid, ans);
        process(nums, mid + 1, r, ans);

        // 合并[l, mid]  [mid, r]
        //  merge(nums, l, r, ans);

        // 老师的merge方法
        merge(nums, l, mid, r, ans);
    }

    /**
     * 自己写的缺陷的地方 数组  {5 2 6 1 3 7 2 1}
     * {1 2 5 6} {1 2 3 7} 不能直接用原数组的值来确定当前索引位置右侧小于的数量
     * 因为每次merge一次会将原来的值的位置打乱了
     * 正确答案 [5,2,4,0,2,2,1,0]  而我的答案[3,2,5,3,4,4,2,0]
     * {1 2 5 6} {1 2 3 7} merge时 [0] 位置原来为5 而现在直接用1 来比较显然是不对的
     *
     * 还有一个问题是 arr[i] > arr[j] 时 里面还有一个小循环 不好！！！提交用例居然给我超时了！！！
     *
     *
     * 老师的做法是讲 index 和 value 包装成一个Node
     */
    @Deprecated
    public static void mergeOfMySelf(Node[] nums, int l, int r, List<Integer> ans){
        // 辅助数组  谁小拷贝谁
        Node[] help = new Node[r - l + 1];
        int index = 0;

        // 一半长度
        int mid = l + ((r - l) >> 1);
        // l...   r...
        // [5]    [2]
        int i = l;
        int j = mid + 1;

        // 两个数组的长度不一样长可能
        // l ~ mid   mid+1 ~ r
        while (i <= mid && j <= r) {
            if (nums[i].value > nums[j].value) {
                // 左侧的数要大
                help[index] = nums[j];
                // 从 i ~ mid 上所有的数都要比 num[j]大
                // [3 7]  [1 2]
                int tmp = i;
                while (tmp <= mid) {
                    // 找到原来的index的位置
                    ans.set(nums[tmp].index, ans.get(nums[tmp].index) + 1);
                    tmp++;
                }
                j++;
            } else {
                // 右侧的数要大
                help[index] = nums[i];
                i++;
            }
            index++;
        }

        // 下面的while只会执行其中一个
        while (i <= mid) {
            help[index++] = nums[i++];
        }
        while (j <= r) {
            help[index++] = nums[j++];
        }

        // 讲辅助数组排好序的数组放入原来的数组位置
        for (int m = 0; m < help.length; m++) {
            nums[l + m] = help[m];
        }
    }


    /**
     * 老师给的merge方法  倒着拷贝  省去小循环。
     */
    public static void merge(Node[] arr, int l, int m, int r, List<Integer> ans) {
        Node[] help = new Node[r - l + 1];
        int i = help.length - 1;
        int p1 = m;
        int p2 = r;
        while (p1 >= l && p2 >= m + 1) {
            // 原理还是谁小拷贝谁
            if (arr[p1].value > arr[p2].value) {
                ans.set(arr[p1].index, ans.get(arr[p1].index) + p2 - m);
            }
            help[i--] = arr[p1].value > arr[p2].value ? arr[p1--] : arr[p2--];
        }
        while (p1 >= l) {
            help[i--] = arr[p1--];
        }
        while (p2 >= m + 1) {
            help[i--] = arr[p2--];
        }
        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }



    public static void main(String[] args) {
        int[] arr = {5,2,6,1,3,7,2,1};
        countSmaller(arr);
    }

}
