package java_programs.extra;

import java.util.Scanner;

public class NestedParens {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String S = in.next();
        int[] num = new int[S.length()];
        for (int i = 0; i < S.length(); i++)
            num[i] = S.charAt(i) == '(' ? 1 : -1;

        System.out.println(isProperlyNested(num) == 1 ? "GOOD" : "BAD");
    }

    public static int isProperlyNested(int[] A) {
        int bad = 0;
        int depth = 0;
        int i = 0;
        while (i < A.length) {
            depth += A[i];
            if (depth < 0) {
                bad = 1;
            }
            i += 1;
        }
        return bad == 0 ? 1 : 0;
    }
}