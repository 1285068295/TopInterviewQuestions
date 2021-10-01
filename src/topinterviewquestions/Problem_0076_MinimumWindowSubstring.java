package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Lisp
 * @date： 2021/9/29
 * @version: V1.0
 * @slogan:
 * @description :最短包含窗口
 *
 *
 * 【思路 】
 * 使用一张欠账表，分别记录每个字符还欠账多少个 遇到欠账表上的字符时 对应的欠账字符数量就减减  同时减减总的欠账数量
 * 注意  只有当欠账字符的数量为正数的时候  总欠账数量才减减  否则总欠账数量保持不变
 * 如  t="abc"    欠账表  a=1 b=1 c=1 总欠账 all=3 遇到第一个a时 欠帐表a=0 b=1 c=1 all=2  遇到第二个a时  a=-1 b=1 c-1 all=2
 * 每当all=0 时   s字符串的R指针不动  L指针右移缩短距离
 */
public class Problem_0076_MinimumWindowSubstring {

    /**
     * 老师的做法没有使用HashMap 我用HashMap实现来跑一下用例
     */
    public static String minWindow(String s, String t) {

        if (t.length() > s.length()) {
            return "";
        }

        char[] str = s.toCharArray();
        char[] chs = t.toCharArray();

        Map<Character, Integer> map = new HashMap<>();
        // 总的欠债数量
        int all = chs.length;
        int L = 0;
        int R = 0;

        // -1 表示还没有遇到s包含t的窗口时的长度
        int minlen = -1;

        // 记录最短的L
        int ansl = -1;
        // 记录最短的R
        int ansr = -1;

        // 把每个字符的欠账信息放入到map中
        for (int i = 0; i < chs.length; i++) {
            map.put(chs[i], map.getOrDefault(chs[i], 0) + 1);
        }

        /**
         * 这里的代码coding是一个技术活，说实话 从算法的实现逻辑上 很能体现实际的业务逻辑代码的实现是否啰嗦！！！
         *
         * 不单单是要把 功能实现了  更高层次的追求是 如何让你的代码更加的简洁！！！
         */
        while (R < str.length) {
            // 下面这句注释想法不是很成熟
            // 当R=str.length 时 还需要结算是否还有all=0的情况  所以应该时 先处理str[R]处的字符 在结算all=0的情况


            // str[R]字符欠债数量减少1
            if (map.containsKey(str[R])){
                map.put(str[R], map.get(str[R]) - 1);

                // 欠账表对应的字符 在还完块之后 大于等于0  说明这次是有效还款
                if (map.get(str[R]) >= 0){
                    all--;
                }
            }


            // 处理完 str[R]处的字符后 统一的结算all 如果all=0 就已知缩减 L~R 窗口的大小
            // while 循环处理直到再次开始欠账
            while (all == 0) {
                if (minlen == -1 || (minlen > R - L)) {
                    minlen = R - L;
                    ansl = L;
                    ansr = R;
                }

                if (map.containsKey(str[L])) {
                    map.put(str[L], map.get(str[L]) + 1);

                    // 移除str[L] 字符后欠帐表上对应字符大于零了 说明对应字符又欠账了 all要加加
                    // 否则就是 字符后欠帐表上对应字符依然时小于等于零 说明对应字符没有欠账
                    if (map.get(str[L]) > 0) {
                        all++;
                    }
                }
                // 移动L缩减窗口距离
                L++;
            }

            R++;
        }
        return minlen == -1 ? "" : s.substring(ansl, ansr + 1);
    }


    /**
     * 使用数组代替map结构实现
     *
     * 自己实现的第二版
     */
    public static String minWindow2(String s, String t) {

        if (t.length() > s.length()) {
            return "";
        }

        char[] str = s.toCharArray();
        char[] chs = t.toCharArray();


        int[] map = new int[256];
        // 总的欠债数量
        int all = chs.length;
        int L = 0;
        int R = 0;

        // -1 表示还没有遇到s包含t的窗口时的长度
        int minlen = -1;

        // 记录最短的L
        int ansl = -1;
        // 记录最短的R
        int ansr = -1;

        // 把每个字符的欠账信息放入到map中
        for (int i = 0; i < chs.length; i++) {
            map[chs[i]]++;
        }

        /**
         * 这里的代码coding是一个技术活，说实话 从算法的实现逻辑上 很能体现实际的业务逻辑代码的实现是否啰嗦！！！
         *
         * 不单单是要把 功能实现了  更高层次的追求是 如何让你的代码更加的简洁！！！
         */
        while (R < str.length) {
            // 使用数组代替map 不能通过map数组对一个位置上数值为0来判断是否 是欠账字符  因为存在还账后 对应字符为0的情况
            map[str[R]]--;

            // 无效字符map[str[R]]初始值为0 执行减1 后一定小于0
            // 欠账表对应的字符 在还完块之后 大于等于0  说明这次是有效还款
            if (map[str[R]] >= 0) {
                all--;
            }

            // 处理完 str[R]处的字符后 统一的结算all 如果all=0 就已知缩减 L~R 窗口的大小
            // while 循环处理直到再次开始欠账
            while (all == 0) {
                if (minlen == -1 || (minlen > R - L)) {
                    minlen = R - L;
                    ansl = L;
                    ansr = R;
                }

                map[str[L]]++;
                // 移除str[L] 字符后欠帐表上对应字符大于零了 说明对应字符又欠账了 all要加加
                // 否则就是 字符后欠帐表上对应字符依然时小于等于零 说明对应字符没有欠账
                if (map[str[L]] > 0) {
                    all++;
                }

                // 移动L缩减窗口距离
                L++;
            }

            R++;
        }
        return minlen == -1 ? "" : s.substring(ansl, ansr + 1);
    }


    /**
     * teacher  用长度256的数组 代替map结构  不错的想法！！！
     * @param s
     * @param t
     * @return
     */
    public static String minWindow3(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }
        char[] str = s.toCharArray();
        char[] target = t.toCharArray();
        int[] map = new int[256];
        for (char cha : target) {
            map[cha]++;
        }
        int all = target.length;
        int L = 0;
        int R = 0;
        // -1(从来没找到过合法的)
        int minLen = -1;
        int ansl = -1;
        int ansr = -1;
        // [L..R)   [0,0)  R
        while (R != str.length) {
            map[str[R]]--;
            if (map[str[R]] >= 0) {
                all--;
            }
            if (all == 0) {
                while (map[str[L]] < 0) {
                    map[str[L++]]++;
                }
                if (minLen == -1 || minLen > R - L + 1) {
                    minLen = R - L + 1;
                    ansl = L;
                    ansr = R;
                }
                all++;
                map[str[L++]]++;
            }
            R++;
        }
        return minLen == -1 ? "" : s.substring(ansl, ansr + 1);
    }



    public static void main(String[] args) {

        System.out.println(minWindow("sabbbbca", "abc"));
        System.out.println(minWindow2("sabbbbca", "abc"));

    }





}
