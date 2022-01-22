package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

import com.google.inject.assistedinject.Assisted;

public class Patch {

    private ASTNode node;
    private Expression exp;
    private String name;
    private int argIndex;

    @Inject
    public Patch(@Assisted final ASTNode node, @Assisted final Expression exp,
            @Assisted final String name, @Assisted final int argIndex) {
        super();
        this.node = node;
        this.exp = exp;
        this.name = name;
        this.argIndex = argIndex;
    }

    public ASTNode getNode() {
        return node;
    }

    public Expression getExp() {
        return exp;
    }

    public String getName() {
        return name;
    }

    public int getArgIndex() {
        return argIndex;
    }

    @Override
    public String toString() {
        return "Patch [name=" + name + "]";
    }
}
