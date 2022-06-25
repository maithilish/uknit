package org.codetab.uknit.itest.variable;

public class AssignParameterToVar {

    public String getStepName(final StepInfo stepInfo) {
        StepInfo thisStep = stepInfo;
        return thisStep.getName();
    }
}

class StepInfo {
    private String name;

    public String getName() {
        return name;
    }
}
