package org.codetab.uknit.core.make.model;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Expression;

import com.google.inject.assistedinject.Assisted;

/**
 * Maps Expression to an IVar, IVar to Expression or Expression to Expression.
 * <p>
 * List<String> list = new ArrayList<>(); the ClassInstanceCreation exp new
 * ArrayList<>() is mapped to var list.
 * <p>
 * foo = "bar"; the StringLiteral exp is mapped to var foo.
 * @author Maithilish
 *
 */
public class ExpVar {

    private Expression rightExp;
    private Expression leftExp;
    private IVar rightVar;
    private IVar leftVar;

    @Inject
    public ExpVar(@Assisted("left") @Nullable final Expression leftExp,
            @Assisted("right") @Nullable final Expression rightExp) {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
    }

    // TODO make it optional
    public Expression getRightExp() {
        return rightExp;
    }

    public void setRightExp(final Expression exp) {
        this.rightExp = exp;
    }

    public Expression getLeftExp() {
        return leftExp;
    }

    public void setLeftExp(final Expression exp) {
        this.leftExp = exp;
    }

    public Optional<IVar> getLeftVar() {
        return Optional.ofNullable(leftVar);
    }

    public void setLeftVar(final IVar leftVar) {
        this.leftVar = leftVar;
    }

    public Optional<IVar> getRightVar() {
        return Optional.ofNullable(rightVar);
    }

    public void setRightVar(final IVar rightVar) {
        this.rightVar = rightVar;
    }

    public String toRightExpString() {
        return rightExp.toString();
    }

    public String toLeftExpString() {
        return leftExp.toString();
    }

    @Override
    public String toString() {
        return "ExpCarry [leftExp=" + leftExp + ", rightExp=" + rightExp + "]";
    }
}
