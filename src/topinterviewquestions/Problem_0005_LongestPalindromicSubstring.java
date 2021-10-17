package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/9/5
 * @version: V1.0
 * @slogan:
 * @description :给你一个字符串 s，找到 s 中最长的回文子串。
 * 解法 Manacher算法
 *
 * 假设字符串str长度为N，想返回最长回文子串的长度  时间复杂度O(N)
 * 如  xabccbay 最大长度为3  abccba
 *
 *
 * 概念
 * 回文半径和回文直径：
 * 	     因为处理后回文字符串的长度一定是奇数，所以回文半径是包括回文中心在内的回文子串的一半的长度，
 *    回文直径则是回文半径的2倍减1。
 *    比如对于字符串 "aba"，在字符 'b' 处的回文半径就是2，回文直径就是3。
 * 最右回文边界R：
 *    在遍历字符串时，每个字符遍历出的最长回文子串都会有个右边界，而R则是所有已知右边界中最靠右的位置，也就是说R的值是只增不减的。
 * 回文中心C：取得当前R的第一次更新时的回文中心。由此可见R和C时伴生的 。
 * 	  回文中心跟新的时机：i+pArr[i]>R 更新R与C !!!
 *   C		   R
 *   |<------->|
 *     |<------->|
 *     i  pArr[i]
 * i+pArr[i]>R 如上图，当回文最有边界大于原来R时就更新R，同时更新C
 *
 *
 *
 * 半径数组pArr[]：
 *    这个数组记录了原字符串中每一个字符对应的最长回文半径。
 *
 *
 * 整个遍历的过程中只有一个对称中心C和一个最右边界R C与R随者遍历str[i]而变化  千万不要个str[i]搞混了
 * 回文最右边界 int R
 * 回文对称中心 C是R更新时取得的中心点
 *
 * Manacher算法的精妙之处在于  可
 * 由str[i-1]的回文半径R[i-1]推出得到str[i]位置的回文半径大小R[i]
 * 1）理解回文半径数组   回文半径 从字符开始向一次去数字符数  直径就是回文的数量
 * 2）理解所有中心的回文最右边界R，和取得R时的中心点C
 * 3）理解   L…(i')…C…(i)…R  的结构，以及根据i’回文长度进行的状况划分
 * 4）每一种情况划分，都可以加速求解i回文半径的过程
 *
 *
 * 整个过程如下
 * 一每个字符为中心，依次去向左右两侧去扩大，并同时记录以当前字符为中心得回文半径（也可以是直径）
 * 1 i>R ，也就是i在R外，此时没有什么花里胡哨的方法，直接暴力匹配，此时记得看看C和R要不要更新
 *       ...    i-2  i-1   i  i+1 i+2 ...   暴力去扩大左右两侧
 * 2 i在R内 三种情况  [L....x..i'..y....   C   ....m..i..n....R]
 *   现根据对称中心 找到i关于对称中心的的对称点i' 此时i’的回文半径已经计算过了  在parr中
 *   i'+i=2C   ==>   i'=2C-i
 *
 *   关于i'的回文区域边界分以下情况  (根据i的对成点 i'的回文区域进行分情况)
 *	 i位置的对称 i'  以i'的回文边界 是否再R内部分情况
 *   1)i'的回文区域在L-R的内部，此时i的回文直径与 i'相同
 *   2)i'的回文区域左边界超过了L，此时i的回文半径则是i到R,
 *   3)i'的回文区域左边界恰好和L重合，此时i的回文半径最少是i到R，回文区域从R继续向外部匹配
 *伪代码如下
 *  if(i在R外){
 *  	暴力扩大
 *      i-1 和 i+1 对比
 *      i-2 和 i+2 对比...
 *
 *  }else{// i在R内
 *  	if(i的对称点的回文区域彻底在L...R内部){
 *  		pArr[i]=pArr[i对称点]
 *  	}else if(i的对称点的回文区域跑到在L...R外部){
 *  		pArr[i]=i到R的距离 即R-i
 *  	}else{// i的对称点的回文区域最边界和L压线
 *  		从R外开始向外扩
 *  	}
 *  }
 *  if (i + pArr[i] > R) {
 *		R = i + pArr[i];
 *		C = i;
 *	}
 *
 *
 *  0123
 *  #aa#
 *
 */
public class Problem_0005_LongestPalindromicSubstring {

