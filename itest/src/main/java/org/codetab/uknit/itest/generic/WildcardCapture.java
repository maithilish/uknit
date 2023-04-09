package org.codetab.uknit.itest.generic;

import java.io.PrintWriter;
import java.util.List;

public class WildcardCapture {

    public void write(final PrintWriter out, final Object value) {
        printValue(out, 1, value);
    }

    /**
     * The type binding of lambda exp o -> printValue(out,indentLevel + 1,o) is
     * Consumer<? super capture#1-of ?>. To create lambda arg capture we need
     * type as Consumer<? super Object>.
     *
     * @param out
     * @param indentLevel
     * @param value
     */
    private void printValue(final PrintWriter out, final int indentLevel,
            final Object value) {
        ((List<?>) value).forEach(o -> printValue(out, indentLevel + 1, o));
    }
}
