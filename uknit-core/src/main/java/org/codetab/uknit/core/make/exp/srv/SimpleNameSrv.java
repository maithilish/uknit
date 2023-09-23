package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;

public class SimpleNameSrv implements ExpService {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private NodeFactory factory;

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof SimpleName);
        return List.of(); // has no exps
    }

    @Override
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof SimpleName);
        return factory.copyNode(node);
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof SimpleName);

        /*
         * Find var for the name and return its exp if exists, else return the
         * name. N.B. There is no type check in finding the var pack and this
         * may result in wrong pack if name are similar. Ex: String...va is
         * renamed as va0 and infer var for va[0] is also va0.
         */
        Optional<Pack> varPackO =
                packs.findByVarName(nodes.getName(node), heap.getPacks());
        Expression varExp = packs.getExp(varPackO);
        if (nonNull(varExp)) {
            return varExp;
        } else {
            return node;
        }
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof SimpleName);
        return node;
    }
}
