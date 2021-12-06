package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class Field extends DefaultVar {

    private final FieldDeclaration fieldDecl;

    @Inject
    public Field(@Assisted final String name, @Assisted final Type type,
            @Assisted final boolean mock,
            @Assisted final FieldDeclaration fieldDecl) {
        super();
        this.name = name;
        this.type = type;
        this.fieldDecl = fieldDecl;
        this.mock = mock;
    }

    @Override
    public boolean isField() {
        return true;
    }

    public FieldDeclaration getFieldDecl() {
        return fieldDecl;
    }

    @Override
    public String toString() {
        return "Field [name=" + name + ", type=" + type + ", mock=" + mock
                + "]";
    }
}
