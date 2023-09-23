package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ThisExpression;

/**
 * Regig expressions before the final output. Used by classes of
 * org.codetab.uknit.core.make.method.body package such as VerifyStmt, WhenStmt
 * etc., to modify expressions just before the creation of the output stmts.
 *
 * @author Maithilish
 *
 */
public class Rejigs {

    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader srvLoader;

    /**
     * Recursively scan exp and return true if it has any ThisExpression.
     *
     * @param node
     * @return
     */
    public boolean needsRejig(final Expression node) {
        ExpService srv = srvLoader.loadService(node);
        List<Expression> exps = srv.getExps(node);

        boolean needs = false;
        for (Expression exp : exps) {
            if (exp instanceof ThisExpression) {
                needs = true;
            } else if (nonNull(exp)) {
                if (needsRejig(exp)) {
                    needs = true;
                }
            }
        }
        return needs;
    }

    /**
     * Replace this exp with CUT name.
     *
     * @param getOper
     *            should supply non null exp
     * @param setOper
     * @param heap
     * @return
     */
    public boolean rejigThisExp(final Supplier<Expression> getOper,
            final Consumer<Expression> setOper, final Heap heap) {
        boolean status = false;
        Expression exp = getOper.get();
        checkNotNull(exp);

        if (exp instanceof ThisExpression) {
            Name cutName = factory.createName(heap.getCutName());
            setOper.accept(cutName);
            status = true;
        } else {
            ExpService srv = srvLoader.loadService(exp);
            Expression rExp = srv.rejig(exp, heap);
            if (!exp.equals(rExp)) {
                setOper.accept(rExp);
            }
        }
        return status;
    }

    /**
     * Replace any this exp in the exps list with CUT name. Used for list of
     * exps such as Mi.arguments() etc.,
     *
     * @param exps
     * @param heap
     * @return
     */
    public boolean rejigThisExp(final List<Expression> exps, final Heap heap) {
        boolean status = false;
        for (int i = 0; i < exps.size(); i++) {
            Expression exp = exps.get(i);
            // exp should be not null
            checkNotNull(exp);

            if (exp instanceof ThisExpression) {
                Name cutName = factory.createName(heap.getCutName());
                exps.set(i, cutName);
                status = true;
            } else {
                ExpService srv = srvLoader.loadService(exp);
                Expression rExp = srv.rejig(exp, heap);
                if (!exp.equals(rExp)) {
                    exps.set(i, rExp);
                    status = true;
                }
            }
        }
        return status;
    }
}
