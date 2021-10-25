package topinterviewquestions;

import java.util.HashMap;

/**
 * @author ：Lisp
 * @date： 2021/10/21
 * @version: V1.0
 * @slogan:
 * @description :
 * 【实现思路】 使用两个map
 *  删除时 总是把最末尾的key 覆盖掉当前的位置的key
 *  为了实现随机，使用map存储了 索引与key对应的关系
 */
public class Problem_0380_InsertDeleteGetRandom {

    public static class RandomizedSet {


        private HashMap<Integer, Integer> keyIndexMap;
        private HashMap<Integer, Integer> indexKeyMap;
        private int size;


        public RandomizedSet() {
            keyIndexMap = new HashMap<>();
            indexKeyMap = new HashMap<>();
        }

        public boolean insert(int val) {
            if (!keyIndexMap.containsKey(val)) {
                int index = size;
                keyIndexMap.put(val, index);
                indexKeyMap.put(index, val);
                size++;
                return true;
            }
            return false;
        }

        public boolean remove(int val) {
            if (!keyIndexMap.containsKey(val)){
                return false;
            }

            int index = keyIndexMap.get(val);
            int lastKey = indexKeyMap.get(size - 1);

            // keyIndex   "2"-2 x   "5"-5    -> "5"-2
            // indexKey   2-"2"     5-"5" x  -> 2-"5"

            keyIndexMap.put(lastKey, index);
            keyIndexMap.remove(val);

            indexKeyMap.put(index, lastKey);
            indexKeyMap.remove(size - 1);
            size--;
            return true;
        }

        public int getRandom() {
            // [0, 1)
            int random = (int) (Math.random() * size);
            return indexKeyMap.get(random);
        }
    }

    public static void main(String[] args) {

        RandomizedSet test = new RandomizedSet();
        test.insert(0);
        test.insert(1);
        test.remove(1);
        test.insert(2);
        test.remove(1);
        test.getRandom();

    }

}
