package org.codetab.uknit.core.make.method.body;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;

public class ReturnVarStmt {

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Types types;
    @Inject
    private Initializers initializers;

    public Optional<Statement> createStmt(final Heap heap) {
        Optional<IVar> returnVar = heap.getReturnVar();
        Statement stmt = null;
        if (returnVar.isPresent()) {
            IVar var = returnVar.get();
            if (var.isUsed() && !var.isHidden()) {
                String initializer = initializers.getInitializer(var, heap);
                Type type = var.getType();
                String typeLiteral;
                if (type.isParameterizedType()) {
                    typeLiteral = types.getParameterizedTypeName(
                            (ParameterizedType) var.getType());
                } else {
                    typeLiteral = types.getTypeName(var.getType());
                }
                stmt = nodeFactory.createVarDeclStmt(var.getName(), typeLiteral,
                        initializer);
            }
        }
        return Optional.ofNullable(stmt);
    }

    @SuppressWarnings("unchecked")
    public void addStmt(final MethodDeclaration methodDecl,
            final Optional<Statement> stmt) {
        if (stmt.isPresent()) {
            methodDecl.getBody().statements().add(stmt.get());
        }
    }
}
