package org.codetab.uknit.core.make.method.var.infer;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Var;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

public class InferFactory {

    @Inject
    private VarNames varNames;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Types types;
    @Inject
    private Mocks mocks;

    public IVar createInfer(final Type type, final ITypeBinding typeBinding,
            final Heap heap) {
        boolean isMock = mocks.isMockable(type);
        Optional<String> typeName = Optional.empty();
        try {
            typeName = Optional.of(types.getTypeName(type));
        } catch (Exception e) {
            // ignore
        }
        String name = varNames.getInferVarName(typeName, heap);
        Var inferVar = modelFactory.createVar(Kind.INFER, name, type, isMock);
        inferVar.setTypeBinding(typeBinding);
        return inferVar;
    }
}
