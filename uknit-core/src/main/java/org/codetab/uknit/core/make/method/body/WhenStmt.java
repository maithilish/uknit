package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<When> whens = new ArrayList<>();
        /*
         * Returns of When such as when(foo).thenReturn(bar).thenReturn(baz);
         * are held by multiple Invoke Packs. Collect return vars of Whens with
         * same method signature in the first When ignoring others
         */
        for (Invoke invoke : invokes) {

            if (invoke.getWhen().isPresent()
                    && invoke.getWhen().get().isEnable()) {
                When when = invoke.getWhen().get();
                Optional<When> sameWhenO =
                        whens.stream()
                                .filter(w -> w.getMethodSignature()
                                        .equals(when.getMethodSignature()))
                                .findFirst();
                /*
                 * collect return var in the first When, ignoring the When
                 * itself
                 */
                if (sameWhenO.isPresent()) {
                    sameWhenO.get().getReturnVars()
                            .addAll(when.getReturnVars());
                } else {
                    whens.add(when);
                }
            }
        }
        // create stmts from DeDuplicated whens
        for (When when : whens) {
            Statement stmt =
                    nodeFactory.createWhenStatement(when.getMethodSignature(),
                            when.getReturnVars(), whenFormat, returnFormat);
            stmts.add(stmt);
        }
        return stmts;
    }

    @SuppressWarnings("unchecked")
    public void addStmts(final MethodDeclaration methodDecl,
            final List<Statement> stmts) {
        methodDecl.getBody().statements().addAll(stmts);
    }
}
