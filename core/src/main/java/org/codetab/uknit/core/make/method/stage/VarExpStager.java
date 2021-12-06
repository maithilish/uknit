package org.codetab.uknit.core.make.method.stage;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.eclipse.jdt.core.dom.Expression;

public class VarExpStager {

    @Inject
    private ModelFactory modelFactory;

    public ExpVar stage(final Expression lExp, final Expression rExp,
            final Heap heap) {
        checkNotNull(heap);

        ExpVar expVar = modelFactory.createVarExp(lExp, rExp);
        heap.getVarExps().add(expVar);

        return expVar;
    }
}
