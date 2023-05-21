package org.codetab.uknit.core.make.exp.srv;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Interface to interact with Expression.
 *
 * @author Maithilish
 *
 */
public interface ExpService {

    /**
     * An expression may contain other expressions. Returns list of such
     * expressions.
     *
     * @param node
     * @return
     */
    List<Expression> getExps(Expression node);

    /**
     * Get the value returned by the expression. The pack is node's pack which
     * is required get patched exp. It is not possible to get pack from node as
     * multi call IMC results in multiple packs for same node. Ex: im(String[]
     * array) {foo.append(array[0])}; the node is array[0]. Ref itest:
     * internal.ArrayAccess.argParamSame().
     *
     * @param node
     * @param heap
     * @return
     */
    Expression getValue(Expression node, Pack pack, Heap heap);
}
