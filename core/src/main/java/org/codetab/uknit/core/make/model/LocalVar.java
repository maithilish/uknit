package org.codetab.uknit.core.make.model;

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
        this.used = false; // by default not used
    }

    @Override
    public boolean isLocalVar() {
        return true;
    }

    @Override
    public String toString() {
        return "LocalVar [name=" + name + ", type=" + type + ", mock=" + mock
                + "]";
    }
}
