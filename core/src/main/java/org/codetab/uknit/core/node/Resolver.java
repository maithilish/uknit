package org.codetab.uknit.core.node;

import static java.util.Objects.isNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.ExpReturnType;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class Resolver {

    @Inject
    private Types types;
    @Inject
    private Mocks mocks;

    public Optional<ExpReturnType> getExpReturnType(final MethodInvocation mi) {
        IMethodBinding methodBinding = mi.resolveMethodBinding();
        ITypeBinding typeBinding = methodBinding.getReturnType();
        ExpReturnType methodReturnType = null;
        try {
            Optional<Type> type =
                    Optional.of(types.getType(typeBinding, mi.getAST()));
            if (type.isPresent()) {
                boolean mock = mocks.isMockable(type.get());
                if (typeBinding.isEnum()) {
                    mock = false;
                }
                // TODO - move to model factory
                methodReturnType =
                        new ExpReturnType(type.get(), mock, typeBinding);
            }
        } catch (Exception e) {
        }
        return Optional.ofNullable(methodReturnType);
    }

    public Optional<Type> getVarClass(final Expression exp) {
        ITypeBinding typeBinding = exp.resolveTypeBinding();
        try {
            return Optional.of(types.getType(typeBinding, exp.getAST()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public ITypeBinding resolveTypeBinding(final ArrayAccess arrayAccess) {
        return arrayAccess.resolveTypeBinding();
    }

    public ITypeBinding resolveTypeBinding(final LambdaExpression lambdaExp) {
        return lambdaExp.resolveTypeBinding();
    }

    public IMethodBinding resolveMethodBinding(final MethodInvocation mi) {
        return mi.resolveMethodBinding();
    }

    public ITypeBinding resolveBinding(final Type type) {
        return type.resolveBinding();
    }

    public ITypeBinding resolveBinding(final AbstractTypeDeclaration typeDecl) {
        return typeDecl.resolveBinding();
    }

    public boolean hasModifier(final int modifier, final int bitmask) {
        return (modifier & bitmask) > 0;
    }

    /**
     * Whether type is type variable such as <T>
     * @param type
     * @return
     */
    public boolean isTypeVariable(final Type type) {
        ITypeBinding typeBind = type.resolveBinding();
        if (isNull(typeBind)) {
            return false;
        } else {
            return typeBind.isTypeVariable();
        }
    }
}
