package topinterviewquestions;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author ：Lisp
 * @date： 2021/10/23
 * @version: V1.0
 * @slogan:
 * @description :有序矩阵中第 K 小的元素
 *
 * 【解题思路】
 *  利用堆结构  从左上角开始遍历，每次从堆顶弹出元素后，加入当前点的右边和下边的两个点，循环弹出 加入堆中数据
 *  弹出k次时 就是第k小的数
 *
 *  需要注意的是 因为我们弹出 K 次就得到第K 小的数了  所以要使用小根堆。
 *
 */
public class Problem_0378_KthSmallestElementInSortedMatrix {

    public static class Node{
        public int row;
        public int col;
        public int val;

        public Node(int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    /**
     * 小根堆 最小的几个数
     */
    public static class MyComparator implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            return o1.val - o2.val;
        }
    }



    /**
     * 1   13  16  29
     * 4   14  18  63
     * 20  21  27  84
     * 36  39  43  104
     */
    public static int kthSmallest(int[][] matrix, int k) {

        // 踩坑地方 需要去重  不能重复加入
        int N = matrix.length;
        boolean[][] used = new boolean[N][N];

        // 小根堆
        PriorityQueue<Node> heap = new PriorityQueue<>(new MyComparator());
        heap.add(new Node(0, 0, matrix[0][0]));

        for (int i = 1; i < k; i++) {
            Node top = heap.poll();
            int row = top.row;
            int col = top.col;
            if (row + 1 < N && !used[row + 1][col]) {
                used[row + 1][col] = true;
                heap.add(new Node(row + 1, col, matrix[row + 1][col]));
            }
            if (col + 1 < N&& !used[row ][col+ 1]) {
                used[row ][col+ 1] = true;
                heap.add(new Node(row, col + 1, matrix[row][col + 1]));
            }

        }
        return heap.poll().val;
    }

    public static void main(String[] args) {

        int[][] matrix ={
                          {1,3,5},
                          {6,7,12},
                          {11,14,14}};
        System.out.println(kthSmallest(matrix, 6));

    }

}
