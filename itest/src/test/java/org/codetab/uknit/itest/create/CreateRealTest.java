package org.codetab.uknit.itest.create;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CreateRealTest {
    @InjectMocks
    private CreateReal createReal;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateString() {
        String foo = new String();

        String actual = createReal.createString();

        assertEquals(foo, actual);
    }

    @Test
    public void testInitString() {
        String foo = "bar";

        String actual = createReal.initString();

        assertEquals(foo, actual);
    }

    @Test
    public void testCreateAndReturnString() {
        String apple = new String("foo");

        String actual = createReal.createAndReturnString();

        assertEquals(apple, actual);
    }

    @Test
    public void testCreateListOfReal() {
        List<String> list = new ArrayList<>();

        List<String> actual = createReal.createListOfReal();

        assertEquals(list, actual);
    }

    @Test
    public void testDeclareAndCreateString() {
        String foo = new String();

        String actual = createReal.declareAndCreateString();

        assertEquals(foo, actual);
    }

    @Test
    public void testDeclareAndInitString() {
        String foo = "bar";

        String actual = createReal.declareAndInitString();

        assertEquals(foo, actual);
    }

    @Test
    public void testDeclareAndCreateListOfReal() {
        List<String> list = new ArrayList<>();

        List<String> actual = createReal.declareAndCreateListOfReal();

        assertEquals(list, actual);
    }
}
