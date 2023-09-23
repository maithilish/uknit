package org.codetab.uknit.core.make.method.body.initializer;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;

/**
 * Assign name exp as initializer.
 *
 * @author Maithilish
 *
 */
class StepinInitializer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private ModelFactory modelFactory;

    public Optional<Initializer> getInitializer(final Pack pack) {
        Initializer initializer =
                modelFactory.createInitializer(Kind.STEPIN, "STEPIN");

        LOG.debug("Var [name={}] {}", pack.getVar().getName(), initializer);

        return Optional.of(initializer);
    }
}
