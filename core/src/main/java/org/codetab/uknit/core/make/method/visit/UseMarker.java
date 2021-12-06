package org.codetab.uknit.core.make.method.visit;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.eclipse.jdt.core.dom.Expression;

public class UseMarker {

    @Inject
    private UseMarkers useMarkers;

    public void mark(final Heap heap) {
        useMarkers.markVarsUsedInWhens(heap);
        useMarkers.markVarsUsedInReturn(heap);
        List<Expression> exps = useMarkers.getInitializers(heap);
        useMarkers.markExpVars(exps, heap);
    }
}
