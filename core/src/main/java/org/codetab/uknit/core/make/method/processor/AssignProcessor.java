package org.codetab.uknit.core.make.method.processor;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;

public class AssignProcessor {

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

        // LHS is var, do nothing.
        if (nodes.is(lhs, SimpleName.class)) {
            return;
        }

        /**
         * <code>array[0] = "foo"</code> results in two packs. Set LHS array[0]
         * to leftExp of foo pack and remove array[0] pack.
         */
        Optional<Pack> packO = packs.findByExp(heap.getPacks(), rhs);
        if (packO.isPresent()) {
            packO.get().setLeftExp(Optional.of(lhs));
            Optional<Pack> leftPackO = packs.findByExp(heap.getPacks(), lhs);
            if (leftPackO.isPresent()) {
                heap.getPacks().remove(leftPackO.get());
            }
        }
    }

}
