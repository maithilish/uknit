package org.codetab.uknit.core.config;

import java.util.Map.Entry;
import java.util.Properties;

/**
 * Singleton, loads mocker.properties file and provide access to properties.
 * Default properties are overridden by user defined which is overridden by
 * system to get effective configs.
 * @author Maithilish
 *
 */
enum Configuration {

    INSTANCE;

    private Properties defaults;

    private Properties user;

    private Properties system;

    private Properties props; // effective configs

    private Properties knowTypes;

    private Configuration() {
        try {
            defaults = new Properties();
            defaults.load(Configuration.class.getClassLoader()
                    .getResourceAsStream("uknit-defaults.properties"));

            props = new Properties();
            props.putAll(defaults);

            user = new Properties();
            user.load(Configuration.class.getClassLoader()
                    .getResourceAsStream("uknit.properties"));
            props.putAll(user);

            system = new Properties();
            for (Entry<Object, Object> entry : System.getProperties()
                    .entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (key.startsWith("uknit.")) {
                    system.put(key, value);
                }
            }
            props.putAll(system);

        } catch (Exception e) {
            System.out.println("properties file not found, " + e);
            System.exit(0);
        }
    }

    public String getProperty(final String key) {
        return props.getProperty(key);
    }

    public String getProperty(final String key, final String def) {
        return props.getProperty(key, def);
    }

    public void setProperty(final String key, final String value) {
        props.setProperty(key, value);
    }

    public String getKnownTypeFqn(final String clsName) {
        return knowTypes.getProperty(clsName);
    }
}
