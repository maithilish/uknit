package org.codetab.uknit.core.make.method.detect.insert;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Insert;
import org.eclipse.jdt.core.dom.Expression;

public class ForEachStrategy {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Inserters inserters;
    @Inject
    private InsertVars insertVars;

    public Optional<Insert> process(final IVar var, final Class<?> clz,
            final IVar loopVar, final Heap heap) {
        IVar keyVar = null;
        IVar valueVar = null;
        boolean createInsert = false;
        if (inserters.requiresKey(clz)) {
            Optional<ExpVar> expVarO =
                    inserters.findFirstAllowedExpVar(var, clz, heap);
            /*
             * expVar is not present when no collection access method is
             * invoked. 1) Holder returns a collection held by field. 2) Calling
             * method passes collection to IMC where it is accessed. Then in
             * calling method expVar is not present and it is present in
             * internal method. See: itest.insert.VarConflict for example. Don't
             * create insert if expVar is not present.
             */
            if (expVarO.isPresent()) {
                Expression rExp = expVarO.get().getRightExp();
                String invokedMethod = inserters.getInvokedMethod(rExp);
                /**
                 * When requireKey collections, such as Map, is used in forEach
                 * then either for key or value is missing depending whether
                 * keySet() or values() method invoked. We create InferVar for
                 * the missing. Ex: Take Map<String, Date> then,
                 * <p>
                 * for(String key : map.keySet()) {..} - keySet() returns key as
                 * loop var but the value var is missing.
                 * <p>
                 * for(Date date : map.values()) {..} - values() returns date
                 * the value as loop var but the key var is missing.
                 */
                Optional<InferVar> inferVarO =
                        insertVars.createPutInferVar(var, rExp, heap);
                if (inferVarO.isPresent()) {
                    // add newly created infer var to heap
                    heap.getVars().add(inferVarO.get());
                    if (invokedMethod.equals("keySet")) {
                        keyVar = loopVar;
                        valueVar = inferVarO.get();
                    } else if (invokedMethod.equals("values")) {
                        keyVar = inferVarO.get();
                        valueVar = loopVar;
                    }
                }
                logInsert("forEach [key,value]", var, valueVar, keyVar);
                createInsert = true;
            }
        } else {
            valueVar = loopVar;
            logInsert("forEach [value]", var, valueVar, keyVar);
            createInsert = true;
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
