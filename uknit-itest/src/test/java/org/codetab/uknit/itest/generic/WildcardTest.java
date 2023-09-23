package org.codetab.uknit.itest.generic;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class WildcardTest {
    @InjectMocks
    private Wildcard wildcard;

    @Mock
    private HashMap<String, ObjectPool<? extends Connection>> pools;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testConnect() {
        String name = "Foo";
        ObjectPool pool2 = Mockito.mock(ObjectPool.class);
        ObjectPool pool = pool2;
        Connection conn = Mockito.mock(Connection.class);

        when(pools.get(name)).thenReturn(pool2);
        when(pool.borrow()).thenReturn(conn);

        Connection actual = wildcard.connect(name);

        assertSame(conn, actual);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testGetConnectionPool() {
        String name = "Foo";
        ObjectPool pool = Mockito.mock(ObjectPool.class);

        when(pools.get(name)).thenReturn(pool);

        ObjectPool<? extends Connection> actual =
                wildcard.getConnectionPool(name);

        assertSame(pool, actual);
    }

    @Test
    public void testNoBoundWildcard() {
        Map<String, Object> source = new HashMap<>();
        Object apple = Mockito.mock(Object.class);
        String grape = "foo";
        source.put(grape, apple);

        Object actual = wildcard.noBoundWildcard(source);

        assertSame(apple, actual);
    }
}
