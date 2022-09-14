package org.codetab.uknit.core.make.model;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

import com.google.inject.assistedinject.Assisted;

/**
 * Holds info about method invoke that is used by another visit.
 * @author Maithilish
 *
 */
public class Invoke {

    private Expression exp; // either mi or superMi
    /*
     * var on which method invoked, it is null for imported static call and call
     * with super keyword such as super.foo()
     */
    private IVar callVar;
    private Optional<ExpReturnType> expReturnType; // method return type
    private Optional<IVar> returnVar;

    @Inject
    public Invoke(@Assisted @Nullable final IVar callVar,
            @Assisted final Optional<ExpReturnType> expReturnType,
            @Assisted final Expression exp) {
        this.callVar = callVar;
        this.expReturnType = expReturnType;
        this.exp = exp;
        this.returnVar = Optional.empty();
    }

    public boolean isInfer() {
        boolean infer = true;
        int parentType = exp.getParent().getNodeType();
        switch (parentType) {
        case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
            infer = false;
            break;
        case ASTNode.EXPRESSION_STATEMENT:
            infer = false;
            break;
        default:
            break;
        }
        return infer;
    }

    public boolean isWhen() {
        if (expReturnType.isPresent()) {
            if (nonNull(callVar)) {
                return callVar.isMock();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public IVar getCallVar() {
        return callVar;
    }

    public Optional<ExpReturnType> getExpReturnType() {
        return expReturnType;
    }

    public Expression getExp() {
        return exp;
    }

    public Optional<IVar> getReturnVar() {
        return returnVar;
    }

    public void setReturnVar(final Optional<IVar> returnVar) {
        this.returnVar = returnVar;
    }

    @Override
    public String toString() {
        return "Invoke [exp=" + exp + "]";
    }
}
