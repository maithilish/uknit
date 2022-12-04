package org.codetab.uknit.core.make.method.insert;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;

public class InsertProcessor {

    @Inject
    private Inserter inserter;

    public void process(final Heap heap) {
        inserter.getEnhancedForNodes()
                .forEach(node -> inserter.processEnhancedFor(node, heap));
        List<IVar> vars = inserter.filterInsertableVars(heap.getVars());
        inserter.processInsertableVars(vars, heap);
        inserter.enableInserts(heap);
    }
}
