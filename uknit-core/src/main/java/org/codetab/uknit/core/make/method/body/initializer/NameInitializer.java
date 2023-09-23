package org.codetab.uknit.core.make.method.body.initializer;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Assign name exp as initializer.
 *
 * @author Maithilish
 *
 */
class NameInitializer implements IInitializer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Nodes nodes;
    @Inject
    private Patcher patcher;
    @Inject
    private Wrappers wrappers;
    @Inject
    private ModelFactory modelFactory;

    @Override
    public Optional<Initializer> getInitializer(final Pack pack,
            final Pack iniPack, final Heap heap) {

        Expression patchedExp = patcher.copyAndPatch(pack, heap);

        if (nodes.isName(wrappers.unpack(patchedExp))) {
            ASTNode parent = wrappers.stripAndGetParent(iniPack.getExp());
            if (nodes.is(parent, CastExpression.class)) {
                /*
                 * Ex: return (int) person.lid; the initializer is parent cast
                 * exp and not inner name exp.
                 */
                patchedExp = (Expression) parent;
            } else {
                patchedExp = patcher.copyAndPatch(iniPack, heap);
            }
            Initializer initializer =
                    modelFactory.createInitializer(Kind.NAME, patchedExp);
            LOG.debug("Var [name={}] {}", pack.getVar().getName(), initializer);

            return Optional.of(initializer);
        } else {
            return Optional.empty();
        }
    }
}
