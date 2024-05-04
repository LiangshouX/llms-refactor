
package java_programs;
import java.util.ArrayList;

public class Subsequences {
    public static ArrayList<ArrayList<Integer>> subsequences(int a, int b, int k) {
        if (k == 0) {
            return new ArrayList<>();
        }

        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        for (int i = a; i < b + 1 - k; i++) {
            ArrayList<ArrayList<Integer>> base = new ArrayList<>();
            for (ArrayList<Integer> rest : subsequences(i + 1, b, k - 1)) {
                rest.add(0, i);
                base.add(rest);
            }
            ret.addAll(base);
        }

        return ret;
    }
}
