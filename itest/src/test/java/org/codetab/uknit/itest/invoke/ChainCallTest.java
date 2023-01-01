package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.codetab.uknit.itest.invoke.Model.Pet;
import org.codetab.uknit.itest.invoke.Model.Pets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ChainCallTest {
    @InjectMocks
    private ChainCall chainCall;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignInfer() {
        Pets pets = Mockito.mock(Pets.class);
        Map<String, List<Pet>> map = new HashMap<>();
        List<Pet> list = new ArrayList<>();
        String apple = "dog";
        map.put(apple, list);

        when(pets.getPets()).thenReturn(map);

        List<Pet> actual = chainCall.assignInfer(pets);

        assertEquals(list, actual);
    }

    @Test
    public void testReturnInfer() {
        Pets pets = Mockito.mock(Pets.class);
        Map<String, List<Pet>> map = new HashMap<>();
        List<Pet> dogs = new ArrayList<>();
        String apple = "dog";
        map.put(apple, dogs);

        when(pets.getPets()).thenReturn(map);

        List<Pet> actual = chainCall.returnInfer(pets);

        assertEquals(dogs, actual);
    }

    @Test
    public void testMockMockMockChain() {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        File file3 = Mockito.mock(File.class);

        when(file.getAbsoluteFile()).thenReturn(file2);
        when(file2.getParentFile()).thenReturn(file3);

        File actual = chainCall.mockMockMockChain(file);

        assertSame(file3, actual);
    }

    @Test
    public void testMockMockRealChain() {
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        String apple = "Foo";

        when(file.getAbsoluteFile()).thenReturn(file2);
        when(file2.getAbsolutePath()).thenReturn(apple);

        String actual = chainCall.mockMockRealChain(file);

        assertEquals(apple, actual);
    }

    @Test
    public void testMockRealRealChain() {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        String grape = apple.toLowerCase();

        when(file.getName()).thenReturn(apple);

        String actual = chainCall.mockRealRealChain(file);

        assertEquals(grape, actual);
    }

    @Test
    public void testRealRealRealChain() {
        String name = "Foo";
        String grape = name.toUpperCase();

        String actual = chainCall.realRealRealChain(name);

        assertEquals(grape, actual);
    }

    @Test
    public void testMockRealMockChain() {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        IntStream intStream = apple.codePoints();
        IntSummaryStatistics intSummaryStatistics =
                Mockito.mock(IntSummaryStatistics.class);

        when(file.getName()).thenReturn(apple);
        when(intStream.summaryStatistics()).thenReturn(intSummaryStatistics);

        IntSummaryStatistics actual = chainCall.mockRealMockChain(file);

        assertSame(intSummaryStatistics, actual);
    }

    @Test
    public void testMockRealRealChainAsArg() {
        StringBuilder s1 = Mockito.mock(StringBuilder.class);
        StringBuilder s2 = Mockito.mock(StringBuilder.class);
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        String grape = apple.toLowerCase();
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);
        StringBuilder stringBuilder2 = Mockito.mock(StringBuilder.class);

        when(file.getName()).thenReturn(apple);
        when(s2.append(grape)).thenReturn(stringBuilder);
        when(s1.append(stringBuilder)).thenReturn(stringBuilder2);

        StringBuilder actual = chainCall.mockRealRealChainAsArg(s1, s2, file);

        assertSame(stringBuilder2, actual);
    }

    @Test
    public void testMockMockMockChainAsArg() {
        StringBuilder s1 = Mockito.mock(StringBuilder.class);
        StringBuilder s2 = Mockito.mock(StringBuilder.class);
        File file = Mockito.mock(File.class);
        File file2 = Mockito.mock(File.class);
        File file3 = Mockito.mock(File.class);
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);
        StringBuilder stringBuilder2 = Mockito.mock(StringBuilder.class);

        when(file.getAbsoluteFile()).thenReturn(file2);
        when(file2.getParentFile()).thenReturn(file3);
        when(s2.append(file3)).thenReturn(stringBuilder);
        when(s1.append(stringBuilder)).thenReturn(stringBuilder2);

        StringBuilder actual = chainCall.mockMockMockChainAsArg(s1, s2, file);

        assertSame(stringBuilder2, actual);
    }
}
