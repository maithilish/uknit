package org.codetab.uknit.core.make.method.body;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;

public class AssertStmt {

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Asserts asserts;
    @Inject
    private Packs packs;
    @Inject
    private Vars vars;

    public Optional<Statement> createStmt(final Heap heap) {
        Optional<Statement> stmt = Optional.empty();
        Optional<Pack> returnPackO =
                packs.findByVarName("return", heap.getPacks());
        Optional<IVar> expected =
                vars.getExpectedVar(returnPackO, heap.getPacks());
        if (expected.isPresent()) {
            Type retType = heap.getCall().getReturnType();
            String key = asserts.getAssertKey(retType, expected.get().isMock());
            String fmt = asserts.getAssertFormat(key, expected.get().getName());
            stmt = Optional.of(nodeFactory.createAssertStatement(fmt));
        }

        // FIXME Pack - remove this later
        // else {
        // // expected is not present then may be boolean
        // Type retType = heap.getCall().getReturnType();
        // boolean mock = true;
        // if (nonNull(retType) && types.isBoolean(retType)) {
        // String key = asserts.getAssertKey(retType, mock);
        // String fmt = asserts.getAssertFormat(key, "");
        // stmt = Optional.of(nodeFactory.createAssertStatement(fmt));
        // }
        // }

        stmt.ifPresent(s -> heap.setAsserted(true));
        return stmt;
    }

    /**
     * Create fail assertion when there is no assert or verify statement.
     * @param heap
     * @return
     */
    public Optional<Statement> createFailStmt(final Heap heap) {
        Optional<Statement> stmt = Optional.empty();
        if (stmt.isEmpty()) {
            String key = "fail";
            String fmt = asserts.getAssertFormat(key, "");
            stmt = Optional.of(nodeFactory.createAssertStatement(fmt));
        }
        return stmt;
    }

    @SuppressWarnings("unchecked")
    public void addStmt(final MethodDeclaration methodDecl,
            final Optional<Statement> stmt) {
        if (stmt.isPresent()) {
            methodDecl.getBody().statements().add(stmt.get());
        }
    }
}
