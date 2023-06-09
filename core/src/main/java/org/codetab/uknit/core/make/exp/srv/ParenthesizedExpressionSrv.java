package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.SafeExps;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;

public class ParenthesizedExpressionSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Nodes nodes;
    @Inject
    private SafeExps safeExps;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ParenthesizedExpression);

        ParenthesizedExpression pe = (ParenthesizedExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(pe.getExpression()));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ParenthesizedExpression);

        ParenthesizedExpression copy =
                (ParenthesizedExpression) factory.copyNode(node);

        Expression exp = wrappers.strip(copy.getExpression());
        exp = serviceLoader.loadService(exp).unparenthesize(exp);
        copy.setExpression(factory.copyNode(exp));

        if (canRemove(node, exp)) {
            // return inner exp
            return copy.getExpression();
        } else {
            // parenthesized exp
            return copy;
        }
    }

    /**
     * It is not allowed to remove parenthesises in all cases. For example, we
     * can't remove the parenthesise around (Foo) foo in the exp ((Foo)
     * foo).index() which is mandatory to make the call index() valid. However,
     * if index() call is not there in the exp then we can remove.
     *
     * @param node
     * @param innerExp
     * @return
     */
    private boolean canRemove(final Expression node,
            final Expression innerExp) {
        ASTNode parent = wrappers.stripAndGetParent(node);
        if (nodes.is(innerExp, CastExpression.class)) {
            if (nodes.is(parent, MethodInvocation.class)) {
                /**
                 * if node is in parent MI arg list then remove parenthesise
                 * else node is exp of parent MI then don't remove. <code>
                 * x.append((((Foo) foo)).index());
                 *      don't remove, parent MI is ((Foo) foo)).index() and ((Foo) foo) is its exp
                 * x.append(((String[]) (new Object[]...)))
                 *      remove, parent MI is x.append(...) and ((String[])... is its arg
                 * </code>
                 */
                List<Expression> args =
                        safeExps.getArgs((MethodInvocation) parent);
                return args.contains(node);
            }
        }
        return true;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof ParenthesizedExpression);

        // exp of ParenthesizedExpression
        Expression exp = ((ParenthesizedExpression) node).getExpression();
        Expression expCopy = ((ParenthesizedExpression) copy).getExpression();

        ExpService srv = serviceLoader.loadService(exp);
        return srv.getValue(exp, expCopy, pack, createValue, heap);
    }
}
