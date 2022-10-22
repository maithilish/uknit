package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Heaps;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;

public class VarStmt {

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Types types;
    @Inject
    private Heaps heaps;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();
        List<IVar> vars = heaps.getVarsOfKind(heap, Kind.PARAMETER, Kind.INFER,
                Kind.LOCAL);
        for (IVar var : vars) {
            boolean createStmt = false;
            if (var.is(Kind.PARAMETER)) {
                createStmt = true;
            } else if (var.is(Kind.INFER) && var.isEnable()) {
                createStmt = true;
            } else if (var.is(Kind.LOCAL) && var.isEnable()) {
                createStmt = true;
            }
            if (createStmt) {
                // String initializer = initializers.getInitializer(var, heap);
                String initializer = "\"fix me\"";
                Type type = var.getType();
                String typeLiteral;
                if (type.isParameterizedType()) {
                    typeLiteral = types.getParameterizedTypeName(
                            (ParameterizedType) var.getType());
                } else {
                    typeLiteral = types.getTypeName(var.getType());
                }
                Statement stmt = nodeFactory.createVarDeclStmt(var.getName(),
                        typeLiteral, initializer);
                stmts.add(stmt);
            }
        }

        return stmts;
    }

    @SuppressWarnings("unchecked")
    public void addStmts(final MethodDeclaration methodDecl,
            final List<Statement> stmts) {
        methodDecl.getBody().statements().addAll(stmts);
    }
}
