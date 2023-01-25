package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.isNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;

public class Assignor {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private Wrappers wrappers;

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
     * Bare assignment such as foo = factory.foo() happens here. The
     * VariableDeclarationStatement var definition and assignment happens in
     * Packer.packVars(), not here. Ex: Foo foo = factory.foo().
     *
     * @param node
     * @param heap
     */
    public void process(final Assignment node, final Heap heap) {
        Expression lhs = wrappers.strip(node.getLeftHandSide());
        Expression rhs = wrappers.strip(node.getRightHandSide());

        /**
         * Some constructs results in two packs. Assign left pack to right pack
         * and remove left pack.
         */
        Optional<Pack> packO = packs.findByExp(rhs, heap.getPacks());
        if (packO.isPresent()) {
            Pack pack = packO.get();
            if (nodes.is(lhs, SimpleName.class)) {
                if (isNull(pack.getVar())) {
                    Optional<Pack> varPackO = packs
                            .findByVarName(nodes.getName(lhs), heap.getPacks());
                    if (varPackO.isPresent()) {
                        Expression exp = varPackO.get().getExp();
                        if (isNull(exp) || exp instanceof NullLiteral) {
                            /*
                             * Var definition and assign are done in different
                             * stmts. Ex: Date foo; foo = bar(); results in two
                             * packs, search for foo pack, assign its var to bar
                             * pack and remove bar pack. Another example, Locale
                             * locale = foo.locale(); locale = new Locale(); the
                             * first pack is removed once second pack is
                             * assigned.
                             */
                            IVar leftVar = varPackO.get().getVar();
                            pack.setVar(leftVar);
                            heap.removePack(varPackO.get());
                        } else {
                            /*
                             * Var is defined and value assigned to var and then
                             * var is reassigned with new value. Create new var
                             * for the pack so that both old and new value gets
                             * their own packs. Ex: int i = 5; foo.get(i); i =
                             * 7; foo.get(i); two packs are created for first
                             * and third stmts Pack [var=i, exp=5] and Pack
                             * [var:null, exp=7]. Create new var and assign it
                             * the second pack. The new var is clone of var i
                             * with name changed as i-reassigned and the name
                             * will be changed to i2 by
                             * Processor.processVarReassign(). Illegal token
                             * dash in the name ensures that it doesn't clash
                             * with any legal var in MUT.
                             */
                            IVar newVar = varPackO.get().getVar().clone();
                            String newVarName =
                                    newVar.getName() + "-reassigned";
                            newVar.setName(newVarName);
                            pack.setVar(newVar);
                        }
                    }
                }
            } else if (nodes.is(lhs, ArrayAccess.class, FieldAccess.class,
                    QualifiedName.class)) {
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
