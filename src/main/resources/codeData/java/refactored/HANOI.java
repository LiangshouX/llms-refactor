
package java_programs;
import java.util.*;

public class HANOI {
    
    public static List<Pair<Integer,Integer>> hanoi(int height, int start, int end) {
        List<Pair<Integer,Integer>> steps = new ArrayList<>();
        
        if (height > 0) {
            PriorityQueue<Integer> crap_set = new PriorityQueue<>();
            crap_set.add(1);
            crap_set.add(2);
            crap_set.add(3);
            crap_set.remove(start);
            crap_set.remove(end);
            int helper = crap_set.poll();
            steps.addAll(hanoi(height-1, start, helper));
            steps.add(new Pair<>(start, helper));
            steps.addAll(hanoi(height-1, helper, end));
        }
        
        return steps;
    }
    
    public static class Pair<F, S> {
        private F first;
        private S second;
        
        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
        
        public void setFirst(F first) {
            this.first = first;
        }
        
        public void setSecond(S second) {
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
            return "(" + String.valueOf(first) + ", " + String.valueOf(second) + ")";
        }
    }
}
