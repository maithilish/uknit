package org.codetab.uknit.core.make.method.processor;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Var;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Type;

public class InferFactory {

    @Inject
    private VarNames varNames;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Types types;
    @Inject
    private Nodes nodes;
    @Inject
    private Mocks mocks;
    @Inject
    private Resolver resolver;

    /**
     * Create infer var for MI.
     *
     * @param exp
     * @param heap
     * @return
     */
    // FIXME Pack - remove this
    // public IVar createInferForInvoke(final Expression exp, final Heap heap) {
    //
    // checkState(nodes.is(exp, MethodInvocation.class));
    //
    // Optional<ExpReturnType> expRetTypeO = resolver.getExpReturnType(exp);
    // if (expRetTypeO.isPresent()) {
    // Type type = expRetTypeO.get().getType();
    // boolean isMock = mocks.isMockable(type);
    // Optional<String> typeName = Optional.empty();
    // try {
    // typeName = Optional.of(types.getTypeName(type));
    // } catch (Exception e) {
    // // ignore
    // }
    // String name = varNames.getInferVarName(typeName, heap);
    // Var inferVar =
    // modelFactory.createVar(Kind.INFER, name, type, isMock);
    // return inferVar;
    // } else {
    // throw new TypeException(
    // "unable to get exp return type for: " + exp);
    // }
    // }

    public IVar createInfer(final Type type, final Heap heap) {
        boolean isMock = mocks.isMockable(type);
        Optional<String> typeName = Optional.empty();
        try {
            typeName = Optional.of(types.getTypeName(type));
        } catch (Exception e) {
            // ignore
        }
        String name = varNames.getInferVarName(typeName, heap);
        Var inferVar = modelFactory.createVar(Kind.INFER, name, type, isMock);
        return inferVar;
    }
}
