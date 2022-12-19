package org.codetab.uknit.core.make.method.patch.service;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;

public interface PatchService {

    void patch(Pack pack, Expression node, Expression copy, Heap heap);

    List<Expression> getExps(Expression node);

    void patchName(Pack pack, Expression node, Expression copy);
}
