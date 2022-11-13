package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
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
    @Inject
    private Packs packs;

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
     * For example if an IM contains Foo foo = factory.foo(); foo.options(); .
     * In case IM is called twice then second instance of foo conflicts and
     * renamed by setting name: foo2 and realName: foo. Then namesMap will have
     * a entry [foo : foo2]. The patch is created with these details - node:
     * foo.options, exp: foo, name: foo2, index: 0 which is used to create the
     * copy of node foo.options() as foo2.options().
     *
     * @param pack
     * @param namesMap
     * @param heap
     */
    public void createVarPatch(final Pack pack,
            final Map<String, String> namesMap, final Heap heap) {
        Expression exp = pack.getExp();
        if (nonNull(exp)) {
            List<Patch> patches = patcher.creatVarPatches(exp,
                    patchers.getExps(exp), namesMap, heap);
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
    public List<Class<?>> canHaveInvokes() {
        return nodeGroups.nodesWithInvoke();
    }

    /**
     * Returns of a map var names whose name and realName differs. On var name
     * conflict new name is assigned to the var and old name is moved to
     * realName. For example if an IM contains foo.options() then an infer var
     * options is created for it. In case IM is called twice then infer var is
     * named as options2 and second infer var realName is set as option and name
     * is set as option2. Then namesMap will have a entry [option : options2].
     *
     * @param heap
     * @return
     */
    public Map<String, String> getNamesMap(final Heap heap) {
        List<Pack> nameChangedPacks =
                packs.filterIsVarNameChanged(heap.getPacks());
        Map<String, String> namesMap = new HashMap<>();
        nameChangedPacks.forEach(p -> namesMap.put(p.getVar().getRealName(),
                p.getVar().getName()));
        return namesMap;
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
