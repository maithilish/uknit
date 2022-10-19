package org.codetab.uknit.core.zap.make.method.visit;

import javax.inject.Inject;

import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.zap.make.method.stage.VarExpStager;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

public class CreateProcessor {

    @Inject
    private VarExpStager varExpStager;
    @Inject
    private Nodes nodes;

    /**
     * Stage expVar for ClassInstanceCreation.
     *
     * FIXME - explore whether we can create infer var for creation node.
     *
     * @param cic
     * @param heap
     */
    public void process(final ClassInstanceCreation cic, final Heap heap) {
        if (nodes.isAnonOrLambda(cic)) {
            return;
        }
        varExpStager.stage(null, cic, heap);
    }

    public void process(final ArrayCreation ac, final Heap heap) {
        varExpStager.stage(null, ac, heap);
    }
}
