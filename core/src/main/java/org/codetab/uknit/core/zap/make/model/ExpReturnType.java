package org.codetab.uknit.core.zap.make.model;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

public class ExpReturnType {

    private Type type;
    private boolean mock;
    private ITypeBinding typeBinding;

    public ExpReturnType(final Type type, final boolean mock,
            final ITypeBinding typeBinding) {
        this.type = type;
        this.mock = mock;
        this.typeBinding = typeBinding;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public ITypeBinding getTypeBinding() {
        return typeBinding;
    }

    public boolean isMock() {
        return mock;
    }

    @Override
    public String toString() {
        return "MethodReturnType [type=" + type + ", mock=" + mock + "]";
    }

}
