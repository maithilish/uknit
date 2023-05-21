package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;

public class ConditionalExpressionSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private ExpServiceLoader srvLoader;
    @Inject
    private Nodes nodes;
    @Inject
    private Patcher patcher;
    @Inject
    private Packs packs;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(ce.getExpression()));
        exps.add(wrappers.strip(ce.getThenExpression()));
        exps.add(wrappers.strip(ce.getElseExpression()));

        return exps;
    }

    @Override
    public Expression getValue(final Expression node, final Pack pack,
            final Heap heap) {
        checkState(node instanceof ConditionalExpression);

        ConditionalExpression ce = (ConditionalExpression) node;

        Expression exp = wrappers.strip(ce.getExpression());
        Expression thenExp = wrappers.strip(ce.getThenExpression());
        Expression elseExp = wrappers.strip(ce.getElseExpression());

        Optional<Pack> nodePack = packs.findByExp(node, heap.getPacks());
        if (packs.hasPatches(nodePack)) {
            ConditionalExpression patchedCe = (ConditionalExpression) patcher
                    .copyAndPatch(nodePack.get(), heap);
            exp = wrappers.strip(patchedCe.getExpression());
        }

        ExpService srv = srvLoader.loadService(exp);
        Expression expValue = srv.getValue(exp, pack, heap);

        Expression value;
        if (nodes.is(expValue, BooleanLiteral.class)) {
            if (((BooleanLiteral) expValue).booleanValue()) {
                value = thenExp;
            } else {
                value = elseExp;
            }
        } else {
            value = thenExp;
        }
        return value;
    }
}
