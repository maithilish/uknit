package org.codetab.uknit.core.make.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HeapTest {

    @InjectMocks
    private Heap heap;

    @Mock
    private List<IVar> vars;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIndexedVar() {
        Stream<IVar> stream = Stream.of();
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo", heap.getIndexedVar("foo"));

        stream = Stream.of(new LocalVar("foo", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo2", heap.getIndexedVar("foo"));

        stream = Stream.of(new Field("foo", null, false, null, null),
                new LocalVar("foo1", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo2", heap.getIndexedVar("foo"));

        stream = Stream.of(new LocalVar("foo2", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo", heap.getIndexedVar("foo"));

        stream = Stream.of(new LocalVar("foo1", null, false),
                new LocalVar("foo2", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo", heap.getIndexedVar("foo"));

        stream = Stream.of(new Parameter("foo", null, false),
                new LocalVar("foo1", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo2", heap.getIndexedVar("foo"));

        stream = Stream.of(new Parameter("foo", null, false),
                new LocalVar("foo2", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo3", heap.getIndexedVar("foo"));

        stream = Stream.of(new Field("foo", null, false, null, null),
                new LocalVar("foo1", null, false),
                new LocalVar("foo2", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo3", heap.getIndexedVar("foo"));

        stream = Stream.of(new Parameter("foo", null, false),
                new LocalVar("foo3", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo2", heap.getIndexedVar("foo"));

        stream = Stream.of(new Parameter("foo", null, false),
                new Parameter("foo2", null, false),
                new LocalVar("foo3", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo4", heap.getIndexedVar("foo"));

        stream = Stream.of(new Parameter("foo", null, false),
                new Parameter("foo2", null, false),
                new LocalVar("foo4", null, false));
        when(vars.stream()).thenReturn(stream);
        assertEquals("foo3", heap.getIndexedVar("foo"));
    }
}
