package org.codetab.uknit.core.make.method.visit;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Literals;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Variables;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class Packer {

    @Inject
    private Packs packs;
    @Inject
    private Variables variables;
    @Inject
    private Literals literals;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Mocks mocks;
    @Inject
    private Resolver resolver;
    @Inject
    private Expressions expressions;

    public void packVars(final Kind kind, final Type type,
            final List<VariableDeclaration> vdList, final boolean inCtlPath,
            final Heap heap) {
        for (VariableDeclaration vd : vdList) {
            String name = variables.getVariableName(vd);
            boolean isMock = mocks.isMockable(type);
            IVar var = modelFactory.createVar(kind, name, type, isMock);
            Expression initializer = vd.getInitializer();
            Optional<Pack> packO =
                    packs.findByExp(initializer, heap.getPacks());
            if (packO.isPresent()) {
                packO.get().setVar(var);
            } else {
                Pack pack =
                        modelFactory.createPack(var, initializer, inCtlPath);
                heap.addPack(pack);
            }
        }
    }

    public void packExp(final Expression exp, final boolean inCtlPath,
            final Heap heap) {
        IVar var = null; // yet to be assigned so null
        Pack pack = modelFactory.createPack(var, exp, inCtlPath);
        heap.addPack(pack);
    }

    public Invoke createInvoke(final Expression exp, final boolean inCtlPath,
            final Heap heap) {
        IVar var = null; // yet to be assigned so null
        Invoke invoke = modelFactory.createInvoke(var, exp, inCtlPath);
        return invoke;
    }

    /**
     * Pack literal exp only if it is of interest.
     *
     * @param exp
     * @param heap
     */
    public void packLiteralExp(final Expression exp, final boolean inCtlPath,
            final Heap heap) {
        IVar var = null; // yet to be assigned so null
        if (literals.ofInterest(exp)) {
            Pack pack = modelFactory.createPack(var, exp, inCtlPath);
            heap.addPack(pack);
        }
    }

    public void setupInvokes(final Invoke invoke, final Heap heap) {

        // FIXME Pack - Enable this
        // MethodInvocation patchedMi = patcher.copyAndPatch(mi, heap);
        // Expression patchedExp = patchedMi.getExpression();

        MethodInvocation mi = (MethodInvocation) invoke.getExp();
        MethodInvocation patchedMi = (MethodInvocation) invoke.getExp();
        Expression patchedExp = patchedMi.getExpression();

        IVar callVar = null;
        if (nonNull(patchedExp)) {
            try {
                String name = expressions.getName(patchedExp);
                callVar = packs.findVarByName(name, heap.getPacks());
            } catch (IllegalStateException e) {
            }
        }
        Optional<ReturnType> returnType = resolver.getExpReturnType(mi);
        invoke.setCallVar(Optional.of(callVar));
        invoke.setReturnType(returnType);

        IMethodBinding methodBinding = resolver.resolveMethodBinding(mi);

        if (methodBinding.getExceptionTypes().length > 0) {
            heap.setTestThrowsException(true);
        }

        /*
         * The MI foo.bar(zoo) is exp.name(exp). If leading exp is null then MI
         * may be internal or static call.
         */
        // FIXME Pack - enable this
        // if (isNull(patchedExp) && !methods.isStaticCall(mi)) {
        // List<Expression> arguments = methods.getArguments(mi);
        // Optional<IVar> retVar = internalCallProcessor.process(methodBinding,
        // arguments, heap);
        // invoke.setReturnVar(retVar);
        // }
    }
}
