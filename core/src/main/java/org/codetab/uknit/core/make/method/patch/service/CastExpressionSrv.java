package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

public class CastExpressionSrv implements PatchService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private Packs packs;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof CastExpression);
        checkState(copy instanceof CastExpression);

        CastExpression ce = (CastExpression) node;
        CastExpression ceCopy = (CastExpression) copy;

        Expression exp = wrappers.unpack(ce.getExpression());

        Optional<Pack> patchPackO =
                packs.findPatchPack(pack, exp, heap.getPacks());

        if (patchPackO.isPresent()) {
            IVar expVar = patchPackO.get().getVar();
            /*
             * If exp of CastExpression directly resolves to an var then patch
             * it with name else do nothing. Ex: Bar bar = (Bar) foo.obj();
             *
             * If var is null don't recursively process the cast which results
             * in stack overflow.
             */
            if (nonNull(expVar)) {
                Name name = node.getAST().newName(expVar.getName());
                ceCopy.setExpression(name);
            }
        }
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof CastExpression);
        checkState(copy instanceof CastExpression);

        CastExpression ceCopy = (CastExpression) copy;

        final List<Patch> patches = pack.getPatches();

        if (!patches.isEmpty()) {
            IVar expVar = patches.get(0).getVar();

            if (nonNull(expVar)) {
                Name name = node.getAST().newName(expVar.getName());
                ceCopy.setExpression(name);
            }
        }
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof CastExpression);

        CastExpression ce = (CastExpression) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(ce.getExpression()));
        return exps;
    }
}
