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
 * @author Maithilish
 *
 */
public class Pack {

    public enum Nature {
        STATIC_CALL
    }

    private IVar var;
    private Expression exp;
    private List<Patch> patches;
    private boolean inCtlPath;
    private boolean im; // whether originated in internal method.
    private List<Nature> natures;

    /*
     * LHS of assignment, apart from var, may be array access (array[0]), field
     * access ((point).x) or qualified name (point.x)
     */
    private Optional<Expression> leftExp;
    /*
     * pack state change listener
     */
    private Listener listener;

    @Inject
    public Pack(@Assisted @Nullable final IVar var,
            @Assisted @Nullable final Expression exp,
            @Assisted final boolean inCtlPath) {
        this.var = var;
        this.exp = exp;
        patches = new ArrayList<>();
        leftExp = Optional.empty();
        this.inCtlPath = inCtlPath;
        natures = new ArrayList<>();
    }

    public IVar getVar() {
        return var;
    }

    public void setVar(final IVar var) {
        this.var = var;
        listener.packVarChanged();
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

    public void addPatch(final Patch patch) {
        patches.add(patch);
    }

    public boolean isInCtlPath() {
        return inCtlPath;
    }

    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    public boolean isIm() {
        return im;
    }

    public void setIm(final boolean im) {
        this.im = im;
    }

    public void addNature(final Nature nature) {
        if (!natures.contains(nature)) {
            natures.add(nature);
        }
    }

    public List<Nature> getNatures() {
        return natures;
    }

    public boolean is(final Nature nature) {
        return natures.contains(nature);
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
