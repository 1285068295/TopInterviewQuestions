package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/25
 * @version: V1.0
 * @slogan:
 * @description :展开二维向量
 *
 *  有意思的题目
 */
public class Problem_0251_Flatten2DVector {

    public static class Vector2D {

        private int[][] matrix;
        private int row;
        private int col;
        // 当前遍历的元素是否使用过了  调用next方法会将其true
        private boolean curUse;

        public Vector2D(int[][] v) {
            matrix = v;
            row = 0;
            col = -1;
            curUse = true;
            hasNext();
        }

        public int next() {
            int ans = matrix[row][col];
            curUse = true;
            hasNext();
            return ans;
        }

        public boolean hasNext() {
            // 到达最后一行了 所有元素都已经遍历了
            if (row == matrix.length) {
                return false;
            }

            // 多次调用hasNext时情况 提高效率
            if (!curUse) {
                return true;
            }


            // (row，col)用过了  找下一个元素
            if (col < matrix[row].length - 1) {
                // 没有到达当前行末尾
                col++;
            } else {
                // 换行操作
                col = 0;
                // 存在中间的行 [][] 没有元素的情况
                do {
                    row++;
                } while (row < matrix.length && matrix[row].length == 0);
            }

            // 新的(row，col)
            if (row != matrix.length) {
                curUse = false;
                return true;
            } else {
                return false;
            }
        }
    }
}
