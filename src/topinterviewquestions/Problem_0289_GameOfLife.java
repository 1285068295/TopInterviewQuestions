package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/15
 * @version: V1.0
 * @slogan:
 * @description : 生命游戏
 *  有关这个游戏更有意思、更完整的内容：
 *  https://www.bilibili.com/video/BV1rJ411n7ri
 *
 * 【思路】
 *  利用了int类型32数  进行数据压缩
 *  题中二位矩阵的每个元素要么为0 要么为1
 *  当一个细胞经过计算得到 下一个状态是不死亡时 将2进制数 的第二位置为1   如  原来的1  变为  0011
 *  如果死亡 二进制下第二位 依然保持为0
 *  遍历完所有的矩阵元素后  在遍历一遍矩阵  所有的数据右移一位 就是下个状态的值
 *
 */
public class Problem_0289_GameOfLife {


    public static void gameOfLife(int[][] board) {

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[0].length; j++) {

                // 统计 board[i][j]周围的细胞数量
                int neighbors = neighbors(board, i, j);
                // 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
                // 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
                // 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
                // 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
                if ((board[i][j]) == 1) {
                    if (neighbors < 2 || neighbors > 3) {
                        // 死亡  主要要保持2进制下 最后的数据在这次遍历中 值不变
                        board[i][j] = board[i][j] | 0b01;
                    } else {
                        // 生存
                        board[i][j] = board[i][j] | 0b11;
                    }
                } else {
                    if (neighbors == 3) {
                        // 复活
                        board[i][j] = board[i][j] | 0b10;
                    }
                }

            }

        }


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 所有数据的第2位就是 下一个状态的值  1标志生存  0表示死亡
                board[i][j] = board[i][j] >> 1;
            }

        }
    }

    /**
     * 统计 board[i][j]周围的细胞数量
     */
    public static int neighbors(int[][] board, int i, int j) {
        int count = 0;
        count += ok(board, i - 1, j - 1) ? 1 : 0;
        count += ok(board, i - 1, j) ? 1 : 0;
        count += ok(board, i - 1, j + 1) ? 1 : 0;
        count += ok(board, i, j - 1) ? 1 : 0;
        count += ok(board, i, j + 1) ? 1 : 0;
        count += ok(board, i + 1, j - 1) ? 1 : 0;
        count += ok(board, i + 1, j) ? 1 : 0;
        count += ok(board, i + 1, j + 1) ? 1 : 0;
        return count;
    }

    /**
     * 越界判断
     */
    public static boolean ok(int[][] board, int i, int j) {
        return i >= 0 && i < board.length && j >= 0 && j < board[0].length && (board[i][j] & 1) == 1;
    }


}
