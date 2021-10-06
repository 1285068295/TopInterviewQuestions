package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/5
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路一】
 *  从边入上的O入手  相邻的O进行感染，把4条边上的O相邻的O 标记是否改变
 *  思路就是准备了一个新的矩阵 从原来的就矩阵4条边入手  遇到O就把对应新矩阵对应位置改为O 并向四周扩散 相连的O同时映射到新的矩阵上
 *  处理完四条边后 根据新矩阵的O的情况 得到最终的答案
 *
 *
 *
 */
public class Problem_0130_SurroundedRegions {
    public static void solve(char[][] board) {

        int N = board.length;
        int M = board[0].length;


        // 准备了一个新的矩阵
        char[][] newBoard = new char[N][M];

        // 遍历四条边 找到边上的O
        for (int i = 0; i < N; i++) {
            if (board[i][0] == 'O') {
                process(board, i, 0, newBoard);
            }
            if (board[i][M - 1] == 'O') {
                process(board, i, M - 1, newBoard);
            }
        }
        for (int j = 0; j < M; j++) {
            if (board[0][j] == 'O') {
                process(board, 0, j, newBoard);
            }
            if (board[N - 1][j] == 'O') {
                process(board, N - 1, j, newBoard);
            }
        }

        // 根据新矩阵的情况 改变原来的矩阵
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = newBoard[i][j] == 'O' ? 'O' : 'X';
            }
        }
    }

    private static void process(char[][] board, int row, int col, char[][] newBoard){
        // 越界处理
        if (row < 0 || row == board.length || col < 0 || col == board[0].length) {
            return;
        }
        if(newBoard[row][col] != '\0'){
            return;
        }

        if (board[row][col] == 'O') {
            newBoard[row][col] = 'O';
            process(board, row - 1, col, newBoard);
            process(board, row + 1, col, newBoard);
            process(board, row, col + 1, newBoard);
            process(board, row, col - 1, newBoard);
        }
    }







    //  下面代码为老师的思路
    public static void solve1(char[][] board) {
        boolean[] ans = new boolean[1];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    ans[0] = true;
                    can(board, i, j, ans);
                    board[i][j] = ans[0] ? 'T' : 'F';
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char can = board[i][j];
                if (can == 'T' || can == 'F') {
                    board[i][j] = '.';
                    change(board, i, j, can);
                }
            }
        }

    }

    public static void can(char[][] board, int i, int j, boolean[] ans) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            ans[0] = false;
            return;
        }
        if (board[i][j] == 'O') {
            board[i][j] = '.';
            can(board, i - 1, j, ans);
            can(board, i + 1, j, ans);
            can(board, i, j - 1, ans);
            can(board, i, j + 1, ans);
        }
    }

    public static void change(char[][] board, int i, int j, char can) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            return;
        }
        if (board[i][j] == '.') {
            board[i][j] = can == 'T' ? 'X' : 'O';
            change(board, i - 1, j, can);
            change(board, i + 1, j, can);
            change(board, i, j - 1, can);
            change(board, i, j + 1, can);
        }
    }

    // 从边界开始感染的方法，比第一种方法更好
    public static void solve2(char[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }
        int N = board.length;
        int M = board[0].length;
        for (int j = 0; j < M; j++) {
            if (board[0][j] == 'O') {
                free(board, 0, j);
            }
            if (board[N - 1][j] == 'O') {
                free(board, N - 1, j);
            }
        }
        for (int i = 1; i < N - 1; i++) {
            if (board[i][0] == 'O') {
                free(board, i, 0);
            }
            if (board[i][M - 1] == 'O') {
                free(board, i, M - 1);
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == 'F') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public static void free(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'F';
        free(board, i + 1, j);
        free(board, i - 1, j);
        free(board, i, j + 1);
        free(board, i, j - 1);
    }

    public static void main(String[] args) {
        char[] s= new char[1];
        System.out.println(s[0]=='\0');

        
        char[][] board = {
                {'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}};


      char[][] ss = {
              {'X','O','X','O','X','O'},
              {'O','X','O','X','O','X'},
              {'X','O','X','O','X','O'},
              {'O','X','O','X','O','X'}};


      // [ ["X","O","X","O","X","O"],
        // ["O","X","X","X","X","X"],
        // ["X","X","X","O","X","X"],
        // ["O","X","O","X","O","X"]]

        solve(ss);

    }


}
