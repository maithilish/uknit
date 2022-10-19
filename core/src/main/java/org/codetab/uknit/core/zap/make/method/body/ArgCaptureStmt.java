package org.codetab.uknit.core.zap.make.method.body;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.node.ArgCapture;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.codetab.uknit.core.zap.make.model.Verify;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class ArgCaptureStmt {

    @Inject
    private Configs configs;
    @Inject
    private Types types;
    @Inject
    private NodeFactory nodeFactory;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();
        String format =
                configs.getFormat("uknit.anonymous.class.capture.create");
        for (Verify verify : heap.getVerifies()) {
            for (ArgCapture argCapture : verify.getArgCaptures()) {
                String parameterizedType =
                        types.getTypeName(argCapture.getType(), true);
                String type = types.getTypeName(argCapture.getType());
                String varName = argCapture.getName();
                Statement stmt = nodeFactory.createCaptureStatement(varName,
                        parameterizedType, type, format);
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
