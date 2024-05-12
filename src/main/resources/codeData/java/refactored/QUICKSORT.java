import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    public static List<Integer> quicksort(final List<Integer> arr) {
        if (arr.isEmpty()) {
            return new ArrayList<>();
        }

        Integer pivot = arr.get(0);
        List<Integer> lesser = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();

        for (Integer x : arr.subList(1, arr.size())) {
            if (x < pivot) {
                lesser.add(x);
            } else if (x > pivot) {
                greater.add(x);
            }
        }
        List<Integer> middle = new ArrayList<>();
        middle.add(pivot);
        lesser = quicksort(lesser);
        greater = quicksort(greater);
        middle.addAll(greater);
        lesser.addAll(middle);
        return lesser;

    }
}