public class Hanoi {
    public static List<Pair<Integer, Integer>> hanoi(final int height, final int start, final int end) {
        List<Pair<Integer, Integer>> steps = new ArrayList<>();

        if (height > 0) {
            PriorityQueue<Integer> crapSet = new PriorityQueue<>();
            crapSet.add(1);
            crapSet.add(2);
            crapSet.add(3);
            crapSet.remove(start);
            crapSet.remove(end);
            int helper = crapSet.poll();
            steps.addAll(hanoi(height - 1, start, helper));
            steps.add(new Pair<>(start, helper));
            steps.addAll(hanoi(height - 1, helper, end));
        }

        return steps;
    }

    public static class Pair<F, S> {
        private final F first;
        private final S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public F getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }
}