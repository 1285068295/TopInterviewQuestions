package topinterviewquestions;

import java.util.*;

/**
 * @author ：Lisp
 * @date： 2021/9/21
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【题目】 跳跃问题三联  45 55 加这道题
 *  给定数组中start起始地址  每个数组的元素表示 只能向左跳跃arr[i]步数  或者向右跳跃arr[i]步
 *  问至少要跳跃几步end位置  如果不能跳跃来到直接返回 -1
 *
 *  【解题思路】
 *  使用宽度优先遍历来求解 要求的步数就是 二叉树的层级！！！
 *
 *
 *
 * 【题外话】
 *  双向深度优先遍历例题
 *  (1)leetcode 127题 成语接龙；
 *  (2)8数码问题  输入字符串"147853206" 移动多少步可以得到  "123456780"
 *
 *  这道题不适用！！！ 参见jump11方法注释！！！
 *
 *
 * 用双向广度 来炫技  哈哈哈！！！   参考 DBSF.jpg图片
 * 所谓双向广度搜索指的是搜索沿两个方向同时进行：
 * （1）正向搜索：从初始结点向目标结点方向搜索；
 * （2）逆向搜索：从目标结点向初始结点方向搜索；当两个方向的搜索生成同一子结点时终止此搜索过程。
 *
 *  广度双向搜索通常有两种方法：
 *  （1）两个方向交替扩展；
 *  （2）选择结点个数较少的那个方向先扩展。
 *  方法（2）克服了两方向结点的生成速度不平衡的状态，可明显提高效率。
 *
 *  【这道题用来改些动态规划很有意义！！！】
 *
 *
 */
public class Problem_0045_JumpGameIIFollowUpOnClass {

    /**
     * 最优解  使用bfs遍历找答案
     *
     * @param N  一共有N个位置, 每个位置如何跳,记录在arr中
     * @param start 位置从1开始  实际的调用可能不是传入的位置1 可能是其他的数值
     * @param end  最后要去的位置
     * @param arr  每个位置可以跳跃的步数
     * @return
     */
    public static int jump1(int N, int start, int end, int[] arr) {
        if (start < 1 || start > N || end < 1 || end > N) {
            return -1;
        }

        Queue<Integer> queue = new LinkedList<>();
        // key-位置索引  value-位置索引所在的二叉树层级  也就是要跳跃到这里需要走过的步数
        // 可以用map来省去set 来做去重处理
        HashMap<Integer,Integer> levelMap = new HashMap<>();
        // set用来去重 已经走过的位置  不能重复走（因为可以左右跳 有可能会来到原来位置）
        // HashSet<Integer> set = new HashSet<>();

        queue.add(start);
        levelMap.put(start,0);

        while(!queue.isEmpty()){
            Integer index = queue.poll();
            int step = levelMap.get(index);
            if (index == end) {
                return step;
            }

            // 取值范围 [1, N]
            int leftIndex = index - arr[index - 1];
            int rightIndex = index + arr[index - 1];
            if (leftIndex > 0 && !levelMap.containsKey(leftIndex)) {
                levelMap.put(leftIndex, step + 1);
                queue.add(leftIndex);
            }
            if (rightIndex <= N && !levelMap.containsKey(rightIndex)) {
                levelMap.put(rightIndex, step + 1);
                queue.add(rightIndex);
            }
        }
        return -1;

    }






    /**
     *【此方法不适用！！！】
     *                    1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20
     * 如下 int[] arr = { 0, 6, 4, 2, 6, 9, 9, 7, 4, 7, 2, 1, 8, 4, 3, 5, 4, 2, 5, 9 };
     * 	start=10 end=7
     * 	beginVisited={10}  endVisited={7}
     * 	beginVisited={3，17} endVisited={7}   交换beginVisited={7}   endVisited={3，17}  从元素少的开始遍历
     * 	beginVisited={7}   7->-2  7->16  endVisited={7}  现然不能跳跃过去！！！
     *
     * 本质原因是因为  start->end 是单向的！！！ 不能逆着来 从end->start
     *
     *
     * 最优解炫技版本  双向广度优先遍历
     * 用两个set集合分别装要遍历的元素  谁的元素少就遍历谁
     * 每次遍历的一层节点放到一个新的set集合中
     *
     * 使用两个队列也是可以实现的
     *
     *
     * @param N  一共有N个位置, 每个位置如何跳,记录在arr中
     * @param start 位置从1开始  实际的调用可能不是传入的位置1 可能是其他的数值
     * @param end  最后要去的位置
     * @param arr  每个位置可以跳跃的步数
     * @return
     */
    @Deprecated
    public static int jump11(int N, int start, int end, int[] arr) {
        if (start < 1 || start > N || end < 1 || end > N) {
            return -1;
        }


        // key-位置索引  value-位置索引所在的二叉树层级  也就是要跳跃到这里需要走过的步数
        // 可以用map来省去set 来做去重处理
        HashMap<Integer, Integer> levelMap = new HashMap<>();

        // 分别用左边和，右边扩散的哈希表代替单向 BFS 里的队列，它们在双向 BFS 的过程中交替使用
        Set<Integer> beginVisited = new HashSet<>();
        beginVisited.add(start);
        levelMap.put(start, 0);

        Set<Integer> endVisited = new HashSet<>();
        endVisited.add(end);
        levelMap.put(end, 0);


        while (!beginVisited.isEmpty() && !endVisited.isEmpty()) {
            // 谁的元素个数少就遍历谁
            if (beginVisited.size() > endVisited.size()) {
                Set<Integer> tmp = beginVisited;
                beginVisited = endVisited;
                endVisited = tmp;
            }

            // 下一层需要遍历的元素集合
            Set<Integer> nextVisited = new HashSet<>();

            for (Integer cur : beginVisited) {

                int step = levelMap.get(cur);

                if (endVisited.contains(cur)) {
                    return step;
                }

                // 取值范围 [1, N]
                int leftIndex = cur - arr[cur - 1];
                int rightIndex = cur + arr[cur - 1];
                // 必须要有这一步的判断！！！踩坑地方
                if (endVisited.contains(leftIndex) || endVisited.contains(rightIndex)) {
                    return step + 1;
                }


                if (leftIndex > 0 && !levelMap.containsKey(leftIndex)) {
                    levelMap.put(leftIndex, step + 1);
                    nextVisited.add(leftIndex);
                }
                if (rightIndex <= N && !levelMap.containsKey(rightIndex)) {
                    levelMap.put(rightIndex, step + 1);
                    nextVisited.add(rightIndex);
                }


            }
            beginVisited = nextVisited;
        }
        return -1;
    }










