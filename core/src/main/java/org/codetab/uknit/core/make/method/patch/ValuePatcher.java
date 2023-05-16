package org.codetab.uknit.core.make.method.patch;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.codetab.uknit.core.make.model.Heap;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class ValuePatcher {

    @Inject
    private ServiceLoader serviceLoader;

    /**
     * Implemented for MI and if required for other types then add method in
     * PatchService and implement in all services as done in copyAndPatch(). Ex:
     * apple = "foo"; foo.append(apple); then MI copy becomes foo.append("foo").
     *
     * The value patcher is used only to aggregate multiple verifies with
     * times() in make.method.body.VerifyStmt class.
     *
     * @param mi
     * @param heap
     * @return
     */
    public MethodInvocation patchValues(final MethodInvocation mi,
            final Heap heap) {
        MethodInvocation copy =
                (MethodInvocation) ASTNode.copySubtree(mi.getAST(), mi);

        PatchService patchService = serviceLoader.loadService(mi);
        patchService.patchValue(mi, copy, heap);

        return copy;
    }
}
