package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Resolver;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;

class EnumInitializer {

    @Inject
    private Resolver resolver;
    @Inject
    private Packs packs;

    public Optional<String> getInitializer(final IVar var, final Heap heap) {
        Optional<Invoke> invo =
                packs.findInvokeByName(var.getName(), heap.getPacks());
        ITypeBinding typeBind = null;
        if (invo.isPresent()) {
            Invoke invoke = invo.get();
            Optional<ReturnType> exrto = invoke.getReturnType();
            if (exrto.isPresent()) {
                typeBind = exrto.get().getTypeBinding();
            }
        } else {
            typeBind = resolver.resolveBinding(var.getType());
        }
        if (nonNull(typeBind) && typeBind.isEnum()) {
            String packg = typeBind.getPackage().getName();
            String qName = typeBind.getQualifiedName();
            IVariableBinding[] fields = typeBind.getDeclaredFields();
            if (fields.length > 1 && fields[0].getName().contains("$VALUES")) {
                String firstEnumConstant = fields[1].getName();
                String enumName =
                        qName.replace(String.join(".", packg, ""), "");
                return Optional
                        .of(String.join(".", enumName, firstEnumConstant));
            }
        }
        return Optional.empty();
    }
}
