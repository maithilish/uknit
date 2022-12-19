package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class Patch {

    public enum Kind {
        INVOKE, VAR
    }

    private Kind kind;
    private IVar var;
    private int index;

    @Inject
    public Patch(@Assisted final Kind kind, @Assisted final IVar var,
            @Assisted final int index) {
        super();
        this.kind = kind;
        this.var = var;
        this.index = index;
    }

    public Kind getKind() {
        return kind;
    }

    public IVar getVar() {
        return var;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Patch [kind=" + kind + ", var=" + var + ", index=" + index
                + "]";
    }
}
