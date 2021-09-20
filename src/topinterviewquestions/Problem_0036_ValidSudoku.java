package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/20
 * @version: V1.0
 * @slogan:
 * @description :
 * <p>
 * 【解题思路】
 * 用三个数组来表示数字是否出现过
 * row[i][j]=true 表示  第i行 数字j已经出现了
 * col[i][j]=true  表示 第i列 数字j已经出现过了
 * bucket[i][j]=true 表示  第i个矩阵 数字j已经出现过了
 * <p>
 * 使用这三个数组可以  快速的知道 某一行某列的数字是否发生了冲突
 */
public class Problem_0036_ValidSudoku {

    public static boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] bucket = new boolean[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ('.' != board[i][j]) {
                    int num = board[i][j] - '0';
                    int index = (i / 3) * 3 + j / 3;
                    if (row[i][num] || col[j][num] || bucket[index][num]) {
                        return false;
                    }
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[index][num] = true;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

        System.out.println(isValidSudoku(board));;
    }


}
