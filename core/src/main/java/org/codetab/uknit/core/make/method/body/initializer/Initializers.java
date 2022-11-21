package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;

public class Initializers {

    @Inject
    private VarNames varNames;
    @Inject
    private DefinedInitialzer definedInitialzer;
    @Inject
    private EnumInitializer enumInitializer;
    @Inject
    private DerivedInitialzer derivedInitialzer;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;

    public String getInitializer(final IVar var, final Heap heap) {

        String initializer = null;

        Optional<Expression> initExp =
                definedInitialzer.getInitializer(var, heap);

        /*
         * If present try to get initializer from initExp else try from
         * initEnum.
         */
        if (initExp.isPresent()) {
            Optional<Pack> packO =
                    packs.findByExp(initExp.get(), heap.getPacks());
            if (nodes.is(initExp.get(), SimpleName.class)) {
                initializer = nodes.getName(initExp.get());
            } else if (definedInitialzer.isAllowed(initExp.get())) {
                Expression exp = patcher.copyAndPatch(packO.get(), heap);
                initializer = exp.toString();
            } else if (definedInitialzer.isMIAllowed(var, initExp.get(),
                    heap)) {
                Expression exp = patcher.copyAndPatch(packO.get(), heap);
                initializer = exp.toString();
            }
        } else {
            Optional<String> initEnum =
                    enumInitializer.getInitializer(var, heap);
            if (initEnum.isPresent()) {
                initializer = initEnum.get();
            }
        }

        /*
         * if initializer is still null assign derived initializer.
         */
        if (isNull(initializer)) {
            initializer = derivedInitialzer.deriveInitializer(var, heap);
        }

        /*
         * if initializer, such as String initializer, has ${metasyntatic}
         * replace it with metasyntatic such as Foo, Bar etc.,
         */
        if (nonNull(initializer) && initializer.contains("${metasyntatic}")) {
            initializer = initializer.replace("${metasyntatic}",
                    varNames.getMetaSyntantics());
        }
        return initializer;
    }

    public Optional<Expression> getInitializerExp(final IVar var,
            final Heap heap) {
        return definedInitialzer.getInitializer(var, heap);
    }
}
