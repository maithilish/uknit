package org.codetab.uknit.core.node;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.ExpReturnType;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class TypeResolver {

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
}
