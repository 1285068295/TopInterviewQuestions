package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/20
 * @version: V1.0
 * @slogan:
 * @description : 找数组中缺失的第一个正数  等同于 268题
 * 【解题思路】
 * 1  L=0 R=length  在0~L 范围上  每一个数组的位置要保证是  arr[i] = i+1
 * R  当前数组的最好的预期值  如长度为9的数组  最大的数组预期值为 R+1
 * 2 过程如下
 * arr[ 6,1,4,3,1,2,5,9]
 * 0 1 2 3 4 5 6 7
 * 开始时 L=0 R=9  arr[L]为6  要将6放到arr[5]的位置 交换后
 * arr[ 2,1,4,3,1,6,5,9]
 * 0 1 2 3 4 5 6 7
 * 再次计算 L=0 R=9  arr[L]为2  要将2到arr[1]的位置
 * arr[ 1,2,4,3,1,6,5,9]
 * 0 1 2 3 4 5 6 7
 * 此时arr[L=0]=1 L++
 * 再次计算  arr[L=1]=2 L++  L=2
 * arr[ 1,2,3,4,1,6,5,9]
 * 0 1 2 3 4 5 6 7
 * 此时arr[L=2]=3 L++ L=3
 * 再次计算  arr[L=3]=4 L++  L=4
 * <p>
 * 此时arr[L=4]=1  1<=L  就是说 1~L+1上的值 我们已经有了  所以此时的1也是垃圾数据
 * 把arr[L=4] 交换到最后的垃圾区域  同时R-- L不动 R=6
 * arr[ 1,2,3,4,9,6,5,1]
 * 0 1 2 3 4 5 6 7
 * 再次计算  arr[L=4]=9 > R+1 = 6+1 =7 我们最好的预期是  1234567 所以9也是垃圾数据
 * 把arr[L=4] 交换到最后的垃圾区域  同时R-- L不动 R=5
 * arr[ 1,2,3,4,5,6,9,1]
 * 0 1 2 3 4 5 6 7
 * 此时arr[L=4]=5 L++ L=5
 * 再次计算  arr[L=5]=6 L++  L=6
 * L=R 结束
 * <p>
 * 【面试技巧】
 * 遇到做过的题目 现场一定不要说自己做过 要学会装逼 经过一番努力思考之后  自己一步步相处了最优解！！！
 * 这道题的题目原型是 荷兰旗问题  可以从荷兰旗问题跟面试官开始讨论。
 */
public class Problem_0041_FirstMissingPositive {


    public static int firstMissingPositive(int[] nums) {
        int L = 0;
        int R = nums.length -1;
        while (L <= R) {

            if (nums[L] == L + 1) {
                L++;
            } else if (nums[L] > R + 1 || nums[L] < L + 1 || (nums[L] - 1 < nums.length && nums[L] == nums[nums[L] - 1])) {
                // nums[l]为多余数字的情况
                // 1 nums[L] < L+1  过小
                // 2 nums[L] > R+2  过大
                // 3 nums[L] - 1 < R(保证不越界) && nums[L] == nums[nums[L] - 1] 相等了  但是要交换的位置已经存在符合预期的值了
                //   如 nums[4]=10 应该放到  nums[ 9 ] = num[ nums[L] - 1 ]位置
                //   但是nums[9]已经有一个10了  此时nums[4]位置的10也是多余数字

                // swap(nums, L, R--);
                // 上面swap执行与下面的结论等价  直接覆盖 舍弃掉垃圾数据
                 nums[L] = nums[R--];
            } else {
                // nums[L] 可以交换的到 nums[L]-1 位置 情况
                swap(nums, L, nums[L]-1);
            }
        }
        return L + 1;
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
