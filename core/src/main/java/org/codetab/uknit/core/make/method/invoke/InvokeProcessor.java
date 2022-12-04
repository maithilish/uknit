package org.codetab.uknit.core.make.method.invoke;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Methods;

public class InvokeProcessor {

    @Inject
    private Packs packs;
    @Inject
    private Invokes invokes;
    @Inject
    private Methods methods;

    /**
     * Set call var. The call var of MI is expression and its var is known only
     * after infer var and patch is created for it in visit post process.
     *
     * @param heap
     */
    public void process(final Heap heap) {

        // process all invokes where callVar is not yet set
        List<Invoke> invokeList = packs.filterInvokes(heap.getPacks()).stream()
                .filter(i -> i.getCallVar().isEmpty()
                        && methods.isInvokable(i.getExp()))
                .collect(Collectors.toList());

        for (Invoke invoke : invokeList) {
            invokes.setCallVar(invoke, heap);
        }
    }

}
