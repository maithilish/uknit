package org.codetab.uknit.core.make.method.imc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.old.PatcherOld;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.Expression;

public class InternalCalls {

    @Inject
    private PatcherOld patcherOld;
    @Inject
    private Methods methods;
    @Inject
    private Packs packs;

    public List<Invoke> filterInternalInvokes(final List<Invoke> invokes,
            final Heap heap) {
        List<Invoke> internalInvokes = new ArrayList<>();
        for (Invoke invoke : invokes) {
            if (methods.isInvokable(invoke.getExp())) {
                Expression miOrSmiExp = invoke.getExp();
                Optional<Expression> patchedCallExpO =
                        patcherOld.getPatchedCallExp(invoke, heap);

                if (methods.isInternalCall(miOrSmiExp, patchedCallExpO,
                        heap.getMut())) {
                    internalInvokes.add(invoke);
                }
            }
        }
        return internalInvokes;
    }

    /**
     * Init internal heap with Field packs from heap of MUT.
     *
     * @param heap
     * @param internalHeap
     */
    public void initInternalHeap(final Heap heap, final Heap internalHeap) {
        List<Pack> fieldPacks =
                packs.filterByVarKinds(heap.getPacks(), Kind.FIELD);
        internalHeap.addPacks(fieldPacks);
    }
}
