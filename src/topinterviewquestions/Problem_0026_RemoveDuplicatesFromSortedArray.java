package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/18
 * @version: V1.0
 * @slogan:
 * @description :
 */
public class Problem_0026_RemoveDuplicatesFromSortedArray {

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }

        int sorted = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[sorted]) {
                // 1 1 1 3 4
                // 1 3 1 3 4
                nums[++sorted] = nums[i];
            }

        }

        return sorted + 1;
    }


    public static void main(String[] args) {
        int[] arr = {1,1,2};


        System.out.println(removeDuplicates(arr));
    }


}
