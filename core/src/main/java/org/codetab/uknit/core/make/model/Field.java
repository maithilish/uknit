package org.codetab.uknit.core.make.model;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class Field extends DefaultVar {

    private final FieldDeclaration fieldDecl;
    private final FieldDeclaration srcFieldDecl;

    @Inject
    public Field(@Assisted final String name, @Assisted final Type type,
            @Assisted final boolean mock,
            @Assisted("fieldDecl") final FieldDeclaration fieldDecl,
            @Assisted("srcFieldDecl") final FieldDeclaration srcFieldDecl) {
        super();
        this.name = name;
        this.type = type;
        this.fieldDecl = fieldDecl;
        this.srcFieldDecl = srcFieldDecl;
        this.mock = mock;
    }

    @Override
    public boolean isField() {
        return true;
    }

    public FieldDeclaration getFieldDecl() {
        return fieldDecl;
    }

    public FieldDeclaration getSrcFieldDecl() {
        return srcFieldDecl;
    }

    @Override
    public String toString() {
        return "Field [name=" + name + ", type=" + type + ", mock=" + mock
                + "]";
    }
}
