package org.codetab.uknit.core.make.method.verify;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.anon.AnonProcessor;
import org.codetab.uknit.core.make.model.ArgCapture;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Misuses;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class VerifyCreator {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonProcessor anonProcessor;
    @Inject
    private Nodes nodes;
    @Inject
    private Excludes excludes;

    public void createVerify(final Invoke invoke, final Heap heap) {

        // IMC return may replace invoke exp MI with return var name
        if (!nodes.is(invoke.getExp(), MethodInvocation.class)) {
            return;
        }

        if (excludes.exclude(invoke, heap)) {
            return;
        }

        MethodInvocation mi = nodes.as(invoke.getExp(), MethodInvocation.class);
        MethodInvocation patchedMi =
                (MethodInvocation) heap.getPatcher().copyAndPatch(invoke, heap);

        boolean inCtlPath = invoke.isInCtlPath();

        List<ArgCapture> anonCaptures =
                anonProcessor.processVerifyArgs(mi, patchedMi, heap);

        Verify verify = modelFactory.createVerify(patchedMi, inCtlPath);
        verify.getArgCaptures().addAll(anonCaptures);

        invoke.setVerify(Optional.of(verify));

    }

    static class Excludes {

        @Inject
        private Expressions expressions;
        @Inject
        private Packs packs;
        @Inject
        private Methods methods;
        @Inject
        private Misuses misuses;

        public boolean exclude(final Invoke invoke, final Heap heap) {
            Optional<IVar> callVarO = invoke.getCallVar();
            if (callVarO.isEmpty()) {
                return true;
            }

            // exclude if effectively real
            IVar callVar = callVarO.get();
            if (callVar.isEffectivelyReal()) {
                return true;
            }

            // when is present, exclude verify
            if (invoke.getWhen().isPresent()) {
                return true;
            }

            /*
             * Exclude if method is invoked on real object. If var is renamed
             * then use renamed var to known whether method invoked on created
             * var. Ref itest: internal.CallAndAssign.
             * callAndAssignToDifferentNameNullInitialized() where
             * webClient.getOptions() in configure() is invoked on renamed var
             * webClient2.
             */
            Optional<Pack> callVarPackO = findCallVarPack(invoke, heap);
            if (callVarPackO.isPresent()) {
                IVar var = callVarPackO.get().getVar();
                if (var.isCreated() || var.is(Nature.REALISH)
                        || var.is(Nature.OFFLIMIT)) {
                    return true;
                }
            }

            if (misuses.isMisuse(invoke)) {
                return true;
            }

            if (nonNull(invoke.getVar())) {
                IVar var = invoke.getVar();
                if (var.is(Nature.OFFLIMIT)) {
                    return true;
                }

                if (misuses.isMisuse(var)) {
                    return true;
                }
            }

            if (misuses.isVerifyMisuse(invoke)) {
                return true;
            }

            return false;
        }

        /**
         * Get pack for the method invoke call var. Ex: Foo foo; foo.bar(); for
         * MI foo.bar() the call var is foo and first stmt is its pack.
         *
         * @param invoke
         * @param heap
         * @return
         */
        private Optional<Pack> findCallVarPack(final Invoke invoke,
                final Heap heap) {
            Optional<Pack> callVarPackO = Optional.empty();
            Optional<Expression> patchedCallExpO =
                    heap.getPatcher().copyAndPatchCallExp(invoke, heap);
            if (!methods.isInternalCall(invoke.getExp(), patchedCallExpO,
                    heap.getMut())) {
                String name = expressions.getName(patchedCallExpO.get());
                callVarPackO = packs.findByVarName(name, heap.getPacks());
            }
            return callVarPackO;
        }
    }
}
