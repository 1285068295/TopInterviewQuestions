package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/19
 * @version: V1.0
 * @slogan:
 * @description :  提交此时用例时 记得把类名改为 Solution
 */
public class Problem_0384_ShuffleAnArray {

    private int[] origin;
    private int[] shuffle;
    private int N;


    public Problem_0384_ShuffleAnArray(int[] nums) {
        origin = nums;
        N = nums.length;
        shuffle = new int[N];
        for (int i = 0; i < N; i++) {
            shuffle[i] = nums[i];
        }
    }

    public int[] reset() {
        return origin;
    }


    /**
     * 思路就是
     * 第一次利用random函数 随机出 [0,N-1]上一个数  如 5 把arr[5] 交换到 arr[N-1]
     * 第二次利用random函数 随机出 [0,N-2]上一个数  如 2 把arr[2] 交换到 arr[N-2]
     * @return
     */
    public int[] shuffle() {
        int count = N;
        while (count > 1) {
            // [0 ~ count-1]
            int randomIndex = (int) (Math.random() * count);
            int tmp = shuffle[randomIndex];
            shuffle[randomIndex] = shuffle[count - 1];
            shuffle[count - 1] = tmp;
            count--;
        }
        return shuffle;
    }

}
