package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;

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

    public void merge(final Invoke invoke, final Heap heap,
            final Heap internalHeap) {

        LOG.trace("Merge IMC Heap to Caller Heap {}", invoke);
        heap.tracePacks("Caller Packs");
        heap.tracePacks("IMC");

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
        // add param packs whose name differs from arg name
        internalPacks.addAll(0, argParams.getParamsOfDifferentName());

        // add packs created in internal method before invoke index
        heap.addPacks(invokeIndex, internalPacks);
    }
}

/**
 * Updates exp of Invoke that called IM to return var of the IM.
 *
 * @author Maithilish
 *
 */
class InternalReturns {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private NodeFactory nodeFactory;

    private IVar returnVar;
    private Optional<Pack> returnPackO;
    private String returnVarName;

    /**
     * Find and save returnPack, returnVarName and returnVar from internalHeap.
     *
     * @param internalHeap
     */
    public void init(final Heap internalHeap) {
        returnPackO = packs.getReturnPack(internalHeap.getPacks());

        if (returnPackO.isPresent()) {
            try {
                returnVarName = nodes.getName(returnPackO.get().getExp());
                Optional<Pack> varPackO = packs.findByVarName(returnVarName,
                        internalHeap.getPacks());
                if (varPackO.isPresent()) {
                    returnVar = varPackO.get().getVar();
                }
            } catch (CodeException e) {
                returnVarName = null;
                returnVar = null;
            }
        }
    }

    /**
     * Update the exp of the Invoke (i.e. Pack) that called the IM. <code>
     * public void caller() {
     *    Foo foo = factory.createFoo();
     *    Foo otherFoo = internal();
     * }
     *
     * Foo internal() {
     *    Foo foo = factory.createFoo();
     *    return foo;
     * }
     *</code> The Invoke Pack is [var name=otherFoo, exp=internal()], its exp is
     * set to new name foo2 and if there is no name conflict then to foo.
     *
     * The returnVarName is saved in init() before start of merge. On var name
     * conflict the merge assign new name to returnVar.
     *
     * @param invoke
     */
    public void update(final Invoke invoke) {
        if (nonNull(returnVar) && nonNull(returnVarName)) {
            String varName = returnVar.getName();

            if (varName.equals(returnVarName)) {
                /*
                 * Saved returnVarName and name of merged returnVar are same,
                 * assign return var name to invoke's exp, else assign new var
                 * name.
                 */
                if (returnPackO.isPresent()) {
                    invoke.setExp(returnPackO.get().getExp());
                }
            } else {
                invoke.setExp(nodeFactory.createName(varName));
            }
        }
    }
}

/**
 * Manages Arg and Param name mismatch.
 *
 * @author Maithilish
 *
 */
class ArgParams {

    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private Packs packs;
    @Inject
    private Patcher patcher;
    @Inject
    private NodeFactory nodeFactory;

    private List<Optional<Pack>> args;
    private List<Pack> params;

    public void init(final Invoke invoke, final Heap heap,
            final Heap internalHeap) {

        // collect param packs held by internalHeap
        params = packs.filterByVarKinds(internalHeap.getPacks(),
                Kind.PARAMETER);

        // collect arg packs from caller heap
        args = new ArrayList<>();
        if (methods.isInvokable(invoke.getExp())) {
            Expression patchedExp = patcher.copyAndPatch(invoke, heap);
            List<Expression> argList = methods.getArguments(patchedExp);
            for (Expression argExp : argList) {
                try {
                    String name = nodes.getName(argExp);
                    // arg packs are held by heap
                    Optional<Pack> packO =
                            packs.findByVarName(name, heap.getPacks());
                    args.add(packO);
                } catch (CodeException e) {
                    // for 1 to 1 relationship between arg and param list
                    args.add(Optional.empty());
                }
            }
        }
    }

    /**
     * Returns list of parameters with name different from arg name.
     *
     * Example: <code> void internal(String bar) {..}; internal(foo);</code> The
     * caller creates a var for the arg foo and the internal method creates a
     * var for param bar. Since the names differ, the bar is added to the list
     * so that it is merged to caller heap. However if param is also named foo
     * then there is no need for param as arg foo itself can be used in any
     * dependent stmts in the IM.
     *
     * @return
     */
    public List<Pack> getParamsOfDifferentName() {
        List<Pack> list = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            Pack param = params.get(i);
            Optional<Pack> argO = args.get(i);
            if (argO.isPresent() && nonNull(param.getVar())) {
                IVar aVar = argO.get().getVar();
                IVar pVar = param.getVar();
                // arg and param name differs
                if (!aVar.getName().equals(pVar.getName())) {
                    param.setExp(nodeFactory.createName(aVar.getName()));
                    /*
                     * once merged the IMC parameter is effectively local
                     * variable so down grade it to Kind.LOCAL. Also, if not
                     * down graded to LOCAL then they are added to parameter
                     * list of method under test which is used to generate the
                     * test method call and corrupts the test call.
                     */
                    pVar.setKind(Kind.LOCAL);
                    list.add(param);
                }
            }
        }
        return list;
    }
}
