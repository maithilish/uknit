package org.codetab.uknit.core.make.method.body.initializer;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Methods;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

/**
 * Assign initializer if exp is allowed ASTNode.
 *
 * @author Maithilish
 *
 */
class InvokeInitializer implements IInitializer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Patcher patcher;
    @Inject
    private AllowedInvokes allowedInvokes;
    @Inject
    private ModelFactory modelFactory;

    @Override
    public Optional<Initializer> getInitializer(final Pack pack,
            final Pack iniPack, final Heap heap) {

        checkState(iniPack instanceof Invoke);

        Invoke iniInvoke = (Invoke) iniPack;

        if (allowedInvokes.isAllowed(pack, iniInvoke, heap)) {
            Expression patchedExp = patcher.copyAndPatch(iniInvoke, heap);
            Initializer initializer =
                    modelFactory.createInitializer(Kind.EXP, patchedExp);

            LOG.debug("Var [name={}] {}", pack.getVar().getName(), initializer);

            return Optional.of(initializer);
        } else {
            return Optional.empty();
        }
    }

}

class AllowedInvokes {

    @Inject
    private Methods methods;

    public boolean isAllowed(final Pack pack, final Invoke iniInvoke,
            final Heap heap) {

        boolean allowed = true;
        Type varType = pack.getVar().getType();

        MethodInvocation mi = (MethodInvocation) iniInvoke.getExp();
        Optional<ReturnType> returnTypeO = iniInvoke.getReturnType();

        /*
         * Real callVar returns Real - mi allowed, mi creates real instance
         * which is assigned to var.
         *
         * Real callVar returns Mock - mi not allowed, any var returned by real
         * has to be real so the returned var is effectively real.
         *
         * Mock callVar returns Real - mi not allowed, create new real instance
         * of return var so that when can return it.
         *
         * Mock callVar returns Mock - mi not allowed, create new mock instance
         * of return var so that when can return it.
         */
        Optional<IVar> callVarO = iniInvoke.getCallVar();
        if (callVarO.isPresent() && nonNull(pack.getVar())) {
            IVar callVar = callVarO.get();
            IVar returnVar = pack.getVar();
            boolean callVarEffectivelyMock = !callVar.isEffectivelyReal();
            boolean returnVarEffectivelyMock = !returnVar.isEffectivelyReal();

            if (callVarEffectivelyMock) {
                allowed = false;
            }

            if (callVar.isCreated() && returnVarEffectivelyMock) {
                allowed = false;
            }

            /*
             * Ex: File file = files.get(0); mi can't be initializer.
             */
            if (callVar.is(Nature.COLLECTION)) {
                allowed = false;
            }

            if (callVar.is(Nature.OFFLIMIT)) {
                allowed = false;
            }
        }

        if (varType.isArrayType()) {
            allowed = false;
        }

        // for enum, mi can't be initializer
        if (returnTypeO.isPresent()) {
            ITypeBinding typeBind = returnTypeO.get().getTypeBinding();
            if (nonNull(typeBind) && typeBind.isEnum()) {
                allowed = false;
            }
        } else {
            ITypeBinding typeBind = pack.getVar().getTypeBinding();
            if (nonNull(typeBind) && typeBind.isEnum()) {
                allowed = false;
            }
        }

        // if mi is internal, it can't be initializer
        if (methods.isInternalCall(mi, Optional.ofNullable(mi.getExpression()),
                heap.getMut())) {
            allowed = false;
        }

        if (iniInvoke.is(
                org.codetab.uknit.core.make.model.Pack.Nature.STATIC_CALL)) {
            /*
             * Ex: foo(Integer.valueOf(20)); the arg is static and it is not
             * allowed initializer. However, RealInitializer may set such vars
             * to defaults.
             */
            allowed = false;
        }

        if (callVarO.isEmpty()) {
            /*
             * Ex: String name = new Locale("x").getCountry(); call var is empty
             * which denotes that name derives value from an object that is not
             * accessible to test method and mi is not allowed.
             */
            allowed = false;
        }

        /*
         * If var of expression of mi is created then mi can't be initializer.
         * Ex: staticGetSuperField().getRealFoo().getId(); the var returned by
         * exp staticGetSuperField().getRealFoo() is real and the mi can't be
         * initializer. Ref itest:
         * superclass.StaticCall.returnStaticSuperField()
         */
        // Optional<IVar> varO =
        // packs.findVarByExp(mi.getExpression(), heap.getPacks());
        // if (varO.isPresent() && varO.get().isEffectivelyReal()) {
        // allowed = false;
        // }
        return allowed;
    }
}
