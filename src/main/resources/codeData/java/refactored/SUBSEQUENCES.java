package java_programs;

import java.util.ArrayList;
import java.util.List;

public class Subsequences {
    public static List<List<Integer>> subsequences(int start, int end, int length) {
        if (length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int i = start; i < end + 1 - length; i++) {
            List<Integer> base = new ArrayList<>();
            for (List<Integer> rest : subsequences(i + 1, end, length - 1)) {
                rest.add(0, i);
                base.add(new ArrayList<>(rest));
            }
            result.addAll(base);
        }

        return result;
    }
}