package java_programs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author derricklin
 */
public final class POWERSET {

    private POWERSET() {
        // Private constructor to prevent instantiation
    }

    public static List<List<Object>> powerset(final List<Object> originalList) {
        List<List<Object>> output = new ArrayList<>();
        if (originalList.isEmpty()) {
            final List<Object> emptyList = new ArrayList<>();
            output.add(emptyList);
        } else {
            final Object first = originalList.get(0);
            final List<Object> rest = new ArrayList<>(originalList);
            rest.remove(0);
            final List<List<Object>> restSubsets = powerset(rest);

            final List<Object> listToAdd = new ArrayList<>();
            listToAdd.add(first);
            for (final List<Object> subset : restSubsets) {
                listToAdd.addAll(subset);
            }
            output.add(listToAdd);
        }
        return output;
    }
}