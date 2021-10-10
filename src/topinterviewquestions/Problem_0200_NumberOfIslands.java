package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description : 1相连的区域可组成一块岛屿  求岛屿的数量
 * 【思路一】
 *  递归方法 双重for循环遍历所有的1 遇到1时 利用递归把1周边相临的1 感染成2 同时统计岛屿的数量
 *  【思路二】
 *  使用并查集 先把所有的1 建立集合，然后把相邻的1集合合并，最后并查集的集合数量就是岛屿的数量
 *
 *
 */
public class Problem_0200_NumberOfIslands {

    /**
     * 递归的方法
     */
    public int numIslands(char[][] grid) {

        int N = grid.length;
        int M = grid[0].length;

        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == '1') {
                    infect(grid, N, M, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 利用递归感染1 周边的1
     */
    private void infect(char[][] grid, int N, int M, int i, int j) {
        // 越界处理
        if (i < 0 || j < 0 || i == N || j == M) {
            return;
        }

        if (grid[i][j] == '1') {
            grid[i][j] = '2';
            infect(grid, N, M, i + 1, j);
            infect(grid, N, M, i - 1, j);
            infect(grid, N, M, i, j + 1);
            infect(grid, N, M, i, j - 1);
        }
    }


    /**
     * 标准的并查集
     */
    static class UnionFind {

        /**
         * 每个样本封装成Node节点
         */
        public static class Node<V> {
            V value;

            public Node(V v) {
                value = v;
            }
        }

        public static class UnionSet<V> {

            // 样本与节点一一对应表，单纯的就是把v包装了一层Node
            public HashMap<V, Node<V>> nodes;

            // 节点与父节点对应表，这样Node节点自己就不需要设置parent向上指的指针
            public HashMap<Node<V>, Node<V>> parents;

            // 只有一个点为代表点的情况下，记录集合的节点个数如  a->b->c  c就是代表点，节点个数为3，在union时，用少的集合合并到多的集合上
            public HashMap<Node<V>, Integer> sizeMap;// 其大小就是并查集有多少个

            /**
             * 构造方法通过List集合初始化并查集
             */
            public UnionSet(List<V> values) {
                nodes = new HashMap<>();
                parents = new HashMap<>();
                sizeMap = new HashMap<>();
                for (V value : values) {
                    Node<V> node = new Node<>(value);// 带有泛型的初始化参口ArrayList
                    nodes.put(value, node);
                    parents.put(node, node);// 初始时每个节点的就是一个集合，所以每个节点的父节点就是当前节点本身
                    sizeMap.put(node, 1);// 初始时每个并查集的只有一个节点数量
                }
            }


            public boolean isSameSet(V a, V b) {
                if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                    return false;
                }
                // nodes存放的是每个value与之对应的包装了的Node节点
                Node<V> nodeA = nodes.get(a);
                Node<V> NodeB = nodes.get(b);
                // 找到每个节点所在集合对应的代表点，比较代表点是否相等
                Node<V> aParent = findFather(nodeA);
                Node<V> bParent = findFather(NodeB);
                return aParent == bParent;
            }


            /**
             * 向上找parent节点的过程中对经过的每个父节点进行优化
             * <p>
             * 路径扁平化
             * 如 a->b->c->d，在找父节点过程中扁平化后  a->d  b->d  c->d
             */
            public Node<V> findFather(Node<V> cur) {
                Stack<Node<V>> path = new Stack<>();
                while (cur != parents.get(cur)) {
                    // 把经过的每个父节点存下来，后面优化路径使用
                    path.push(cur);
                    cur = parents.get(cur);
                }
                // cur头节点
                while (!path.isEmpty()) {
                    // 更新路径上的父节点
                    parents.put(path.pop(), cur);
                }
                return cur;
            }


            /**
             * 找到一个节点的所在集合的代表点，如a->b->c a b c集合的代表点为c
             * 一路向上找父节点，直到父节点就是节点本身时就是代表点
             * 递归方法一路向上找，找父节点的过程中经历的节点没有优化
             */
            @SuppressWarnings("unused")
            private Node<V> findFather2(Node<V> cur) {
                Node<V> parent = parents.get(cur);
                if (parent != cur) {
                    parent = findFather(parent);
                }
                return parent;
            }

            public void union(V a, V b) {
                if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                    return;
                }
                // nodes存放的是每个value与之对应的包装了的Node节点
                Node<V> nodeA = nodes.get(a);
                Node<V> NodeB = nodes.get(b);

                Node<V> aParent = findFather(nodeA);
                Node<V> bParent = findFather(NodeB);
                if (aParent != bParent) {// 不在同一个并查集进行合并
                    int aSetSize = sizeMap.get(aParent);
                    int bSetSize = sizeMap.get(bParent);
                    if (aSetSize < bSetSize) {
                        // 节点的合并节点多的集合上，同时更新parents表和sizeMap表，别忘了移除节点
                        parents.put(aParent, bParent);
                        sizeMap.put(bParent, aSetSize + bSetSize);
                        sizeMap.remove(aParent);// aPrent挂到bParent下面，aParent不在是代表点
                    } else {
                        parents.put(bParent, aParent);
                        sizeMap.put(aParent, aSetSize + bSetSize);
                        sizeMap.remove(bParent);
                    }

                    // 上面代码优化为以下代码  优秀呀！！！从逻辑理解来说  上面的代码思路更为清晰。
//				int aSetSize = sizeMap.get(aParent);
//				int bSetSize = sizeMap.get(bParent);
//				Node<V> big = aSetSize > bSetSize ? parents.get(aParent) : parents.get(bParent);
//				Node<V> small = (big == aParent ? bParent : aParent);
//				parents.put(small, big);
//				sizeMap.put(big, aSetSize + bSetSize);
//				sizeMap.remove(small);
                }
            }

        }
    }


