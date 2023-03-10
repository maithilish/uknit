package org.codetab.uknit.itest.generic;

import java.io.Closeable;
import java.sql.Connection;
import java.util.HashMap;

/**
 * TODO L - handle wildcard in when return.
 *
 * @author Maithilish
 *
 */
public class Wildcard {

    protected HashMap<String, ObjectPool<? extends Connection>> pools =
            new HashMap<>();

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

}

interface ObjectPool<T> extends Closeable {
    T borrow();
}
