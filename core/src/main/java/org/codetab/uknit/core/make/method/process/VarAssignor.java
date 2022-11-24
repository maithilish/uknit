package org.codetab.uknit.core.make.method.process;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

public class VarAssignor {

    @Inject
    private Vars vars;
    @Inject
    private Nodes nodes;
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

    // no harm in assigning new name node as name is not, so far, resolved
    // in case required then add resolvable exp field in Pack

    /**
     * Reassigned var gets new name and update any assignment that uses it. Ex:
     * <code>
     * Object obj = foo.obj();
     * obj = new Locale("");
     * Object obj2 = obj;
     * Object obj3 = obj2;
     * </code>
     *
     * The obj is renamed as obj4 on reassignment in second stmt. Update third
     * stmt exp to obj4.
     *
     * Ref itest: linked.Created.isCreated2()
     * @param var
     * @param heap
     */
    public void updateAssigns(final IVar var, final Heap heap) {

        checkNotNull(var);
        checkNotNull(heap);

        List<Pack> referredPacks = heap.getPacks().stream().filter(p -> {
            Expression exp = p.getExp();
            return nonNull(exp) && nodes.isName(exp)
                    && nodes.getQualifiedName(exp).equals(var.getOldName());
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
