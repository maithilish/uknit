package org.codetab.uknit.core.make.method.visit;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Literals;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Variables;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class Packer {

    @Inject
    private Packs packs;
    @Inject
    private Vars vars;
    @Inject
    private Nodes nodes;
    @Inject
    private Variables variables;
    @Inject
    private Types types;
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
    @Inject
    private Wrappers wrappers;
    @Inject
    private Methods methods;

    /**
     * Creates packs for VarDecl list. If initializer exists then its pack is
     * used else new pack is created. Newly created var is set to the pack.
     *
     * @param kind
     * @param type
     * @param vdList
     * @param inCtlPath
     * @param heap
     */
    public void packVars(final Kind kind, final Type type,
            final List<VariableDeclaration> vdList, final boolean inCtlPath,
            final Heap heap) {
        for (VariableDeclaration vd : vdList) {
            String name = variables.getVariableName(vd);
            boolean isMock = mocks.isMockable(type);
            IVar var = modelFactory.createVar(kind, name, type, isMock);
            var.setTypeBinding(type.resolveBinding());
            Expression initializer = wrappers.strip(vd.getInitializer());

            /*
             * The initializer, if exists, then its type overrides the var
             * declaration type. Ex: Object obj = new Date(); obj's type is
             * upgraded from Object to Date
             *
             * For parameterised type don't use initializer type as it may be a
             * diamond <>. Ex: List<File> list = new ArrayList<>(); initializer
             * type is ArrayList<> and it is not allowed in LHS.
             *
             * FIXME Pack - similar to invoke returnType, check is it possible
             * to simplify the invoke
             */
            Optional<Type> initializerTypeO = types.getType(initializer);
            initializerTypeO.ifPresent(t -> {
                if (!t.isParameterizedType()) {
                    var.setType(t);
                    var.setTypeBinding(initializer.resolveTypeBinding());
                }
            });

            /*
             * if initializer pack is present assign var to it otherwise create
             * new pack
             */
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

    public void packAnon(final Expression exp, final boolean inCtlPath,
            final Heap heap) {
        IVar var = null; // yet to be assigned so null
        Pack pack = modelFactory.createPack(var, exp, inCtlPath);
        pack.addNature(Nature.ANONYMOUS);
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

    public Pack packStandinVar(final IVar var, final boolean inCtlPath,
            final Heap heap) {
        Expression exp = null; // no exp
        Pack pack = modelFactory.createPack(var, exp, inCtlPath);
        heap.addPack(pack);
        return pack;
    }

    /**
     * Setup fields of Invoke. Invoke exp can be MethodInvocation or
     * SuperMethodInvocation.
     *
     * @param invoke
     * @param imc
     * @param heap
     */
    public void setupInvokes(final Invoke invoke, final boolean imc,
            final Heap heap) {

        final Expression exp = invoke.getExp(); // MI, SMI

        /*
         * Find callVar of invoke. It is empty for all types of IMC - with
         * keywords this and super or without keywords (plain call)
         */
        Optional<Expression> patchedCallExpO =
                heap.getPatcher().copyAndPatchCallExp(invoke, heap);
        Optional<IVar> callVarO = Optional.empty();
        /*
         * If in IM, don't set callVar. The final var used by the IM, arg or
         * param, is decided only after IMC merge and it will be set later in
         * post process by InvokeProcessor.process().
         */
        if (patchedCallExpO.isPresent() && !imc) {
            try {
                String name = expressions.getName(patchedCallExpO.get());
                callVarO = Optional.of(vars.findVarByName(name, heap));
            } catch (VarNotFoundException e) {
            }
        }

        // find return type of invoke Ex: Foo foo = bar.foo();
        Optional<ReturnType> returnTypeO = resolver.getExpReturnType(exp);

        Optional<Expression> castedExpO = expressions.getCastedExp(exp);
        if (castedExpO.isPresent()) {
            /*
             * Ex: Foo foo = (Foo) bar.obj(); Casted MI results in a Pack for
             * CastExp and an Invoke for MI. Even though return type of
             * bar.obj() is Object, it is safe to change the type to cast type
             * so that when infer var is created for Invoke, type is set to Foo
             * instead of Object.
             */
            returnTypeO = resolver.getExpReturnType(
                    nodes.as(castedExpO.get(), CastExpression.class));
        }

        invoke.setCallVar(callVarO);
        invoke.setReturnType(returnTypeO);

        /*
         * if invokes throw exception set it in heap so that throws clause is
         * added to test method declaration in the end.
         */
        if (resolver.resolveMethodBinding(exp).getExceptionTypes().length > 0) {
            heap.setTestThrowsException(true);
        }

        boolean isInternalCall = methods.isInternalCall(exp,
                heap.getPatcher().copyAndPatchCallExp(invoke, heap),
                heap.getMut());
        boolean isStaticCall = methods.isStaticCall(exp);

        if (isStaticCall && !isInternalCall) {
            invoke.addNature(Nature.STATIC_CALL);
        }
    }
}
