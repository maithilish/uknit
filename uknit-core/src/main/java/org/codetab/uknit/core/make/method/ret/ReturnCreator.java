package org.codetab.uknit.core.make.method.ret;

import static java.util.Objects.isNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class ReturnCreator {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private Mocks mocks;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Wrappers wrappers;

    /**
     * Creates return var for the return statement expression and sets it to the
     * existing pack which is already created by some other visit such as method
     * invocation, literal etc.,
     *
     * The return var name is set as "return" (yes java keyword!) which ensures
     * that the var name is unique as any other variable can't legally use this
     * name. It makes it easy to find the return pack.
     *
     * @param rs
     * @param methodReturnType
     * @param heap
     */
    public void createReturnVar(final ReturnStatement rs,
            final Type methodReturnType, final boolean inCtlPath,
            final Heap heap) {

        /*
         * strip any outer exp such as Cast, Parentheses etc., Ex: return
         * (String) obj;
         */
        Expression exp = wrappers.unpack(rs.getExpression());

        if (isNull(exp)) {
            // Method return is void. Ex: return;
            String retVarName = "return";
            boolean isMock = false;
            IVar var = modelFactory.createVar(Kind.RETURN, retVarName,
                    methodReturnType, isMock);
            Pack pack = modelFactory.createPack(packs.getId(), var, exp,
                    inCtlPath, heap.isIm());
            heap.addPack(pack);
        } else if (nodes.is(exp, SimpleName.class)) {
            /*
             * Exp maps to var. Pack for var already exists, create new return
             * var and pack for it. Ex: return paramA; return localVarB; etc.,
             */
            String name = nodes.getName(exp);
            Optional<Pack> packO = packs.findByVarName(name, heap.getPacks());
            if (packO.isPresent()) {
                String retVarName = "return";
                boolean isMock = mocks.isMockable(methodReturnType);
                IVar var = modelFactory.createVar(Kind.RETURN, retVarName,
                        methodReturnType, isMock);
                var.setTypeBinding(exp.resolveTypeBinding());
                Pack pack = modelFactory.createPack(packs.getId(), var, exp,
                        inCtlPath, heap.isIm());
                heap.addPack(pack);
            } else {
                throw new IllegalStateException(
                        nodes.exMessage("unable to find pack for ", rs));
            }
        } else {
            /*
             * Exp doesn't map to var (simple name). The retStmt exp is visited
             * before visit of retStmt itself. Search for the pack created in
             * exp visit and then create var and set it to the pack. The infer
             * var for the exp is created later by the Processor and pack exp is
             * replaced with infer var name. Ex: return "foo"; return
             * bar.methodA(); etc.,
             */
            Optional<Pack> packO = packs.findByExp(exp, heap.getPacks());
            if (packO.isPresent()) {
                String name = "return";
                boolean isMock = mocks.isMockable(methodReturnType);
                IVar var = modelFactory.createVar(Kind.RETURN, name,
                        methodReturnType, isMock);
                var.setTypeBinding(exp.resolveTypeBinding());
                packO.get().setVar(var);
            } else {
                throw new IllegalStateException(
                        nodes.exMessage("unable to find pack for ", rs));
            }
        }
    }

}
