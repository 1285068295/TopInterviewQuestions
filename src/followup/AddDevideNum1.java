package followup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/9/25
 * @version: V1.0
 * @slogan:
 * @description :
 * 【题目】
 * 100 = 3 + 69258 / 714
 * 100 = 82 + 3546 / 197
 *
 * 等号右边的部分，可以写成 p1 + p2 / p3的形式
 * 要求p1和p2和p3，所使用的数字，必须把1~9使用完全，并且不重复
 * 满足的话，我们就说，形如p1 + p2 / p3，一个有效的"带分数"形式
 * 要求，p2 / p3 必须整除
 *
 * 输入N，返回N有多少种带分数形式
 * 100，有11种带分数形式
 *
 * 输入的N，N  < 10的8次方
 *
 * 【解题思路】
 *  1~9全排排列情况下  共36万中情况（8数码问题可知）
 *  我们可以在每一种情况下  枚举+ / 的位置
 *  因为要保证 p2/p3 整除，所以  p2>=p3 即p2的位数不能少于p3
 *  按照以下的情况来枚举
 *  p1 是1位数   p2 4位数  p3 4位数
 *              p2 5位数  p3 3位数
 *              p2 6位数  p3 2位数
 *              p2 7位数  p3 1位数
 *  p1 是2位数  ...
 *  p1 是3位数  ...
 *  ...
 *  可以很清楚的知道  共有16种情况来划分
 *

 *  所以暴力求解  36万 * 16 = 500万  <10^8 指数级别  所以暴力解答 一定可以过
 *
 *  这道题考察的就是  根据实际的数据状况来设计  算法流程。
 *
 *  通过全排列收集到 1~9所有的可能性  然后去枚举 + / 的位置
 *
 *
 *  不能用 长度位11的数组来排列组合  共有39,916,800 方案数
 *
 *  可以用长度位 9的数来枚举  只存1~9数字  '+'  '/'的位置 单独使用两个变量  addSplit  devSplit来记录
 *
 *
 * 【重要的结论】 ： C/C++ 1~2s 内完成   java 3~4s 内  算法数量级必须保证在  常数项在10^8以内
 * 【收获】 参考第46题 + 这道题  我们可抽取排列组合的模板方法了！！！  套路呀！！！
 *
 * 【这道题可以抽取出排列组合的模板方法出来！！！  很重要呀！！！】
 *
 *
 * @see   AddDevideNum2 老师的做法  代码实现起来有点啰嗦，不过相对来说时间复杂度要低应该！！！
 *
 */
public class AddDevideNum1 {

    /**
     * 1~9 所有的带分数都算一遍  放到map中  key-带分数  value-有效的带分数方案
     */
    public static HashMap<Integer, List<String>> map = new HashMap<>();

    /**
     * 参考了 第46题的排列组合的 代码实现  如果准备长度位11的数组，数组的指定位置放 '+' '-' 实现起来太难了！！！
     */
    public static int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};


    /**
     * 因为老师的方法使用了数字直接来转换 实际代码写起来有点难！ 所以我参考借鉴了 第46题的排列组合的代码
     * 这种方式代码容易理解  但是存在一个致命的缺陷就是 时间复杂度 应该不是 500万级别的！！！(实际测试下来居然我的更快！！！？？？)
     */
    public static int ways(int N){
        //  for test  为了测试跑100次计算平均时间用的
        //  map.clear();

        if (map.isEmpty()) {
            process(N, nums, 0, map);
        }
        return map.get(N).size();

    }

    /**
     * 【一个很nice的想法！！！】
     *  把'+' '/' 放到 收集答案的时候！！！  而不是在递归调用的上层取排列情况
     */
    private static void process(int N, int[] nums, int cur, HashMap<Integer, List<String>> map) {
        if (cur == nums.length){
            // 踩坑点  妈蛋的想了很久才搞明白的！！！  addSplit devSplit的位置枚举是在这个if种枚举的 不是在 else 里面枚举的！！！

            // 0    1 2 3 4 5 6 7   8
            // 1  + 2 3 4 5 6 7 8 / 9

            // 0    1 2 3 4   5 6 7 8
            // 1  + 2 3 4 5 / 6 7 8 9

            // 0  1   2 3 4 5   6 7 8
            // 1  2 + 3 4 5 6 / 7 8 9

            // '+' 1 ~ 6
            for (int addSplit = 1; addSplit < 7; addSplit++) {
                for (int devSplit = (nums.length - addSplit) / 2 + addSplit; devSplit < 8; devSplit++) {

                    int p1 = 0, p2 = 0, p3 = 0;
                    int i = 0;
                    // 根据+ / 位置  收集p1  p2  p3的值
                    while (i < addSplit) {
                        p1 = p1 * 10 + nums[i];
                        i++;
                    }
                    while (i < devSplit) {
                        p2 = p2 * 10 + nums[i];
                        i++;
                    }
                    while (i < nums.length) {
                        p3 = p3 * 10 + nums[i];
                        i++;
                    }

                    // 收集答案
                    if (p2 % p3 == 0) {
                        String value = p1 + "+" + p2 + "/" + p3;
                        int m = p1 + p2 / p3;
                        if (map.containsKey(m)) {
                            map.get(m).add(value);
                        } else {
                            List<String> list = new ArrayList<>();
                            list.add(value);
                            map.put(m, list);
                        }
                    }
                }
            }
        }else {
            for (int i = 0; i < nums.length - cur; i++) {
                // 因为是原地调整  所以需要恢复现场！！！
                swap(nums, cur, cur + i);
                process(N, nums, cur + 1, map);
                // 恢复现场！！！
                swap(nums, cur + i, cur);
            }
        }
    }


    public static void swap(int[] arr, int i ,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        int times  =  100;
        for (int i = 0; i < times; i++) {
            ways(556);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start)/times);


    }


}
