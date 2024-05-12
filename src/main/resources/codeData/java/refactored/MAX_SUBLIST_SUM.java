public class MaxSublistSumUtility {
    private MaxSublistSumUtility() {}

    public static int calculateMaxSublistSum(int... nums) {
        int maxEndingHere = 0;
        int maxSoFar = 0;

        for (int num : nums) {
            maxEndingHere = Math.max(num, maxEndingHere + num);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }
}