package org.codetab.uknit.itest.create;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CreateMockTest {
    @InjectMocks
    private CreateMock createMock;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateListOfMocks() {
        List<LocalDate> list = new ArrayList<>();

        List<LocalDate> actual = createMock.createListOfMocks();

        assertEquals(list, actual);
    }

    @Test
    public void testDeclareAndCreateListOfMocks() {
        List<LocalDate> list = new ArrayList<>();

        List<LocalDate> actual = createMock.declareAndCreateListOfMocks();

        assertEquals(list, actual);
    }

    @Test
    public void testCreateMockable() {
        Event event = new Event("foo");

        Event actual = createMock.createMockable();

        assertEquals(event, actual);
    }

    @Test
    public void testDeclareAndCreateMockable() {
        Event event = new Event("foo");

        Event actual = createMock.declareAndCreateMockable();

        assertEquals(event, actual);
    }
}
