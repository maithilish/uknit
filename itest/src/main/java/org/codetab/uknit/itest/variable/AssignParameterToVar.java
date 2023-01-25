package org.codetab.uknit.itest.variable;

import org.codetab.uknit.itest.variable.Model.StepInfo;

class AssignParameterToVar {

    public String getStepName(final StepInfo stepInfo) {
        StepInfo thisStep = stepInfo;
        return thisStep.getName();
    }
}
