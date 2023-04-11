package org.codetab.uknit.core.make.method.patch;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.codetab.uknit.core.make.method.var.linked.LinkedPack;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class ValuePatcher {

    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private LinkedPack linkedPack;

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
        List<Expression> exps = patchService.getExps(mi);
        for (int i = 0; i < exps.size(); i++) {
            Expression exp = exps.get(i);
            if (nodes.isName(exp)) {
                Optional<Pack> packO = packs.findByVarName(nodes.getName(exp),
                        heap.getPacks());
                if (packO.isPresent()) {
                    /*
                     * reverse traverse linked pack and get the first available
                     * initializer.
                     */
                    Optional<Initializer> initializer =
                            linkedPack.getLinkedInitializer(packO.get(), heap);

                    /*
                     * if initializer is expression then patch the MI's exp and
                     * args
                     */
                    if (initializer.isPresent() && initializer.get()
                            .getInitializer() instanceof Expression) {
                        Expression iniExp =
                                (Expression) initializer.get().getInitializer();
                        Expression iniExpCopy = (Expression) ASTNode
                                .copySubtree(iniExp.getAST(), iniExp);
                        if (i == 0) {
                            copy.setExpression(iniExpCopy);
                        } else {
                            @SuppressWarnings("unchecked")
                            List<Expression> args = copy.arguments();
                            args.set(i - 1, iniExpCopy);
                        }
                    }
                }
            }
        }
        return copy;
    }
}
