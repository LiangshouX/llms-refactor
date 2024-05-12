public class Kth {
    public static Integer kth(final List<Integer> arr, final int k) {
        final int pivot = arr.get(0);
        final List<Integer> below = new ArrayList<>();
        final List<Integer> above = new ArrayList<>();
        for (final Integer x : arr) {
            if (x < pivot) {
                below.add(x);
            } else if (x > pivot) {
                above.add(x);
            }
        }

        final int numLess = below.size();
        final int numLessOrEq = arr.size() - above.size();
        if (k < numLess) {
            return kth(below, k);
        } else if (k >= numLessOrEq) {
            return kth(above, k);
        } else {
            return pivot;
        }
    }
}