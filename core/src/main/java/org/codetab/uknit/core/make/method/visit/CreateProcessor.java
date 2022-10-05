package org.codetab.uknit.core.make.method.visit;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.stage.VarExpStager;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

public class CreateProcessor {

    @Inject
    private VarExpStager varExpStager;
    @Inject
    private Nodes nodes;

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
