package topinterviewquestions;

import java.util.*;

/**
 * @author ：Lisp
 * @date： 2021/9/25
 * @version: V1.0
 * @slogan:
 * @description :
 * 【解题思路】 对字符串的字符排序后  直接用map来分组
 *
 */
public class Problem_0049_GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {

        HashMap<String, List<String>> group = new HashMap<>();


        for (String str : strs) {

            char[] chs = str.toCharArray();
            Arrays.sort(chs);

            if (group.containsKey(String.valueOf(chs))) {
                group.get(String.valueOf(chs)).add(str);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(str);
                group.put(String.valueOf(chs), list);
            }
        }

        List<List<String>> ans = new ArrayList<>();
        for (Map.Entry entry : group.entrySet()) {
            ans.add((List<String>) entry.getValue());
        }

        return ans;

    }


    /**
     * teacher 内存占用低！！！？？？
     */
    public static List<List<String>> groupAnagrams2(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            char[] chs = str.toCharArray();
            Arrays.sort(chs);
            String key = String.valueOf(chs);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            map.get(key).add(str);
        }
        List<List<String>> res = new ArrayList<List<String>>();
        for (List<String> list : map.values()) {
            res.add(list);
        }
        return res;
    }


    public static void main(String[] args) {
        String[] strs  = {"eat","tea","tan","ate","nat","bat"};
        List<List<String>> lists = groupAnagrams(strs);


    }
}
