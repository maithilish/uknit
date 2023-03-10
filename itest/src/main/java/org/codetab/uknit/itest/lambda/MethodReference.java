package org.codetab.uknit.itest.lambda;

import java.util.Properties;
import java.util.function.Function;

/**
 * Test Method Reference
 *
 * TODO N - test is not proper, fix it.
 *
 * @author Maithilish
 *
 */
public class MethodReference {

    public void foo(final Properties properties) {
        acceptBoolean(properties, "true");
    }

    private void acceptBoolean(final Properties properties,
            final String value) {
        accept(properties, value, Boolean::parseBoolean);
    }

    private <V> void accept(final Properties properties, final String value,
            final Function<String, V> func) {
        func.apply(value);
    }
}
