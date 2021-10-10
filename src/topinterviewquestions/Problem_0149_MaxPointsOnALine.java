package topinterviewquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/7
 * @version: V1.0
 * @slogan:
 * @description :
 * 【难点】 斜率的表达不能转成小数 因为精度如 10000000/ 10000001  和9999999/10000000 是相同的
 * 可以用字符串表达如  "3/10" 注意化简如  "2/4" = "1/2"
 * 实际使用了两个map来表示斜率的
 */
public class Problem_0149_MaxPointsOnALine {

    public static int maxPoints(int[][] points) {

        // key-斜率的分子  value-(key-斜率的分母， value-当前斜率下共线的点数量)
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();

        int ans = 0;
        for (int i = 0; i < points.length; i++) {
            // 每次都要清空map
            map.clear();
            int samePoint = 1;
            // 这里的初始值仔细考虑下
            int sameX = 0;
            int sameY = 0;
            // 哪个斜率压中的点最多，把最多的点的数量，赋值给line
            int line = 0;
            // 注意内层的循环从i+1开始 可以去重(a, b) 与 (b, a)情况
            for (int j = i + 1; j < points.length; j++) {
                if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
                    samePoint++;
                } else if (points[i][0] == points[j][0]) {
                    sameX++;
                } else if (points[i][1] == points[j][1]) {
                    sameY++;
                } else {
                    int x = points[i][0] - points[j][0];
                    int y = points[i][1] - points[j][1];
                    // 辗转相除法最大公因数  化简斜率
                    int gcd = gcd(x, y);
                    x /= gcd;
                    y /= gcd;

                    if (!map.containsKey(x)) {
                        map.put(x, new HashMap<>());
                    }
                    if (!map.get(x).containsKey(y)) {
                        map.get(x).put(y, 0);
                    }
                    map.get(x).put(y, map.get(x).get(y) + 1);
                    // 哪个斜率压中的点最多，把最多的点的数量，赋值给line
                    line = Math.max(line, map.get(x).get(y));
                }
            }
            // 这里需要注意比较的大小的结果赋值  最后加的samePoint的初始值为1（包含了点 (points[i][0], points[i][1])   ）
            ans = Math.max(ans, Math.max(Math.max(sameX, sameY), line) + samePoint);
        }
        return ans;
    }


    /**
     * 第一次调用时 必须保证x y同时不为0
     */
    private static int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    public static void main(String[] args) {
        System.out.println(gcd(2,4));
        System.out.println(gcd(9,15));
    }


}
