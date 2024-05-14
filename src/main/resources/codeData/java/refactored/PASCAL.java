package java_programs;
import java.util.*;

/**
 * Utility class for generating Pascal's triangle
 * @author derricklin
 */
public final class PascalUtil {

    private PascalUtil() {
        // private constructor to hide the implicit public one
    }

    public static List<List<Integer>> generatePascalTriangle(final int numRows) {
        final List<List<Integer>> rows = new ArrayList<>();
        final List<Integer> init = new ArrayList<>();
        init.add(1);
        rows.add(init);

        List<Integer> row;
        int upleft, upright;
        for (int rowIndex=1; rowIndex<numRows; rowIndex++) {
            row = new ArrayList<>();
            for (int columnIndex=0; columnIndex<rowIndex; columnIndex++) {
                if (columnIndex > 0) {
                    upleft = rows.get(rowIndex-1).get(columnIndex-1);
                } else {
                    upleft = 0;
                }
                if (columnIndex < rowIndex) {
                    upright = rows.get(rowIndex-1).get(columnIndex);
                } else {
                    upright = 0;
                }
                row.add(upleft+upright);
            }
            rows.add(row);
        }

        return rows;
    }
}