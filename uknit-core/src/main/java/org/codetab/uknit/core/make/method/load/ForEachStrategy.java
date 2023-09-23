package org.codetab.uknit.core.make.method.load;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Load;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;

public class ForEachStrategy {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Loaders loaders;
    @Inject
    private LoadVars loadVars;
    @Inject
    private Packs packs;
    @Inject
    private ModelFactory modelFactory;

    public Optional<Load> process(final IVar var, final Class<?> clz,
            final IVar loopVar, final Heap heap) {

        checkNotNull(var);
        checkNotNull(clz);
        checkNotNull(loopVar);
        checkNotNull(heap);

        IVar keyVar = null;
        IVar valueVar = null;
        boolean createload = false;

        if (loaders.requiresKey(clz)) {
            /*
             * Requires key such as map.put(). Invoke is not present when no
             * collection access method is invoked. 1) Holder returns a
             * collection held by field. 2) Calling method passes collection to
             * IMC where it is accessed. Then in calling method invoke is not
             * present and it is present in internal method. See:
             * itest.load.VarConflict for example. Don't create load if invoke
             * is not present.
             */
            List<Invoke> invokes = loaders.findAllowedPacks(var, clz, heap);
            if (!invokes.isEmpty()) {

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
                        loaders.findPutInferVar(invokes, var, heap);
                if (inferVarO.isEmpty()) {
                    Expression rExp = invokes.get(0).getExp();
                    inferVarO = loadVars.createPutInferVar(var, rExp, heap);
                    // create new pack for inferVar and exp.
                    if (inferVarO.isPresent()) {
                        Pack pack = modelFactory.createPack(packs.getId(),
                                inferVarO.get(), rExp, false, heap.isIm());
                        heap.addPack(pack);
                    }
                }

                // determine key or value based on invoked method
                if (inferVarO.isPresent()) {

                    String invokedMethod =
                            loaders.getInvokedMethod(invokes.get(0).getExp());
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
                logLoad("forEach [key,value]", var, valueVar, keyVar);
                createload = true;
            }
        } else {
            // requires no key such as list.add()
            valueVar = loopVar;
            logLoad("forEach [value]", var, valueVar, keyVar);
            createload = true;
        }

        if (createload) {
            String loadMethod = loaders.getLoadMethod(clz);
            Load load = loaders.createLoad(var, valueVar, keyVar, loadMethod);
            return Optional.of(load);
        } else {
            return Optional.empty();
        }
    }

    private void logLoad(final String strategy, final IVar var,
            final IVar valueVar, final IVar keyVar) {
        LOG.trace("load strategy: {}", strategy);
        LOG.trace("collection var: {}", var);
        LOG.trace("value var: {}", valueVar);
        LOG.trace("key var: {}", keyVar);
    }
}
