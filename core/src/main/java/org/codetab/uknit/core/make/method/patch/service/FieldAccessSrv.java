package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.SimpleName;

public class FieldAccessSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof FieldAccess);
        checkState(copy instanceof FieldAccess);

        FieldAccess fa = (FieldAccess) node;
        FieldAccess faCopy = (FieldAccess) copy;

        Expression exp = wrappers.unpack(fa.getExpression());
        Expression expCopy = wrappers.unpack(faCopy.getExpression());
        patchers.patchExpWithName(pack, exp, expCopy, heap,
                faCopy::setExpression);

        Expression name = wrappers.unpack(fa.getName());
        Expression nameCopy = wrappers.unpack(faCopy.getName());
        patchers.patchExpWithName(pack, name, nameCopy, heap,
                n -> faCopy.setName((SimpleName) n));
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy) {
        checkState(node instanceof FieldAccess);
        checkState(copy instanceof FieldAccess);

        FieldAccess fa = (FieldAccess) node;
        FieldAccess faCopy = (FieldAccess) copy;

        final List<Patch> patches = pack.getPatches();

        int index = 0;
        Expression exp = wrappers.unpack(fa.getExpression());
        Expression expCopy = wrappers.unpack(faCopy.getExpression());
        patchers.patchExpWithName(exp, expCopy, patches, index,
                faCopy::setExpression);

        index = 1;
        Expression name = wrappers.unpack(fa.getName());
        Expression nameCopy = wrappers.unpack(faCopy.getName());
        patchers.patchExpWithName(name, nameCopy, patches, index,
                n -> faCopy.setName((SimpleName) n));
    }

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof FieldAccess);

        FieldAccess fa = (FieldAccess) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(fa.getExpression()));
        exps.add(wrappers.strip(fa.getName()));

        return exps;
    }
}
