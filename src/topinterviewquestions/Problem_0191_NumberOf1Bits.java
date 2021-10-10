package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/10
 * @version: V1.0
 * @slogan:
 * @description : 无符号整数二进制表示时 1的个数
 */
public class Problem_0191_NumberOf1Bits {


    /**
     * 方法1
     */
    public static int hammingWeight1(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >> 1;
        }
        return count;
    }


    /**
     * 方法2
     * -n = (~n) + 1
     * 1 = 0000 0000 0000 0001
     *-1 = 1111 1111 1111 1111
     *
     * n        = 0001 0010
     * rightOne = 0000 0010
     * 去掉最右侧的1 n = n^rightOne
     *
     *
     *
     */
    public static int hammingWeight2(int n) {
        int bits = 0;
        // 二进制下 最右侧的1


        int rightOne = 0;
        while(n != 0) {
            bits++;
            //  rightOne = n & ((~n) +1);
            rightOne = n & (-n);
            n ^= rightOne;
        }
        return bits;
    }




    /**
     * 了解即可
     */
    public static int hammingWeight3(int n) {
        n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
        return n;
    }



    public static void main(String[] args) {
       //  System.out.println(hammingWeight1(4294967293));
    }

}
