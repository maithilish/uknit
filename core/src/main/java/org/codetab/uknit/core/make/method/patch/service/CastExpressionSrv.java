package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.ServiceLoader;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

public class CastExpressionSrv implements PatchService {

    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof CastExpression);
        checkState(copy instanceof CastExpression);

        CastExpression ce = (CastExpression) node;
        CastExpression ceCopy = (CastExpression) copy;

        Expression exp = wrappers.unpack(ce.getExpression());
        Expression expCopy = wrappers.unpack(ceCopy.getExpression());

        IVar expVar = patches.get(exp);

        // REVIEW - why no recursive
        if (nonNull(expVar)) {
            Name name = node.getAST().newName(expVar.getName());
            ceCopy.setExpression(name);
        }
        // else {
        // if (ce.getExpression() instanceof ParenthesizedExpression) {
        // PatchService patchService =
        // serviceLoader.loadService(ce.getExpression());
        // patchService.patch(ce.getExpression(), ceCopy.getExpression(),
        // patches);
        // }
        // }
    }
}
