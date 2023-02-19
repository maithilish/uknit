package org.codetab.uknit.core.make.method.imc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.make.method.MethodMakers;
import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.Expression;

public class InternalCalls {

    @Inject
    private Methods methods;
    @Inject
    private MethodMakers methodMakers;
    @Inject
    private Controller ctl;

    public List<Invoke> filterInternalInvokes(final List<Invoke> invokes,
            final Heap heap) {
        List<Invoke> internalInvokes = new ArrayList<>();
        for (Invoke invoke : invokes) {
            if (methods.isInvokable(invoke.getExp())) {
                Expression miOrSmiExp = invoke.getExp();
                Optional<Expression> patchedCallExpO =
                        heap.getPatcher().copyAndPatchCallExp(invoke, heap);
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
        internalHeap.setTestClzName(heap.getTestClzName());
        List<Field> fieldsCopy = ctl.getClzMaker().getClzMap()
                .getDefinedFieldsCopy(heap.getTestClzName());
        List<Pack> packsCopy = methodMakers.createFieldPacks(fieldsCopy);
        internalHeap.addPacks(packsCopy);
    }
}
