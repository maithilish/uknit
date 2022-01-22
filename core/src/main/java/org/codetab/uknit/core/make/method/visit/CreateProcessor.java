package org.codetab.uknit.core.make.method.visit;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.stage.VarExpStager;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;

public class CreateProcessor {

    @Inject
    private VarExpStager varExpStager;
    @Inject
    private Patcher patcher;
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

    public void stagePatches(final ClassInstanceCreation cic, final Heap heap) {
        @SuppressWarnings("unchecked")
        List<Expression> exps = new ArrayList<>(cic.arguments());
        patcher.stageInferPatch(cic, exps, heap);
    }

    public void stagePatches(final ArrayCreation ac, final Heap heap) {
        @SuppressWarnings("unchecked")
        List<Expression> exps = new ArrayList<>(ac.dimensions());
        patcher.stageInferPatch(ac, exps, heap);
    }
}
