package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.VarNames;
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
    private Stepins stepins;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;

    public String getInitializer(final IVar var, final Heap heap) {

        String initializer = null;

        Optional<Expression> initExpO =
                definedInitialzer.getInitializer(var, heap);

        // try to get initializer from initExp if present
        if (initExpO.isPresent()) {

            Optional<Pack> packO =
                    packs.findByExp(initExpO.get(), heap.getPacks());

            if (stepins.isStepin(initExpO.get(), heap)) {
                initializer = "STEPIN";
            } else if (nodes.is(initExpO.get(), SimpleName.class)) {
                Expression exp = initExpO.get();
                /*
                 * Ex: Pack [var: date2, exp: d1] and Patch [exp: d1, name:
                 * date] then d1 is patched to date.
                 */
                if (packO.isPresent()) {
                    exp = heap.getPatcher().copyAndPatch(packO.get(), heap);
                }
                initializer = nodes.getName(exp);
            } else if (definedInitialzer.isAllowed(initExpO.get(), heap)) {
                Expression exp =
                        heap.getPatcher().copyAndPatch(packO.get(), heap);
                initializer = exp.toString();
            } else if (definedInitialzer.isMIAllowed(var, initExpO.get(),
                    heap)) {
                Expression exp =
                        heap.getPatcher().copyAndPatch(packO.get(), heap);
                initializer = exp.toString();
            }
        }

        // try to get from initEnum if present
        if (isNull(initializer)) {
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
