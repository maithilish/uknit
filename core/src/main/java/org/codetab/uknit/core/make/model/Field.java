package org.codetab.uknit.core.make.model;

import java.util.Objects;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class Field extends Var {

    private final FieldDeclaration fieldDecl;
    private final FieldDeclaration srcFieldDecl;

    @Inject
    public Field(@Assisted final String name, @Assisted final Type type,
            @Assisted final boolean mock,
            @Assisted("fieldDecl") final FieldDeclaration fieldDecl,
            @Assisted("srcFieldDecl") final FieldDeclaration srcFieldDecl) {
        super(Kind.FIELD, name, type, mock);
        this.fieldDecl = fieldDecl;
        this.srcFieldDecl = srcFieldDecl;
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

    /**
     * Deep copy of the field. The fieldDecl and srcFieldDecl are not deep
     * copies but points to the originals.
     *
     * Field supports deepCopy() (implemented in super Var) and fieldDeepCopy().
     * The deepCopy() returns instance of Var, whereas fieldDeepCopy() returns
     * instance of Field.
     *
     * @return
     */
    public Field fieldDeepCopy() {
        Field clone = new Field(name, type, mock, fieldDecl, srcFieldDecl);
        clone.setCreated(created);
        clone.setDeepStub(deepStub);
        clone.setEnable(enable);
        if (enforce.isPresent()) {
            clone.setEnforce(enforce.get());
        }
        clone.setOldName(oldName);
        properties.forEach((k, v) -> clone.setProperty(k, v));
        natures.forEach(n -> clone.addNature(n));
        return clone;
    }

    @Override
    public String toString() {
        return "Field [name=" + name + ", type=" + type + ", mock=" + mock
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
        Field other = (Field) obj;
        return Objects.equals(fieldDecl, other.fieldDecl)
                && Objects.equals(srcFieldDecl, other.srcFieldDecl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fieldDecl, srcFieldDecl);
    }
}
