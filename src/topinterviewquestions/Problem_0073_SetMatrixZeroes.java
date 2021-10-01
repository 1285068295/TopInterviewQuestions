package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/27
 * @version: V1.0
 * @slogan:
 * @description :矩阵置零
 *【解题思路】
 * 思路一： 使用两个变量
 *
 *
 *
 *
 * 思路二： 使用一个变量
 *
 *
 */
public class Problem_0073_SetMatrixZeroes {


    /**
     * 使用两个变量的求解方案 firstRow  firstCol
     */
    public static void setZeroes(int[][] matrix) {

        // 第一行是否需要置为0
        boolean firstRow = false;
        // 第一列是否需要置为0
        boolean firstCol = false;
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                firstRow = true;
                break;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                firstCol = true;
                break;
            }
        }

        // 利用矩阵的第一行第一列 来存储对应的点 所在的行 所在的列是否需要置为0
        // 做法：[i][j]=0时 把[i][0]=0  [0][j]=0
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 从上往下根据第0行  第0列上存储的0来置换矩阵
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                // 对应行全置为0
                for (int col = 1; col < matrix[0].length; col++) {
                    matrix[i][col] = 0;
                }
            }
        }


        // 从左往右根据第0列  第0列上存储的0来置换矩阵
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                // 对应行全置为0
                for (int row = 1; row < matrix.length; row++) {
                    matrix[row][j] = 0;
                }
            }
        }

        if (firstCol) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }

        if (firstRow) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
    }


    /**
     * for test
     */
    public static void print(int[][] matrix) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

    }





















    public static void main(String[] args) {


        int[][] matrix = {{1, 2, 3, 0}, {0, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 0, 4}};
        int[][] matrix2 = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};


        print(matrix);
        setZeroes(matrix);
        print(matrix);


        print(matrix2);
        setZeroes(matrix2);
        print(matrix2);



    }






}
