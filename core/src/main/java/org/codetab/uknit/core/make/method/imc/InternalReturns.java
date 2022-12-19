package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Var;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;

/**
 * Updates exp of Invoke that called IM to return var of the IM.
 *
 * @author Maithilish
 *
 */
class InternalReturns {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private NodeFactory nodeFactory;

    private IVar returnVar;
    private Optional<Pack> returnPackO;
    private String returnVarName;

    /**
     * Find and save returnPack, returnVarName and returnVar from internalHeap.
     *
     * @param internalHeap
     */
    public void init(final Heap internalHeap) {
        returnPackO = packs.getReturnPack(internalHeap.getPacks());

        if (returnPackO.isPresent()) {
            try {
                returnVarName = nodes.getName(returnPackO.get().getExp());
                Optional<Pack> varPackO = packs.findByVarName(returnVarName,
                        internalHeap.getPacks());
                if (varPackO.isPresent()) {
                    returnVar = varPackO.get().getVar();
                }
            } catch (CodeException e) {
                returnVarName = null;
                returnVar = null;
            }
        }
    }

    /**
     * Update the exp of the Invoke (i.e. Pack) that called the IM. <code>
     * public void caller() {
     *    Foo foo = factory.createFoo();
     *    Foo otherFoo = internal();
     * }
     *
     * Foo internal() {
     *    Foo foo = factory.createFoo();
     *    return foo;
     * }
     *</code> The Invoke Pack is [var name=otherFoo, exp=internal()], its exp is
     * set to new name foo2 and if there is no name conflict then to foo.
     *
     * The returnVarName is saved in init() before start of merge. On var name
     * conflict the merge assign new name to returnVar.
     *
     * @param invoke
     * @param heap
     */
    public void updateExp(final Invoke invoke) {
        if (nonNull(returnVar) && nonNull(returnVarName)) {
            String varName = returnVar.getName();

            if (varName.equals(returnVarName)) {
                /*
                 * Saved returnVarName and name of merged returnVar are same,
                 * assign return var name to invoke's exp, else assign new var
                 * name.
                 */
                if (returnPackO.isPresent()) {
                    invoke.setExp(returnPackO.get().getExp());
                }
            } else {
                invoke.setExp(nodeFactory.createName(varName));
            }
        }
    }

    /**
     * Copy return var info to invoke var. If IM Return var is Kind.Field then
     * update name in tailing matched patches (in calling heap) as the field
     * name.<code>
     *
     * Invoke [var=payload2 exp=getPayload()]
     * Invoke [var=jobInfo exp=getPayload().getJobInfo()]
     *        [Patch exp=getPayload() name=payload2]
     *
     * </code> Suppose getPayload() returns super field named payload, then
     * patch name is updated to payload.
     *
     * Ref itest: superclass.MultiGetMock.getMulti().
     *
     * @param invoke
     * @param heap
     * @param internalHeap
     */
    public void updateVar(final Invoke invoke, final Heap heap,
            final Heap internalHeap) {
        if (!nodes.isName(invoke.getExp()) || isNull(invoke.getVar())) {
            return;
        }
        Optional<Pack> returnVarPackO = packs
                .findByVarName(nodes.getName(invoke.getExp()), heap.getPacks());

        // update invoke var states with return field states, only for field
        if (returnVarPackO.isPresent()) {
            IVar returnField = returnVarPackO.get().getVar();
            ((Var) invoke.getVar()).updateStates((Var) returnField);
        }
    }

    /**
     * Add patch for chained IM Call. Ex: name = internal().getName().
     *
     * Ex itest: internal.CallInternal.process()
     *
     * @param invoke
     * @param invokeIndex
     * @param heap
     */
    public void addIMPatch(final Invoke invoke, final int invokeIndex,
            final Heap heap) {
        if (nonNull(returnVar) && nonNull(returnVarName)) {
            heap.getPatcher().addPatch(invoke.getExp(), returnVar);
        }
    }
}
