package org.codetab.uknit.itest.create;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.itest.create.Model.Event;

public class CreateMock {

    public List<File> assignCreateListOfMocks() {
        List<File> list = new ArrayList<>();
        return list;
    }

    public List<File> declareAssignCreateListOfMocks() {
        List<File> list;
        list = new ArrayList<>();
        return list;
    }

    public List<File> returnCreateListOfMocks() {
        return new ArrayList<>();
    }

    public Event assginCreateMockable() {
        Event event = new Event("foo");
        return event;
    }

    public Event declareAssignCreateMockable() {
        Event event;
        // Event is mockable, but creation overrides in assignment
        event = new Event("foo");
        return event;
    }

    public Event returnCreateMockable() {
        return new Event("foo");
    }
}
