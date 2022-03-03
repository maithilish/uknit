package org.codetab.uknit.core.make.method.visit;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.Expression;

public class UseMarker {

    @Inject
    private UseMarkers useMarkers;

    public void mark(final Heap heap) {
        // useMarkers.markVarsUsedInWhens(heap);
        // useMarkers.markVarsUsedInReturn(heap);
        // useMarkers.markVarsUsedInVerify(heap);

        Set<String> names = useMarkers.getEnableNames(heap);
        useMarkers.enable(names, heap);

        List<Expression> exps = useMarkers.getInitializers(heap);
        useMarkers.getEnableNameForInitializers(exps, heap);

        useMarkers.forceEnable(heap);
    }

    public void enforce(final String name, final Heap heap) {
        IVar var = heap.findVar(name);
        var.setEnforce(Optional.of(true));
    }
}
