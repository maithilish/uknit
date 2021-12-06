package org.codetab.uknit.itest.clz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PojoTest {
    @InjectMocks
    private Pojo pojo;

    @Mock
    private Date bar;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPojo() {
        String foo = "foo";
        Date bar = Mockito.mock(Date.class);

        @SuppressWarnings("unused")
        Pojo actual = new Pojo(foo, bar);
    }

    @Test
    public void testGetBar() {

        Date actual = pojo.getBar();

        assertSame(bar, actual);
    }

    @Test
    public void testSetBar() {
        Date bar = Mockito.mock(Date.class);
        pojo.setBar(bar);
    }

    @Test
    public void testGetFoo() {
        String foo = null;

        String actual = pojo.getFoo();

        assertEquals(foo, actual);
    }

    @Test
    public void testSetFoo() {
        String foo = "foo";
        pojo.setFoo(foo);
    }
}
