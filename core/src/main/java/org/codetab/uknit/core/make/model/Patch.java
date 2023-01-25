package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class Patch {

    public enum Kind {
        VAR
    }

    private Kind kind;
    // foo.format(id), exp use defined name id
    private String definedName;
    private IVar var;
    private int index;

    @Inject
    public Patch(@Assisted final Kind kind, @Assisted final String definedName,
            @Assisted final IVar var, @Assisted final int index) {
        super();
        this.kind = kind;
        this.definedName = definedName;
        this.var = var;
        this.index = index;
    }

    public Kind getKind() {
        return kind;
    }

    public String getDefinedName() {
        return definedName;
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
