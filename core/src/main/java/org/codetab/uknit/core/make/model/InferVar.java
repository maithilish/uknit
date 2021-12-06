package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class InferVar extends DefaultVar {

    @Inject
    public InferVar(@Assisted final String name, @Assisted final Type type,
            @Assisted final boolean mock) {
        super();
        this.name = name;
        this.type = type;
        this.mock = mock;
        this.used = false;
    }

    @Override
    public boolean isInferVar() {
        return true;
    }

    @Override
    public String toString() {
        return "InferVar [name=" + name + ", type=" + type + ", mock=" + mock
                + "]";
    }

    public void setType(final Type type) {
        this.type = type;
    }
}
