package org.codetab.uknit.core.make.method.var;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

/**
 * Var Reassign helper methods.
 *
 * @author Maithilish
 *
 */
public class Reassignors {

    @Inject
    private Vars vars;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Patcher patcher;
    @Inject
    private NodeFactory nodeFactory;

    /**
     * The reassigned vars are named as foo-reassigned where illegal dash is
     * used to avoid any conflict with vars in the MUT.
     *
     * Get and set indexed var name for such vars.
     *
     * @param var
     * @param heap
     */
    public void renameAssigns(final IVar var, final Heap heap) {

        checkNotNull(var);
        checkNotNull(heap);
        checkState(var.getName().endsWith("-reassigned"));

        String newName = vars.getIndexedVar(
                var.getName().replace("-reassigned", ""), heap.getVars());
        var.setName(newName);
    }

    /**
     * Set old name of reassigned vars to name of its previous state.
     *
     * <code>
     *  V1 [name=state, oldName=state]
     *  V2 [name=state2, oldName=state]
     *  V3 [name=state3, oldName=state]
     *
     *  to
     *
     *  V1 [name=state, oldName=state]
     *  V2 [name=state2, oldName=state]
     *  V3 [name=state3, oldName=state2]
     * </code>
     *
     * @param reassignedVars
     * @param heap
     */
    public void updateOldNames(final List<IVar> reassignedVars,
            final Heap heap) {

        Map<String, List<IVar>> varMap = reassignedVars.stream()
                .collect(Collectors.groupingBy(v -> v.getOldName(),
                        Collectors.mapping(v -> v, Collectors.toList())));

        for (List<IVar> varList : varMap.values()) {
            if (varList.size() > 1) {
                for (int i = 1; i < varList.size(); i++) {
                    IVar var = varList.get(i);
                    IVar previousVar = varList.get(i - 1);
                    var.setOldName(previousVar.getName());
                }
            }
        }
    }

    /**
     * Reassigned var gets new name and update any assignment that uses it. Ex:
     *
     * <code>
     * String message = "foo";
     *   if (pathA) {
     *       message = duck.fly("if canSwim");
     *       duck.dive(message);
     *   } else if (pathB) {
     *       message = duck.fly("else if canDive");
     *       duck.dive(message);
     *   }
     *   return message;
     * </code>
     *
     * The exp of return pack and duck.dive() pack are initially set to message.
     * In pathA test, message is reassigned as message2 and both pack's exp are
     * updated as message2. In PathB test, message is reassigned as message3 and
     * both exps are updated to message3. More complex examples in itest:
     * ifelse.when.IfElseIf.java
     *
     * @param var
     * @param heap
     */
    public void updateReferredRHSExps(final IVar var, final Heap heap) {

        checkNotNull(var);
        checkNotNull(heap);

        // do nothing and return, if var pack is not in ctl path.
        Optional<Pack> varPackO =
                packs.findByVarName(var.getName(), heap.getPacks());
        if (varPackO.isPresent()) {
            if (!varPackO.get().isInCtlPath()) {
                return;
            }
        } else {
            throw new IllegalStateException(
                    spaceit("unable to find pack for", var.getName()));
        }

        /**
         * <code>
         * V1 [name=state, oldName=state]
         * V2 [name=state2, oldName=state]
         * V3 [name=state3, oldName=state2]
         * V3 [name=state4, oldName=state3]
         * </code>
         *
         * The original name of V1, V2 and V3 is state and old names are state3,
         * state2 and state.
         */
        String originalName = var.getDefinedName();
        List<String> oldNames = vars.getOldNames(var, heap);

        /*
         * Get packs whose exp refers a reassigned var. Filter either using
         * original name or if exp is already updated with another reassigned
         * name then filter on old names. Ex: If var is V2, packs whose exp is
         * state are filtered and exp is update to state2. If next var is V3
         * then no pack is matched for original name state as exp is already
         * updated to state2, so filter using the old name state2. In case V3 is
         * not in ctl path then next var would be V4 and again filtered using
         * old name state2.
         */
        List<Pack> referredPacks = heap.getPacks().stream().filter(p -> {
            Expression exp = p.getExp();
            if (nonNull(exp)) {
                exp = patcher.copyAndPatch(p, heap);
                String name = "";
                if (nodes.isName(exp)) {
                    name = nodes.getQualifiedName(exp);
                }
                return name.equals(originalName) || oldNames.contains(name);
            }
            return false;
        }).collect(Collectors.toList());

        for (Pack refPack : referredPacks) {
            /*
             * It is safe to create new node for new name as name is not
             * normally resolved (so far). In case name has to resolved in
             * future then add resolvable exp field in Pack to hold the original
             * name.
             */
            Name reassignedName = nodeFactory.createName(var.getName());
            refPack.setExp(reassignedName);
        }
    }
}
