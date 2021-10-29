package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/27
 * @version: V1.0
 * @slogan:
 * @description :设计井字棋
 */
public class Problem_0348_DesignTicTacToe {

    class TicTacToe {
        private int[][] rows;
        private int[][] cols;
        private int[] leftUp;
        private int[] rightUp;
        private boolean[][] matrix;
        private int N;

        public TicTacToe(int n) {
            // rows[i][1] 表示1号玩家在第i行具有的棋子的数量
            // rows[i][2] 表示2号玩家在第i行具有的棋子的数量
            rows = new int[n][3];
            // cols[j][1] 表示1号玩家在第j列具有的棋子的数量
            // cols[j][2] 表示2号玩家在第j列具有的棋子的数量
            cols = new int[n][3];
            // leftUp[1] 表示1号玩家在左斜线上具有的棋子的数量
            // leftUp[2] 表示2号玩家在左斜线上具有的棋子的数量
            leftUp = new int[3];
            // rightUp[1] 表示1号玩家在右斜线上具有的棋子的数量
            // rightUp[2] 表示2号玩家在右斜线上具有的棋子的数量
            rightUp = new int[3];
            matrix = new boolean[n][n];
            N = n;
        }

        public int move(int row, int col, int player) {
            if (matrix[row][col]) {
                return 0;
            }
            matrix[row][col] = true;
            rows[row][player]++;
            cols[col][player]++;
            if (row == col) {
                leftUp[player]++;
            }
            if (row + col == N - 1) {
                rightUp[player]++;
            }
            if (rows[row][player] == N || cols[col][player] == N || leftUp[player] == N || rightUp[player] == N) {
                return player;
            }
            return 0;
        }

    }
}
