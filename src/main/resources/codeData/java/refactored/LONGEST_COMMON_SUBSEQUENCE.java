
public class LongestCommonSubsequenceUtil {
    public static String findLongestCommonSubsequence(String firstString, String secondString) {
        if (firstString.isEmpty() || secondString.isEmpty()) {
            return "";
        } else if (firstString.charAt(0) == secondString.charAt(0)) {
            return firstString.charAt(0) + findLongestCommonSubsequence(firstString.substring(1), secondString);
        } else {
            String first = findLongestCommonSubsequence(firstString, secondString.substring(1));
            String second = findLongestCommonSubsequence(firstString.substring(1), secondString);
            return first.length() >= second.length() ? first : second;
        }
    }
}