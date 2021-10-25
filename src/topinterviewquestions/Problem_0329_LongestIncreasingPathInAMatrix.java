package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/24
 * @version: V1.0
 * @slogan:
 * @description :矩阵中的最长递增路径
 */
public class Problem_0329_LongestIncreasingPathInAMatrix {

    /**
     * 动态规划版本
     */
    public static int longestIncreasingPath(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        int maxPath = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                maxPath = Math.max(maxPath, process(matrix, i, j, dp));
            }
        }

        return maxPath;
    }

    private static int process(int[][] matrix, int i, int j, int[][] dp) {
        if (i < 0 || j < 0 || i == matrix.length || j == matrix[0].length) {
            return 0;
        }


        if (dp[i][j] != 0){
            // 计算过的值 至少为1 不可能为0
            return dp[i][j];
        }

        int left = 0;
        if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
            dp[i][j - 1] = process(matrix, i, j - 1, dp);
            left = dp[i][j - 1];
        }

        int right = 0;
        if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
            dp[i][j + 1] = process(matrix, i, j + 1, dp);
            right = dp[i][j + 1];
        }

        int up = 0;
        if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
            dp[i - 1][j] = process(matrix, i - 1, j, dp);
            up = dp[i - 1][j];
        }

        int down = 0;
        if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
            dp[i + 1][j] = process(matrix, i + 1, j, dp);
            down = dp[i + 1][j];
        }
        dp[i][j] = 1 + Math.max(Math.max(left, right), Math.max(up, down));
        return dp[i][j];
    }


    /**
     * 简化变量版本
     */
    public static int process3(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        // 不越界
        // 往左右上下四个方向，能走出最长的后续是多少？
        int next = 0;
        // i, j i-1,j
        if (i > 0 && matrix[i - 1][j] > matrix[i][j]) {
            next = process3(matrix, i - 1, j, dp);
        }
        // i + 1
        if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
            next = Math.max(next, process3(matrix, i + 1, j, dp));
        }
        if (j > 0 && matrix[i][j - 1] > matrix[i][j]) {
            next = Math.max(next, process3(matrix, i, j - 1, dp));
        }
        if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
            next = Math.max(next, process3(matrix, i, j + 1, dp));
        }
        dp[i][j] = 1 + next;
        return 1 + next;
    }












    /**
     * 暴力解答
     * @param matrix
     * @return
     */
    public static int longestIncreasingPath2(int[][] matrix) {
        int maxPath = 0;
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                maxPath = Math.max(maxPath, process2(matrix, i, j));
            }
        }
        return maxPath;
    }

    /**
     * 从(i，j) 出发 最长的递增串长度为多少
     */
    private static int process2(int[][] matrix, int i, int j) {
        if (i < 0 || j < 0 || i == matrix.length || j == matrix[0].length) {
            return 0;
        }


        int left = 0;
        if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
            left = process2(matrix, i, j - 1);
        }

        int right = 0;
        if (j + 1 < matrix[0].length &&  matrix[i][j + 1] > matrix[i][j]) {
            right = process2(matrix, i, j + 1);
        }

        int up = 0;
        if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
            up = process2(matrix, i - 1, j);
        }

        int down = 0;
        if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
            down = process2(matrix, i + 1, j);
        }
        return 1 + Math.max(Math.max(left, right), Math.max(up, down));
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {9,9,4},
                {6,6,8},
                {2,1,1}};

        System.out.println(longestIncreasingPath(matrix));
//        System.out.println(longestIncreasingPath2(matrix));
    }
}
