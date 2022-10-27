package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class WhenStmt {

    @Inject
    private Configs configs;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Packs packs;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();
        String whenFormat = configs.getFormat("uknit.format.when");
        String returnFormat = configs.getFormat("uknit.format.when.return");
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        for (Invoke invoke : invokes) {
            if (invoke.getWhen().isPresent()) {
                When when = invoke.getWhen().get();
                Statement stmt = nodeFactory.createWhenStatement(
                        when.getMethodSignature(), when.getReturnVars(),
                        whenFormat, returnFormat);
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
