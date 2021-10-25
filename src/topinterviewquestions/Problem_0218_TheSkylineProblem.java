package topinterviewquestions;

import java.util.*;

/**
 * @author ：Lisp
 * @date： 2021/10/17
 * @version: V1.0
 * @slogan:
 * @description :天际线问题
 * 【解题思路】
 *  整体流程设计为 找到每个位置点的的最大高度。
 *  把每座楼封装成两个节点 Node(x, flag, height)
 *  节点有三个属性 x坐标值  flag上升还是减低  height 对应的高度（）
 *  按照x的大小排列     flag为true表示上升的坐标点，flag=true的点排在flag=false下降的坐标点的前面(可以防止纸片状大楼)
 *  使用treeSet排序
 *  排完序后 依次的建立每个点对应的高度 在整理出轮廓线
 *
 *  这道题的难点在于 如何求出每个x点对应的最大高度！！！
 *  老师的解题思路是利用了 上升和下降的高度的次数来统计的！！！
 *
 */
public class Problem_0218_TheSkylineProblem {

    public static class Node{
        public int x;
        public boolean isAdd;
        public int h;
        public Node(int x, boolean isAdd, int height){
            this.x = x;
            this.isAdd = isAdd;
            this.h = height;
        }
    }

    public static class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node n1, Node n2) {
            if (n1.x != n2.x) {
                return n1.x - n2.x;
            }
            // isAdd为true的放在前面
            return n1.isAdd ? -1 : 1;
        }
    }

    public static List<List<Integer>> getSkyline(int[][] buildings) {
        int N = buildings.length;
        Node[] nodes = new Node[2 * N];
        // 每栋大楼构建两个点  左侧点为上升点 右侧点为下降点
        for (int i = 0; i < buildings.length; i++) {
            nodes[i * 2] = new Node(buildings[i][0], true, buildings[i][2]);
            nodes[i * 2 + 1] = new Node(buildings[i][1], false, buildings[i][2]);
        }
        // 排序
        Arrays.sort(nodes, new NodeComparator());


        // 有序表，key 代表某个高度 value 这个高度出现的次数
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
        // 有序表 key x的值 value 处在x位置时的高度
        TreeMap<Integer, Integer> xMaxHeight = new TreeMap<>();


        // 通过统计次数 以及mapHeightTimes.lastKey()得到x 坐标位置的最大高度  ！！！ 妙哉！！！
        // isAdd为正数高度次数就加1  否则就减1
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].isAdd) {
                if (!mapHeightTimes.containsKey(nodes[i].h)) {
                    mapHeightTimes.put(nodes[i].h, 1);
                } else {
                    mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) + 1);
                }
            } else {
                if (mapHeightTimes.get(nodes[i].h) == 1) {
                    mapHeightTimes.remove(nodes[i].h);
                } else {
                    mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) - 1);
                }
            }
            if (mapHeightTimes.isEmpty()) {
                xMaxHeight.put(nodes[i].x, 0);
            } else {
                xMaxHeight.put(nodes[i].x, mapHeightTimes.lastKey());
            }
        }
        List<List<Integer>>  ans = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : xMaxHeight.entrySet()) {
            int curX = entry.getKey();
            int curMaxHeight = entry.getValue();
            if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) != curMaxHeight) {
                ans.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
            }
        }
        return ans;
    }

    public static void main(String[] args) {

        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        map.put(4,4);
        map.put(5,5);

        System.out.println(map.ceilingKey(4));
        System.out.println(map.floorKey(55));


        int[][] buildings = {{1, 3, 1}, {3, 4, 2}, {3, 6, 1}, {4, 5, 3}};
        getSkyline(buildings);

    }


}
