//Corrected package name from quixey to java_programs.extra;
package java_programs.extra;

public class MODULO_INVERSE {
    public static int inverse(int base, int mod) {
        if(base == 1) {
            return base;
        } else {
            int coeff = base - inverse(mod % base, base);
            return (coeff*mod) / base;
        }
    }
}