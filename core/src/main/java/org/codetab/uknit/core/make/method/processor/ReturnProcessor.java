package org.codetab.uknit.core.make.method.processor;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class ReturnProcessor {

    @Inject
    private Packs packs;
    @Inject
    private Expressions expressions;
    @Inject
    private Nodes nodes;
    @Inject
    private Mocks mocks;
    @Inject
    private ModelFactory modelFactory;

    public void createReturnVar(final ReturnStatement rs,
            final Type methodReturnType, final Heap heap) {

        // strip any outer exp such as Cast, Parentheses etc.,
        Expression exp = expressions.stripWraperExpression(rs.getExpression());

        if (nodes.is(exp, SimpleName.class)) {
            String name = nodes.getName(exp);
            Optional<Pack> packO = packs.findByVarName(heap.getPacks(), name);
            if (packO.isPresent()) {
                String retVarName = "return";
                boolean isMock = mocks.isMockable(methodReturnType);
                IVar var = modelFactory.createVar(Kind.RETURN, retVarName,
                        methodReturnType, isMock);
                Pack pack = modelFactory.createPack(var, exp);
                heap.addPack(pack);
            } else {
                throw new IllegalStateException(
                        nodes.exMessage("unable to find pack for ", rs));
            }
        } else {
            /*
             * exp doesn't map to var (simple name).
             */
            Optional<Pack> packO = packs.findByExp(heap.getPacks(), exp);
            if (packO.isPresent()) {
                String name = "return";
                boolean isMock = mocks.isMockable(methodReturnType);
                IVar var = modelFactory.createVar(Kind.RETURN, name,
                        methodReturnType, isMock);
                packO.get().setVar(var);
            } else {
                throw new IllegalStateException(
                        nodes.exMessage("unable to find pack for ", rs));
            }
        }
    }

}
