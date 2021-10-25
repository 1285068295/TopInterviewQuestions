package topinterviewquestions;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author ：Lisp
 * @date： 2021/10/17
 * @version: V1.0
 * @slogan:
 * @description : 4平方和定理
 * 【解题思路】 暴力打表找规律。
 *
 * 我自己的暴力解答的思路 不好  重复计算量太大！！！
 * 不如老师的好
 * 暴力解  老师的暴力解答居然可以跑完测试用力！！！
 * 而我的的暴力尝试 时间复杂度太高 都跑不完测试用力！！！
 *
 *
 *
 */
public class Problem_0279_PerfectSquares {


    /**
     * 暴力递归
     */
    public static int numSquares1(int n) {
        LinkedList<Integer> path = new LinkedList<Integer>();
        return f1(n, 1, path);
    }

    /**
     * f(13) = min{1+f(12), 2+f(11), 3+f(10) ...   }
     *
     * @param num  数字num被差分成平方数之和的最小个数
     * @param i
     * @return
     */
    public static int f1(int num, int i, LinkedList<Integer> path) {

        if (num == 0) {
            // 找到一种方案
            return path.size();
        }
        if (num < 0) {
            // 无效方案
            return -1;
        }
        int ans = Integer.MAX_VALUE;
        for (int j = 1; j * j <= num; j++) {
            path.add(j);
            int tmp = f1(num - j * j, j, path);
            if (tmp != -1) {
                ans = Math.min(ans, tmp);
            }
            path.removeLast();
        }

        return ans;
    }

    /**
     * 暴力递归改记忆化搜索
     */
    public static int numSquares2(int n) {
        LinkedList<Integer> path = new LinkedList<Integer>();
        HashMap<Integer, Integer> map = new HashMap<>();

        return f2(n, n, 1, path, map);

    }


    public static int f2(int N, int num, int i, LinkedList<Integer> path, HashMap<Integer, Integer> map) {
        if (map.containsKey(num)) {
            return map.get(num);
        }

        if (num == 0) {
            // 找到一种方案
            map.put(N, path.size());
            return path.size();
        }
        if (num < 0) {
            // 无效方案
            return -1;
        }
        int ans = Integer.MAX_VALUE;
        // 从大到小的尝试  为了利用map的记录
        // 如果从小到大的尝试 map可能存放的不是最优解  如4 第一次尝试时 会放入 map(4,4= 1+1+1+1)
        for (int j = (int) Math.sqrt(num); j >= 1; j--) {
            path.add(j);
            int tmp = f2(N,num - j * j, j, path,map);
            if (tmp != -1) {
                ans = Math.min(ans, tmp);
            }
            path.removeLast();
        }

        return ans;
    }


    /**
     * 暴力递归改记忆化搜索
     * 这个方法时错误的如  23 = 16 + 4 + 1 + 1 + 1  最后得到5个数
     */
    @Deprecated
    public static int numSquares3(int n) {
        LinkedList<Integer> path = new LinkedList<Integer>();
        HashMap<Integer, Integer> map = new HashMap<>();

        return f3(n, n, 1, path, map);
    }

    /**
     * f2优化版本 提前结束for循环  节省大量的重复计算
     */
    @Deprecated
    public static int f3(int N, int num, int i, LinkedList<Integer> path, HashMap<Integer, Integer> map) {
        if (map.containsKey(num)) {
            return map.get(num);
        }

        if (num == 0) {
            // 找到一种方案
            map.put(N, path.size());
            return path.size();
        }
        if (num < 0) {
            // 无效方案
            return -1;
        }
        int ans = Integer.MAX_VALUE;
        // 从大到小的尝试  为了利用map的记录
        // 如果从小到大的尝试 map可能存放的不是最优解  如4 第一次尝试时 会放入 map(4,4= 1+1+1+1)
        for (int j = (int) Math.sqrt(num); j >= 1; j--) {
            path.add(j);
            int tmp = f3(N,num - j * j, j, path,map);
            if (tmp != -1) {
                return tmp;
            }
            path.removeLast();
        }

        return ans;
    }


    /**
     * 暴力解  老师的暴力解答居然可以跑完测试用力！！！
     * 而我的的暴力尝试 时间复杂度太高 都跑不完测试用力！！！
     * 因为一个数必然是可以 用平方数分解的 所以不需要判断  最差情况全部为 1
     */
    public static int numSquaresTeacher1(int n) {
        int res = n, num = 2;
        while (num * num <= n) {
            // 这里的a b会大量减少重复计算！！！！
            int a = n / (num * num), b = n % (num * num);
            // a 表示多个num*num 如 12 = 4 + 4 + 4
            // b = n - num * num
            res = Math.min(res, a + numSquaresTeacher1(b));
            num++;
        }
        return res;
    }


    /**
     * 通过打表可以看出
     * 1 : 1, 4, 9, 16, 25, 36, ...
     * 4 : 7, 15, 23, 28, 31, 39, 47, 55, 60, 63, 71
     * 规律解
     * 规律一：个数不超过4
     * 规律二：出现1个的时候，显而易见
     * 规律三：任何数 % 8 == 7，一定是4个
     * 规律四：任何数消去4的因子之后，剩下rest，rest % 8 == 7，一定是4个
     */
    public static int numSquaresTeacher2(int n) {
        int rest = n;
        while (rest % 4 == 0) {
            rest /= 4;
        }
        if (rest % 8 == 7) {
            return 4;
        }
        int f = (int) Math.sqrt(n);
        if (f * f == n) {
            return 1;
        }

        // 这里还是存在暴力尝试的情况
        for (int first = 1; first * first <= n; first++) {
            int second = (int) Math.sqrt(n - first * first);
            if (first * first + second * second == n) {
                return 2;
            }
        }
        return 3;
    }

    // 数学解
    // 1）四平方和定理
    // 2）任何数消掉4的因子，结论不变
    public static int numSquaresTeacher3(int n) {
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) {
            return 4;
        }
        for (int a = 0; a * a <= n; ++a) {
            int b = (int) Math.sqrt(n - a * a);
            if (a * a + b * b == n) {
                return (a > 0 && b > 0) ? 2 : 1;
            }
        }
        return 3;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int t = numSquaresTeacher1(i);
            if (t == 4){
                System.out.println("i=" + i + " " + t);
            }
        }
    }


}
