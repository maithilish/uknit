package org.codetab.uknit.core.make.method.when;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.ExpManager;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.anon.AnonProcessor;
import org.codetab.uknit.core.make.method.patch.ExpService;
import org.codetab.uknit.core.make.method.verify.VerifyCreator;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Misuses;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Type;

public class WhenCreator {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private AnonProcessor anonProcessor;
    @Inject
    private VerifyCreator verifyCreator;
    @Inject
    private ExpService expService;
    @Inject
    private Nodes nodes;
    @Inject
    private Excludes excludes;
    @Inject
    private ExpManager expManager;
    @Inject
    private Similars similars;

    public List<Invoke> filterWhenInvokes(final List<Invoke> invokes,
            final Heap heap) {
        List<Invoke> whenInvokes = new ArrayList<>();
        for (Invoke invoke : invokes) {
            boolean isWhen = true;
            // IMC return may replace invoke exp MI with return var name
            if (!nodes.is(invoke.getExp(), MethodInvocation.class)) {
                isWhen = false;
            }

            if (excludes.exclude(invoke, heap)) {
                isWhen = false;
            }
            if (isWhen) {
                whenInvokes.add(invoke);
            }
        }
        return whenInvokes;
    }

    public void createWhen(final Invoke invoke, final List<Invoke> whenInvokes,
            final Heap heap) {

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
                (MethodInvocation) heap.getPatcher().copyAndPatch(invoke, heap);
        patchedMi = (MethodInvocation) expManager.unparenthesize(patchedMi);

        boolean anonReplaced =
                anonProcessor.processWhenArgs(mi, patchedMi, heap);
        if (anonReplaced) {
            verifyCreator.createVerify(invoke, heap);
        }

        /*
         * if we are inCtlFlowPath then stage when else stage verify to create
         * verify with never().
         */
        if (invoke.isInCtlPath()) {
            String methodSignature =
                    expManager.rejig(patchedMi, heap).toString();

            Optional<Invoke> similarInvokeO = similars
                    .searchInvokeWithSameInitializer(invoke, whenInvokes);

            When when;
            if (similarInvokeO.isPresent()) {
                // if similar when exists then use it
                when = similarInvokeO.get().getWhen().get();

                if (invoke.getWhen().isEmpty()) {
                    /*
                     * for the invoke add a disabled when so that verify is not
                     * created and no output of when stmt
                     */
                    When disabledWhen = modelFactory.createWhen(methodSignature,
                            callVarO.get());
                    disabledWhen.setEnable(false);
                    invoke.setWhen(Optional.of(disabledWhen));
                } else {
                    invoke.getWhen().get().setEnable(false);
                }
            } else {
                if (invoke.getWhen().isEmpty()) {
                    when = modelFactory.createWhen(methodSignature,
                            callVarO.get());
                    invoke.setWhen(Optional.of(when));
                } else {
                    when = invoke.getWhen().get();
                }
            }
            // When when = invoke.getWhen().get();
            when.getReturnVars().add(whenReturnVar);

            /*
             * Add names used in patched mi to when names. Return var of
             * disabled when is added to a similar when and no name in patched
             * mi (when method signature) is used and don't add them.
             */
            if (invoke.getWhen().get().isEnable()) {
                List<String> names = when.getNames();
                List<Name> namesInExp = expService.listNames(patchedMi);
                for (Name name : namesInExp) {
                    names.add(nodes.getName(name));
                }
            }
        } else {
            verifyCreator.createVerify(invoke, heap);
        }
    }

    static class Similars {

        /**
         * Look for invoke with same initializer.
         *
         * Ex: File[] files = {f1};
         * foo.appendString(files[0].getAbsolutePath());
         * foo.appendString(files[0].getAbsolutePath());
         *
         * For two files[0] array access, two invoke with infer vars file and
         * file1 are created. Both pack's initializer is f1 and two invokes are
         * similar.
         *
         * @param invoke
         * @param whenInvokes
         * @return
         */
        public Optional<Invoke> searchInvokeWithSameInitializer(
                final Invoke invoke, final List<Invoke> whenInvokes) {
            if (invoke.getCallVar().isPresent()) {
                for (Invoke inv : whenInvokes) {
                    // exclude the input invoke from search
                    if (inv.getId() == invoke.getId()
                            || inv.getWhen().isEmpty()) {
                        continue;
                    }

                    Optional<Initializer> invIniO =
                            inv.getWhen().get().getCallVar().getInitializer();
                    Optional<Initializer> invokeIniO =
                            invoke.getCallVar().get().getInitializer();
                    if (invIniO.isPresent() && invokeIniO.isPresent() && invIniO
                            .get().getInitializer() instanceof Expression) {
                        /*
                         * File a = f1; File b = f1; a.getAbsolutePath();
                         * b.getAbsolutePath(); The two f1 exp may be different
                         * instances so compare exp strings.
                         */
                        String invIni =
                                invIniO.get().getInitializer().toString();
                        String invokeIni =
                                invokeIniO.get().getInitializer().toString();
                        if (invIni.equals(invokeIni)) {
                            return Optional.of(inv);
                        }
                    }
                }
            }
            return Optional.empty();
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
            if (callVarPackO.isPresent()) {
                IVar var = callVarPackO.get().getVar();
                if (var.isCreated()) {
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
