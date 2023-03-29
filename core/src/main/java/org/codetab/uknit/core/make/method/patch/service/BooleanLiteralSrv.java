package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;
import static org.codetab.uknit.core.node.Messages.noImpl;

import java.util.List;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Expression;

public class BooleanLiteralSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof BooleanLiteral);
        checkState(copy instanceof BooleanLiteral);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {
        checkState(node instanceof BooleanLiteral);
        checkState(copy instanceof BooleanLiteral);
        if (pack.getPatches().size() > 0) {
            String msg = noImpl("%s has patches, not implemented", node);
            throw new CodeException(msg);
        }
    }

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof BooleanLiteral);
        return List.of(exp);
    }
}
