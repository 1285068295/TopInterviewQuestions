package followup;

/**
 * @author ：Lisp
 * @date： 2021/9/24
 * @version: V1.0
 * @slogan:
 * @description :旋转打印星星
 *
 *  需要用二位数组的坐标来标记那些位置有 *
 *  标记完成后 直接两层for循环打印即可
 *
 *  【难点在于】要能想到用二位数组来存储实际*的位置！！！
 *
 */
public class PrintStar {



    public static void printStar(int n) {
        int tr = 0, tc = 0;
        int dr = n -1, dc = n-1;
        char[][] matrix = new char[n][n];
        while (tr <= dr) {
            printPosition(matrix, tr, tc, dr, dc);
            tr+=2;
            tc+=2;
            dr-=2;
            dc-=2;
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]+  " ");
            }
            System.out.println();
        }
    }

    /**
     * 一圈一圈的旋转
     * @param tr
     * @param tc
     * @param dr
     * @param dc
     */
    private static void printPosition(char[][]matrix, int tr, int tc, int dr, int dc) {

        // 两个横向
        for (int i = tc; i <= dc; i++) {
            matrix[tr][i] = '*';
            matrix[dr][i] = '*';
        }
        // 挖去一个点
        matrix[dr][tc] = ' ';

        // 两个纵向
        for (int i = tr; i <= dr; i++) {
            matrix[i][dc] = '*';
            matrix[i][tc + 1] = '*';
        }
        // 挖去一个点
        matrix[tr + 1][tc + 1] = ' ';

    }

    public static void main(String[] args) {
        printStar(20);
    }



}
