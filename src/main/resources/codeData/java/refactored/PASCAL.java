
package java_programs;
import java.util.*;

public class PASCAL {
    public static List<List<Integer>> pascal(int n) {
        List<List<Integer>> rows = new ArrayList<>();
        List<Integer> init = new ArrayList<>();
        init.add(1);
        rows.add(init);

        for (int r = 1; r < n; r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c < r; c++) {
                int upleft = (c > 0) ? rows.get(r - 1).get(c - 1) : 0;
                int upright = (c < r) ? rows.get(r - 1).get(c) : 0;
                row.add(upleft + upright);
            }
            rows.add(row);
        }

        return rows;
    }
}
