package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Lisp
 * @date： 2021/9/25
 * @version: V1.0
 * @slogan:
 * @description :
 *
 * 【解题思路】 一圈一圈的打印
 *
 *  参考 PrintStar.class
 *
 */
public class Problem_0054_SpiralMatrix {

    public static List<Integer> spiralOrder(int[][] matrix) {

        int tr = 0, tc = 0;
        int dr = matrix.length -1, dc = matrix[0].length -1;

        List<Integer> ans = new ArrayList<>();

        while (tr <= dr && tc<=dc) {
            getAns(ans, matrix, tr++, tc++, dr--, dc--);
        }

        return ans;

    }

    private static void getAns(List<Integer> ans, int[][] matrix,  int tr, int tc, int dr, int dc) {




        if (tr==dr && tc ==dc){
            ans.add(matrix[tr][tc]);
            return;
        }

        if (tr == dr) {
            for (int i = tc; i <= dc; i++) {
                ans.add(matrix[tr][i]);
            }
            return;
        }

        if (tc == dc) {
            for (int i = tr; i <= dr; i++) {
                ans.add(matrix[i][dc]);
            }
            return;
        }


        for (int i = tc; i < dc; i++) {
            ans.add(matrix[tr][i]);
        }

        for (int i = tr; i < dr; i++) {
            ans.add(matrix[i][dc]);
        }

        for (int i = dc; i > tc; i--) {
            ans.add(matrix[dr][i]);
        }


        for (int i = dr; i > tr; i--) {
            ans.add(matrix[i][tc]);
        }
    }





    /**
     * teacher
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return ans;
        }
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a <= c && b <= d) {
            addEdge(matrix, a++, b++, c--, d--, ans);
        }
        return ans;
    }

    public static void addEdge(int[][] m, int a, int b, int c, int d, List<Integer> ans) {
        if (a == c) {
            for (int i = b; i <= d; i++) {
                ans.add(m[a][i]);
            }
        } else if (b == d) {
            for (int i = a; i <= c; i++) {
                ans.add(m[i][b]);
            }
        } else {
            int curC = b;
            int curR = a;
            while (curC != d) {
                ans.add(m[a][curC]);
                curC++;
            }
            while (curR != c) {
                ans.add(m[curR][d]);
                curR++;
            }
            while (curC != b) {
                ans.add(m[c][curC]);
                curC--;
            }
            while (curR != a) {
                ans.add(m[curR][b]);
                curR--;
            }
        }
    }



    public static void main(String[] args) {
        int[][] arr1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        int[][] arr2 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};

        int[][] arr3 = {{1, 2, 3, 4}};


        int[][] arr4= {{1}, {2}, {3}, {4},{5},{6},{7},{8},{9},{10}};

        List<Integer> list = spiralOrder(arr1);
        List<Integer> list2 = spiralOrder(arr2);
        List<Integer> list3 = spiralOrder(arr3);
        List<Integer> list4 = spiralOrder(arr4);

    }

}
