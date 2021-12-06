package org.codetab.uknit.core.make.model;

import org.eclipse.jdt.core.dom.Type;

public interface IVar {

    public String getName();

    public Type getType();

    public boolean isMock();

    void setMock(final boolean mock);

    public boolean isUsed();

    void setUsed(final boolean used);

    public boolean isExposed();

    void setExposed(final boolean exposed);

    public boolean isDeepStub();

    public void setDeepStub(final boolean deepStub);

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
