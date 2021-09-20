package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/18
 * @version: V1.0
 * @slogan:
 * @description :KMP
 * 【KMP算法原理】
 * 1 str与match字符串 用两个指针进行匹配 str[x] match[y] 同时进行移动匹配
 *   当str[x]与match[y]不匹配时  str[x]的位置不移动  match[y]回退到next[y]的位置进行比较
 *
 * 2 next数组的含义
 *    特别注意  next[i]是要求i前面的字符串   0~ i-1位置上字符串的最大公共前缀长度  与i位置本身的字符没有半毛钱关系！！！
 *             用match[i-1] 与next[i-1]+1位置字符比较的
 *             最大公共前缀后缀长度求解时  比较  match[i-1]  match[  next[i-1] ]
 *
 *
 *   第一层的含义：match[y]与str[x]不匹配时 y要回退的位置就是  next[y] 如果依然不匹配继续回退  next[ next[y] ]...
 *   第二层的含义：i位置  前面的字符串 的最大公关前缀与后缀的长度
 *   如match="aab aab k"  再"k" 字符位置  最大公共前缀为 aab  aab  长度为3  next[6]=3
 *   当str=...... aab aab m
 *   match=       aab aab k    k与a没有匹配
 *                             str=...... aab aab m
 *   match回退到next[6]位置   match=           aab a ab k
 *
 *                          str=...... aab aab   m
 *  match回退到next[3]位置   match=            a  a b aab k
 *3 next数组  next[0]=-1 next[1] = 0
 *
 *4 时间复杂度  O(N)
 *
 *
 *
 */
public class Problem_0028_ImplementStrStr {

    public static int strStr(String s, String m) {

        if (s == null || m == null || s.length() < m.length()) {
            return -1;
        }
        if (m.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        char[] match = m.toCharArray();


        int[] next = getNextArray(match);

        // 两个指针同时开始移动进行匹配
        int x = 0;
        int y = 0;


        while (x < str.length && y < match.length) {

            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (next[y] >= 0) {
                // next[0]=-1
                y = next[y];
            } else {
                // y=0 都没有匹配上  需要将str字符串向后移动一位
                x++;
            }
        }

        // 1 y越界  x越界
        // 2 y越界  x没越界
        // 3 y越界  x越界
        return y == match.length ? x - y : -1;
    }

    /**
     * 生成next数组
     *
     * @param match
     * @return
     */
    private static int[] getNextArray(char[] match) {

        if (match.length == 1) {
            return new int[]{-1};
        }
        if (match.length == 2) {
            return new int[]{-1, 0};
        }

        int[] next = new int[match.length];

        next[0] = -1;
        next[1] = 0;
        int i = 2;
        // match[i] 与 match [ next[i-1] ] 进行比较！！！
        int cn = next[i - 1];

        while (i < match.length) {
            if (match[i - 1] == match[cn]) {
                // cn 已经是next[i-1]了  不需要再进行next[cn]!!!
                next[i] =cn + 1;

                // 或者cn =next[i]
                cn =next[i];

                // 下一次 需要计算 i+1 的位置
                i++;

            } else if (cn > 0) {
                // 踩坑点 cn>0 的条件分支
                cn = next[cn];
            } else {
                // cn=0  next[cn] = -1
                next[i] = 0;
                i++;
            }
        }
        return next;
    }


    public static void main(String[] args) {
        System.out.println(strStr("mississippi",  "issipi"));


    }

}
