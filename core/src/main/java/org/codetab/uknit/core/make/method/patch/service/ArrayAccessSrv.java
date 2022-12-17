package org.codetab.uknit.core.make.method.patch.service;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;

public class ArrayAccessSrv implements PatchService {

    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;

    @Override
    public void patch(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches) {

        checkState(node instanceof ArrayAccess);
        checkState(copy instanceof ArrayAccess);

        ArrayAccess aa = (ArrayAccess) node;
        ArrayAccess aaCopy = (ArrayAccess) copy;

        Expression array = wrappers.unpack(aa.getArray());
        Expression arrayCopy = wrappers.unpack(aaCopy.getArray());
        patchers.patchExpWithName(array, arrayCopy, patches, aaCopy::setArray);

        Expression arrayIndex = wrappers.unpack(aa.getIndex());
        Expression arrayIndexCopy = wrappers.unpack(aaCopy.getIndex());
        patchers.patchExpWithName(arrayIndex, arrayIndexCopy, patches,
                aaCopy::setIndex);
    }
}
