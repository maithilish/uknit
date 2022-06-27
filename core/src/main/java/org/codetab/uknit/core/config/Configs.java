package org.codetab.uknit.core.config;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.Optional;

import javax.inject.Singleton;

import org.codetab.uknit.core.exception.CodeException;

@Singleton
public class Configs {

    private Configuration configuration = Configuration.INSTANCE;

    public String getConfig(final String key) {
        return configuration.getProperty(key);
    }

    public String getConfig(final String key, final String def) {
        String value = configuration.getProperty(key);
        if (isNull(value)) {
            value = def;
        }
        return value;
    }

    public boolean getConfig(final String key, final boolean def) {
        boolean value = def;
        String v = configuration.getProperty(key);
        if (nonNull(v)) {
            value = Boolean.valueOf(v);
        }
        return value;
    }

    /**
     * Return fully qualified name for java lang and other base class
     * @param clsName
     * @return
     */
    public Optional<String> getKnownTypeFqn(final String clsName) {
        return Optional.ofNullable(configuration.getKnownTypeFqn(clsName));
    }

    /**
     * for tests
     * @param key
     * @param value
     */
    public void setProperty(final String key, final String value) {
        configuration.setProperty(key, value);
    }

    public String clearProperty(final String key) {
        return configuration.clearProperty(key);
    }

    public String getFormat(final String key) {
        String value = null;
        switch (key) {
        case "uknit.format.when":
            value = configuration.getProperty("uknit.format.when", "when(%s)");
            break;
        case "uknit.format.when.return":
            value = configuration.getProperty("uknit.format.when.return",
                    ".thenReturn(%s)");
            break;
        case "uknit.format.verify":
            value = configuration.getProperty("uknit.format.verify",
                    "verify(%s).%s(%s);");
            break;
        case "uknit.format.call":
            value = configuration.getProperty("uknit.format.call",
                    "%s actual = %s.%s(%s);");
            break;
        case "uknit.format.callVoid":
            value = configuration.getProperty("uknit.format.callVoid",
                    "%s.%s(%s);");
            break;
        case "uknit.format.callConstructor":
            value = configuration.getProperty("uknit.format.callConstructor",
                    "%s actual = new %s(%s);");
            break;
        case "uknit.anonymous.class.capture.create":
            value = configuration.getProperty(
                    "uknit.anonymous.class.capture.create",
                    "ArgumentCaptor<%s> %s = ArgumentCaptor.forClass(%s.class);");
            break;
        default:
            break;
        }
        if (isNull(value)) {
            throw new CodeException(spaceit(
                    "could not get method statement format, unknown key:",
                    value));
        }
        return value;
    }
}
