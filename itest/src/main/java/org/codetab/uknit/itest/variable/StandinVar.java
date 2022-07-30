package org.codetab.uknit.itest.variable;

import static java.util.Objects.isNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Add stand-in local var for disabled but used field by when, verify etc.,
 * @author m
 *
 */
public class StandinVar {

    private String assignedField;
    private String unassignedField;

    private Map<String, Object> realField;
    private Map<String, Object> initRealField = new HashMap<>();

    private Date mockField;
    private Date initMockField = new Date();

    private Date assignedMockField;
    private Date unassignedMockField;

    public void open(final String name) {
        assignedField = name;
        assignedMockField = new Date();
    }

    public Object useAssignedField() {
        return realField.get(assignedField);
    }

    public Object useUnassignedField() {
        return realField.get(unassignedField);
    }

    public Object useRealField() {
        return realField.get(assignedField);
    }

    public Object useinitRealField() {
        return initRealField.get(assignedField);
    }

    public int useMockField() {
        return mockField.compareTo(assignedMockField);
    }

    public int useInitMockField() {
        return initMockField.compareTo(assignedMockField);
    }

    public int useUnassignedMockField() {
        return mockField.compareTo(unassignedMockField);
    }

    public Map<String, Object> getRealField() {
        if (isNull(realField)) {
            realField = new HashMap<>();
        }
        return realField;
    }

    public Map<String, Object> getInitRealField() {
        return initRealField;
    }

}
