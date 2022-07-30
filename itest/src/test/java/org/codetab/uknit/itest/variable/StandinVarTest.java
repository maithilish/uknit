package org.codetab.uknit.itest.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class StandinVarTest {
    @InjectMocks
    private StandinVar standinVar;

    @Mock
    private Date mockField;
    @Mock
    private Date initMockField;
    @Mock
    private Date assignedMockField;
    @Mock
    private Date unassignedMockField;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOpen() {
        String name = "Foo";
        standinVar.open(name);
        // fail("unable to assert, STEPIN");
    }

    @Test
    public void testUseAssignedField() {
        Object apple = Mockito.mock(Object.class);

        String name = "Foo";
        standinVar.open(name);
        standinVar.getRealField().put(name, apple);
        Object actual = standinVar.useAssignedField();

        assertSame(apple, actual);
    }

    @Test
    public void testUseUnassignedField() {
        Object apple = null;

        standinVar.getRealField();

        Object actual = standinVar.useUnassignedField();

        assertSame(apple, actual);
    }

    @Test
    public void testUseRealField() {
        Object apple = Mockito.mock(Object.class);

        String name = "Foo";
        standinVar.open(name);
        standinVar.getRealField().put(name, apple);

        Object actual = standinVar.useRealField();

        assertSame(apple, actual);
    }

    @Test
    public void testUseinitRealField() {
        Object apple = Mockito.mock(Object.class);

        String name = "Foo";
        standinVar.open(name);
        standinVar.getInitRealField().put(name, apple);

        Object actual = standinVar.useinitRealField();

        assertSame(apple, actual);
    }

    @Test
    public void testUseMockField() {
        int apple = 1;

        when(mockField.compareTo(assignedMockField)).thenReturn(apple);

        int actual = standinVar.useMockField();

        assertEquals(apple, actual);
    }

    @Test
    public void testUseInitMockField() {
        int apple = 1;

        when(initMockField.compareTo(assignedMockField)).thenReturn(apple);

        int actual = standinVar.useInitMockField();

        assertEquals(apple, actual);
    }

    @Test
    public void testUseUnassignedMockField() {
        int apple = 1;

        when(mockField.compareTo(unassignedMockField)).thenReturn(apple);

        int actual = standinVar.useUnassignedMockField();

        assertEquals(apple, actual);
    }

    @Test
    public void testGetRealFieldIf() {
        Map<String, Object> realField = new HashMap<>();

        Map<String, Object> actual = standinVar.getRealField();

        assertEquals(realField, actual);
    }

    @Test
    public void testGetRealField() {
        Map<String, Object> realField = new HashMap<>();

        Map<String, Object> actual = standinVar.getRealField();

        assertEquals(realField, actual);
    }

    @Test
    public void testGetInitRealField() {
        Map<String, Object> initRealField = new HashMap<>();

        Map<String, Object> actual = standinVar.getInitRealField();

        assertEquals(initRealField, actual);
    }
}
