package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Type;

/**
 * Derive initializer based on config or type.
 * @author Maithilish
 *
 */
class DerivedInitialzer {
    @Inject
    private Configs configs;
    @Inject
    private Types types;
    @Inject
    private Resolver resolver;

    public String deriveInitializer(final IVar var, final Heap heap) {
        Type type = var.getType();
        boolean deep = var.isDeepStub();

        // initialize to configured type
        String typeName = types.getTypeName(type);
        String key = String.join(".", "uknit.createInstance", typeName);
        String initializer = configs.getConfig(key);

        if (isNull(initializer) && type.isArrayType()) {
            initializer =
                    configs.getConfig("uknit.createInstance.arrayType", "{}");
        }

        // default createInstance config is set as mock by user
        if (nonNull(initializer) && initializer.equalsIgnoreCase("mock")) {
            initializer = null;
        }

        if (resolver.isTypeVariable(type)) {
            initializer = "STEPIN";
        }

        // initialize to mock
        if (isNull(initializer)) {
            if (var.isMock()) {
                // mock but created is effectively real, can't test as mock
                if (var.isCreated()) {
                    initializer = "STEPIN";
                } else {
                    // mock initialiser
                    String initializerForMock;
                    if (deep) {
                        initializerForMock = configs
                                .getConfig("uknit.createInstance.mockDeep");
                    } else {
                        initializerForMock =
                                configs.getConfig("uknit.createInstance.mock");
                    }

                    if (initializerForMock.equals("null")) {
                        initializer = null;
                    } else {
                        initializer =
                                initializerForMock.replace("${type}", typeName);
                    }
                }
            } else {
                initializer = "STEPIN";
            }
        }
        return initializer;
    }
}
