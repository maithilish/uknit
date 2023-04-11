package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;
import static org.codetab.uknit.core.node.Messages.noImpl;

import java.util.List;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;

public class NumberLiteralSrv implements PatchService {

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof NumberLiteral);
        checkState(copy instanceof NumberLiteral);
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof NumberLiteral);
        checkState(copy instanceof NumberLiteral);
        if (pack.getPatches().size() > 0) {
            String msg = noImpl("%s has patches, not implemented", node);
            throw new CodeException(msg);
        }
    }

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof NumberLiteral);
        return List.of(exp);
    }
}
