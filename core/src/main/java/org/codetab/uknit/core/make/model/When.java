package org.codetab.uknit.core.make.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class When {

    private String methodSignature;
    // vars used by when - used to mark used
    private List<String> names;

    private List<String> returnNames;

    @Inject
    public When(@Assisted final String methodSignature) {
        this.methodSignature = methodSignature;
        returnNames = new ArrayList<>();
        names = new ArrayList<>();
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getReturnNames() {
        return returnNames;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    @Override
    public String toString() {
        return "When [mi=" + methodSignature + ", returnNames=" + returnNames
                + "]";
    }
}
