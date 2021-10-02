package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/1
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 * 暴力递归  深度优先进行遍历搜索。每个字符位置  有四个方向走，注意不要走重复的路线。
 *  题目明确要求了 不可以向回走！！！
 *  本题使用的临时变量添加现场 注意一定不要忘记恢复现场原状。
 *
 *
 * 代码一次性过 强无敌！！！
 *
 */
public class Problem_0079_WordSearch {


    public static boolean exist(char[][] board, String word) {
        char[] w = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (process(board, w,i,j,0)){
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 从 board[i][j] 位置出发是否可以走出word字符
     *
     * 注意走过的路线不要重复走
     *
     */
    private static boolean process(char[][] board, char[] word, int i, int j, int index) {
        if (index == word.length){
            return true;
        }

        // 越界情况
        if (i<0||i>=board.length||j<0||j>=board[0].length){
            return false;
        }

        if (board[i][j] != word[index]){
            return false;
        }

        // board[i][j] = word[index] 将board[i][j] 置为空字符  为了达到不走重复路经的目的
        // 使用临时变量添加现场！！！
        char tmp = board[i][j];
        board[i][j] = 0;

        if (process(board, word, i - 1, j, index + 1)
                || process(board, word, i + 1, j, index + 1)
                || process(board, word, i, j - 1, index + 1)
                || process(board, word, i, j + 1, index + 1)) {
            return true;
        }
        // 恢复现场
        board[i][j] = tmp;
        return false;
    }

}
