package org.codetab.uknit.itest.load;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codetab.uknit.itest.load.Model.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ForEachInternalTest {
    @InjectMocks
    private ForEachInternal forEachInternal;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCallListForEach() {
        List<Integer> foos = new ArrayList<>();
        int key = 0;
        foos.add(key);
        forEachInternal.callListForEach(foos);
    }

    @Test
    public void testCallListForEachTwice() {
        List<Integer> foos = new ArrayList<>();
        int key2 = 0;
        foos.add(key2);
        forEachInternal.callListForEachTwice(foos);
    }

    @Test
    public void testCallListForEachDiffName() {
        List<Integer> foos = new ArrayList<>();
        int key = 0;
        foos.add(key);
        forEachInternal.callListForEachDiffName(foos);
    }

    @Test
    public void testCallSimilarWithOneMap() {
        Map<String, Foo> foos = new HashMap<>();
        Foo foo = Mockito.mock(Foo.class);
        String key2 = "Bar";
        foos.put(key2, foo);
        forEachInternal.callSimilarWithOneMap(foos);

        verify(foo, times(2)).file();
    }

    @Test
    public void testCallSimilarWithTwoMaps() {
        Map<String, Foo> foos = new HashMap<>();
        Map<String, Foo> boos = new HashMap<>();
        String key = "Foo";
        Foo foo = Mockito.mock(Foo.class);
        String key2 = "Bar";
        Foo foo2 = Mockito.mock(Foo.class);
        foos.put(key, foo);
        boos.put(key2, foo2);
        forEachInternal.callSimilarWithTwoMaps(foos, boos);

        verify(foo).file();
        verify(foo2).file();
    }
}
