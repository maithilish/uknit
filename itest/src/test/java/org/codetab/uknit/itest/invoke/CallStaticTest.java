package org.codetab.uknit.itest.invoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.nio.file.FileSystem;
import java.nio.file.Path;

import org.codetab.uknit.itest.invoke.Model.Statics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CallStaticTest {
    @InjectMocks
    private CallStatic callStatic;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAssignStatic() {
        String name = Statics.getName("foo");

        String actual = callStatic.assignStatic();

        assertEquals(name, actual);
    }

    @Test
    public void testReturnStatic() {
        String apple = Statics.getName("foo");

        String actual = callStatic.returnStatic();

        assertEquals(apple, actual);
    }

    @Test
    public void testAssignInvokeOnStaticCallMock() {
        Path fileName = Path.of("foo");

        Path actual = callStatic.assignInvokeOnStaticCallMock();

        assertEquals(fileName, actual);
    }

    @Test
    public void testReturnInvokeOnStaticCallMock() {
        Path path2 = Path.of("foo");

        Path actual = callStatic.returnInvokeOnStaticCallMock();

        assertEquals(path2, actual);
    }

    @Test
    public void testAssignInvokeOnStaticCallReal() {
        String name = Statics.getName("hello").toLowerCase();

        String actual = callStatic.assignInvokeOnStaticCallReal();

        assertEquals(name, actual);
    }

    @Test
    public void testReturnInvokeOnStaticCallReal() {
        String grape = Statics.getName("hello").toLowerCase();

        String actual = callStatic.returnInvokeOnStaticCallReal();

        assertEquals(grape, actual);
    }

    @Test
    public void testAssignStaticInInvokeExp() {
        String foo = "Foo";
        String bar = "Bar";
        FileSystem fileSystem = Path.of("foo").getFileSystem();

        FileSystem actual = callStatic.assignStaticInInvokeExp(foo, bar);

        assertEquals(fileSystem, actual);
    }

    @Test
    public void testReturnStaticInInvokeExp() {
        String foo = "Foo";
        String bar = "Bar";
        FileSystem fileSystem = Path.of("foo").getFileSystem();

        FileSystem actual = callStatic.returnStaticInInvokeExp(foo, bar);

        assertEquals(fileSystem, actual);
    }

    @Test
    public void testAssignStaticInArg() {
        Path path = Mockito.mock(Path.class);
        int val = 1;

        when(path.compareTo(
                Path.of(Statics.getName(Statics.getFile().getAbsolutePath()))))
                        .thenReturn(val);

        int actual = callStatic.assignStaticInArg(path);

        assertEquals(val, actual);
    }

    @Test
    public void testReturnStaticInArg() {
        Path path = Mockito.mock(Path.class);
        int orange = 1;

        when(path.compareTo(
                Path.of(Statics.getName(Statics.getFile().getAbsolutePath()))))
                        .thenReturn(orange);

        int actual = callStatic.returnStaticInArg(path);

        assertEquals(orange, actual);
    }
}
