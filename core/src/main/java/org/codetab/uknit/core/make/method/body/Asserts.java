package org.codetab.uknit.core.make.method.body;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Type;

public class Asserts {

    @Inject
    private Configs configs;
    @Inject
    private Types types;

    public String getAssertKey(final Type type, final boolean mock) {
        checkNotNull(type);

        if (type.isArrayType()) {
            return "arrayEquals";
        }

        String typeName = types.getTypeName(type);

        if (type.isPrimitiveType()) {
            String assertKey;
            switch (typeName) {
            case "boolean":
                assertKey = "boolean";
                break;
            case "float":
                assertKey = "float";
                break;
            case "double":
                assertKey = "float";
                break;
            default:
                assertKey = "equals";
                break;
            }
            return assertKey;
        }

        // String, Integer etc.,
        if (types.isUnmodifiable(typeName)) {
            return "equals";
        }
        if (!mock) {
            return "equals";
        }
        return "same";
    }

    public String getAssertFormat(final String assertKey,
            final String expected) {

        checkNotNull(assertKey);
        checkNotNull(expected);

        String fmt = configs
                .getConfig(String.join(".", "uknit.format.assert", assertKey));

        if (fmt.contains("${expected}")) {
            fmt = fmt.replace("${expected}", expected);
        }

        return fmt;
    }
}
