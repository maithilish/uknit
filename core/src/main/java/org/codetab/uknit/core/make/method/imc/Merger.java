package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Heaps;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.TypeLiteral;

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
    @Inject
    private Packs packs;
    @Inject
    private Methods methods;
    @Inject
    private Expressions expressions;
    @Inject
    private Nodes nodes;

    private ArgParams argParams;
    private InternalReturns internalReturns;

    public void merge(final Invoke invoke, final Heap heap,
            final Heap internalHeap) {

        debugIM(invoke);

        heaps.debugPacks("Heap", heap);
        heaps.debugPacks("IM Heap", internalHeap);

        // save states
        internalReturns = di.instance(InternalReturns.class);
        internalReturns.init(internalHeap);

        argParams = di.instance(ArgParams.class);
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
                        internalVar.setOldName(name);
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
                if (iPack instanceof Invoke
                        && ((Invoke) iPack).getReturnType().isPresent()) {
                    if (((Invoke) iPack).getReturnType().get().isVoid()) {
                        internalPacks.add(iPack);
                    } else if (isNull(iPack.getVar())) {
                        // var is not set, but return is not void
                        throw new IllegalStateException(
                                spaceit("var is not set", iPack.toString()));
                    }
                }
            }
        }

        // update param packs whose name differs from arg name
        argParams.updateParamsOfDifferentName(callingMethodVars);

        // update invoke exp to internal return var name
        internalReturns.addIMPatch(invoke, invokeIndex, heap);

        internalReturns.updateExp(invoke, internalHeap);

        // if return var is field then update return var
        internalReturns.updateVar(invoke, heap, internalHeap);

        // add packs created in internal method before invoke index
        internalPacks.forEach(p -> p.setIm(true));
        heap.addPacks(invokeIndex, internalPacks);

        heaps.debugPacks("Heap after merge", heap);
    }

    /**
     * Set call var of MI in internal method. The processEnhancedFor() depends
     * on call var to load collections.
     *
     * Ex: for (String key : meters.keySet()) {..}, when call var is not set
     * then loader wrongly loads a Set to the map else put key and meter to the
     * map.
     *
     * @param heap
     */
    public void processInvokes(final Heap heap) {

        // process all invokes where callVar is not yet set
        List<Invoke> invokeList = packs.filterInvokes(heap.getPacks()).stream()
                .filter(i -> i.getCallVar().isEmpty()
                        && methods.isInvokable(i.getExp()))
                .collect(Collectors.toList());

        for (Invoke invoke : invokeList) {
            Optional<IVar> callVarO = Optional.empty();
            Optional<Expression> patchedCallExpO =
                    heap.getPatcher().copyAndPatchCallExp(invoke, heap);
            if (patchedCallExpO.isPresent()) {
                // if var for name is not found then find var for old name
                String name = expressions.getName(patchedCallExpO.get());
                if (invoke.is(Nature.STATIC_CALL)) {
                    nodes.doNothing();
                } else if (isNull(name)) {
                    /*
                     * this.foo() or String.class.cast(source), ignore
                     * ThisExpression and TypeLiteral as there is no call var in
                     * method invoke.
                     */
                    if (!nodes.is(patchedCallExpO.get(), TypeLiteral.class,
                            ThisExpression.class)) {
                        throw new VarNotFoundException(
                                nodes.exMessage("call var name is null",
                                        patchedCallExpO.get()));
                    }
                } else {
                    try {
                        callVarO = Optional.of(vars.findVarByName(name, heap));
                    } catch (VarNotFoundException e) {
                        callVarO =
                                Optional.of(vars.findVarByOldName(name, heap));
                    }
                }
                // call var is param's corresponding arg
                if (callVarO.isPresent()) {
                    callVarO = argParams.getArg(callVarO.get());
                }
            }
            invoke.setCallVar(callVarO);
        }
    }

    /**
     * Merge loader of internal heap to the loader of calling heap.
     *
     * @param heap
     * @param internalHeap
     */
    public void mergeLoader(final Heap heap, final Heap internalHeap) {
        argParams.updateForEachVars(internalHeap);
        heap.getLoader().merge(internalHeap.getLoader());
    }

    /**
     * Merge patches of internal heap to the calling heap.
     *
     * @param heap
     * @param internalHeap
     */
    public void mergePatcher(final Heap heap, final Heap internalHeap) {
        heap.getPatcher().merge(internalHeap.getPatcher());
    }

    private void debugIM(final Invoke invoke) {
        String varName = "";
        if (nonNull(invoke.getVar())) {
            varName = invoke.getVar().getName();
        }
        LOG.debug("Merge - Invoke {} Var [name={}], Exp [exp={}]",
                invoke.getId(), varName, invoke.getExp());
        LOG.debug("");
        LOG.debug("IMC method: {}", invoke.getExp());
    }
}
