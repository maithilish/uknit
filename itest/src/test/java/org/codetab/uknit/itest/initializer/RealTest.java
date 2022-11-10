package org.codetab.uknit.itest.initializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Iterators;

public class RealTest {
    @InjectMocks
    private Real real;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignRealReturnsReal() {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        String name = apple.toLowerCase();

        when(file.getName()).thenReturn(apple);

        String actual = real.assignRealReturnsReal(file);

        assertEquals(name, actual);
    }

    @Test
    public void testReturnRealReturnsReal() {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        String grape = apple.toLowerCase();

        when(file.getName()).thenReturn(apple);

        String actual = real.returnRealReturnsReal(file);

        assertEquals(grape, actual);
    }

    @Test
    public void testAssignRealReturnsRealInArgs() {
        StringBuilder s1 = Mockito.mock(StringBuilder.class);
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        String grape = apple.toLowerCase();
        StringBuilder s2 = Mockito.mock(StringBuilder.class);

        when(file.getName()).thenReturn(apple);
        when(s1.append(grape)).thenReturn(s2);

        StringBuilder actual = real.assignRealReturnsRealInArgs(s1, file);

        assertSame(s2, actual);
    }

    @Test
    public void testReturnRealReturnsRealInArgs() {
        StringBuilder s1 = Mockito.mock(StringBuilder.class);
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        String grape = apple.toLowerCase();
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);

        when(file.getName()).thenReturn(apple);
        when(s1.append(grape)).thenReturn(stringBuilder);

        StringBuilder actual = real.returnRealReturnsRealInArgs(s1, file);

        assertSame(stringBuilder, actual);
    }

    @Test
    public void testAssignRealReturnsRealInArgs2() {
        StringBuilder s1 = Mockito.mock(StringBuilder.class);
        StringBuilder s2 = Mockito.mock(StringBuilder.class);
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        String grape = apple.toLowerCase();
        StringBuilder stringBuilder = Mockito.mock(StringBuilder.class);
        StringBuilder s3 = Mockito.mock(StringBuilder.class);

        when(file.getName()).thenReturn(apple);
        when(s2.append(grape)).thenReturn(stringBuilder);
        when(s1.append(stringBuilder)).thenReturn(s3);

        StringBuilder actual = real.assignRealReturnsRealInArgs2(s1, s2, file);

        assertSame(s3, actual);
    }

    @Test
    public void testReturnRealReturnsRealInArgs2() {
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

        StringBuilder actual = real.returnRealReturnsRealInArgs2(s1, s2, file);

        assertSame(stringBuilder2, actual);
    }

    @Test
    public void testAssignRealReturnsMock() {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        IntStream codePoints = apple.codePoints();

        when(file.getName()).thenReturn(apple);

        IntStream actual = real.assignRealReturnsMock(file);

        Iterators.elementsEqual(codePoints.iterator(), actual.iterator());
    }

    @Test
    public void testReturnRalReturnsMock() {
        File file = Mockito.mock(File.class);
        String apple = "Foo";
        IntStream intStream = apple.codePoints();

        when(file.getName()).thenReturn(apple);

        IntStream actual = real.returnRalReturnsMock(file);

        Iterators.elementsEqual(intStream.iterator(), actual.iterator());
    }

    @Test
    public void testAssignRealCollectionReturnsMock() {
        List<File> files = new ArrayList<>();
        File file = Mockito.mock(File.class);
        files.add(file);

        File actual = real.assignRealCollectionReturnsMock(files);

        assertSame(file, actual);
    }

    @Test
    public void testReturnRealCollectionReturnsMock() {
        List<File> files = new ArrayList<>();
        File file = Mockito.mock(File.class);
        files.add(file);

        File actual = real.returnRealCollectionReturnsMock(files);

        assertSame(file, actual);
    }
}
