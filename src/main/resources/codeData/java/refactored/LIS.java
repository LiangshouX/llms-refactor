public class LongestIncreasingSubsequence {
    public static int findLengthOfLIS(final int[] nums) {
        final Map<Integer, Integer> ends = new HashMap<>(100);
        int longestLength = 0;

        int index = 0;
        for (final int num : nums) {

            final List<Integer> prefixLengths = new ArrayList<>(100);
            for (int j = 1; j < longestLength + 1; j++) {
                if (nums[ends.get(j)] < num) {
                    prefixLengths.add(j);
                }
            }

            final int length = !prefixLengths.isEmpty() ? Collections.max(prefixLengths) : 0;

            if (length == longestLength || num < nums[ends.get(length + 1)]) {
                ends.put(length + 1, index);
                longestLength = length + 1;
            }

            index++;
        }
        return longestLength;
    }
}