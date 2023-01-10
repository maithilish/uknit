package org.codetab.uknit.core.make.model;

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
}
