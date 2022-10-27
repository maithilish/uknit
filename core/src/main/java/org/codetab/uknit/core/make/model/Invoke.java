package org.codetab.uknit.core.make.model;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Expression;

import com.google.inject.assistedinject.Assisted;

public class Invoke extends Pack {

    /*
     * Var on which method is invoked. It is null for, imported static call call
     * with super keyword such as super.foo()
     */
    private Optional<IVar> callVar;

    // MethodInvocation return type
    private Optional<ReturnType> returnType;

    private Optional<When> when;
    private Optional<Verify> verify;

    @Inject
    public Invoke(@Assisted @Nullable final IVar var,
            @Assisted @Nullable final Expression exp,
            @Assisted final boolean inCtlPath) {
        super(var, exp, inCtlPath);
        when = Optional.empty();
        verify = Optional.empty();
    }

    public Optional<IVar> getCallVar() {
        return callVar;
    }

    public void setCallVar(final Optional<IVar> callVar) {
        this.callVar = callVar;
    }

    public Optional<ReturnType> getReturnType() {
        return returnType;
    }

    public void setReturnType(final Optional<ReturnType> returnType) {
        this.returnType = returnType;
    }

    public Optional<When> getWhen() {
        return when;
    }

    public void setWhen(final Optional<When> when) {
        this.when = when;
    }

    public Optional<Verify> getVerify() {
        return verify;
    }

    public void setVerify(final Optional<Verify> verify) {
        this.verify = verify;
    }

    @Override
    public String toString() {
        if (getLeftExp().isPresent()) {
            return "Invoke [var=" + getVar() + ", exp=" + getExp()
                    + ", leftExp=" + getLeftExp() + "]";
        } else {
            return "Invoke [var=" + getVar() + ", exp=" + getExp() + "]";
        }
    }
}
