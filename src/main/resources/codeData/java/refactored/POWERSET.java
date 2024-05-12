import java.util.*;

public class PowerSet {
    public static List<List<Object>> powerset(final List<Object> arr) {
        if (!arr.isEmpty()) {
            Object first = arr.get(0);
            arr.remove(0);
            List<Object> rest = new ArrayList<>(arr);
            List<List<Object>> rest_subsets = powerset(rest);

            List<List<Object>> output = new ArrayList<>();
            List<Object> to_add = new ArrayList<>();
            to_add.add(first);
            for (List<Object> subset : rest_subsets) {
                to_add.addAll(subset);
            }
            output.add(to_add);

            return output;
        } else {
            List<List<Object>> empty_set = new ArrayList<>();
            empty_set.add(new ArrayList<>());
            return empty_set;
        }
    }
}