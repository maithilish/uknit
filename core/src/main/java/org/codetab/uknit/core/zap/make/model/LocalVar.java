package org.codetab.uknit.core.zap.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class LocalVar extends DefaultVar {

    @Inject
    public LocalVar(@Assisted final String name, @Assisted final Type type,
            @Assisted final boolean mock) {
        super();
        this.name = name;
        this.type = type;
        this.mock = mock;
    }

    @Override
    public boolean isLocalVar() {
        return true;
    }

    @Override
    public String toString() {
        return "LocalVar [name=" + name + ", type=" + type + ", mock=" + mock
                + ", enable=" + enable + ", created=" + created + "]";
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