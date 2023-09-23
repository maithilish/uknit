package org.codetab.uknit.core.config;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.Optional;
import java.util.Properties;

import javax.inject.Singleton;

import org.codetab.uknit.core.exception.CodeException;

@Singleton
public class Configs {

    private Configuration configuration = Configuration.INSTANCE;

    /**
     * Get config or null if not found.
     *
     * @param key
     * @return
     */
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

    public String getNames(final String key, final String def) {
        String value = configuration.getNames(key);
        if (isNull(value)) {
            return def;
        } else {
            return value;
        }
    }

    public Properties getProperties(final String prefix) {
        return configuration.getProperties(prefix);
    }

    public String clearProperty(final String key) {
        return configuration.clearProperty(key);
    }

    public String getFormat(final String key) {
        String value = configuration.getProperty(key);
        if (isNull(value)) {
            throw new CodeException(spaceit(
                    "could not get method statement format, unknown key:",
                    value));
        }
        return value;
    }

    public boolean isLibRunMode() {
        return configuration.getProperty("uknit.run.mode")
                .equalsIgnoreCase("lib");
    }
}
