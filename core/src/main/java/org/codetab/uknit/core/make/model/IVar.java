package org.codetab.uknit.core.make.model;

import org.eclipse.jdt.core.dom.Type;

public interface IVar {

    String getName();

    void setName(String name);

    Type getType();

    void setType(Type type);

    boolean isMock();

    void setMock(final boolean mock);

    boolean isUsed();

    void setUsed(final boolean used);

    boolean isExposed();

    void setExposed(final boolean exposed);

    boolean isDeepStub();

    void setDeepStub(final boolean deepStub);

    public default boolean isField() {
        return false;
    }

    public default boolean isParameter() {
        return false;
    }

    public default boolean isLocalVar() {
        return false;
    }

    public default boolean isInferVar() {
        return false;
    }

    public default boolean isReturnVar() {
        return false;
    }
}
