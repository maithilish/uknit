package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;

public class SimpleNameSrv implements ExpService {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof SimpleName);
        return List.of(exp);
    }

    @Override
    public Expression getValue(final Expression node, final Pack pack,
            final Heap heap) {
        checkState(node instanceof SimpleName);
        // find var for the name and return its value
        Optional<Pack> varPackO =
                packs.findByVarName(nodes.getName(node), heap.getPacks());
        return packs.getExp(varPackO);
    }
}
