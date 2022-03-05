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
    }

    @Override
    public boolean isInferVar() {
        return true;
    }

    @Override
    public String toString() {
        return "InferVar [name=" + name + ", type=" + type + ", mock=" + mock
                + ", disable=" + enable + ", created=" + created + "]";
    }

    @Override
    public void setType(final Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
