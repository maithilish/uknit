package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Insert;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class InsertStmt {

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Configs configs;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();
        String insertFormat = configs.getFormat("uknit.format.insert");
        for (Insert insert : heap.getInserts()) {
            if (insert.isEnable()) {
                Statement stmt = nodeFactory.createInsertStmt(
                        insert.getConsumer(), insert.getCall(),
                        insert.getArgs(), insertFormat);
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
