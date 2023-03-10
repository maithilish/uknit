package org.codetab.uknit.core.make.model;

import java.util.Objects;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class Initializer {

    public enum Kind {
        MOCK, STEPIN, EXP, NAME, INVOKE, REAL, LITERAL
    }

    private Kind kind;
    private Object initializer; // String, Name, Expression

    @Inject
    public Initializer(@Assisted final Kind kind,
            @Assisted final Object initializer) {
        this.kind = kind;
        this.initializer = initializer;
    }

    public Kind getKind() {
        return kind;
    }

    public Object getInitializer() {
        return initializer;
    }

    @Override
    public String toString() {
        return "Initializer [kind=" + kind + ", initializer=" + initializer
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(initializer, kind);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Initializer other = (Initializer) obj;
        return Objects.equals(initializer, other.initializer)
                && kind == other.kind;
    }
}
