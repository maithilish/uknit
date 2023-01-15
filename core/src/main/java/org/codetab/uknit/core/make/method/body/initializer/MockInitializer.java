package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.node.Types;

class MockInitializer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Configs configs;
    @Inject
    private MockExcludes mockExcludes;
    @Inject
    private Types types;
    @Inject
    private ModelFactory modelFactory;

    public Optional<Initializer> getInitializer(final IVar var,
            final Optional<Pack> iniPackO, final Heap heap) {

        if (var.isMock() && !mockExcludes.exclude(var, iniPackO)) {
            String initializerForMock;
            initializerForMock = configs.getConfig("uknit.createInstance.mock");
            String typeName = types.getTypeName(var.getType());
            String ini = initializerForMock.replace("${type}", typeName);

            Initializer initializer =
                    modelFactory.createInitializer(Kind.MOCK, ini);

            LOG.debug("Var [name={}] {}", var.getName(), initializer);

            return Optional.of(initializer);
        } else {
            return Optional.empty();
        }
    }

}

class MockExcludes {

    public boolean exclude(final IVar var, final Optional<Pack> iniPackO) {
        boolean exclude = false;

        // enum are not mocks
        if (nonNull(var.getTypeBinding()) && var.getTypeBinding().isEnum()) {
            exclude = true;
        }

        // if exp is static call, assigning mock to var is useless
        if (iniPackO.isPresent() && iniPackO.get().is(Nature.STATIC_CALL)) {
            exclude = true;
        }

        if (var.isEffectivelyReal()) {
            exclude = true;
        }

        if (var.is(org.codetab.uknit.core.make.model.IVar.Nature.OFFLIMIT)) {
            exclude = true;
        }

        return exclude;
    }
}
