package org.codetab.uknit.core.make.method.var;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.patch.ServiceLoader;
import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

public class VarEnablers {

    @Inject
    private Types types;
    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Packs packs;
    @Inject
    private Vars vars;
    @Inject
    private ServiceLoader serviceLoader;

    public void collectVarNamesOfWhens(final Set<String> names,
            final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        for (Invoke invoke : invokes) {
            Optional<When> whenO = invoke.getWhen();
            if (whenO.isPresent()) {
                When when = whenO.get();
                for (IVar var : when.getReturnVars()) {
                    names.add(var.getName());
                }
                names.addAll(when.getNames());
            }
        }
    }

    public void collectVarNamesOfVerifies(final Set<String> names,
            final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        for (Invoke invoke : invokes) {
            Optional<Verify> verifyO = invoke.getVerify();
            if (verifyO.isPresent()) {
                Verify verify = verifyO.get();
                MethodInvocation mi = verify.getMi();
                for (String name : methods.getNames(mi)) {
                    names.add(name);
                }
            }
        }
    }

    public void collectVarNamesOfReturn(final Set<String> names,
            final Heap heap) {
        Optional<IVar> expectedVar =
                vars.getExpectedVar(packs.getReturnPack(heap.getPacks()), heap);
        expectedVar.ifPresent(v -> {
            if (!types.isBoolean(v.getType())) {
                names.add(v.getName());
            }
        });
    }

    /**
     * Collect var names used in initializers.
     *
     * @param initializerList
     * @param heap
     * @return
     */
    public List<String> collectNamesUsedInInitializers(
            final List<Expression> initializerList, final Heap heap) {

        List<String> names = new ArrayList<>();

        for (Expression ini : initializerList) {
            Optional<Pack> packO =
                    packs.findByExpOrExpName(ini, heap.getPacks());
            if (packO.isPresent()) {
                ini = heap.getPatcher().copyAndPatch(packO.get(), heap);
            }
            PatchService srv = serviceLoader.loadService(ini);
            List<Expression> expsOfIni = srv.getExps(ini);
            List<Expression> linkedInis = new ArrayList<>();
            for (Expression expOfIni : expsOfIni) {
                if (nodes.isSimpleName(expOfIni)) {
                    String name = nodes.getName(expOfIni);
                    names.add(name);
                    /*
                     * Process linked initializers. Find the pack for the name
                     * and collect its initializer if present. Ref itest:
                     * internal.InternalNestedArg.realDiffNameE(). Exclude the
                     * initializer that already exists in initializerList to
                     * avoid cyclic. Ref itest: qname.QName.assignQName().
                     */
                    Optional<Pack> varPackO =
                            packs.findByVarName(name, heap.getPacks());
                    if (varPackO.isPresent() && nonNull(varPackO.get().getVar())
                            && varPackO.get().getVar().getInitializer()
                                    .isPresent()) {
                        Object varIni = varPackO.get().getVar().getInitializer()
                                .get().getInitializer();
                        if (varIni instanceof Expression
                                && !initializerList.contains(varIni)) {
                            linkedInis.add((Expression) varIni);
                        }
                    }
                }
            }
            // recursively process linked initializers.
            List<String> varNames =
                    collectNamesUsedInInitializers(linkedInis, heap);
            names.addAll(varNames);
        }

        return names;
    }

    /**
     * Enable vars used in initializers.
     *
     * @param names
     * @param heap
     */
    public void enabledVarsUsedInInitializers(final List<String> names,
            final Heap heap) {
        for (String name : names) {
            try {
                vars.findVarByName(name, heap).setEnable(true);
            } catch (VarNotFoundException e) {
                try {
                    vars.findVarByOldName(name, heap).setEnable(true);
                } catch (VarNotFoundException e2) {
                }
            }
        }
    }

    /**
     * Get initializers in infer, local and return vars.
     *
     * @param heap
     * @return
     */
    public List<Expression> getInitializers(final Heap heap) {
        List<Expression> list = new ArrayList<>();
        List<IVar> usedVars = vars.filterVars(heap,
                v -> (v.isInferVar() || v.isLocalVar() || v.isReturnVar())
                        && v.isEnable());

        for (IVar usedVar : usedVars) {
            usedVar.getInitializer().ifPresent(i -> {
                if (i.getInitializer() instanceof Expression) {
                    list.add((Expression) i.getInitializer());
                }
            });
        }
        return list;
    }

    public void disableVars(final Set<String> names, final Heap heap) {

        List<IVar> localVars = vars.filterVars(heap,
                v -> (v.isInferVar() || v.isLocalVar() || v.isReturnVar()));

        localVars.forEach(v -> {
            if (!names.contains(v.getName())) {
                v.setEnable(false);
            }
        });
    }

    public void enableFields(final Set<String> names, final Heap heap) {

        List<IVar> fields = vars.filterVars(heap, v -> (v.isField()));

        fields.forEach(v -> {
            if (names.contains(v.getName())) {
                v.setEnable(true);
            }
        });
    }

    public void setEnableFromEnforce(final Heap heap) {

        List<IVar> enforcedVars =
                vars.filterVars(heap, v -> (v.getEnforce().isPresent()));

        enforcedVars.forEach(v -> {
            v.setEnable(v.getEnforce().get());
        });
    }

    /**
     * Create stand-in local var for disabled but used field.
     * @param field
     * @return
     */
    public IVar createStandinVar(final IVar field) {
        IVar var = modelFactory.createVar(Kind.LOCAL, field.getName(),
                field.getType(), field.isMock());
        var.setInitializer(field.getInitializer());
        var.setCreated(false);
        var.setDeepStub(false);
        var.setEnable(true);
        var.setEnforce(false);
        return var;
    }

}
