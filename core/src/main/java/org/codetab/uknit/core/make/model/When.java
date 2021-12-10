package org.codetab.uknit.core.make.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class When {

    private String methodSignature;
    // vars used by when - used to mark used
    private List<String> names;

    private List<IVar> returnVars;

    @Inject
    public When(@Assisted final String methodSignature) {
        this.methodSignature = methodSignature;
        returnVars = new ArrayList<>();
        names = new ArrayList<>();
    }

    public List<String> getNames() {
        return names;
    }

    public List<IVar> getReturnVars() {
        return returnVars;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    @Override
    public String toString() {
        return "When [mi=" + methodSignature + ", returnVars=" + returnVars
                + "]";
    }
}
