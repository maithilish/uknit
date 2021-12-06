package org.codetab.uknit.core.util;

import java.util.Random;

public class StringUtils {

    /**
     * Generate lower case random string of specified length.
     * @param len
     * @return
     */
    public String generateString(final int len) {

        // lower case alphabets range
        final int lower = 97;
        final int upper = 122;

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            // take a random value between 97 and 122
            int randomChar =
                    lower + (int) (random.nextFloat() * (upper - lower + 1));
            sb.append((char) randomChar);
        }
        return sb.toString();
    }

    public static String spaceit(final String... parts) {
        return String.join(" ", parts);
    }

    public static String lineit(final String... parts) {
        StringBuilder sb = new StringBuilder();
        String lineBreak = System.lineSeparator();
        for (String part : parts) {
            sb.append(part);
            sb.append(lineBreak);
        }
        return sb.toString();
    }

    public static String capitalize(final String str) {
        int strLen = (str == null ? 0 : str.length());
        if (strLen == 0) {
            return str;
        }

        char firstCodepoint = (char) str.codePointAt(0);
        char newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(newCodePoint);
        if (strLen > 1) {
            sb.append(str.substring(1));
        }
        return sb.toString();
    }
}
