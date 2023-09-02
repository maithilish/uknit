package org.codetab.uknit.itest.variable;

import java.io.File;

import org.codetab.uknit.itest.variable.Model.Foo;
import org.codetab.uknit.itest.variable.Model.StepInfo;

class AssignParameterToVar {

    public String getStepName(final StepInfo stepInfo) {
        StepInfo thisStep = stepInfo;
        return thisStep.getName();
    }

    public void valueOfVar(final Foo foo, final File f1, final File f2) {
        boolean flg = true;
        File a = flg ? f1 : f2;
        File b = flg ? f1 : f2;
        File c = f2;
        foo.appendString(a.getAbsolutePath());
        foo.appendString(a.getAbsolutePath());
        foo.appendString(((a).getAbsolutePath()));
        foo.appendString(b.getAbsolutePath());
        foo.appendString(((b).getAbsolutePath()));
        foo.appendString(c.getAbsolutePath());
    }
}
