package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class ArgCapture {

    private String name;
    private Type type;

    @Inject
    public ArgCapture(@Assisted final String name, @Assisted final Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ArgCapture [name=" + name + ", type=" + type + "]";
    }
}
