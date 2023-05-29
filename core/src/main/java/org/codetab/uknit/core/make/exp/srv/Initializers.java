package org.codetab.uknit.core.make.exp.srv;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.ResolveException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Type;

public class Initializers {

    @Inject
    private Packs packs;
    @Inject
    private Types types;
    @Inject
    private Configs configs;

    // REVIEW - doc it
    public Expression getInitializerAsExpression(final Expression node,
            final boolean createValue, final Heap heap) {

        IVar var = packs.getVar(packs.findByExp(node, heap.getPacks()));
        Optional<Type> typeO = Optional.empty();
        if (nonNull(var) && var.getInitializer().isPresent()) {
            Object ini = var.getInitializer().get().getInitializer();
            if (ini instanceof Expression) {
                return (Expression) ini;
            } else {
                typeO = Optional.of(var.getType());
            }
        } else {
            try {
                typeO = types.getType(node);
            } catch (ResolveException e) {
                // ignore to return null
            }
        }
        if (typeO.isPresent() && createValue) {
            return createIniValue(node, typeO);
        } else {
            return null;
        }
    }

    private Expression createIniValue(final Expression node,
            final Optional<Type> typeO) {
        Type type = typeO.get();
        String typeName = types.getTypeName(type);
        String key = String.join(".", "uknit.createInstance", typeName);
        String defaultValue = configs.getConfig(key);
        Expression ini = null;

        if (type.isPrimitiveType()) {
            if (typeName.equals("int")) {
                ini = node.getAST().newNumberLiteral(defaultValue);
            } else if (typeName.equals("boolean")) {
                ini = type.getAST()
                        .newBooleanLiteral(Boolean.valueOf(defaultValue));
            } else {
                // implement for other primitive types
                new CodeException("not implemented for " + typeName);
            }
        }
        return ini;
    }
}
