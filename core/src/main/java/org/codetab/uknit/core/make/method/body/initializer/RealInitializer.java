package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Type;

class RealInitializer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Configs configs;
    @Inject
    private Types types;
    @Inject
    private VarNames varNames;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private RealExcludes realExcludes;

    public Optional<Initializer> getInitializer(final IVar var,
            final Optional<Pack> iniPackO, final Heap heap) {

        if (realExcludes.exclude(var, iniPackO, heap)) {
            return Optional.empty();
        } else {
            Type type = var.getType();

            // initialize to configured type
            String typeName = types.getTypeName(type);
            String key = String.join(".", "uknit.createInstance", typeName);
            String ini = configs.getConfig(key);

            if (isNull(ini) && type.isArrayType()) {
                ini = configs.getConfig("uknit.createInstance.arrayType", "{}");
            }

            ini = varNames.replaceMetaSyntantics(ini);

            /*
             * The ini is null for real but no config createInstance such as
             * Object class. Ref itest: initializer.Real.realOfObjectClass()
             */
            Initializer initializer = null;
            if (nonNull(ini)) {
                initializer = modelFactory.createInitializer(Kind.REAL, ini);
            }

            LOG.debug("Var [name={}] {}", var.getName(), initializer);

            return Optional.ofNullable(initializer);
        }
    }

}

class RealExcludes {

    @Inject
    private Types types;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;

    public boolean exclude(final IVar var, final Optional<Pack> iniPackO,
            final Heap heap) {
        boolean exclude = false;

        // if exp is static call, assigning mock to var is useless
        if (iniPackO.isPresent() && iniPackO.get() instanceof Invoke
                && iniPackO.get().is(Nature.STATIC_CALL)) {
            /*
             * Ex: foo(Integer.valueOf(20)); the arg is static but infer var and
             * call var is unmodifiable, so allowed. The output is String apple
             * = Integer.valueOf(20). Ref itest:
             * ret.Unmodifiable.returnByteVar().
             *
             * Ex: String name = Statics.getName("foo"); static but not infer
             * and call var not unmodifiable, so not allowed. The output is
             * String apple = STEPIN. Ref itest:
             * invoke.CallStatic.assignStatic().
             *
             */
            Invoke invoke = (Invoke) iniPackO.get();
            Optional<Expression> callExpO =
                    patcher.copyAndPatchCallExp(invoke, heap);
            boolean callVarUnmodifiable = false;
            if (callExpO.isPresent() && nodes.isName(callExpO.get())) {
                callVarUnmodifiable =
                        types.isUnmodifiable(nodes.getName(callExpO.get()));
            }

            if (!var.isInferVar() && !callVarUnmodifiable) {
                exclude = true;
            }
        }

        // exclude enum
        if (nonNull(var.getTypeBinding()) && var.getTypeBinding().isEnum()) {
            exclude = true;
        }

        return exclude;
    }
}
