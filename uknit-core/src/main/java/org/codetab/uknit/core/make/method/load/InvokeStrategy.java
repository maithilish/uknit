package org.codetab.uknit.core.make.method.load;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Load;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class InvokeStrategy {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Loaders loaders;
    @Inject
    private Nodes nodes;

    public Optional<Load> process(final IVar var, final Class<?> clz,
            final Heap heap) {

        checkNotNull(var);
        checkNotNull(clz);
        checkNotNull(heap);

        IVar keyVar = null;
        IVar valueVar = null;
        boolean createload = false;

        Optional<Pack> packO = loaders.findFirstAllowedPack(var, clz, heap);
        if (packO.isPresent() && nonNull(packO.get().getVar())) {
            valueVar = packO.get().getVar();
            Expression exp = packO.get().getExp();

            // check loadable with allowed method calls
            if (loaders.isLoadable(var, clz, exp)) {

                if (nodes.is(exp, MethodInvocation.class)
                        && loaders.requiresKey(clz)) {
                    // requires key such as map.put()
                    MethodInvocation patchedExp = (MethodInvocation) heap
                            .getPatcher().copyAndPatch(packO.get(), heap);
                    keyVar = loaders.getKeyArg(var,
                            nodes.as(exp, MethodInvocation.class), patchedExp,
                            heap);
                    logload("invoke [key,value]", var, valueVar, keyVar);
                } else {
                    // requires no key such as list.add()
                    logload("invoke [value]", var, valueVar, keyVar);
                }
                createload = true;
            }
        }
        if (createload) {
            String loadMethod = loaders.getLoadMethod(clz);
            Load load = loaders.createLoad(var, valueVar, keyVar, loadMethod);
            return Optional.of(load);
        } else {
            return Optional.empty();
        }
    }

    private void logload(final String strategy, final IVar var,
            final IVar valueVar, final IVar keyVar) {
        LOG.trace("load strategy: {}", strategy);
        LOG.trace("collection var: {}", var);
        LOG.trace("value var: {}", valueVar);
        LOG.trace("key var: {}", keyVar);
    }
}
