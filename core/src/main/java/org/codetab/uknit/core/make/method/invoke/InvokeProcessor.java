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
     * Set invoke call var. The MI expression is the call var and it is known
     * only after infer var and patch is created in visit post process.
     *
     * The Packer.setupInvokes() sets call var if it is known and IM merger also
     * tries to set it but they are overridden and again set here.
     *
     * @param heap
     */
    public void process(final Heap heap) {

        List<Invoke> invokeList = packs.filterInvokes(heap.getPacks()).stream()
                .filter(i -> methods.isInvokable(i.getExp()))
                .collect(Collectors.toList());

        for (Invoke invoke : invokeList) {
            invokes.setCallVar(invoke, heap);
        }
    }

}
