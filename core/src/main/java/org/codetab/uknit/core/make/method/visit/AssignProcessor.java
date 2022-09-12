package org.codetab.uknit.core.make.method.visit;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.stage.VarExpStager;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.LocalVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class AssignProcessor {

    @Inject
    private Nodes nodes;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private NodeFactory nodeFactory;
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
                /*
                 * External method returns InferVar. Ex: String x = null; x =
                 * locale.get(); The mi for locale.get() creates inferVar apple
                 * and ExpVar [leftVar=apple, rightExp=locale.get()]. In assign
                 * visit find the expVar and change inferVar name to x from
                 * apple so that expVar becomes [leftVar=x,
                 * rightExp=locale.get()]. Remove var x and remove its expVar
                 * any.
                 */
                if (lvo.isPresent() && lvo.get().isInferVar()) {
                    IVar leftInferVar = lvo.get();
                    leftInferVar.setName(name);
                    leftInferVar.setType(var.getType());
                    leftInferVar.setMock(var.isMock());
                    // remove the duplicate and corresponding expVar
                    heap.getVars().remove(var);
                    heap.findByLeftVar(name)
                            .ifPresent(e -> heap.getVarExps().remove(e));
                } else if (lvo.isPresent() && lvo.get().isLocalVar()) {
                    /*
                     * Internal method returns LocalVar. Create new assignment
                     * leftVar = rightExp return var. Ex: Client client =
                     * context(); if context() returns var names client2 then
                     * create a new ExpVar with leftVar=client and
                     * rightExp=client2 (SimpleName Expression) so that
                     * Initializers.getInitializer() returns initializer client2
                     * for var client. Itest: internal.CallAndAssignIT
                     */
                    Optional<ExpVar> oEv = heap.findByLeftVar(var.getName());
                    Name rNameExp = nodeFactory.createName(lvo.get().getName());
                    if (oEv.isEmpty()) {
                        ExpVar exVar =
                                modelFactory.createVarExp(null, rNameExp);
                        exVar.setLeftVar(var);
                        heap.getVarExps().add(exVar);
                    } else {
                        ExpVar exVar = oEv.get();
                        exVar.setRightExp(rNameExp);
                    }
                } else if (lvo.isEmpty()) {
                    /*
                     * Ex: foo = new String(); The ClzInstCreation creates
                     * ExpVar without leftVar. Set leftVar of the ExpVar to
                     * LocalVar foo. Itest: create.CreateRealIT
                     */
                    optExpVar.get().setLeftVar(var);
                }
            }
        } else if (nodes.is(lExp, FieldAccess.class)) {
            FieldAccess fa = nodes.as(lExp, FieldAccess.class);
            String name = nodes.getName(fa.getName());
            Optional<IVar> field = heap.findField(name);
            if (field.isPresent()) {
                heap.findByRightExp(rExp)
                        .ifPresent(i -> i.setLeftVar(field.get()));
            }
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
        } else if (nodes.is(lExp, QualifiedName.class)) {
            QualifiedName qn = nodes.as(lExp, QualifiedName.class);

            Optional<Type> type = Optional.of(types
                    .getType(resolver.resolveTypeBinding(qn), qn.getAST()));
            LocalVar localVar = modelFactory.createLocalVar(lExp.toString(),
                    type.get(), false);
            heap.getVars().add(localVar);
            ExpVar expVar = varExpStager.stage(lExp, rExp, heap);
            expVar.setLeftVar(localVar);
        } else {
            throw new CodeException(nodes.noImplmentationMessage(lExp));
        }
    }
}
