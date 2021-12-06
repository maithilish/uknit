package org.codetab.uknit.itest.create;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateMock {

    public List<LocalDate> createListOfMocks() {
        List<LocalDate> list = new ArrayList<>();
        return list;
    }

    public List<LocalDate> declareAndCreateListOfMocks() {
        List<LocalDate> list;
        list = new ArrayList<>();
        return list;
    }

    public Event createMockable() {
        Event event = new Event("foo");
        return event;
    }

    public Event declareAndCreateMockable() {
        Event event;
        // Event is mockable, but creation overrides in assignment
        event = new Event("foo");
        return event;
    }

}
