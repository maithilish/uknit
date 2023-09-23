package org.codetab.uknit.itest.generic;

import java.io.Closeable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO L - handle wildcard in when return.
 *
 * @author Maithilish
 *
 */
public class Wildcard {

    protected HashMap<String, ObjectPool<? extends Connection>> pools =
            new HashMap<>();

    /*
     * TODO N - remove type args from parameterized type and switch to raw type.
     *
     */
    public Connection connect(final String name) {
        final ObjectPool<? extends Connection> pool = getConnectionPool(name);
        final Connection conn = pool.borrow();
        return conn;

    }

    public synchronized ObjectPool<? extends Connection> getConnectionPool(
            final String name) {
        final ObjectPool<? extends Connection> pool = pools.get(name);
        return pool;
    }

    /**
     * TODO L - no bound wildcard should be replaced with Object in var
     * definitions.
     *
     * @param source
     * @return
     */
    public Object noBoundWildcard(final Map<String, ?> source) {
        return source.get("foo");
    }
}

interface ObjectPool<T> extends Closeable {
    T borrow();
}