    public static String maxLength(String s){
        if(s == null || s.length() == 0){
            return "";
        }
        char[] str = manacherString(s);

        // 回文半径数组
        int[] pArr = new int[str.length];
        // 回文中心
        int C = -1;
        // 回文半径
        int R = 0;
        // 最大回文半径
        int max = 0;
        // 最长回文的中心
        int center = -1;

        /**
         * i在R外面 暴力扩充  用i-1与i+1  i-2与i+2 ...进行对比
         * i在R内部
         *     1 i关于C的对称点i'  i'的回文半径 在以C为中心的回文半径内部
         *     2 i关于C的对称点i'  i'的回文半径 在以C为中心的回文半径外部
         *     3 i关于C的对称点i'  i'的回文半径 刚好在以C为中心的回文半径边界上  需要从边界位置开始暴力扩充
         */
        for (int i = 0; i < str.length; i++) {
            // #a#
            // i=0 --> C=0 R=1
            // i=1 --> C=1 R=2
            if (i > C + R - 1) {
                // 暴力扩充
                int j = 1;
                while (i - j >= 0 && i + j < str.length && str[i - j] == str[i + j]) {
                    j++;
                }
                pArr[i] = j;
            } else {
                // i的对称点 ii
                int ii = 2 * C - i;
                int iiR = pArr[ii];
                if (ii - iiR + 1 > C - R + 1) {
                    // ii的回文半径在以C为中心的回文内部
                    pArr[i] = iiR;
                } else if (ii - iiR + 1 < C - R+ 1) {
                    // ii的回文半径超出了 以C为中心的回文半径
                    pArr[i] = C + R -i;
                } else {
                    // ii的回文半径刚好等于 以C为中心的回文半径
                    // 暴力扩充
                    int j =iiR;
                    while (i - j >= 0 && i + j < str.length && str[i - j] == str[i + j]) {
                        j++;
                    }
                    pArr[i] = j;
                }
            }
            // 是否发现新的边界
            if (i + pArr[i] > C + R) {
                C = i;
                R = pArr[i];
            }
            // 更新i位置的最大回文半径
            if(max < pArr[i]){
                max = pArr[i];
                center = i;
            }
        }
        // max 是#a#b#b#a#的回文半径  max = 5  #a#b#
        // #a#b#c#b#a#  max=6  #a#b#c
        // 原字符串的回文半径  center为偶数 对称中心位于虚轴上 否则在实字符上

        // 0 1 2 3  4  5  6  7  8                2  3  4  5  6
        // # c # b  #  b  #  a  #       ->       #  b  #  b  #
        //       center             max=3

        // 0 1 2 3 4  5  6  7  8  9  10                 2 3 4  5  6  7  8
        // # c # b #  a  #  b  #  d   #     ->          # b #  a  #  b  #
        //          center          max=6

        // 变换后字符的回文区间 left right 一定是对应的添加的字符 "#"
        int left = center - (max - 1);
        int right = center + (max - 1);
        // 原字符的回文区间
        left = (left + 1) / 2;
        right = (right - 1) / 2;
        s.substring(left, right + 1);
        return s.substring(left, right + 1);
    }


    /**
     * 对字符添加虚拟字符
     * 如 abc123 处理后为#a#b#c#1#2#3#
     */
    private static char[] manacherString(String s) {
        StringBuffer sb = new StringBuffer("#");
        char[] chs = s.toCharArray();
        for (char ch : chs) {
            sb.append(ch).append("#");
        }
        return sb.append("#").toString().toCharArray();
    }

    /**
     *  teacher 精炼代码
     */
    public static String longestPalindrome(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char[] charArr = manacherString(str);
        int[] pArr = new int[charArr.length];
        int index = -1;
        int pR = -1;
        int max = Integer.MIN_VALUE;
        int mid = 0;
        for (int i = 0; i != charArr.length; i++) {
            pArr[i] = pR > i ? Math.min(pArr[2 * index - i], pR - i) : 1;
            while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
                if (charArr[i + pArr[i]] == charArr[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            if (i + pArr[i] > pR) {
                pR = i + pArr[i];
                index = i;
            }
            if (max < pArr[i]) {
                max = pArr[i];
                mid = i;
            }
        }
        mid = (mid - 1) / 2;
        max = max - 1;
        return str.substring((max & 1) == 0 ? mid - (max / 2) + 1 : mid - (max / 2), mid + (max / 2) + 1);
    }





    public static void main(String[] args) {
        String s = "cbbd";
        System.out.println(maxLength(s));
    }

}
