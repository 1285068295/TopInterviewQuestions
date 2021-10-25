package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】
 * matrix = [[1, 4,  7,  11, 15],
 *           [2, 5,  8,  12, 19],
 *           [3, 6,  9,  16, 22],
 *           [10,13, 14, 17, 24],
 *           [18,21, 23, 26, 30]],
 *  target = 5
 *  从i=0 j=matrix[0].length=1（右上角）位置 利用单调性搜索
 */
public class Problem_0240_SearchA2DMatrixII {

    public static boolean searchMatrix(int[][] matrix, int target) {
        int N = matrix.length;
        int M = matrix[0].length;
        int row = 0, col = M - 1;
        while (row < N && col >= 0) {
            if (matrix[row][col] < target) {
                row++;
            } else if (matrix[row][col] > target) {
                col--;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 4, 7, 11, 15}, {2, 5, 8, 12, 19}, {3, 6, 9, 16, 22}, {10, 13, 14, 17, 24}, {18, 21, 23, 26, 30}};

        System.out.println(searchMatrix(matrix, 20));
    }

}
