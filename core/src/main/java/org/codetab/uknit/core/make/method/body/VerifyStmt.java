package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class VerifyStmt {

    @Inject
    private Configs configs;
    @Inject
    private NodeFactory nodeFactory;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();

        for (Verify verify : heap.getVerifies()) {
            String format;
            if (verify.isInCtlFlowPath()) {
                format = configs.getFormat("uknit.format.verify");
            } else {
                format = configs.getFormat("uknit.format.verifyNever");
            }

            Statement stmt =
                    nodeFactory.createVerifyStatement(verify.getMi(), format);
            stmts.add(stmt);
        }
        if (!heap.getVerifies().isEmpty()) {
            heap.setAsserted(true);
        }
        return stmts;
    }

    @SuppressWarnings("unchecked")
    public void addStmts(final MethodDeclaration methodDecl,
            final List<Statement> stmts) {
        methodDecl.getBody().statements().addAll(stmts);
    }
}
