package org.codetab.uknit.core.node;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.ReturnType;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class Resolver {

    @Inject
    private Types types;
    @Inject
    private Mocks mocks;
    @Inject
    private Nodes nodes;

    public Optional<ReturnType> getExpReturnType(final Expression exp) {
        IMethodBinding methodBinding = null;
        if (nodes.is(exp, MethodInvocation.class)) {
            methodBinding = nodes.as(exp, MethodInvocation.class)
                    .resolveMethodBinding();
        } else if (nodes.is(exp, SuperMethodInvocation.class)) {
            methodBinding = nodes.as(exp, SuperMethodInvocation.class)
                    .resolveMethodBinding();
        }
        ReturnType methodReturnType = null;
        if (nonNull(methodBinding)) {
            ITypeBinding typeBinding = methodBinding.getReturnType();
            try {
                Optional<Type> type =
                        Optional.of(types.getType(typeBinding, exp.getAST()));
                if (type.isPresent()) {
                    boolean mock = mocks.isMockable(type.get());
                    if (typeBinding.isEnum()) {
                        mock = false;
                    }
                    // FIXME - move to model factory
                    methodReturnType =
                            new ReturnType(type.get(), mock, typeBinding);
                }
            } catch (Exception e) {
            }
        }
        return Optional.ofNullable(methodReturnType);
    }

    public Type getReturnType(final Expression exp) {
        ITypeBinding typeBinding = exp.resolveTypeBinding();
        return types.getType(typeBinding, exp.getAST());
    }

    public Optional<Type> getVarClass(final Expression exp) {
        ITypeBinding typeBinding = exp.resolveTypeBinding();
        try {
            return Optional.of(types.getType(typeBinding, exp.getAST()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public IMethodBinding resolveMethodBinding(final Expression exp) {
        if (nodes.is(exp, MethodInvocation.class)) {
            return nodes.as(exp, MethodInvocation.class).resolveMethodBinding();
        } else if (nodes.is(exp, SuperMethodInvocation.class)) {
            return nodes.as(exp, SuperMethodInvocation.class)
                    .resolveMethodBinding();
        } else {
            throw new CodeException(nodes.noImplmentationMessage(exp));
        }
    }

    public ITypeBinding resolveTypeBinding(final Expression exp) {
        try {
            exp.getClass().getMethod("resolveTypeBinding");
            return exp.resolveTypeBinding();
        } catch (NoSuchMethodException | SecurityException e) {
            String msg = nodes.exMessage(
                    "no resolveTypeBinding method in node type: ", exp);
            throw new CodeException(msg);
        }
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
