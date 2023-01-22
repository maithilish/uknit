package org.codetab.uknit.core.make.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import com.google.inject.assistedinject.Assisted;

/**
 * Normally the Invoke expression is MI but in case of IMC it may set to other
 * types such as var name.
 *
 * @author Maithilish
 *
 */
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

        if (nonNull(exp)) {
            checkExpState(exp);
        }

        when = Optional.empty();
        verify = Optional.empty();
    }

    @Override
    public void setExp(final Expression exp) {
        checkNotNull(exp);
        checkExpState(exp);

        super.setExp(exp);
    }

    private void checkExpState(final Expression exp) {
        checkState(exp instanceof MethodInvocation
                || exp instanceof SuperMethodInvocation
                || exp instanceof SimpleName);
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

    public MethodInvocation getMi() {
        if (nonNull(getExp()) && getExp() instanceof MethodInvocation) {
            return (MethodInvocation) getExp();
        } else {
            return null;
        }
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
