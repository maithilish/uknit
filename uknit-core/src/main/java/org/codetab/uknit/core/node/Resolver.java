package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.exception.ResolveException;
import org.codetab.uknit.core.make.model.ReturnType;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
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

    /**
     * Get return type for MI, SMI and CastExpression.
     * @param exp
     * @return
     */
    public Optional<ReturnType> getExpReturnType(final Expression exp) {

        checkState(nodes.is(exp, MethodInvocation.class,
                SuperMethodInvocation.class, CastExpression.class));

        IMethodBinding methodBinding = null;
        if (nodes.is(exp, MethodInvocation.class)) {
            methodBinding = nodes.as(exp, MethodInvocation.class)
                    .resolveMethodBinding();
        } else if (nodes.is(exp, SuperMethodInvocation.class)) {
            methodBinding = nodes.as(exp, SuperMethodInvocation.class)
                    .resolveMethodBinding();
        }

        ITypeBinding typeBinding = null;
        if (nonNull(methodBinding)) {
            typeBinding = methodBinding.getReturnType();
        } else if (nodes.is(exp, CastExpression.class)) {
            typeBinding =
                    nodes.as(exp, CastExpression.class).resolveTypeBinding();
        }

        ReturnType methodReturnType = null;
        if (nonNull(typeBinding)) {
            try {
                Optional<Type> type =
                        Optional.of(types.getType(typeBinding, exp.getAST()));
                if (type.isPresent()) {
                    boolean mock = mocks.isMockable(type.get());
                    if (typeBinding.isEnum()) {
                        mock = false;
                    }
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
        IMethodBinding bind;
        if (nodes.is(exp, MethodInvocation.class)) {
            bind = nodes.as(exp, MethodInvocation.class).resolveMethodBinding();
        } else if (nodes.is(exp, SuperMethodInvocation.class)) {
            bind = nodes.as(exp, SuperMethodInvocation.class)
                    .resolveMethodBinding();
        } else {
            throw new CodeException(nodes.noImplmentationMessage(exp));
        }
        if (isNull(bind)) {
            throw new ResolveException(
                    nodes.exMessage("unable to resolve", exp));
        } else {
            return bind;
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

    /**
     * Get declaring class (ITypeBinding) for a node.
     *
     * @param node
     * @return
     */
    public ITypeBinding getDeclaringClass(final ASTNode node) {
        IMethodBinding methodBinding = null;
        if (nodes.is(node, MethodInvocation.class)) {
            methodBinding = nodes.as(node, MethodInvocation.class)
                    .resolveMethodBinding();
        } else if (nodes.is(node, SuperMethodInvocation.class)) {
            methodBinding = nodes.as(node, SuperMethodInvocation.class)
                    .resolveMethodBinding();
        } else if (nodes.is(node, MethodDeclaration.class)) {
            methodBinding =
                    nodes.as(node, MethodDeclaration.class).resolveBinding();
        } else {
            throw new CodeException(nodes.noImplmentationMessage(node));
        }
        if (isNull(methodBinding)) {
            throw new CriticalException(
                    "unable to get declaring class, methodBinding is null");
        }
        return methodBinding.getDeclaringClass();
    }
}
