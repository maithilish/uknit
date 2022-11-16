package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
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
    private Patcher patcher;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Vars vars;

    private List<Optional<Pack>> args;
    private List<Pack> params;

    /**
     * Init list of parameters and args of IM. Normally, IM arguments are
     * collapsed to var names but inline args such as literals doesn't have
     * vars. If var exists find its pack and add it arg list else create new
     * pack and set arg exp as the exp. The var is created and set by
     * createVarsForInlineArgs() for such packs.
     *
     * @param invoke
     * @param heap
     * @param internalHeap
     */
    public void init(final Invoke invoke, final Heap heap,
            final Heap internalHeap) {

        // collect param packs held by internalHeap
        params = packs.filterByVarKinds(internalHeap.getPacks(),
                Kind.PARAMETER);

        // collect arg packs from caller heap
        args = new ArrayList<>();

        if (methods.isInvokable(invoke.getExp())) {
            // get arg list after patch

            List<Expression> argList = methods.getArguments(invoke.getExp());

            List<Expression> patchedArgList =
                    methods.getArguments(patcher.copyAndPatch(invoke, heap));

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
                     * literals, infix etc., Create new pack if doesn't exists
                     * and use it as arg. These are in-line args for which vars
                     * are created later in ArgParams.createVarsForInlineArgs().
                     * Ex: im(foo, 20).
                     *
                     * This stages explicit var for QName based on param name in
                     * createVarsForInlineArgs(). To directly use QName in when
                     * etc., we can add another if-else for QName and create var
                     * with QName as var name, then createVarsForInlineArgs()
                     * will not create var based on param name. See:
                     * internal.InlineArgTest.testFieldAccessArg() as an
                     * example.
                     */
                    Optional<Pack> argPackO =
                            packs.findByExp(argExp, heap.getPacks());
                    if (argPackO.isPresent()) {
                        /*
                         * In IM call such as im(foo, 10 + 1) or im(foo.id), the
                         * packs for the infix exp 10 + 1 or QName foo.id are
                         * already created by visitor.
                         */
                        args.add(argPackO);
                    } else {
                        /*
                         * packs for literals are not created by visitor so
                         * create it which is specific to IM call.
                         */
                        Pack argPack = modelFactory.createPack(null, argExp,
                                invoke.isInCtlPath());
                        heap.addPack(argPack);
                        args.add(Optional.of(argPack));
                    }
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
            Optional<Pack> argO = args.get(i);

            // arg var doesn't exists
            if (argO.isPresent() && isNull(argO.get().getVar())
                    && nonNull(param.getVar())) {

                /*
                 * new arg var is clone of matching param var down graded to
                 * LOCAL. If var of same name exists in calling var list, then
                 * rename it. For example, foo(int index) and two calls are made
                 * foo(10) and foo(20). For first call parameter is used without
                 * renaming with value 10, but for second call parameter is
                 * renamed as index2 with value 20.
                 */
                IVar aVar = param.getVar().clone();
                aVar.setKind(Kind.LOCAL);
                argO.get().setVar(aVar);

                boolean varExists = callingMethodVars.stream()
                        .anyMatch(v -> v.getName().equals(aVar.getName()));
                if (varExists) {
                    String name = aVar.getName();
                    String newName =
                            vars.getIndexedVar(name, callingMethodVars);
                    if (!name.equals(newName)) {
                        aVar.setRealName(name);
                        aVar.setName(newName);
                    }
                }
            }
        }
    }

    /**
     * Update names of the parameters, whose name differs from arg name, to arg
     * names. The processVarNameChange() call in MethodMaker will create var
     * patch for such packs.
     *
     * Example: void internal(String bar) {..}; internal(foo); The caller
     * creates the arg foo and the IM creates the param bar. However merge()
     * discards the parameter bar and doesn't merge it with caller heap. The
     * renaming of the parameter bar as foo enables processVarNameChange() to
     * create the var patches down the line which patches the statements in the
     * IM that uses parameter bar to foo.
     *
     * @return
     */
    public void updateParamsOfDifferentName(
            final List<IVar> callingMethodVars) {

        for (int i = 0; i < params.size(); i++) {

            Pack param = params.get(i);
            Optional<Pack> argO = args.get(i);

            if (argO.isPresent() && nonNull(argO.get().getVar())
                    && nonNull(param.getVar())) {

                IVar aVar = argO.get().getVar();
                IVar pVar = param.getVar();

                // if arg and param names differ, use param as arg
                if (!aVar.getName().equals(pVar.getName())) {
                    pVar.setRealName(pVar.getName());
                    pVar.setName(aVar.getName());
                }
            }
        }
    }
}
