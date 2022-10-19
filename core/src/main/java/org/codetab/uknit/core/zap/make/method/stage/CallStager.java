package org.codetab.uknit.core.zap.make.method.stage;

import javax.inject.Inject;

import org.codetab.uknit.core.zap.make.model.Call;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.codetab.uknit.core.zap.make.model.ModelFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CallStager {

    @Inject
    private ModelFactory modelFactory;

    /**
     * Stage method under test call.
     * @param methodDecl
     * @param heap
     */
    public void stageCall(final MethodDeclaration methodDecl, final Heap heap) {
        Type returnType = methodDecl.getReturnType2();
        SimpleName name = methodDecl.getName();
        TypeDeclaration clz = (TypeDeclaration) methodDecl.getParent();

        Call call = modelFactory.createCall(clz, name, returnType, methodDecl);
        heap.setCall(call);
    }
}
