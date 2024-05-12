
package java_programs.extra;

public class ModuloInverse {
    public static int inverse(int base, int mod) {
        if (base == 1) {
            return base;
        } else {
            int coeff = base - inverse(mod % base, base);
            return (coeff * mod) / base;
        }
    }
}