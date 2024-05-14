package java_programs;
import java.util.*;

public final class HANOI {
    private HANOI() {}

    public static List<HanoiPair<Integer,Integer>> hanoi(final int height, final int start, final int end) {
        final List<HanoiPair<Integer,Integer>> steps = new ArrayList<>();

        if (height > 0) {
            final Queue<Integer> stepsSet = new PriorityQueue<>();
            stepsSet.add(1);
            stepsSet.add(2);
            stepsSet.add(3);
            stepsSet.remove(start);
            stepsSet.remove(end);
            final int helper = stepsSet.poll();
            steps.addAll(hanoi(height-1, start, helper));
            steps.add(new HanoiPair<>(start, end));
            steps.addAll(hanoi(height-1, helper, end));
        }

        return steps;
    }


    public static class HanoiPair<F, S> {
        private F first;
        private S second;

        public HanoiPair(final F first, final S second) {
            this.first = first;
            this.second = second;
        }

        public void setFirst(final F first) {
            this.first = first;
        }

        public void setSecond(final S second) {
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