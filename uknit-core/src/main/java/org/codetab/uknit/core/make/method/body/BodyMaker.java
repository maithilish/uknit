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
    private LoadStmt loadStmt;
    @Inject
    private AssertStmt assertStmt;
    @Inject
    private CallStmt callStmt;
    @Inject
    private WhenStmt whenStmt;
    @Inject
    private VerifyStmt verifyStmt;
    @Inject
    private ArgCaptureStmt argCaptureStmt;
    @Inject
    private ReturnVarStmt returnVarStmt;

    public void generateVarStmts(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = varStmt.createStmts(heap);
        varStmt.addStmts(methodDecl, stmts);
    }

    public void generateLoads(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = loadStmt.createStmts(heap);
        varStmt.addStmts(methodDecl, stmts);
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

    public void generateFailAssertStmt(final MethodDeclaration methodDecl,
            final Heap heap) {
        // when no assert or verify statement exists, generate fail assertion
        if (!heap.isAsserted()) {
            Optional<Statement> stmt = assertStmt.createFailStmt(heap);
            assertStmt.addStmt(methodDecl, stmt);
        }
    }

    public void generateWhenStmts(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = whenStmt.createStmts(heap);
        whenStmt.addStmts(methodDecl, stmts);
    }

    public void generateVerifyStmts(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = verifyStmt.createStmts(heap);
        verifyStmt.addStmts(methodDecl, stmts);
    }

    public void generateArgCaptureStmts(final MethodDeclaration methodDecl,
            final Heap heap) {
        List<Statement> stmts = argCaptureStmt.createStmts(heap);
        argCaptureStmt.addStmts(methodDecl, stmts);
    }

    public void generateReturnVarStmt(final MethodDeclaration methodDecl,
            final Heap heap) {
        Optional<Statement> stmt = returnVarStmt.createStmt(heap);
        returnVarStmt.addStmt(methodDecl, stmt);
    }

}
