package org.codetab.uknit.core.make.method.detect.insert;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.method.visit.Patcher;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Insert;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class InvokeStrategy {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Inserters inserters;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;

    public Optional<Insert> process(final IVar var, final Class<?> clz,
            final Heap heap) {

        checkNotNull(var);
        checkNotNull(clz);
        checkNotNull(heap);

        IVar keyVar = null;
        IVar valueVar = null;
        boolean createInsert = false;

        Optional<ExpVar> expVarO =
                inserters.findFirstAllowedExpVar(var, clz, heap);
        if (expVarO.isPresent() && expVarO.get().getLeftVar().isPresent()) {
            valueVar = expVarO.get().getLeftVar().get();
            Expression rExp = expVarO.get().getRightExp();

            // check insertable with allowed method calls
            if (inserters.isInsertable(var, clz, rExp)) {

                if (nodes.is(rExp, MethodInvocation.class)
                        && inserters.requiresKey(clz)) {
                    // requires key such as map.put()
                    MethodInvocation patchedRExp = patcher.copyAndPatch(
                            nodes.as(rExp, MethodInvocation.class), heap);
                    keyVar = inserters.getKeyArg(var,
                            nodes.as(rExp, MethodInvocation.class), patchedRExp,
                            heap);
                    logInsert("invoke [key,value]", var, valueVar, keyVar);
                } else {
                    // requires no key such as list.add()
                    logInsert("invoke [value]", var, valueVar, keyVar);
                }
                createInsert = true;
            }
        }
        if (createInsert) {
            String insertMethod = inserters.getInsertMethod(clz);
            Insert insert =
                    inserters.createInsert(var, valueVar, keyVar, insertMethod);
            return Optional.of(insert);
        } else {
            return Optional.empty();
        }
    }

    private void logInsert(final String strategy, final IVar var,
            final IVar valueVar, final IVar keyVar) {
        LOG.trace("insert strategy: {}", strategy);
        LOG.trace("collection var: {}", var);
        LOG.trace("value var: {}", valueVar);
        LOG.trace("key var: {}", keyVar);
    }
}
