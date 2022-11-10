package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
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

    public String getInitializer(final IVar var, final Heap heap) {
        String initializer = null;
        Optional<Expression> iniExp =
                definedInitialzer.getInitializer(var, heap);
        Optional<String> enumIni = enumInitializer.getInitializer(var, heap);

        if (iniExp.isPresent() && definedInitialzer.isAllowed(iniExp.get())) {
            Expression exp = patcher.copyAndPatch(iniExp.get(), heap);
            initializer = exp.toString();
        } else if (iniExp.isPresent()
                && definedInitialzer.isMIAllowed(var, iniExp.get(), heap)) {
            Expression exp = patcher.copyAndPatch(iniExp.get(), heap);
            initializer = exp.toString();
        } else if (iniExp.isPresent()
                && nodes.is(iniExp.get(), SimpleName.class)) {
            initializer = nodes.getName(iniExp.get());
        } else if (enumIni.isPresent()) {
            initializer = enumIni.get();
        } else {
            initializer = derivedInitialzer.deriveInitializer(var, heap);
        }

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


