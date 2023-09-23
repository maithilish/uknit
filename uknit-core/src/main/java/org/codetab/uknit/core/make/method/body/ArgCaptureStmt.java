package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.ArgCapture;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class ArgCaptureStmt {

    @Inject
    private Configs configs;
    @Inject
    private Types types;
    @Inject
    private Packs packs;
    @Inject
    private NodeFactory nodeFactory;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();
        String format =
                configs.getFormat("uknit.anonymous.class.capture.create");

        List<Verify> verifies = packs.filterInvokes(heap.getPacks()).stream()
                .filter(i -> i.getVerify().isPresent())
                .map(i -> i.getVerify().get()).collect(Collectors.toList());

        for (Verify verify : verifies) {
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
