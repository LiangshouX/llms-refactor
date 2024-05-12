public class LongestCommonSubsequence {
    public static int findLongestCommonSubsequenceLength(String s, String t) {
        Map<Integer, Map<Integer,Integer>> dp = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            Map<Integer,Integer> initialize = new HashMap<>();
            dp.put(i, initialize);
            for (int j = 0; j < t.length(); j++) {
                Map<Integer,Integer> internalMap = dp.get(i);
                internalMap.put(j, 0);
                dp.put(i, internalMap);
            }
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < t.length(); j++) {
                if (s.charAt(i) == t.charAt(j)) {
                    int insertValue = 0;
                    if (dp.containsKey(i-1)) {
                        insertValue = dp.get(i-1).getOrDefault(j, 0) + 1;
                    }
                    Map<Integer, Integer> internalMap = dp.get(i);
                    internalMap.put(j, insertValue);
                    dp.put(i, internalMap);
                }
            }
        }

        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            resultList.add(dp.get(i).values().stream().max(Integer::compareTo).orElse(0));
        }
        return Collections.max(resultList);
    }
}