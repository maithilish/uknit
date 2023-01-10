package org.codetab.uknit.core.make.method.body.initializer;

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
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Type;

/**
 * Assign name exp as initializer.
 *
 * @author Maithilish
 *
 */
class SensibleInitializer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Types types;
    @Inject
    private Configs configs;

    public Optional<Initializer> getInitializer(final Pack pack,
            final Heap heap) {
        IVar var = pack.getVar();
        Type type = var.getType();
        String typeName = types.getTypeName(type);

        if (type.isPrimitiveType()) {
            String key = String.join(".", "uknit.createInstance", typeName);
            String ini = configs.getConfig(key);

            Initializer initializer =
                    modelFactory.createInitializer(Kind.LITERAL, ini);

            LOG.debug("Var [name={}] {}", var.getName(), initializer);

            return Optional.of(initializer);
        }
        return Optional.empty();
    }
}
