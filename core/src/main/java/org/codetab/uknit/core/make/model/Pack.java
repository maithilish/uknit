package org.codetab.uknit.core.make.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Expression;

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

    @Inject
    public Pack(final IVar var) {
        this.var = var;
        patches = new ArrayList<>();
    }

    @Inject
    public Pack(final Expression exp) {
        this.exp = exp;
        patches = new ArrayList<>();
    }

    @Inject
    public Pack(final IVar var, final Expression exp) {
        this.var = var;
        this.exp = exp;
        patches = new ArrayList<>();
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

    public List<Patch> getPatches() {
        return patches;
    }

}
