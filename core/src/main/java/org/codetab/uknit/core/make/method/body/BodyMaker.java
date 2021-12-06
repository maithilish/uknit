package org.codetab.uknit.core.make.method.body;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class BodyMaker {

    @Inject
    private VarStmt varStmt;
    @Inject
    private ReturnVarStmt returnVarStmt;
    @Inject
    private VerifyStmt verifyStmt;
    @Inject
    private AssertStmt assertStmt;
    @Inject
    private CallStmt callStmt;
    @Inject
    private WhenStmt whenStmt;

    public void generateVarStmts(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = varStmt.createStmts(heap);
        varStmt.addStmts(methodDecl, stmts);
    }

    public void generateWhenStmts(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = whenStmt.createStmts(heap);
        whenStmt.addStmts(methodDecl, stmts);
    }

    /**
     * Generates call statement to invoke method under test.
     * @param methodDecl
     * @param heap
     */
    public void generateCallStmt(final MethodDeclaration methodDecl,
            final Heap heap) {
        Statement stmt = callStmt.createStmts(heap);
        callStmt.addStmt(methodDecl, stmt);
    }

    public void generateAssertStmt(final MethodDeclaration methodDecl,
            final Heap heap) {
        Optional<Statement> stmt = assertStmt.createStmt(heap);
        assertStmt.addStmt(methodDecl, stmt);

    }

    public void generateReturnVarStmt(final MethodDeclaration methodDecl,
            final Heap heap) {
        Optional<Statement> stmt = returnVarStmt.createStmt(heap);
        returnVarStmt.addStmt(methodDecl, stmt);
    }

    public void generateVerifyStmts(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = verifyStmt.createStmts(heap);
        verifyStmt.addStmts(methodDecl, stmts);
    }
}