    /**start 和 end从1开始
     * 暴力递归  使用boolean类型数组 walk 来表示 某个位置是否走过
     *
     * 因为可变参数 突破了整形  所以这种递归一定不能改造成动态规划！！！
     *
     * 参考动态规划的总纲建议！！！
     *
     */
    public static int jump2(int N, int start, int end, int[] arr) {
        boolean[] walk = new boolean[N];
        return f2(arr, N, end, start, walk);
    }


    /**
     * 暴力递归
     * @param arr
     * @param N  数组的长度 最多需要走的步数
     * @param end 要到达的目的地
     * @param cur 当前位置
     * @param walk 已经走过的位置的记录  true表示已经走过了
     * @return
     *
     * 深度优先需要还原现场原因  踩坑地方！！！
     *
     *                  4
     *               3     5
     *            2          7
     *               5
     *                  7
     *   4 ——> 3  ——> 2 ——> 5 ——> 7  不还原现场  将找不到  4 ——> 5 ——> 7
     *
     */
    private static int f2(int[] arr, int N, int end, int cur, boolean[] walk) {
        if (cur == end) {
            return 0;
        }

        if (walk[cur-1]) {
            return -1;
        }

        int nextLeftStep = -1;
        int nextRightStep = -1;


        walk[cur-1] = true;
        // 取值区间[1, N]
        int leftIndex = cur - arr[cur - 1];
        if (leftIndex > 0) {
            nextLeftStep = f2(arr, N, end, leftIndex, walk);
        }


        int rightIndex = cur + arr[cur - 1];
        if (rightIndex <= N) {
            nextRightStep = f2(arr, N, end, rightIndex, walk);
        }
        // 深度优先需要还原现场！！！踩坑地方！！！
        walk[cur-1] = false;


        if (nextLeftStep == -1 && nextRightStep == -1) {
            return -1;
        } else if (nextLeftStep == -1) {
            return nextRightStep+1;
        } else if (nextRightStep == -1) {
            return nextLeftStep +1;
        }else {
            return Math.min(nextLeftStep,nextRightStep)+1;
        }
    }



    /**
     * 动态规划第三个版本
     * 针对第二种的暴力递归进行优化  把数组的可变参数修改为整型变量！！！
     *
     * 十分巧妙！！！ 用 k 表示已经走过的步数
     * baseCase k > N 时  一定是不可到达的
     *
     */
    public static int jump3(int N, int start, int end, int[] arr) {
        return f3(arr, N, end, start, 0);
    }


    /**
     * 因为每个点在遍历过程中最多只能走过一次，所以在最坏的情况下  由start->end k的步数是把arr中数组的所有点踩一遍
     * 步数最多不超过N  超过N一定是不能到达的！！！！
     *
     *
     * @param arr
     * @param N     最多可以走的步数
     * @param end   要到达的目的地
     * @param cur   起始地址
     * @param k     达到start位置已经走过的步数
     * @return
     */
    private static int f3(int[] arr, int N, int end, int cur, int k) {

        if (cur == end){
            return 0;
        }

        // baseCase 因为走N步相当与把所有的位置都走了一遍
        // 每个点只能走一次 不能走两次  所以超过N的步数还没到 一定是不能到达的
        if (k > N ){
            return -1;
        }

        int nextLeftStep = -1;
        int nextRightStep = -1;


        // 取值区间[1, N]
        int leftIndex = cur - arr[cur - 1];
        if (leftIndex > 0) {
            nextLeftStep = f3(arr, N, end, leftIndex, k+1);
        }


        int rightIndex = cur + arr[cur - 1];
        if (rightIndex <= N) {
            nextRightStep = f3(arr, N, end, rightIndex, k+1);
        }


        if (nextLeftStep == -1 && nextRightStep == -1) {
            return -1;
        } else if (nextLeftStep == -1) {
            return nextRightStep+1;
        } else if (nextRightStep == -1) {
            return nextLeftStep +1;
        }else {
            return Math.min(nextLeftStep,nextRightStep)+1;
        }
    }



    // for test
    public static int[] gerRandomArray(int N, int R) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * R);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int maxN = 20;
        int maxV = 10;
        int testTimes = 2000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerRandomArray(maxN, maxV);
            int N = arr.length;
            int start = (int) (Math.random() * N) + 1;
            int end = (int) (Math.random() * N) + 1;
            int ans1 = jump1(N, start, end, arr);
            int ans2 = jump2(N, start, end, arr);
            int ans3 = jump3(N, start, end, arr);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("start : " + start);
                System.out.println("end : " + end);
                System.out.println("ans1 : " + ans1);
                System.out.println("ans2 : " + ans2);
                System.out.println("ans3 : " + ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test end");
    }



}
