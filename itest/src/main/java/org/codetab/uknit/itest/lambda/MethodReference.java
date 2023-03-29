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

    private External external;

    public boolean inExternalArg() {
        return external.apply("true", Boolean::parseBoolean);
    }

    public void inInternalArg(final Properties properties, final String value) {
        accept(properties, value, Boolean::parseBoolean);
    }

    public void inNestedInternal(final Properties properties) {
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

class External {

    public boolean apply(final String v, final Function<String, Boolean> f) {
        return f.apply(v);
    }
}
