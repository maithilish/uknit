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

        // list of internal packs to merge
        List<Pack> internalPacks = new ArrayList<>();

        InternalReturns internalReturns = di.instance(InternalReturns.class);
        ArgParams argParams = di.instance(ArgParams.class);

        internalReturns.init(internalHeap);
        argParams.init(invoke, heap, internalHeap);

        int invokeIndex = heap.getPacks().indexOf(invoke);

        // vars.getVars() is unmodifiable, create new list
        List<IVar> callingMethodVars = new ArrayList<>(vars.getVars(heap));

        for (Pack iPack : internalHeap.getPacks()) {
            IVar internalVar = iPack.getVar();
            if (nonNull(internalVar) && internalVar.is(Kind.RETURN)) {
                continue;
            }
            if (nonNull(internalVar)) {
                if (internalVar.is(Kind.LOCAL)) {
                    String name = internalVar.getName();
                    String newName =
                            vars.getIndexedVar(name, callingMethodVars);
                    /*
                     * when internal method var name conflicts with calling
                     * method var then change name to newName. Ex: if both
                     * methods has var named date then the getIndexedVar()
                     * returns date2 and internal method var name is changed to
                     * date2.
                     */
                    if (!name.equals(newName)) {
                        internalVar.setName(newName);
                    }
                    // collect packs created in internal method
                    internalPacks.add(iPack);
                    callingMethodVars.add(internalVar);
                }
                /*
                 * VarNames returns unique name across internal calls, so name
                 * never conflicts
                 */
                if (internalVar.is(Kind.INFER)) {
                    internalPacks.add(iPack);
                }
            } else {
                /**
                 * var is null, but pack is invoke and return type is void then
                 * it is candidate for verify, add it.
                 * <p>
                 * Ex: webClient.getOptions().setJavaScriptEnabled(false); in
                 * this the webClient.getOptions() is patched to infer var
                 * option but the option.setJavaScriptEnabled() returns void.
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

        internalReturns.update(invoke);
        internalPacks.addAll(0, argParams.update());

        // add packs created in internal method before invoke index
        heap.addPacks(invokeIndex, internalPacks);
    }
}

class InternalReturns {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private NodeFactory nodeFactory;

    private IVar var;
    private Optional<Pack> returnPackO;
    private String returnVarName;

    public void init(final Heap internalHeap) {
        returnPackO = packs.getReturnPack(internalHeap.getPacks());

        if (returnPackO.isPresent()) {
            try {
                returnVarName = nodes.getName(returnPackO.get().getExp());
                Optional<Pack> varPackO = packs.findByVarName(returnVarName,
                        internalHeap.getPacks());
                if (varPackO.isPresent()) {
                    var = varPackO.get().getVar();
                }
            } catch (CodeException e) {
                returnVarName = null;
                var = null;
            }
        }
    }

    public void update(final Invoke invoke) {
        if (nonNull(var) && nonNull(returnVarName)) {
            String varName = var.getName();

            if (varName.equals(returnVarName)) {
                if (returnPackO.isPresent()) {
                    invoke.setExp(returnPackO.get().getExp());
                }
            } else {
                invoke.setExp(nodeFactory.createName(varName));
            }
        }
    }
}

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

        // param packs are held by internalHeap
        params = packs.filterByVarKinds(internalHeap.getPacks(),
                Kind.PARAMETER);

        args = new ArrayList<>();
        if (methods.isInvokable(invoke.getExp())) {
            Expression patchedExp = patcher.copyAndPatch(invoke.getExp(), heap);
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

    public List<Pack> update() {
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
                     * down graded to local then they are added to parameter
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
