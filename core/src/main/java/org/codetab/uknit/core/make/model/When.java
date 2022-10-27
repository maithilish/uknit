package org.codetab.uknit.core.make.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class When {

    private String methodSignature;

    // var used by the mi in when(...) part
    private IVar callVar;

    // vars for thenReturns(...) part
    private List<IVar> returnVars;

    // vars used by when - enabler uses them to enable vars
    private List<String> names;

    @Inject
    public When(@Assisted final String methodSignature,
            @Assisted final IVar callVar) {
        this.methodSignature = methodSignature;
        this.callVar = callVar;
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

    public IVar getCallVar() {
        return callVar;
    }

    @Override
    public String toString() {
        return "When [mi=" + methodSignature + ", returnVars=" + returnVars
                + "]";
    }

    /**
     * callVar is excluded as IVar doesn't define hash/equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(methodSignature, names, returnVars);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        When other = (When) obj;
        return Objects.equals(methodSignature, other.methodSignature)
                && Objects.equals(names, other.names)
                && Objects.equals(returnVars, other.returnVars);
    }
}
