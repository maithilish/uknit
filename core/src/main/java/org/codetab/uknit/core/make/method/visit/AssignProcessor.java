package org.codetab.uknit.core.make.method.visit;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.stage.VarExpStager;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.LocalVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class AssignProcessor {

    @Inject
    private Nodes nodes;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private VarExpStager varExpStager;
    @Inject
    private Types types;
    @Inject
    private Mocks mocks;
    @Inject
    private Resolver resolver;

    public void process(final Assignment node, final Heap heap) {
        Expression rExp = node.getRightHandSide();
        Expression lExp = node.getLeftHandSide();
        if (nodes.is(lExp, SimpleName.class)) {
            String name = nodes.getName(lExp);
            IVar var = heap.findVar(name);
            if (nodes.isCreation(rExp)) {
                var.setMock(false);
            } else {
                /*
                 * Person person = null; person = q.take(); the first statement
                 * is creation, but next assignment negates it.
                 */
                var.setMock(mocks.isMockable(var.getType()));
                var.setCreated(false);
            }
            Optional<ExpVar> optExpVar = heap.findByRightExp(rExp);
            if (optExpVar.isPresent()) {
                Optional<IVar> lvo = optExpVar.get().getLeftVar();
                if (lvo.isPresent()) {
                    IVar inferVar = lvo.get();
                    inferVar.setName(name);
                    inferVar.setType(var.getType());
                    inferVar.setMock(var.isMock());
                    // remove the duplicate and corresponding expVar
                    heap.getVars().remove(var);
                    heap.findByLeftVar(name)
                            .ifPresent(e -> heap.getVarExps().remove(e));
                } else {
                    optExpVar.get().setLeftVar(var);
                }
            }
        } else if (nodes.is(lExp, FieldAccess.class)) {
            FieldAccess fa = nodes.as(lExp, FieldAccess.class);
            String name = nodes.getName(fa.getName());
            IVar var = heap.findVar(name);
            heap.findByRightExp(rExp).ifPresent(i -> i.setLeftVar(var));
        } else if (nodes.is(lExp, ArrayAccess.class)) {
            // TODO - fix this
            ArrayAccess aa = nodes.as(lExp, ArrayAccess.class);
            Optional<Type> type = Optional.of(types
                    .getType(resolver.resolveTypeBinding(aa), aa.getAST()));
            LocalVar localVar = modelFactory.createLocalVar(lExp.toString(),
                    type.get(), false);
            heap.getVars().add(localVar);
            ExpVar expVar = varExpStager.stage(lExp, rExp, heap);
            expVar.setLeftVar(localVar);
        } else {
            throw nodes.unexpectedException(lExp);
        }
    }
}
