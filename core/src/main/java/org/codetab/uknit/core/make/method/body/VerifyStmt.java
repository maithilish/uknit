package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class VerifyStmt {

    @Inject
    private Configs configs;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Packs packs;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();
        String verifyFormat = configs.getFormat("uknit.format.verify");
        String verifyNeverFormat =
                configs.getFormat("uknit.format.verifyNever");

        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        for (Invoke invoke : invokes) {
            if (invoke.getVerify().isPresent()) {

                Verify verify = invoke.getVerify().get();

                String format = verifyNeverFormat;
                if (verify.isInCtlFlowPath()) {
                    format = verifyFormat;
                }

                Statement stmt = nodeFactory
                        .createVerifyStatement(verify.getMi(), format);
                stmts.add(stmt);

                // as verify stmt is created, assertFail is not required
                heap.setAsserted(true);
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
