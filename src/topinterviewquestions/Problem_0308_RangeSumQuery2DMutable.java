package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/25
 * @version: V1.0
 * @slogan:
 * @description :二维区域和检索
 *
 * 给定一个二维矩阵，计算其子矩形范围内元素的总和，该子矩阵的左上角为 (row1, col1) ，右下角为 (row2, col2)。
 *
 * Range Sum Query 2D
 * 上图子矩阵左上角 (row1, col1) = (2, 1) ，右下角(row2, col2) = (4, 3)，该子矩形内元素的总和为 8。
 *
 *  给定 matrix = [
 *                  [3, 0, 1, 4, 2],
 *                  [5, 6, 3, 2, 1],
 *                  [1, 2, 0, 1, 5],
 *                  [4, 1, 0, 1, 7],
 *                  [1, 0, 3, 0, 5]
 *               ]
 * sumRegion(2, 1, 4, 3) -> 8
 * sumRegion(1, 1, 2, 2) -> 11
 * sumRegion(1, 2, 2, 4) -> 12
 *
 * 二维 三维 四维 ... 带有更新操作时  参考indexTree 的更新 求和操作  很简单 有几个维度就嵌套几个for循环
 *
 */
public class Problem_0308_RangeSumQuery2DMutable {

    private int[][] tree;
    private int[][] nums;
    private int N;
    private int M;

    public Problem_0308_RangeSumQuery2DMutable(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        N = matrix.length;
        M = matrix[0].length;
        tree = new int[N + 1][M + 1];
        nums = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    // 用户给我的row，col不能越界
    private int sum(int row, int col) {
        int sum = 0;
        for (int i = row + 1; i > 0; i -= i & (-i)) {
            for (int j = col + 1; j > 0; j -= j & (-j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }

    // 用户给我的row，col不能越界
    public void update(int row, int col, int val) {
        if (N == 0 || M == 0) {
            return;
        }
        int add = val - nums[row][col];
        nums[row][col] = val;
        for (int i = row + 1; i <= N; i += i & (-i)) {
            for (int j = col + 1; j <= M; j += j & (-j)) {
                tree[i][j] += add;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (N == 0 || M == 0) {
            return 0;
        }
        // 多减去一个重叠的 (row1-1, col1-1)的区域
        return sum(row2, col2)  - sum(row1 - 1, col2) - sum(row2, col1 - 1) + sum(row1 - 1, col1 - 1);
    }
}
