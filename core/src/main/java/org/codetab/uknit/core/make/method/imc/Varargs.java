package org.codetab.uknit.core.make.method.imc;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.exp.Arrays;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Initializer.Kind;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ArrayAccess;

/**
 * Manage IM varargs parameter and its access by IM.
 *
 * Ex: private im(Foo foo, String...va) { foo.appened(va[0]) }
 *
 * @author Maithilish
 *
 */
public class Varargs {

    @Inject
    private Nodes nodes;
    @Inject
    private Arrays arrays;
    @Inject
    private Packs packs;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private NodeFactory nodeFactory;

    /*
     * List of args from calling method that corresponds to varargs param. Ex:
     * if IM imc(Foo foo, String...va) and called as imc(foo, a, b, c) then
     * vaArgs list will have packs P1[var=a], P2[var=b], P3[var=c] since arg a,
     * b and c corresponds to varargs va.
     */
    private List<Optional<Pack>> vaArgs;

    // Holds varargs parameter.
    private Optional<Pack> vaParam;

    public void init(final List<Pack> params, final List<Optional<Pack>> args,
            final List<IVar> callingMethodVars, final Heap internalHeap) {

        boolean initList = true;

        int varArgIndex = -1;
        Pack lastParam = null;

        if (params.isEmpty()) {
            initList = false;
        } else {
            varArgIndex = params.size() - 1;
            lastParam = params.get(varArgIndex);
            if (!lastParam.getVar().is(Nature.VARARG)) {
                initList = false;
            }
        }
        /*
         * No arg for var arg param then nothing do. Ex: args [foo] params
         * [foo,varArg]
         */
        if (args.size() < params.size()) {
            initList = false;
        }

        if (initList) {
            vaArgs = args.subList(varArgIndex, args.size());
            vaParam = Optional.of(lastParam);
        } else {
            vaArgs = new ArrayList<>();
            vaParam = Optional.empty();
        }
    }

    /**
     * Whether IM has varargs parameter. For use before init.
     *
     * @param params
     * @return
     */
    public boolean hasVarargs(final List<Pack> params) {
        if (params.isEmpty()) {
            return false;
        } else {
            return params.get(params.size() - 1).getVar().is(Nature.VARARG);
        }
    }

    /**
     * Whether IM has varargs parameter. For use after init.
     *
     * @return
     */
    public boolean hasVarargs() {
        return vaParam.isPresent();
    }

    /**
     * Whether exp of the pack uses or accesses the varargs. Ex: private im(Foo
     * foo, String...va) { foo.appened(va[0]) }, the foo.append() pack uses
     * varargs.
     *
     * @param pack
     * @return
     */
    public boolean usesVarargs(final Pack pack, final Heap heap) {
        if (vaParam.isPresent() && nodes.is(pack.getExp(), ArrayAccess.class)) {
            String name = arrays.getArrayName((ArrayAccess) pack.getExp(), pack,
                    heap);
            if (packs.hasVar(vaParam)) {
                return packs.getVar(vaParam).getName().equals(name);
            }
        }
        return false;
    }

    /**
     * Create initializer for used varargs. Normally the initializers are
     * created in post process of main method, but the varargs parameter is not
     * merged and lost at the end of merger and is not available for post
     * process, so it's created here.
     *
     * @param pack
     */
    public void createInitializerForVararg(final Pack pack, final Heap heap) {
        ArrayAccess aa = (ArrayAccess) pack.getExp();
        int index = arrays.getIndex(aa, pack, heap);
        if (index >= vaArgs.size()) {
            throw new CriticalException(spaceit("missing varargs arg,",
                    String.valueOf(vaArgs.size()), "args passed to varargs",
                    "but code is trying to access", String.valueOf(index + 1),
                    "items"));
        }
        if (index >= 0) {
            Optional<Pack> vaArg = vaArgs.get(index);
            if (packs.hasVar(vaArg)) {
                IVar var = packs.getVar(vaArg);
                Initializer ini = modelFactory.createInitializer(Kind.EXP,
                        nodeFactory.createName(var.getName(), aa.getAST()));
                pack.getVar().setInitializer(Optional.ofNullable(ini));
            }
        }
    }
}
