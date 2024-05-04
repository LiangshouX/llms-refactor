
package java_programs;
import java.util.*;

public class LCS_LENGTH {
    public static Integer lcs_length(String s, String t) {
        Map<Integer, Map<Integer,Integer>> dp = new HashMap<>();

        for (int i=0; i < s.length(); i++) {
            Map<Integer,Integer> initialize = new HashMap<>();
            dp.put(i, initialize);
            for (int j=0; j < t.length(); j++) {
                Map<Integer,Integer> internal_map = dp.get(i);
                internal_map.put(j,0);
                dp.put(i, internal_map);
            }
        }

        for (int i=0; i < s.length(); i++) {
            for (int j=0; j < t.length(); j++) {
                if (s.charAt(i) == t.charAt(j)) {
                    if (dp.containsKey(i-1)) {
                        Map<Integer, Integer> internal_map = dp.get(i);
                        int insert_value = dp.get(i-1).get(j) + 1;
                        internal_map.put(j, insert_value);
                        dp.put(i,internal_map);
                    } else {
                        Map<Integer, Integer> internal_map = dp.get(i);
                        internal_map.put(j,1);
                        dp.put(i,internal_map);
                    }
                }
            }
        }

        if (!dp.isEmpty()) {
            List<Integer> ret_list = new ArrayList<>();
            for (int i=0; i<s.length(); i++) {
                ret_list.add(!dp.get(i).isEmpty() ? Collections.max(dp.get(i).values()) : 0);
            }
            return Collections.max(ret_list);
        } else {
            return 0;
        }
    }
}
