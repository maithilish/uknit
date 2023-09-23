package org.codetab.uknit.itest.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StandinVarTest {
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
        String name = "Baz";
        standinVar.open(name);

        // fail("unable to assert, STEPIN");
    }

    // can't test, map is not initialized
    // @Test
    // public void testUseAssignedField() {
    // Object apple = "Foo";
    //
    // Object actual = standinVar.useAssignedField();
    //
    // assertEquals(apple, actual);
    // }
    //
    // @Test
    // public void testUseUnassignedField() {
    // Object apple = "Foo";
    //
    // Object actual = standinVar.useUnassignedField();
    //
    // assertEquals(apple, actual);
    // }
    //
    // @Test
    // public void testUseRealField() {
    // Object apple = "Foo";
    //
    // Object actual = standinVar.useRealField();
    //
    // assertEquals(apple, actual);
    // }

    @Test
    public void testUseinitRealField() {
        Object apple = null;

        Object actual = standinVar.useinitRealField();

        assertEquals(apple, actual);
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
    public void testGetRealFieldIfIsNull() {
        Map<String, Object> realField = new HashMap<>();

        Map<String, Object> actual = standinVar.getRealField();

        assertEquals(realField, actual);
    }

    @Test
    public void testGetRealFieldElseIsNull() {
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
