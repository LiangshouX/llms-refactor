public final class KHeapsort {
    public static List<Integer> kheapsort(final List<Integer> arr, final int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (final Integer v : arr.subList(0, k)) {
            heap.add(v);
        }

        List<Integer> output = new ArrayList<>();
        for (final Integer x : arr) {
            heap.add(x);
            final Integer popped = heap.poll();
            output.add(popped);
        }

        while (!heap.isEmpty()) {
            output.add(heap.poll());
        }

        return output;
    }
}