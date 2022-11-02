package org.codetab.uknit.core.make.model;

import java.util.Optional;

import org.eclipse.jdt.core.dom.Type;

public interface IVar {

    enum Kind {
        FIELD, PARAMETER, LOCAL, RETURN, INFER
    }

    void setKind(Kind kind);

    Kind getKind();

    boolean is(Kind kind);

    String getName();

    void setName(String name);

    Type getType();

    void setType(Type type);

    boolean isMock();

    void setMock(boolean mock);

    boolean isCreated();

    void setCreated(boolean created);

    boolean isEnable();

    void setEnable(boolean enable);

    void setEnforce(Optional<Boolean> enforce);

    Optional<Boolean> getEnforce();

    boolean isDeepStub();

    void setDeepStub(boolean deepStub);

    default boolean isField() {
        return is(Kind.FIELD);
    }

    default boolean isParameter() {
        return is(Kind.PARAMETER);
    }

    default boolean isLocalVar() {
        return is(Kind.LOCAL);
    }

    default boolean isInferVar() {
        return is(Kind.INFER);
    }

    default boolean isReturnVar() {
        return is(Kind.RETURN);
    }
}
