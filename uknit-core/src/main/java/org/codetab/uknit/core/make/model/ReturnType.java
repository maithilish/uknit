package org.codetab.uknit.core.make.model;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;

public class ReturnType {

    private Type type;
    private boolean mock;
    private ITypeBinding typeBinding;

    public ReturnType(final Type type, final boolean mock,
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

    public boolean isVoid() {
        if (type.isPrimitiveType()) {
            return ((PrimitiveType) type).getPrimitiveTypeCode()
                    .equals(PrimitiveType.VOID);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "MethodReturnType [type=" + type + ", mock=" + mock + "]";
    }

}
