package org.codetab.uknit.itest.exception.nosplit;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CatchMultiple {

    private static final Logger LOG = LogManager.getLogger();

    public void run(final Config config) {
        String key = "foo";
        try {
            config.getConfigA(key);
            config.getConfigB(key);
            config.getConfigC(key);
        } catch (IllegalStateException e) {
            LOG.error(e.getLocalizedMessage());
        } catch (IllegalArgumentException | IllegalAccessError e) {
            LOG.error(e.getLocalizedMessage());
        }
    }
}

class Config {

    private Properties properties;

    public String getConfigA(final String key) throws IllegalStateException {
        return properties.getProperty(key);
    }

    public String getConfigB(final String key) throws IllegalArgumentException {
        return properties.getProperty(key);
    }

    public String getConfigC(final String key) throws IllegalAccessError {
        return properties.getProperty(key);
    }
}
