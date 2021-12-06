package org.codetab.uknit.core.make.model;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.inject.assistedinject.Assisted;

/**
 * Holds info about method invoke that is used by another visit.
 * @author m
 *
 */
public class Invoke {

    private MethodInvocation mi;
    // var on which method invoked
    private IVar var;
    // method return type
    private Optional<ExpReturnType> expReturnType;
    private Optional<InferVar> inferVar;

    @Inject
    public Invoke(@Assisted @Nullable final IVar var,
            @Assisted final Optional<ExpReturnType> expReturnType,
            @Assisted final MethodInvocation mi) {
        this.var = var;
        this.expReturnType = expReturnType;
        this.mi = mi;
        this.inferVar = Optional.empty();
    }

    public boolean isInfer() {
        boolean infer = true;
        int parentType = mi.getParent().getNodeType();
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
            if (nonNull(var)) {
            return var.isMock();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public IVar getVar() {
        return var;
    }

    public Optional<ExpReturnType> getExpReturnType() {
        return expReturnType;
    }

    public MethodInvocation getMi() {
        return mi;
    }

    public Optional<InferVar> getInferVar() {
        return inferVar;
    }

    public void setInferVar(final Optional<InferVar> inferVar) {
        this.inferVar = inferVar;
    }

    @Override
    public String toString() {
        return "Invoke [mi=" + mi + "]";
    }
}
