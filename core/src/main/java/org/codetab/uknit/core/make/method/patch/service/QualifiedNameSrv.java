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
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;

public class QualifiedNameSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {

        checkState(node instanceof QualifiedName);
        checkState(copy instanceof QualifiedName);

        QualifiedName qn = (QualifiedName) node;
        QualifiedName qnCopy = (QualifiedName) copy;

        Expression qualifier = wrappers.unpack(qn.getQualifier());
        Expression qualifierCopy = wrappers.unpack(qnCopy.getQualifier());
        patchers.patchExpWithName(pack, qualifier, qualifierCopy, heap,
                n -> qnCopy.setQualifier((Name) n));

        Expression name = wrappers.unpack(qn.getName());
        Expression nameCopy = wrappers.unpack(qnCopy.getName());
        patchers.patchExpWithName(pack, name, nameCopy, heap,
                n -> qnCopy.setName((SimpleName) n));
    }

    @Override
    public void patchName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap) {
        checkState(node instanceof QualifiedName);
        checkState(copy instanceof QualifiedName);

        QualifiedName qn = (QualifiedName) node;
        QualifiedName qnCopy = (QualifiedName) copy;

        final List<Patch> patches = pack.getPatches();

        int index = 0;
        Expression qualifier = wrappers.unpack(qn.getQualifier());
        Expression qualifierCopy = wrappers.unpack(qnCopy.getQualifier());
        patchers.patchExpWithPackPatches(qualifier, qualifierCopy, patches,
                index, n -> qnCopy.setQualifier((Name) n));

        index = 1;
        Expression name = wrappers.unpack(qn.getName());
        Expression nameCopy = wrappers.unpack(qnCopy.getName());
        patchers.patchExpWithPackPatches(name, nameCopy, patches, index,
                n -> qnCopy.setName((SimpleName) n));
    }

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof QualifiedName);

        QualifiedName qn = (QualifiedName) exp;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(qn.getQualifier()));
        exps.add(wrappers.strip(qn.getName()));

        return exps;
    }
}
