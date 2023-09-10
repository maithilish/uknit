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
     * An expression and its expressions may have parenthesized. This method
     * removes all including inner parenthesises from the copy of expression and
     * returns the copy.
     *
     * Ex: for exp (cities)[(((foo).index(((flag) ? ("foo") : (("bar"))))))]
     * returns cities[foo.index(flag ? "foo" : "bar")]
     *
     * Note: It returns the copy of the original without modifying the original.
     *
     * @param node
     * @return
     */
    Expression unparenthesize(Expression node);

    /**
     * Get the value returned by the expression. The pack is node's pack which
     * is required get patched exp. It is not possible to get pack from node as
     * multi call IMC results in multiple packs for same node. Ex: im(String[]
     * array) {foo.append(array[0])}; the node is array[0]. Ref itest:
     * internal.ArrayAccess.argParamSame().
     *
     * If createValue is false then returns value if initializer is set in IVar
     * and if not set then in some cases returns node itself as it may be used
     * as initializer otherwise returns null. If createValue is true then it
     * returns same as above but instead of returning null it tries to create
     * value for primitives such as int and boolean otherwise returns null. Ex:
     * Ref itest: exp.value.ArrayAccess.
     *
     * @param node
     * @param copy
     * @param createValue
     * @param heap
     * @return
     */
    Expression getValue(Expression node, Expression copy, Pack pack,
            boolean createValue, Heap heap);

    <T extends Expression> T rejig(T node, Heap heap);
}
