package org.codetab.uknit.core.make.method.patch.service;

import java.util.Map;

import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.Expression;

public interface PatchService {

    void patch(Expression node, Expression copy, Map<Expression, IVar> patches);
}
