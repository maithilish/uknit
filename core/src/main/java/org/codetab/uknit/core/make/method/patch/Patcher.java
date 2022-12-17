package org.codetab.uknit.core.make.method.patch;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

public class Patcher {

    @Inject
    private ServiceLoader serviceLoader;

    private Map<Expression, IVar> patches;

    /**
     *
     * REVIEW service rules - remove wrapper cast and parenthesized exp with
     * Wrappers.unpack() method. Don't unpack the list of exps such as
     * MI.arguments() etc., as we need the list returned by the copy to patch.
     * The unpack on list creates a new list from the original list and any
     * patch applied on new list will not reflect in the node copy. For such
     * lists, unpack each exp individually in the for loop.
     *
     * @param pack
     * @param heap
     * @return
     */
    public Expression copyAndPatch(final Pack pack, final Heap heap) {
        checkNotNull(pack.getExp());

        /*
         * node is packs exp and exps is exps of the node. Ex: For Pack exp
         * foo.opt(bar) the node is foo.opt(bar) and exps are foo and bar. The
         * opt is just a name.
         */
        Expression node = pack.getExp();
        Expression nodeCopy =
                (Expression) ASTNode.copySubtree(node.getAST(), node);

        PatchService patchService = serviceLoader.loadService(node);
        patchService.patch(node, nodeCopy, patches);

        return nodeCopy;
    }

    public void setup() {
        patches = new HashMap<>();
    }

    public void addPatch(final Expression exp, final IVar var) {
        patches.put(exp, var);
    }
}
