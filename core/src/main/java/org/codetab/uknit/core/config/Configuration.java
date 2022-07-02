package org.codetab.uknit.core.config;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Singleton, loads uknit properties file and provide access to properties.
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

    private Properties names;

    Configuration() {
        try {
            defaults = new Properties();
            defaults.load(Configuration.class.getClassLoader()
                    .getResourceAsStream("uknit-defaults.properties"));

            props = new Properties();
            props.putAll(defaults);

            // used by itest to ignore user defined properties
            boolean loadUserDefined = Boolean.valueOf(System
                    .getProperty("uknit.configs.loadUserDefined", "true"));

            if (loadUserDefined) {
                user = new Properties();
                user.load(Configuration.class.getClassLoader()
                        .getResourceAsStream("uknit.properties"));
                props.putAll(user);
            }

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

            // load test framework properties
            Properties testFrameworkProps = loadTestFrameworkProperties(props
                    .getProperty("uknit.profile.test.framework", "junit5"));
            props.putAll(testFrameworkProps);

            names = new Properties();
            names.load(Configuration.class.getClassLoader()
                    .getResourceAsStream("names.properties"));
        } catch (Exception e) {
            System.out.println("properties file not found, " + e);
            System.exit(0);
        }
    }

    /**
     * Load test framework properties - junit4 or junit5
     * @param property
     * @return
     * @throws IOException
     */
    private Properties loadTestFrameworkProperties(final String testFramework)
            throws IOException {
        Properties testFrameworkProps = new Properties();
        testFrameworkProps
                .load(Configuration.class.getClassLoader().getResourceAsStream(
                        String.join(".", testFramework, "properties")));
        return testFrameworkProps;
    }

    public String getProperty(final String key) {
        return props.getProperty(key);
    }

    public String clearProperty(final String key) {
        return (String) props.remove(key);
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

    public String getNames(final String key) {
        return names.getProperty(key);
    }
}
