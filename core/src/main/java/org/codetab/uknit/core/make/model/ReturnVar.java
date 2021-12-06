package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class ReturnVar extends DefaultVar {

    @Inject
    public ReturnVar(@Assisted final String name, @Assisted final Type type,
            @Assisted final boolean mock) {
        super();
        this.name = name;
        this.type = type;
        this.mock = mock;
    }

    @Override
    public boolean isReturnVar() {
        return true;
    }

    @Override
    public String toString() {
        return "ReturnVar [name=" + name + ", type=" + type + ", mock=" + mock
                + "]";
    }
}
