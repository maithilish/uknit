package org.codetab.uknit.core.make.method;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.model.Call;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CallCreator {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Nodes nodes;

    /**
     * Stage method under test call.
     * @param methodDecl
     * @param heap
     * @return
     */
    public void createCall(final MethodDeclaration methodDecl,
            final Heap heap) {
        Type returnType = methodDecl.getReturnType2();
        SimpleName name = methodDecl.getName();
        ASTNode parent = methodDecl.getParent();
        if (nodes.is(parent, TypeDeclaration.class, EnumDeclaration.class)) {
            AbstractTypeDeclaration clz =
                    (AbstractTypeDeclaration) methodDecl.getParent();
            Call call =
                    modelFactory.createCall(clz, name, returnType, methodDecl);
            heap.setCall(call);
        } else {
            throw new CriticalException(nodes.exMessage(
                    "Expected Type or EnumDeclaration, but found", parent));
        }
    }
}
