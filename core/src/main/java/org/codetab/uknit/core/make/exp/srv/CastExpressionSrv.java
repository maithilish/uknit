package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

public class CastExpressionSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof CastExpression);

        CastExpression ce = (CastExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(ce.getExpression()));
        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof CastExpression);
        CastExpression copy = (CastExpression) factory.copyNode(node);

        Expression exp = wrappers.strip(copy.getExpression());

        exp = serviceLoader.loadService(exp).unparenthesize(exp);
        safelySetExp(copy, exp);

        return copy;
    }

    private void safelySetExp(final CastExpression copy, final Expression exp) {
        /*
         * for Postfix in (Integer) a++ parenthesise is not essential but for
         * prefix it is required. For (Integer) (--a) retain parenthesise by
         * setting the exp to inner parenthesise.
         *
         * If exp of cast exp is parenthesized cast exp then retain
         * parenthesize. Ex: foo.appendPitbull((Pitbull) ((Pet) dog)); in
         * exp.value.Cast.expIsCastExp()
         */
        if (copy.getExpression() instanceof ParenthesizedExpression
                && (exp instanceof PrefixExpression
                        || exp instanceof CastExpression)) {
            ParenthesizedExpression pExp =
                    (ParenthesizedExpression) copy.getExpression();
            pExp.setExpression(factory.copyNode(exp));
        } else {
            copy.setExpression(factory.copyNode(exp));
        }
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof CastExpression);

        // exp of CastExpression
        Expression exp = ((CastExpression) node).getExpression();
        Expression expCopy = ((CastExpression) copy).getExpression();

        ExpService srv = serviceLoader.loadService(exp);
        return srv.getValue(exp, expCopy, pack, createValue, heap);
    }
}
