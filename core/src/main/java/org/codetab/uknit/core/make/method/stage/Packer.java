package org.codetab.uknit.core.make.method.stage;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Literals;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Variables;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class Packer {

    @Inject
    private Packs packs;
    @Inject
    private Variables variables;
    @Inject
    private Literals literals;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Mocks mocks;

    public void packVars(final Kind kind, final Type type,
            final List<VariableDeclaration> vdList, final Heap heap) {
        for (VariableDeclaration vd : vdList) {
            String name = variables.getVariableName(vd);
            boolean isMock = mocks.isMockable(type);
            IVar var = modelFactory.createVar(kind, name, type, isMock);
            Expression initializer = vd.getInitializer();
            Optional<Pack> packO =
                    packs.findByExp(heap.getPacks(), initializer);
            if (packO.isPresent()) {
                packO.get().setVar(var);
            } else {
                Pack pack = modelFactory.createPack(var, initializer);
                heap.addPack(pack);
            }
        }
    }

    public void packExp(final Expression exp, final Heap heap) {
        IVar var = null; // yet to be assigned so null
        Pack pack = modelFactory.createPack(var, exp);
        heap.addPack(pack);
    }

    /**
     * Pack literal exp only if it is of interest.
     *
     * @param exp
     * @param heap
     */
    public void packLiteralExp(final Expression exp, final Heap heap) {
        IVar var = null; // yet to be assigned so null
        if (literals.ofInterest(exp)) {
            Pack pack = modelFactory.createPack(var, exp);
            heap.addPack(pack);
        }
    }
}
