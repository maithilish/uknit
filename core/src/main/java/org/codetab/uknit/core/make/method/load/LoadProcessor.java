package org.codetab.uknit.core.make.method.load;

import java.util.List;
import java.util.stream.Collectors;

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
        /*
         * Filter collections that are created in MUT as CUT is responsible to
         * load these collections. Test can only consume its items. Ref itest:
         * load.SuperGet class.
         */
        vars = vars.stream().filter(v -> !v.isCreated())
                .collect(Collectors.toList());
        loader.processLoadableVars(vars, heap);
        loader.enableLoads(heap);
    }
}
