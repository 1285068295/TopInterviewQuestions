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
 *【解题思路】
 * 使用拓扑排序来解答，从入度为0的课程开始求解。
 *
 *
 *
 */
public class Problem_0207_CourseSchedule {

    /**
     * 图结构的node节点
     *
     *  一个node，就是一个课程
     *  name是课程的编号
     *  in是课程的入度
     *
     *  这道题只需要要入度（from 不需要出度to）
     */
    public static class Node {
        public int name;
        public int in;
        public ArrayList<Node> nexts;

        public Node(int n) {
            name = n;
            in = 0;
            nexts = new ArrayList<>();
        }
    }

    /**
     * @param numCourses  无效参数  不需要使用
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {

        if (prerequisites == null || prerequisites.length == 0){
            // 各个课程之间不存在依赖关系  一门一门的修即可
            return true;
        }


        // key-课程  value-当前课程对应的node节点
        HashMap<Integer, Node> nodes = new HashMap<>();
        // 把各个课程的依赖 构建出node
        for (int[] arr : prerequisites) {
            // [1, 0] from=0 to=1 课程 1 依赖于课程 0
            int from = arr[1];
            int to = arr[0];

            // 下面两个if是把课程构建成node节点 只需要构建一次 之后
            // 遇到相同的课程不会再构建
            if (!nodes.containsKey(from)){
                nodes.put(from, new Node(from));
            }

            if (!nodes.containsKey(to)){
                nodes.put(to, new Node(to));
            }

            // 注意map key-课程编号  value-课程对应的node节点
            Node t = nodes.get(to);
            Node f = nodes.get(from);


            // from依赖的课程的入度加1
            f.nexts.add(t);
            // 1 -> 2 表示2的入度加1 修完1课程才能修2课程
            t.in++;
        }

        // 总的依赖的课程数量
        int needPrerequisitesNums = nodes.size();
        // 双向队列 只存放入度为0的节点
        Queue<Node> queue = new LinkedList<>();

        // 找到所有入度为0的课程 放入到队列中
        for (Node node : nodes.values()) {
            if (node.in == 0) {
                queue.add(node);
            }
        }

        // 统计队列一共处理了多少入度为0的节点
        int count = 0;
        while(!queue.isEmpty()){
            Node zeroNode = queue.poll();

            count++;

            for (Node next : zeroNode.nexts) {
                if(--next.in == 0){
                    // 只有next入度为0的课程才加入到队列中
                    queue.add(next);
                }
            }

        }

        // 最终修完的count课程数量 必须等于 存在依赖关系的课程数量  否则一定是有循环依赖
        return count == needPrerequisitesNums;
    }




}
