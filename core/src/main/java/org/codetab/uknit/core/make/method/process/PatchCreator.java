package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.nonNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.method.patch.Patchers;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.NodeGroups;
import org.eclipse.jdt.core.dom.Expression;

public class PatchCreator {

    @Inject
    private Patcher patcher;
    @Inject
    private Patchers patchers;
    @Inject
    private NodeGroups nodeGroups;

    /**
     * Patch to replace MIs in exp such as MI, ArrayAccess,
     * ClassInstanceCreateion, Infix etc., with var.
     *
     * @param packList
     * @param heap
     */
    public void createInvokePatch(final Pack pack, final Heap heap) {
        Expression exp = pack.getExp();
        List<Patch> patches =
                patcher.creatInvokePatches(exp, patchers.getExps(exp), heap);
        pack.getPatches().addAll(patches);
    }

    /**
     * Creates patch of type Kink.Var for renaming the var.
     *
     * For example if an IM contains Foo foo = factory.foo(); foo.options(); If
     * IM is called twice then second instance of foo is renamed as foo2. The
     * renamedPack var holds the rename info [name: foo2, oldName: foo]. Now,
     * the foo in the pack [exp: foo.options()] has to be updated foo2. The
     * patch [node: foo.options, exp: foo, name: foo2, index: 0] is created
     * which can be used to create the copy of node foo.options() as
     * foo2.options().
     *
     * @param pack
     * @param namesMap
     * @param heap
     */
    public void createVarPatch(final Pack pack, final Pack renamedPack) {
        Expression exp = pack.getExp();
        if (nonNull(exp)) {
            List<Patch> patches = patcher.creatVarPatches(exp,
                    patchers.getExps(exp), renamedPack);
            pack.getPatches().addAll(patches);
        }
    }

    /**
     * List of ASTNode that can contain MethodInvocation.
     * <p>
     * SuperMethodInvocation too can contain MI but it is not considered as we
     * use only its return value not the node itself.
     * <p>
     * Other Super nodes such as SuperConstructorInvocation etc., are yet to be
     * handled so not included for now.
     *
     * @return
     */
    public List<Class<? extends Expression>> canHaveInvokes() {
        return nodeGroups.nodesWithInvoke();
    }

    /**
     * Stage patch to replace entire super method invocation with return var.
     * <p>
     * Example: return super.foo(bar); to return orange;
     * @param node
     * @param retVar
     * @param heap
     */
    // public void stageSuperMiPatch(final SuperMethodInvocation node,
    // final Optional<IVar> retVar, final Heap heap) {
    // if (retVar.isPresent()) {
    // patcher.stageSuperPatch(node, retVar.get(), heap);
    // }
    // }

    // public void stageReturnStmtPatches(final ReturnStatement rs,
    // final Heap heap) {
    // List<Expression> exps = patchers.getExps(rs);
    // patcher.stageInferPatch(rs, exps, heap);
    // patcher.stageInternalPatch(rs, exps, heap);
    // // FIXME - analyze can we drop this
    // patcher.stageInitializerPatch(rs, exps, heap);
    //
    // }
    //

    // public void stageAssignmentPatches(final Assignment assign,
    // final Heap heap) {
    // List<Expression> exps = patchers.getExps(assign);
    // patcher.stageInferPatch(assign, exps, heap);
    // patcher.stageInternalPatch(assign, exps, heap);
    // }
}
