package topinterviewquestions;

/**
 * @author ：Lisp
 * @date： 2021/10/17
 * @version: V1.0
 * @slogan:
 * @description :
 * 【题目】 如果在一群人中 有一个不认识所有人  但是所有人都认识他 那么这个人就是明星
 *      给你一群人 问这群人中是否存在明星
 *
 *      如果存在明星时 一定只存在一个明星
 *
 */
public class Problem_0277_FindTheCelebrity {

    /**
     * 提交时不要提交这个函数，只提交下面的方法
     *
     * 返回x 是否认识 i
     */
    public static boolean knows(int x, int i) {
        return true;
    }


    /**
     * for循环的逻辑有点绕  仔细思考下
     * @param arr
     * @return
     */
    public static int findCelebrity(int[] arr) {

        // 明星候选人
        int cand = 0;
        for (int i = 1; i < arr.length; i++) {
            // cand认识i 则明星一定不是cand
            if (knows(cand, i)) {
                cand = i;
            }
        }

        // 上面的for循环结束时 cand+1 ~ arr.length 一定都不可能成为明星
        // 最后抓到的cand 一定是 0 ~ cand 上一定是存在某人认识 cand的
        // 因为 cand 不认识  cand+1 ~ arr.length(明星要求其他所有的人搜认识明星)
        //下面的for循环校验  cand 是否不认识 0~cand（第一个for校验了cand不认识 cand+1~arr.length上所有人）
        for (int i = 0; i < cand; i++) {
            if (knows(cand, i)) {
                // cand 要不认识 0~cand上的所有人踩可能成为明星
                return -1;
            }
        }

        // 上面两个for说明 cand不认识 0~arr.length
        // 最后校验 所有人是否认识cand
        for (int i = 0; i < arr.length; i++) {
            // 必须所有的人都认识cand cand才能成为明星
            if (!knows(i, cand)) {
                return -1;
            }
        }
        return cand;
    }


}
