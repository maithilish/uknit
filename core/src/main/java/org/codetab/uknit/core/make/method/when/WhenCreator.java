package org.codetab.uknit.core.make.method.when;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.lamda.AnonymousProcessor;
import org.codetab.uknit.core.make.method.lamda.LambdaProcessor;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.method.verify.VerifyCreator;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class WhenCreator {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonymousProcessor anonymousProcessor;
    @Inject
    private LambdaProcessor lambdaProcessor;
    @Inject
    private VerifyCreator verifyCreator;
    @Inject
    private Methods methods;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;
    @Inject
    private Excludes excludes;

    public void createWhen(final Invoke invoke, final Heap heap) {

        // IMC return may replace invoke exp MI with return var name
        if (!nodes.is(invoke.getExp(), MethodInvocation.class)) {
            return;
        }

        if (excludes.exclude(invoke, heap)) {
            return;
        }

        IVar whenReturnVar = invoke.getVar();
        Optional<IVar> callVarO = invoke.getCallVar();

        /*
         * As when replaces anon and lambda with arg matchers and verify
         * replaces it with captures, we need two copies of mi. Source mi is
         * untouched and used to resolve type binding of lambda. The copy
         * doesn't resolve bindings!
         */
        MethodInvocation mi = nodes.as(invoke.getExp(), MethodInvocation.class);
        MethodInvocation patchedMi =
                (MethodInvocation) patcher.copyAndPatch(invoke, heap);

        boolean anonReplaced =
                anonymousProcessor.patchAnonymousArgs(patchedMi, heap);
        if (anonReplaced) {
            verifyCreator.createVerify(invoke, heap);
        }

        boolean lambdaReplaced =
                lambdaProcessor.patchLambdaArgs(patchedMi, mi, heap);
        if (lambdaReplaced) {
            verifyCreator.createVerify(invoke, heap);
        }

        /*
         * if we are inCtlFlowPath then stage when else stage verify to create
         * verify with never().
         */
        if (invoke.isInCtlPath()) {
            String methodSignature = patchedMi.toString();
            if (invoke.getWhen().isEmpty()) {
                When when = modelFactory.createWhen(methodSignature,
                        callVarO.get());
                invoke.setWhen(Optional.of(when));
            }

            When when = invoke.getWhen().get();
            when.getReturnVars().add(whenReturnVar);

            List<String> usedNames = methods.getNames(patchedMi);
            when.getNames().addAll(usedNames);

        } else {
            verifyCreator.createVerify(invoke, heap);
        }
    }

    static class Excludes {

        @Inject
        private Expressions expressions;
        @Inject
        private Packs packs;
        @Inject
        private Types types;
        @Inject
        private Methods methods;
        @Inject
        private Patcher patcher;

        public boolean exclude(final Invoke invoke, final Heap heap) {
            Optional<IVar> callVarO = invoke.getCallVar();
            if (callVarO.isEmpty()) {
                return true;
            }

            IVar callVar = callVarO.get();
            // exclude if created
            if (callVar.isCreated()) {
                return true;
            }
            // exclude if real
            if (!callVar.isMock()) {
                return true;
            }
            // returnType void, exclude
            if (invoke.getReturnType().isPresent()) {
                Type type = invoke.getReturnType().get().getType();
                if (!types.capableToReturnValue(type)) {
                    return true;
                }
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
            if (callVarPackO.isPresent()
                    && callVarPackO.get().getVar().isCreated()) {
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
            Optional<Expression> patchedExpO =
                    patcher.getPatchedCallExp(invoke, heap);
            if (!methods.isInternalCall(invoke.getExp(), patchedExpO,
                    heap.getMut())) {
                String name = expressions.getName(patchedExpO.get());
                callVarPackO = packs.findByVarName(name, heap.getPacks());
            }
            return callVarPackO;
        }
    }
}
