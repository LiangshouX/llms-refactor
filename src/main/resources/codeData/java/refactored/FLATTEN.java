package java_programs;
import java.util.*;

public final class FLATTEN {
    private FLATTEN() {
        throw new IllegalStateException("Utility class");
    }

    public static Object flatten(final Object arr) {
        Object result = arr;
        if (arr instanceof List) {
            final List<Object> narr = (List<Object>) arr;
            final List<Object> tempResult = new ArrayList<>(50);
            for (final Object x : narr) {
                if (x instanceof List) {
                    tempResult.addAll((List<Object>) flatten(x));
                } else {
                    tempResult.add(flatten(x));
                }
            }
            result = tempResult;
        }
        return result;
    }
}