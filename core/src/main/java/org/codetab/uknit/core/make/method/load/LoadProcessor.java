package org.codetab.uknit.core.make.method.load;

import java.util.List;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;

public class LoadProcessor {

    public void processEnhancedFor(final Heap heap) {
        Loader loader = heap.getLoader();
        loader.getEnhancedForNodes()
                .forEach(node -> loader.processEnhancedFor(node, heap));
    }

    public void processLoadableVars(final Heap heap) {
        Loader loader = heap.getLoader();
        List<IVar> vars = loader.filterLoadableVars(heap.getVars());
        loader.processLoadableVars(vars, heap);
        loader.enableLoads(heap);
    }
}
