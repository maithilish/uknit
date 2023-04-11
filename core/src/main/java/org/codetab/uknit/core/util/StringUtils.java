package org.codetab.uknit.core.util;

import static java.util.Objects.nonNull;

import java.util.List;
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

    // REVIEW - remove redundant toString in references
    public static String spaceit(final Object... parts) {
        String[] strings = new String[parts.length];
        for (int i = 0; i < parts.length; i++) {
            strings[i] = parts[i].toString();
        }
        return String.join(" ", strings);
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
        int strLen = 0;
        if (nonNull(str)) {
            strLen = str.length();
        }
        if (strLen == 0) {
            return str;
        }

        boolean allUpper = str.chars().noneMatch(Character::isLowerCase);

        char firstCodepoint = (char) str.codePointAt(0);
        char newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint && !allUpper) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(newCodePoint);
        if (strLen > 1) {
            if (allUpper) {
                sb.append(str.substring(1).toLowerCase());
            } else {
                sb.append(str.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * Capitalise each string and concat.
     * @param parts
     * @return
     */
    public static String concatCapitalized(final List<String> parts) {
        StringBuilder buffer = new StringBuilder();
        for (String part : parts) {
            int strLen = 0;
            if (nonNull(part) && !part.isBlank()) {
                strLen = part.length();
            }
            if (strLen > 0) {
                char firstChar = (char) part.codePointAt(0);
                char upperChar = Character.toTitleCase(firstChar);
                if (firstChar == upperChar) {
                    buffer.append(part);
                } else {
                    buffer.append(upperChar);
                    if (strLen > 1) {
                        buffer.append(part.substring(1));
                    }
                }
            }
        }
        return buffer.toString();
    }
}
