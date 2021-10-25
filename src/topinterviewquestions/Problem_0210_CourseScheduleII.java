package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author ：Lisp
 * @date： 2021/10/11
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 参考207题 中途遍历过程中同时收集答案即可
 *  拓扑排序
 *
 */
public class Problem_0210_CourseScheduleII {

    public class Node{
        // 课程编号
        public int name;
        // 入度
        public int in;

        /**
         * 谁依赖当前得课程！！！
         */
        public ArrayList<Node> nexts;

        public Node(int name){
            this.name = name;
            this.in = 0;
            // 这里需要初始化呀
            this.nexts = new ArrayList<>();
        }

    }


    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] ans = new int[numCourses];
        // 没有相互依赖的课程
        if (prerequisites == null || prerequisites.length == 0){
            for (int i = 0; i < numCourses; i++) {
                ans[i] = i;
            }
            return ans;
        }



        // key-课程编号  value-课程对应的node节点
        HashMap<Integer, Node> nodes = new HashMap<>();

        for (int[] arr : prerequisites) {
            // [1,0] 要学习课程 1，你需要先完成课程 0.
            int to = arr[0];
            int from = arr[1];

            // 课程是否构建过node节点了 只构建一次
            if (!nodes.containsKey(to)){
                nodes.put(to, new Node(to));
            }

            if (!nodes.containsKey(from)){
                nodes.put(from, new Node(from));
            }

            // from和to各自对应的node节点
            Node t = nodes.get(to);
            Node f = nodes.get(from);

            // to的课程节点入度加1
            t.in++;
            // from的下一个课程指向to
            f.nexts.add(t);
        }

        // 存在依赖的所有的课程数量
        int needPrerequisitesNum = nodes.size();
        // 入度变为0时 表示课程修完了 count++
        int count = 0;

        Queue<Node> queue = new LinkedList<>();
        for (Node node : nodes.values()) {
            if (node.in == 0){
                // 第一遍for筛选出所有入度为0的课程
                queue.add(node);
            }
        }

        while(!queue.isEmpty()){
            // 因为queue中只加入入度为0的节点 所以弹出的每个节点都是入度为0的点
            Node zeroInNode = queue.poll();
            // 收集答案
            ans[count] = zeroInNode.name;
            // 弹出的课程修完了
            count++;
            for (Node next: zeroInNode.nexts) {
                if (--next.in == 0){
                    queue.add(next);
                }
            }
        }


        // count与needPrerequisitesNum 相等表示相互依赖的全部  还需要处理没有相互依赖的课程 顺序修即可
        if (count != needPrerequisitesNum){
            return new int[0];
        }

        // 处理剩下的美哟依赖关系的课程
        for (int i = 0; i < numCourses; i++) {
            if (!nodes.containsKey(i)){
                ans[count++] = i;
            }

        }
        return ans;
    }
}
