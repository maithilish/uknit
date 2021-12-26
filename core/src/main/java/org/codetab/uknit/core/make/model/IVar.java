package org.codetab.uknit.core.make.model;

import java.util.Optional;

import org.eclipse.jdt.core.dom.Type;

public interface IVar {

    String getName();

    void setName(String name);

    Type getType();

    void setType(Type type);

    boolean isMock();

    void setMock(boolean mock);

    boolean isCreated();

    void setCreated(boolean created);

    boolean isUsed();

    void setUsed(boolean used);

    boolean isHidden();

    void setHidden(boolean hidden);

    boolean isDeepStub();

    void setDeepStub(boolean deepStub);

    Optional<String> getValue();

    void setValue(String value);

    default boolean isField() {
        return false;
    }

    default boolean isParameter() {
        return false;
    }

    default boolean isLocalVar() {
        return false;
    }

    default boolean isInferVar() {
        return false;
    }

    default boolean isReturnVar() {
        return false;
    }
}
