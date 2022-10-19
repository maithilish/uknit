package org.codetab.uknit.core.zap.make.method.visit;

import javax.inject.Inject;

import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.zap.make.method.stage.VarExpStager;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.StringLiteral;

public class LiteralProcessor {

    @Inject
    private Nodes nodes;
    @Inject
    private VarExpStager varExpStager;

    /**
     * To avoid clutter in varExp list, stage required VarExp
     * <p>
     * Ex: foo = "bar"; we need varExp here to reassign.
     * @param sl
     * @param heap
     */
    public void process(final StringLiteral sl, final Heap heap) {
        if (nodes.is(sl.getParent(), Assignment.class)) {
            varExpStager.stage(null, sl, heap);
        }
        // if required stage for other node types
    }
}
