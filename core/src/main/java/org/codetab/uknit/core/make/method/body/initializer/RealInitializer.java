package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.node.Types;
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

        if (realExcludes.exclude(var, iniPackO)) {
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

            Initializer initializer =
                    modelFactory.createInitializer(Kind.REAL, ini);

            LOG.debug("Var [name={}] {}", var.getName(), initializer);

            return Optional.ofNullable(initializer);
        }
    }

}

class RealExcludes {

    public boolean exclude(final IVar var, final Optional<Pack> iniPackO) {
        boolean exclude = false;

        // if exp is static call, assigning mock to var is useless
        if (iniPackO.isPresent() && iniPackO.get().is(Nature.STATIC_CALL)) {
            /*
             * Ex: foo(Integer.valueOf(20)); the arg is static but infer var so
             * allowed and output is String apple = Integer.valueOf(20). String
             * name = Statics.name("foo"), static but infer allowed and output
             * is String apple = "foo". If not infer then not allowed.
             */
            if (!var.isInferVar()) {
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
