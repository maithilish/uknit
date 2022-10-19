package org.codetab.uknit.jtest.wildcard;

import java.util.Map;

/**
 * Wildcard in parameter allows to pass any arg. The Map<String, ?> allows
 * Map<String, Date>, Map<String, String> etc.,
 *
 * TODO - Set param type to Map<String, Object> instead of Map<String, ?>.
 *
 * @author m
 *
 */
public class WildcardParameter {

    public void foo(final Map<String, ?> source) {
        String[] keys = {"foo", "bar"};
        for (final String key : keys) {
            @SuppressWarnings("unused")
            final Object value = source.get(key);
        }
    }
}
