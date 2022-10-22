package org.codetab.uknit.core.make.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Expression;

import com.google.inject.assistedinject.Assisted;

/**
 * Pack of Var, Expression and its patches.
 *
 * @author m
 *
 */
public class Pack {

    private IVar var;
    private Expression exp;
    private List<Patch> patches;

    /*
     * LHS of assignment, apart from var, may be array access (array[0]), field
     * access ((point).x) or qualified name (point.x)
     */
    private Optional<Expression> leftExp;

    @Inject
    public Pack(@Assisted @Nullable final IVar var,
            @Assisted @Nullable final Expression exp) {
        this.var = var;
        this.exp = exp;
        patches = new ArrayList<>();
        leftExp = Optional.empty();
    }

    public IVar getVar() {
        return var;
    }

    public void setVar(final IVar var) {
        this.var = var;
    }

    public Expression getExp() {
        return exp;
    }

    public void setExp(final Expression exp) {
        this.exp = exp;
    }

    public Optional<Expression> getLeftExp() {
        return leftExp;
    }

    public void setLeftExp(final Optional<Expression> leftExp) {
        this.leftExp = leftExp;
    }

    public List<Patch> getPatches() {
        return patches;
    }

    @Override
    public String toString() {
        if (leftExp.isPresent()) {
            return "Pack [var=" + var + ", exp=" + exp + ", leftExp=" + leftExp
                    + "]";
        } else {
            return "Pack [var=" + var + ", exp=" + exp + "]";
        }
    }
}
