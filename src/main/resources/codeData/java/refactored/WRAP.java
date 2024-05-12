public class WrapUtil {
    private static final Logger log = Logger.getLogger(WrapUtil.class.getName());

    public static void main(String[] args) {
        log.fine("abc".lastIndexOf("c", 30));
    }

    public static List<String> wrap(String text, int cols) {
        List<String> lines = new ArrayList<>();

        String remainingText = text;
        while (remainingText.length() > cols) {
            int end = remainingText.lastIndexOf(' ', cols); // off by one?
            if (end == -1) {
                end = cols;
            }
            String line = remainingText.substring(0, end).trim();
            remainingText = remainingText.substring(end).trim();
            lines.add(line);
        }

        if (!remainingText.isEmpty()) {
            lines.add(remainingText);
        }

        return lines;
    }
}