package java_programs;

import java.util.List;
import java.util.ArrayList;

/**
 * WrapText is a utility class for wrapping text to a specific column width.
 *
 * @author Derrick Lin
 */
public final class WrapText {

    private WrapText() {
        // This utility class is not publicly instantiable.
    }

    public static void testWrap() {
        String text = "abc";
        int index = text.lastIndexOf('c');
        System.out.println(index);
    }

    public static List<String> wrap(final String text, final int cols) {
        final List<String> lines = new ArrayList<>();

        String line;
        String remainingText = new String(text);
        while (remainingText.length() > cols) {
            int end = remainingText.lastIndexOf(' ', cols);
            if (end == -1) {
                end = cols;
            }
            line = remainingText.substring(0, end);
            remainingText = remainingText.substring(end);
            lines.add(line);
        }

        return lines;
    }
}