package org.codetab.uknit.core.make.method.process;

import static java.util.Objects.isNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.SimpleName;

public class Assignor {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;

    /**
     *
     * LHS of assignment, apart from var, can be array access (array[0]), field
     * access ((point).x) or qualified name (point.x). Visitor creates single
     * pack with var and exp for var, but for others two packs (with var=null)
     * are created.
     *
     * This method sets LHS exp to leftExp of right pack and remove the left
     * pack.
     *
     * @param node
     * @param heap
     */
    public void process(final Assignment node, final Heap heap) {
        Expression lhs = node.getLeftHandSide();
        Expression rhs = node.getRightHandSide();

        /**
         * Some constructs results in two packs. Assign left pack to right pack
         * and remove left pack.
         */
        Optional<Pack> packO = packs.findByExp(rhs, heap.getPacks());
        if (packO.isPresent()) {
            Pack pack = packO.get();
            if (nodes.is(lhs, SimpleName.class)) {
                /*
                 * Date foo; foo = bar(); results in two packs, search for foo
                 * pack, assign its var to bar pack and remove bar pack.
                 */
                if (isNull(pack.getVar())) {
                    Optional<Pack> leftPackO = packs
                            .findByVarName(nodes.getName(lhs), heap.getPacks());
                    if (leftPackO.isPresent()) {
                        IVar leftVar = leftPackO.get().getVar();
                        pack.setVar(leftVar);
                        heap.removePack(leftPackO.get());
                    }
                }
            } else if (nodes.is(lhs, ArrayAccess.class, FieldAccess.class)) {
                /*
                 * array[0] = "foo", set LHS array[0] to leftExp of foo pack (
                 * it is exp and not var, so to leftExp) and remove array[0]
                 * pack.
                 */
                pack.setLeftExp(Optional.of(lhs));
                Optional<Pack> leftPackO =
                        packs.findByExp(lhs, heap.getPacks());
                if (leftPackO.isPresent()) {
                    heap.removePack(leftPackO.get());
                }
            } else {
                throw new CodeException(nodes.noImplmentationMessage(node));
            }
        }
    }

}
