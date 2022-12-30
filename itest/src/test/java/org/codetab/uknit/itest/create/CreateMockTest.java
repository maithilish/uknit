package org.codetab.uknit.itest.create;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.itest.create.Model.Event;
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
    public void testAssignCreateListOfMocks() {
        List<File> list = new ArrayList<>();

        List<File> actual = createMock.assignCreateListOfMocks();

        assertEquals(list, actual);
    }

    @Test
    public void testDeclareAssignCreateListOfMocks() {
        List<File> list = new ArrayList<>();

        List<File> actual = createMock.declareAssignCreateListOfMocks();

        assertEquals(list, actual);
    }

    @Test
    public void testReturnCreateListOfMocks() {
        List<File> list = new ArrayList<>();

        List<File> actual = createMock.returnCreateListOfMocks();

        assertEquals(list, actual);
    }

    @Test
    public void testAssginCreateMockable() {
        Event event = new Event("foo");

        Event actual = createMock.assginCreateMockable();

        assertEquals(event, actual);
    }

    @Test
    public void testDeclareAssignCreateMockable() {
        Event event = new Event("foo");

        Event actual = createMock.declareAssignCreateMockable();

        assertEquals(event, actual);
    }

    @Test
    public void testReturnCreateMockable() {
        Event event = new Event("foo");

        Event actual = createMock.returnCreateMockable();

        assertEquals(event, actual);
    }
}
