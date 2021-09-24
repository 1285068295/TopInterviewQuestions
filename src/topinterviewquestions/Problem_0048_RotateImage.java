package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/24
 * @version: V1.0
 * @slogan:
 * @description :
 * 【旋转图像思路】
 * 一层一层的旋转 先转最外层 在转里面的 因为外城的旋转不会移动到内层去。
 * 用左上角和右下角的坐标表示要旋转的圈
 * T(tr,tc)  D(dr, dc)  每转一层  tr-- tc-- dr++ dc++ 当tr<=dr时旋转
 *
 *
 */
public class Problem_0048_RotateImage {

    public static void rotate(int[][] matrix) {
        int tr = 0, tc = 0;
        int dr = matrix.length -1, dc = matrix.length -1;

        print(matrix);
        while (tr < dr) {
            rotateCircle(matrix, tr++, tc++, dr--, dc--);
        }

    }

    /**
     * 一圈一圈的旋转
     * @param matrix
     * @param tr
     * @param tc
     * @param dr
     * @param dc
     */
    private static void rotateCircle(int[][] matrix, int tr, int tc, int dr, int dc) {

        // 分4组 第一组 tc~dc-1   第二组 tr~dr-1  第三组 dc-tc+1  第一组  dr~tr+1
        for (int i = 0; i < dc-tc; i++) {
            int tmp = matrix[tr][tc+i];
            matrix[tr][tc + i] = matrix[dr - i][tc];
            matrix[dr - i][tc] = matrix[dr][dc- i];
            matrix[dr][dc- i]  = matrix[tr + i][dc];
            matrix[tr + i][dc] = tmp;
        }
        print(matrix);

    }

    private static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }


    }


    public static void main(String[] args) {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        rotate(matrix);
    }
}
