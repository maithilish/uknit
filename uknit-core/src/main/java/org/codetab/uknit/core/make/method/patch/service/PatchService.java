package org.codetab.uknit.core.make.method.patch.service;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Interface to patch various types nodes. See notes/designnotes.md for details.
 *
 * @author Maithilish
 *
 */
public interface PatchService {

    /**
     * Patch invoke expressions in an expression.
     *
     * Invoke exps in any exp (including another invoke exp) is replaced with
     * corresponding var name. No separate patch is created for this, instead
     * the pack list in heap is searched for the invoke pack and its var name is
     * used to patch the invoke in exp.
     *
     * Ex: (Foo) getObj(); The CastExpression contains an MethodInvocation
     * (Invoke) and if invoke returns apple then patched exp is (Foo) apple;
     *
     * @param pack
     * @param node
     * @param copy
     * @param heap
     */
    void patch(Pack pack, Expression node, Expression copy, Heap heap);

    /**
     * Applies pack level patch.
     *
     * A var may be renamed in case of name conflict and all depending exp are
     * patched with new name. It is not possible to search for relevant var such
     * renames instead pack level Patch is created and added to pack's patch
     * list.
     *
     * @param pack
     * @param node
     * @param copy
     * @param heap
     */
    void patchName(Pack pack, Expression node, Expression copy, Heap heap);

    /**
     * An expression may contain other expressions. Returns list of such
     * expressions.
     *
     * @param node
     * @return
     */
    List<Expression> getExps(Expression node);

    /**
     * Patch var value from initializer (if exists).
     *
     * N.B Works only on already patched exp as input.
     *
     * @param node
     * @param copy
     * @param heap
     */
    void patchValue(Expression node, Expression copy, Heap heap);
}
