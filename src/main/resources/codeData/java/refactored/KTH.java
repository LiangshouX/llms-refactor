
package java_programs;
import java.util.ArrayList;

public class KTH {
    public static Integer kth(ArrayList<Integer> arr, int k) {
        int pivot = arr.get(0);
        ArrayList<Integer> below = new ArrayList<>();
        ArrayList<Integer> above = new ArrayList<>();
        
        for (Integer x : arr) {
            if (x < pivot) {
                below.add(x);
            } else if (x > pivot) {
                above.add(x);
            }
        }

        int num_less = below.size();
        int num_lessoreq = arr.size() - above.size();
        
        if (k < num_less) {
            return kth(below, k);
        } else if (k >= num_lessoreq) {
            return kth(above, k);
        } else {
            return pivot;
        }
    }
}
