package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

import com.google.inject.assistedinject.Assisted;

public class Patch {

    public enum Kind {
        INVOKE, VAR
    }

    private Kind kind;
    private ASTNode node;
    private Expression exp;
    private String name;
    private int expIndex;

    @Inject
    public Patch(@Assisted final Kind kind, @Assisted final ASTNode node,
            @Assisted final Expression exp, @Assisted final String name,
            @Assisted final int expIndex) {
        super();
        this.kind = kind;
        this.node = node;
        this.exp = exp;
        this.name = name;
        this.expIndex = expIndex;
    }

    public Kind getKind() {
        return kind;
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

    public void setName(final String name) {
        this.name = name;
    }

    public int getExpIndex() {
        return expIndex;
    }

    @Override
    public String toString() {
        return "Patch [kind=" + kind + ", patch exp=" + exp + ", to name="
                + name + ", in node=" + node + ", expIndex=" + expIndex + "]";
    }

}
