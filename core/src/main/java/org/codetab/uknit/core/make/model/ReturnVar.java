package org.codetab.uknit.core.make.model;

import java.util.Objects;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class ReturnVar extends DefaultVar {

    // whether var is class under test (SUT)
    private boolean selfField;

    @Inject
    public ReturnVar(@Assisted final String name, @Assisted final Type type,
            @Assisted final boolean mock) {
        super();
        this.name = name;
        this.type = type;
        this.mock = mock;
        selfField = false;
    }

    @Override
    public boolean isReturnVar() {
        return true;
    }

    /**
     * whether var is class under test (SUT)
     * @return
     */
    public boolean isSelfField() {
        return selfField;
    }

    public void setSelfField(final boolean selfField) {
        this.selfField = selfField;
    }

    @Override
    public String toString() {
        return "ReturnVar [name=" + name + ", type=" + type + ", mock=" + mock
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
        ReturnVar other = (ReturnVar) obj;
        return Objects.equals(selfField, other.selfField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), selfField);
    }
}
