package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/1
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 荷兰其过度问题  快排的前身！！！
 *
 */
public class Problem_0075_SortColors {

    /** [2,0,2,1,1,0]
     *  [0,0,1,1,2,2]
     */
    public static void sortColors(int[] nums) {

        // 小于区域的边界位置
        int less = -1;
        // 使用1作为轴点来进行划分区域  小于1的放到左侧 大于1的放到右侧
        int pivot = 1;

        int L =0, R= nums.length -1;
        while (L <= R) {
            if (nums[L] < pivot) {
                // 当前值小于的情况  交换到小于区域
                swap(nums, ++less, L++);
            } else if (nums[L] > pivot) {
                // 交换L R的位置数据  L不动 R--
                // 把L处数据 交换到大于区域位置
                swap(nums, L, R--);
            } else {
                // 相等的时候L++ 小于区域less位置不动
                L++;
            }
        }
    }

    private static void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    public static void main(String[] args) {
        int[] nums = {2,0,1};
        sortColors(nums);
    }
}
