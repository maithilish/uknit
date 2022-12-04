package org.codetab.uknit.core.make.method.insert;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Insert;
import org.codetab.uknit.core.make.model.Invoke;
import org.eclipse.jdt.core.dom.Expression;

public class ForEachStrategy {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Inserters inserters;
    @Inject
    private InsertVars insertVars;

    public Optional<Insert> process(final IVar var, final Class<?> clz,
            final IVar loopVar, final Heap heap) {

        checkNotNull(var);
        checkNotNull(clz);
        checkNotNull(loopVar);
        checkNotNull(heap);

        IVar keyVar = null;
        IVar valueVar = null;
        boolean createInsert = false;

        if (inserters.requiresKey(clz)) {
            /*
             * Requires key such as map.put(). ExpVar is not present when no
             * collection access method is invoked. 1) Holder returns a
             * collection held by field. 2) Calling method passes collection to
             * IMC where it is accessed. Then in calling method expVar is not
             * present and it is present in internal method. See:
             * itest.insert.VarConflict for example. Don't create insert if
             * expVar is not present.
             */
            List<Invoke> expVars = inserters.findAllowedPacks(var, clz, heap);
            if (!expVars.isEmpty()) {

                /**
                 * When requireKey collections, such as Map, is used in
                 * for-each, the collection var may have multiple invokes such
                 * as keySet(), get() etc., in the block. Try to find the put
                 * var (key or value) from access invokes such as map.get(). *
                 * <p>
                 * However, for-each may contain just one invoke keySet() or
                 * values() and in such case, one of the put var doesn't exists.
                 * Then create the missing. then either for key or value is
                 * missing depending whether keySet() or values() method
                 * invoked. We create InferVar for the missing. Ex: Take
                 * Map<String, Date> then,
                 * <p>
                 * for(String key : map.keySet()) {..} - keySet() returns key as
                 * loop var but the value var is missing.
                 * <p>
                 * for(Date date : map.values()) {..} - values() returns date
                 * the value as loop var but the key var is missing.
                 */
                Optional<IVar> inferVarO =
                        inserters.findPutInferVar(expVars, var, heap);
                if (inferVarO.isEmpty()) {
                    Expression rExp = expVars.get(0).getExp();
                    inferVarO = insertVars.createPutInferVar(var, rExp, heap);
                    // add created infer var to heap
                    inferVarO.ifPresent(heap.getVars()::add);
                }

                // determine key or value based on invoked method
                if (inferVarO.isPresent()) {

                    String invokedMethod =
                            inserters.getInvokedMethod(expVars.get(0).getExp());
                    if (invokedMethod.equals("keySet")) {
                        keyVar = loopVar;
                        valueVar = inferVarO.get();
                    } else if (invokedMethod.equals("values")) {
                        keyVar = inferVarO.get();
                        valueVar = loopVar;
                    } else {
                        keyVar = loopVar;
                        valueVar = inferVarO.get();
                    }
                }
                logInsert("forEach [key,value]", var, valueVar, keyVar);
                createInsert = true;
            }
        } else {
            // requires no key such as list.add()
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
