package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SimpleName;

/**
 * Resolves Arg and Param name mismatch.
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
    private ModelFactory modelFactory;
    @Inject
    private Vars vars;

    private List<Optional<Pack>> args;
    private List<Pack> params;

    /**
     * Init list of parameters and args of IM. Normally, IM arguments are
     * collapsed to var names but inline args such as literals don't have vars.
     * If var exists find its pack and add it arg list else create new pack and
     * set arg exp as the exp. The var is created and set by
     * createVarsForInlineArgs() for such packs.
     *
     * @param invoke
     * @param heap
     * @param internalHeap
     */
    public void init(final Invoke invoke, final Heap heap,
            final Heap internalHeap) {

        if (!methods.isInvokable(invoke.getExp())) {
            return;
        }

        // collect param packs held by internalHeap
        params = packs.filterByVarKinds(internalHeap.getPacks(),
                Kind.PARAMETER);

        // collect arg packs from caller heap
        args = new ArrayList<>();

        List<Expression> argList = methods.getArguments(invoke.getExp());
        List<Expression> patchedArgList = methods
                .getArguments(heap.getPatcher().copyAndPatch(invoke, heap));

        for (int i = 0; i < patchedArgList.size(); i++) {

            Expression patchedArgExp = patchedArgList.get(i);
            Expression argExp = argList.get(i);

            if (nodes.is(patchedArgExp, SimpleName.class)) {
                String name = nodes.getName(patchedArgExp);
                // arg packs are held by caller heap
                Optional<Pack> packO =
                        packs.findByVarName(name, heap.getPacks());
                args.add(packO);
            } else {
                /*
                 * The argExp is other than name such as QualifiedName,
                 * literals, infix etc.,
                 */
                Optional<Pack> argPackO =
                        packs.findByExp(argExp, heap.getPacks());
                if (argPackO.isPresent()) {
                    /*
                     * In IM call such as im(foo, 10 + 1) or im(foo.id), the
                     * packs for the infix exp 10 + 1 or QName foo.id are
                     * already created by visitor.
                     *
                     * The createVarsForInlineArgs() stages explicit var for
                     * QName based on param name. To directly use QName in when
                     * etc., we can add another if-else for QName and create var
                     * with QName as var name, then createVarsForInlineArgs()
                     * will not create var based on param name. See:
                     * internal.InlineArgTest.testFieldAccessArg() as an
                     * example.
                     */
                    args.add(argPackO);
                } else {
                    /*
                     * Visitor doesn't create pack for literal. Create new pack
                     * if doesn't exists and use it as arg. Var is not set here.
                     * These are in-line args for which vars are created later
                     * in ArgParams.createVarsForInlineArgs(). Ex: im(foo, 20).
                     * These packs are created only for IM call.
                     */
                    Pack argPack = modelFactory.createPack(packs.getId(), null,
                            argExp, invoke.isInCtlPath(), heap.isIm());
                    heap.addPack(argPack);
                    args.add(Optional.of(argPack));
                }
            }
        }
    }

    /**
     * The pack for inline arg such as literals, infix etc., (Ex:
     * internal(foo,30)) is created in init() but var doesn't exists. Create
     * vars for such args.
     *
     * @param callingMethodVars
     */
    public void createVarsForInlineArgs(final List<IVar> callingMethodVars) {
        for (int i = 0; i < params.size(); i++) {

            Pack param = params.get(i);
            Optional<Pack> argO;
            if (param.getVar().getNatures().contains(Nature.VARARG)
                    && i >= args.size()) {
                /*
                 * If last param is var arg and arg doesn't exist for var arg
                 * param then arg is empty. Ref itest:
                 * imc.vararg.VarArgParameter.noArgForVarArgParam()
                 */
                argO = Optional.empty();
            } else {
                argO = args.get(i);
            }

            // arg var doesn't exists
            if (argO.isPresent() && isNull(argO.get().getVar())
                    && nonNull(param.getVar())) {

                /*
                 * new arg var is clone of matching param var down graded to
                 * LOCAL. If var of same name exists in calling var list, then
                 * rename it. For example, imc(int index) and two calls are made
                 * imc(10) and imc(20). For the first call parameter is used
                 * without renaming with value 10, but for second call parameter
                 * is renamed as index2 with value 20.
                 */
                IVar aVar = param.getVar().deepCopy();
                aVar.setKind(Kind.LOCAL);
                argO.get().setVar(aVar);

                boolean varExists = callingMethodVars.stream()
                        .anyMatch(v -> v.getName().equals(aVar.getName()));
                if (varExists) {
                    String name = aVar.getName();
                    String newName =
                            vars.getIndexedVar(name, callingMethodVars);
                    if (!name.equals(newName)) {
                        aVar.setOldName(name);
                        aVar.setName(newName);
                    }
                }
            }
        }
    }

    /**
     * Update names of the parameters, whose name differs from arg name, to arg
     * names. The processVarPatches() call in PostProcess will create var patch
     * for such packs.
     *
     * Example: void imc(String bar) {..}; imc(foo); The caller heap has arg foo
     * and the IM heap has the param bar. The renaming of the parameter bar as
     * foo enables processVarPatches() to create the var patches down the line
     * which patches the statements in the IM that uses parameter bar to foo.
     * The merge() discards the parameter bar and doesn't merge it with caller
     * heap.
     *
     * @return
     */
    public void updateParamsOfDifferentName(
            final List<IVar> callingMethodVars) {

        for (int i = 0; i < params.size(); i++) {

            Pack param = params.get(i);
            Optional<Pack> argO;
            if (param.getVar().getNatures().contains(Nature.VARARG)
                    && i >= args.size()) {
                argO = Optional.empty();
            } else {
                argO = args.get(i);
            }

            if (argO.isPresent() && argO.get().hasVar() && param.hasVar()) {

                IVar aVar = argO.get().getVar();
                IVar pVar = param.getVar();

                // if arg and param names differ, use param as arg
                if (!aVar.getName().equals(pVar.getName())) {
                    pVar.setOldName(pVar.getName());
                    pVar.setName(aVar.getName());
                }
            }
        }
    }

    /**
     * The forEachVars in internal heap may refers param, in such cases replace
     * it with arg so that loader uses arg var in calling method.
     *
     * Ref itest: load ForEachInternal.callListForEach()
     *
     * @param heap
     */
    public void updateForEachVars(final Heap heap) {
        Map<IVar, IVar> forEachVars = heap.getLoader().getForEachVars();
        for (int i = 0; i < params.size(); i++) {

            IVar param = params.get(i).getVar();
            IVar arg = null;
            Optional<Pack> argO;
            if (param.getNatures().contains(Nature.VARARG)
                    && i >= args.size()) {
                argO = Optional.empty();
            } else {
                argO = args.get(i);
            }
            if (argO.isPresent()) {
                arg = argO.get().getVar();
            }

            if (nonNull(param) && nonNull(arg)
                    && forEachVars.containsKey(param)) {
                IVar loopVar = forEachVars.get(param);
                forEachVars.remove(param);
                forEachVars.put(arg, loopVar);
            }
        }
    }

    /**
     * Find corresponding arg for the param.
     *
     * @param param
     * @return
     */
    public Optional<IVar> getArg(final IVar param) {
        for (int i = 0; i < params.size(); i++) {
            Pack paramPack = params.get(i);
            if (paramPack.getVar().equals(param) && args.get(i).isPresent()) {
                return Optional.of(args.get(i).get().getVar());
            }
        }
        return Optional.empty();
    }

    // REVIEW
    public void normalizeVarArg(final Heap internalHeap,
            final List<IVar> callingMethodVars) {
        if (params.isEmpty()) {
            return;
        }
        int varArgIndex = params.size() - 1;
        Pack lastParam = params.get(varArgIndex);
        if (!lastParam.getVar().is(Nature.VARARG)) {
            return;
        }
        /*
         * No arg for var arg param then nothing do. Ex: args [foo] params
         * [foo,varArg]
         */
        if (args.size() < params.size()) {
            return;
        }

        List<Optional<Pack>> vaArgs = args.subList(varArgIndex, args.size());

        String lastParamName = lastParam.getVar().getName();
        params.remove(lastParam);
        internalHeap.removePack(lastParam);
        for (int i = 0; i < vaArgs.size(); i++) {
            Optional<Pack> vaArgO = vaArgs.get(i);
            if (vaArgO.isPresent()) {
                IVar vaArgVar = vaArgO.get().getVar();
                IVar vaParamVar = modelFactory.createVar(vaArgVar.getKind(),
                        lastParamName + "[" + i + "]", vaArgVar.getType(),
                        vaArgVar.isMock());
                vaParamVar.setOldName(vaParamVar.getName());
                vaParamVar.setName(lastParamName + i);

                Pack vaParamPack =
                        modelFactory.createPack(packs.getId(), vaParamVar, null,
                                lastParam.isInCtlPath(), internalHeap.isIm());
                vaParamPack.setIm(lastParam.isIm());

                params.add(vaParamPack);
                internalHeap.addPack(vaParamPack);
            }
        }

    }

}
