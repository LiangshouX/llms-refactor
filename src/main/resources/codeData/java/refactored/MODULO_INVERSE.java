package java_programs.extra;

public final class ModuloInverse {
    private ModuloInverse() {
    }

    public static int inverse(final int base, final int mod) {
        final int magicNumber = 1;
        int result;
        if(base == magicNumber) {
            result = base;
        } else {
            final int coeff = base - inverse(mod % base, base);
            result = coeff * mod / base;
        }
        return result;
    }
}