    /**
     * 并查集的每个key包装一层
     */
    public static class Element<V> {
        public V value;

        public Element(V value) {
            this.value = value;
        }
    }

    /**
     * 手动实现并查集功能
     * 1 isSameSet(V a, V b)
     * 	  找到a b对应的头节点看是否相等，在找的过程中缩短路径
     * 2 union(V a, V b)
     *   合并两个节点，先找到各自头节点，再把小的集合挂到大的集合上
     * 3 并查集的集合个数
     *   sizeMap的大小就是 并查集中集合个数，
     *   sizeMap中 key:每个集合的头节点，value:各个集合中节点的个数
     *
     */
    public static class UnionFindSet<V>{
        // 每个value，映射对应一个Element
        private HashMap<V, Element<V>> valueMap;
        // 每个节点对应一个 headMap一定是Element与Element对应的
        private HashMap<Element<V>, Element<V>> headMap;

        // sizeMap的 key就是每个小集合的头节点（代表点） value是小集合中节点个数
        // 注意sizeMap的size就是并查集中所有集合的个数
        private HashMap<Element<V>, Integer> sizeMap;

        /**
         * 构建初始的并查集
         */
        public UnionFindSet(List<V> list){
            valueMap = new HashMap<>();
            headMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v : list) {
                Element<V> e = new Element<>(v);
                valueMap.put(v, e);
                headMap.put(e, e);
                sizeMap.put(e, 1);
            }
        }


        public boolean isSameSet(V a, V b){
            if(valueMap.containsKey(a) && valueMap.containsKey(b)){
                // a b 这两个点对应的头节点是相同的，则a b 在同一个集合中
                // 必须要保证findHead的参数不能为null
                return findHead(valueMap.get(a)) == findHead(valueMap.get(b));
            }
            return false;
        }

        /**
         * 对于每一个节点，调用的findHead返回值一定不会为null
         */
        private Element<V> findHead(Element<V> element) {
            // 临时的栈存放经过的节点
            Stack<Element<V>> path = new Stack<>();
            // 从输入参数element出发，往上一直找，找到不能再往上的头节点，返回

            // 踩坑点：跳出循环的条件要注意  不是headMap.get(element)！=null
            while (headMap.get(element) != element) {
                path.push(element);
                element = headMap.get(element);
            }
            // 压缩路径，更新经过的每个节点的头节点
            while (!path.isEmpty()) {
                headMap.put(path.pop(), element);
            }
            return element;
        }


        /**
         * 合并a b两个点对应的集合
         */
        public void union(V a, V b){
            if(valueMap.containsKey(a) && valueMap.containsKey(b)){
                Element<V> headA = findHead(valueMap.get(a));
                Element<V> headB = findHead(valueMap.get(b));
                // 把节点数少的集合挂到节点数多的集合上
                int sizeA = sizeMap.get(headA);
                int sizeB = sizeMap.get(headB);
                // 踩坑点：必须在不相等的情况下去合并，小的集合挂到大的集合上
                if (headA != headB) {
                    if (sizeA > sizeB) {
                        headMap.put(headB, headA);
                        sizeMap.put(headA, sizeA + sizeB);
                        sizeMap.remove(headB);
                    } else {
                        headMap.put(headA, headB);
                        sizeMap.put(headB, sizeA + sizeB);
                        sizeMap.remove(headA);
                    }
                }
            }
        }

        /**
         * 并查集中集合的个数
         */
        public int getNum(){
            return sizeMap.size();
        }
    }


    /**
     * 使用并查集来合并点集合
     * 过程：
     * 把每个点映射成一个字符串  i_j 创建并查集，
     * 遍历所有的元素，遇到1 就把当前点上侧和左侧的1 对应到集合合并为一个集合
     */
    public  static int countIslands2(int[][] m) {
        List<String> list = new ArrayList<>();
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[0].length; col++) {
                if (m[row][col] == 1) {
                    // 每个点对应成一个字符串  这一点做的可以呀！！！
                    String position = String.valueOf(row) + "_" + String.valueOf(col);
                    list.add(position);
                }
            }
        }
        // 每个点构建并查集集合
        UnionFindSet<String> unionFindSet = new UnionFindSet<>(list);
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m[0].length; col++) {
                if (m[row][col] == 1) {// 合并每个点四周的1对应的集合
                    // 只需要合并左侧和上侧的即可，遍历所有的点一定能全部合并掉
                    if (row - 1 >= 0 && m[row - 1][col] == 1) {// 合并上侧点
                        String up = String.valueOf(row - 1) + "_" + String.valueOf(col);
                        String posotion = String.valueOf(row) + "_" + String.valueOf(col);
                        unionFindSet.union(up, posotion);
                    }
                    if (col - 1 >= 0 && m[row][col - 1] == 1) {// 合并左侧点
                        String left = String.valueOf(row) + "_" + String.valueOf(col - 1);
                        String posotion = String.valueOf(row) + "_" + String.valueOf(col);
                        unionFindSet.union(left, posotion);
                    }
                    // 每个点对应成一个字符串
                    String position = String.valueOf(row) + "_" + String.valueOf(col);
                    list.add(position);
                }
            }
        }
        return unionFindSet.getNum();
    }


}
