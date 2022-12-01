package org.codetab.uknit.core.make.method.visit;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Braces;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Literals;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Variables;
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
    private Patcher patcher;
    @Inject
    private Resolver resolver;
    @Inject
    private Expressions expressions;
    @Inject
    private Braces braces;

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
            Expression initializer = braces.strip(vd.getInitializer());

            /*
             * The initializer, if exists, then its type overrides the var
             * declaration type. Ex: Object obj = new Date(); obj's type is
             * upgraded from Object to Date
             *
             * FIXME Pack - similar to invoke returnType, check is it possible
             * to simplify the invoke
             */
            Optional<Type> initializerTypeO = types.getType(initializer);
            initializerTypeO.ifPresent(t -> {
                var.setType(t);
                var.setTypeBinding(initializer.resolveTypeBinding());
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

    public void packStandinVar(final IVar var, final boolean inCtlPath,
            final Heap heap) {
        Expression exp = null; // no exp
        Pack pack = modelFactory.createPack(var, exp, inCtlPath);
        heap.addPack(pack);
    }

    /**
     * Setup fields of Invoke. Invoke exp can be MethodInvocation or
     * SuperMethodInvocation.
     *
     * @param invoke
     * @param heap
     */
    public void setupInvokes(final Invoke invoke, final Heap heap) {

        Expression exp = invoke.getExp(); // MI, SMI

        /*
         * Find callVar of invoke. It is empty for all types of IMC - with
         * keywords this and super or without keywords (plain call)
         */
        Optional<Expression> patchedExpO =
                patcher.getPatchedCallExp(invoke, heap);
        Optional<IVar> callVarO = Optional.empty();
        if (patchedExpO.isPresent()) {
            try {
                String name = expressions.getName(patchedExpO.get());
                callVarO = Optional.of(vars.findVarByName(name, heap));
            } catch (VarNotFoundException e) {
            }
        }

        // find return type of invoke
        // REVIEW - fix this extra in next if else block
        Optional<ReturnType> returnTypeO = resolver.getExpReturnType(exp);
        // REVIEW
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
        } else {
            // Ex: Foo foo = bar.foo();
            resolver.getExpReturnType(exp);
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
    }
}
