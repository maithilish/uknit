package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.method.Heaps;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;

/**
 * Merges IM Heap on return of IM with Invoker Heap.
 *
 * @author Maithilish
 *
 */
public class Merger {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Vars vars;
    @Inject
    private DInjector di;
    @Inject
    private Heaps heaps;

    public void merge(final Invoke invoke, final Heap heap,
            final Heap internalHeap) {

        LOG.debug("Merge IM Heap {}", invoke);
        heaps.debugPacks("[ Heap Packs ]", heap);
        heaps.debugPacks("[ IM Heap Packs ]", internalHeap);

        // save states
        InternalReturns internalReturns = di.instance(InternalReturns.class);
        internalReturns.init(internalHeap);

        ArgParams argParams = di.instance(ArgParams.class);
        argParams.init(invoke, heap, internalHeap);

        int invokeIndex = heap.getPacks().indexOf(invoke);

        // list of internal packs to merge
        List<Pack> internalPacks = new ArrayList<>();

        // create modifiable list of vars to check name conflict
        List<IVar> callingMethodVars = new ArrayList<>(vars.getVars(heap));

        /*
         * create missing inline vars for literals etc., in IMC args and reload
         * the calling method vars list.
         */
        argParams.createVarsForInlineArgs(callingMethodVars);

        callingMethodVars = new ArrayList<>(vars.getVars(heap));

        for (Pack iPack : internalHeap.getPacks()) {
            IVar internalVar = iPack.getVar();
            if (nonNull(internalVar) && internalVar.is(Kind.RETURN)) {
                continue;
            }
            if (nonNull(internalVar)) {
                /*
                 * When IM var (Local or Infer) name conflicts with calling
                 * method vars change the IM var name. In case of Infer vars if
                 * name is obtained from VarNames.getInferVarName() then name
                 * (apple, grape etc.,) is unique across calling and IM calls
                 * but if name is based on type then name may conflict. Ex:
                 * client.options().enabled(..); here for client.options() the
                 * infer var name is options and it will conflict if IM is
                 * called multiple times.
                 */
                if (internalVar.is(Kind.LOCAL) || internalVar.is(Kind.INFER)) {
                    /*
                     * when internal method var name conflicts with calling
                     * method var then change name to newName. Ex: if both
                     * methods has var named date then the getIndexedVar()
                     * returns date2 and internal method var name is changed to
                     * date2.
                     */
                    String name = internalVar.getName();
                    String newName =
                            vars.getIndexedVar(name, callingMethodVars);
                    if (!name.equals(newName)) {
                        internalVar.setName(newName);
                    }

                    // collect packs created in internal method
                    internalPacks.add(iPack);
                    /*
                     * add internalVar so that its name also taken into account
                     * by future calls togetIndexedVar()
                     */
                    callingMethodVars.add(internalVar);
                }
            } else {
                /**
                 * var is null, but pack is invoke and return type is void then
                 * it is candidate for verify, add it.
                 * <p>
                 * Ex: client.getOptions().setFooEnabled(false); in this the
                 * client.getOptions() is patched to infer var option but the
                 * option.setFooEnabled() returns void.
                 */
                boolean illegalState = true;
                if (iPack instanceof Invoke
                        && ((Invoke) iPack).getReturnType().isPresent()) {
                    if (((Invoke) iPack).getReturnType().get().isVoid()) {
                        internalPacks.add(iPack);
                        illegalState = false;
                    }
                }
                if (illegalState) {
                    throw new IllegalStateException(
                            spaceit("var is not set", iPack.toString()));
                }
            }
        }

        // update invoke exp to internal return var name
        internalReturns.update(invoke);

        // update param packs whose name differs from arg name
        argParams.updateParamsOfDifferentName(callingMethodVars);

        // add packs created in internal method before invoke index
        heap.addPacks(invokeIndex, internalPacks);

        heaps.debugPacks("[ Heap Packs after merge ]", heap);
    }
}
