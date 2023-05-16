package org.codetab.uknit.core.make.exp.srv;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
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
     * Get the value returned by the expression.
     *
     * @param node
     * @param heap
     * @return
     */
    Expression getValue(Expression node, Heap heap);
}
