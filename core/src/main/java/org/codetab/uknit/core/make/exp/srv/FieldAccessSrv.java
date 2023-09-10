package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThisExpression;

/**
 * In JDT, (person).id is FieldAccess exp and person.id is QualifiedName exp.
 *
 * @author Maithilish
 *
 */
public class FieldAccessSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Initializers initializers;
    @Inject
    private Nodes nodes;
    @Inject
    private Rejigs rejigs;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof FieldAccess);

        FieldAccess fa = (FieldAccess) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(fa.getExpression()));
        exps.add(wrappers.strip(fa.getName()));

        return exps;
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof FieldAccess);
        FieldAccess copy = factory.copyExp((FieldAccess) node);

        Expression exp = wrappers.strip(copy.getExpression());
        exp = serviceLoader.loadService(exp).unparenthesize(exp);

        // The exp is fully striped, set it directly or to inner parenthesize
        if (copy.getExpression() instanceof ParenthesizedExpression) {
            if (exp instanceof ThisExpression) {
                /*
                 * No parenthesize for ThisExpression in FieldAccess. Ex:
                 * (this).foo; even though it is FieldAccess there is no need of
                 * parenthesize, so directly set the this.
                 */
                copy.setExpression(factory.copyExp(exp));
            } else {
                /*
                 * if not this exp then retain the inner parenthesise set the
                 * exp to parenthesise of field access. Ex: ((box)).name becomes
                 * (box).name.
                 */
                ParenthesizedExpression pExp =
                        (ParenthesizedExpression) copy.getExpression();
                pExp.setExpression(factory.copyExp(exp));
            }
        } else {
            // for non-parenthesized set directly
            copy.setExpression(factory.copyExp(exp));
        }

        // parenthesize is not allowed for name

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof FieldAccess);
        Expression value = initializers.getInitializerAsExpression(node,
                createValue, heap);
        FieldAccess fa = (FieldAccess) node;
        if (nodes.is(fa.getExpression(), ThisExpression.class)) {
            SimpleName name = fa.getName();
            ExpService srv = serviceLoader.loadService(name);
            value = srv.getValue(name, name, pack, createValue, heap);
        }
        return value;
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof FieldAccess);
        if (rejigs.needsRejig(node)) {
            T copy = factory.copyExp(node);
            FieldAccess wc = (FieldAccess) copy;
            /*
             * FA may be with or without parenthesize, retain parenthesize if
             * there is one.
             */
            if (wc.getExpression() instanceof ParenthesizedExpression) {
                ParenthesizedExpression pExp =
                        (ParenthesizedExpression) wc.getExpression();
                rejigs.rejigThisExp(pExp::getExpression, pExp::setExpression,
                        heap);
            } else {
                // replace any ref to this to CUT name
                rejigs.rejigThisExp(wc::getExpression, wc::setExpression, heap);
            }
            return copy;
        } else {
            return node;
        }
    }
}
