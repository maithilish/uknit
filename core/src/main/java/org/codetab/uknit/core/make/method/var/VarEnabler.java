package org.codetab.uknit.core.make.method.var;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.var.linked.LinkedPack;
import org.codetab.uknit.core.make.method.visit.Packer;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.eclipse.jdt.core.dom.Expression;

public class VarEnabler {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private VarEnablers varEnablers;
    @Inject
    private Vars vars;
    @Inject
    private Packer packer;
    @Inject
    private LinkedPack linkedPack;

    /**
     * Update enable field of vars.
     * <p>
     * It collects the names of vars used whens, verifies and return var. Then
     * it marks all the vars that are not in names list as disabled
     *
     * Note: when this method is called all vars are enabled by default and this
     * method disables the var that are not used.
     *
     * @param names
     * @param heap
     */
    public void updateVarEnableState(final Set<String> names, final Heap heap) {

        // disable vars that are not used in when, verify and return
        varEnablers.disableVars(names, heap);
    }

    /**
     * Enable named vars.
     *
     * @param names
     * @param heap
     */
    public void enableVars(final Set<String> names, final Heap heap) {
        List<IVar> varList = heap.getVars();
        varList.stream().filter(v -> names.contains(v.getName()))
                .forEach(v -> v.setEnable(true));
    }

    /**
     * Collects the initializer expressions assigned to enabled vars and marks
     * the vars used by such expression also as enabled.
     *
     * @param heap
     * @return
     */
    public List<String> enableVarsUsedInInitializers(final Heap heap) {
        // enable vars used in initializer assigned to vars enabled above
        List<Expression> exps = varEnablers.getInitializers(heap);
        List<String> names =
                varEnablers.collectNamesUsedInInitializers(exps, heap);
        varEnablers.enabledVarsUsedInInitializers(names, heap);
        return names;
    }

    public void enableFromEnforce(final Heap heap) {
        // finally, override enable with enforce (enables or disables)
        varEnablers.setEnableFromEnforce(heap);
    }

    /**
     * Check whether enabled field of all local vars - infer, local, return and
     * parameters - is true. If not, log and throw error.
     * <p>
     * By default local vars are enabled. As a rule, only
     * VarEnabler.enableVars() method is allowed to update enable state and no
     * other code. Fields are exempted from this.
     * @param heap
     */
    public void checkEnableState(final Heap heap) {
        List<IVar> localVars = vars.filterVars(heap, v -> (v.isInferVar()
                || v.isLocalVar() || v.isReturnVar() || v.isParameter()));
        List<IVar> disabledVars = localVars.stream().filter(v -> !v.isEnable())
                .collect(Collectors.toList());
        if (disabledVars.size() > 0) {
            LOG.error("vars with illegal enable state:");
            disabledVars.forEach(v -> LOG.error("{}", v));
            throw new CodeException(
                    "Illegal state: some vars are disabled, see log");
        }
    }

    /**
     * Set enforce field of var to value (enables or disables).
     * <p>
     * Enforce field of var is used override the state of enable field.
     * @param name
     * @param value
     * @param heap
     */
    public void enforce(final String name, final Optional<Boolean> value,
            final Heap heap) {
        IVar var = vars.findVarByName(name, heap);
        var.setEnforce(value.get());
    }

    /**
     * Add stand-in local var for disabled but used field.
     *
     * FIXME N - review this later and add tests
     *
     * @param names
     * @param heap
     * @return
     */
    public List<Pack> addLocalVarForDisabledField(final Set<String> names,
            final Heap heap) {
        List<Pack> standinPacks = new ArrayList<>();

        for (String name : names) {
            Optional<IVar> varO;
            try {
                varO = Optional.of(vars.findVarByName(name, heap));
            } catch (VarNotFoundException e) {
                varO = Optional.empty();
            }
            if (varO.isPresent() && varO.get().isField()) {
                IVar field = varO.get();
                boolean createStandin = false;
                if (field.isEnable() && !field.isMock()) {
                    createStandin = true;
                }
                if (!field.isEnable()) {
                    createStandin = true;
                }
                if (createStandin) {
                    IVar var = varEnablers.createStandinVar(varO.get());
                    Pack standinPack = packer.packStandinVar(var, true, heap);
                    standinPacks.add(standinPack);
                }
            }
        }
        return standinPacks;
    }

    public Set<String> collectUsedVarNames(final Heap heap) {
        Set<String> names = new HashSet<>();
        varEnablers.collectVarNamesOfWhens(names, heap);
        varEnablers.collectVarNamesOfVerifies(names, heap);
        varEnablers.collectVarNamesOfReturn(names, heap);
        return names;
    }

    /**
     * The var assigned in Pack and is enabled then collect names of other vars
     * that are used to define the var.
     *
     * Ex: Foo foo1 = new Foo(); foo2 = foo1; if foo2 is enabled then enable
     * foo1 also.
     *
     * @param heap
     * @return
     */
    public Set<String> collectLinkedVarNames(final Heap heap) {
        Set<String> names = new HashSet<>();
        // list packs with enabled var
        List<Pack> enabledPacks = heap.getPacks().stream().filter(p -> {
            return nonNull(p.getVar()) && p.getVar().isEnable();
        }).collect(Collectors.toList());

        for (Pack enabledPack : enabledPacks) {
            /*
             * Get linked vars packs and enable the vars.
             *
             * Exclude fields, ref itest: load.SuperFieldConflict.foo().
             */
            List<Pack> linkPacks =
                    linkedPack.getLinkedVarPacks(enabledPack, heap);
            linkPacks.stream().filter(p -> {
                return nonNull(p.getVar())
                        && !p.getVar().getKind().equals(Kind.FIELD);
            }).forEach(p -> {
                names.add(p.getVar().getName());
            });
        }
        return names;
    }

    /**
     * For enabled local vars, get linked fields and collect their names.
     *
     * Ex: The super method returns field it is assigned to local var, if local
     * var is enabled then enable linked super field too. Ref itest:
     * load.SuperGet.getSuperField().
     *
     * @param heap
     * @return
     */
    public Set<String> collectLinkedFieldNames(final Heap heap) {
        Set<String> names = new HashSet<>();
        // list packs with enabled var - only local vars not fields
        List<Pack> enabledPacks = heap.getPacks().stream().filter(p -> {
            return nonNull(p.getVar()) && p.getVar().isEnable()
                    && !p.getVar().isField();
        }).collect(Collectors.toList());

        for (Pack enabledPack : enabledPacks) {
            // get linked packs, filter fields and collect names
            List<Pack> linkPacks =
                    linkedPack.getLinkedVarPacks(enabledPack, heap);
            linkPacks.stream().filter(p -> {
                return nonNull(p.getVar())
                        && p.getVar().getKind().equals(Kind.FIELD);
            }).forEach(p -> {
                names.add(p.getVar().getName());
            });
        }
        return names;
    }

    public void disableVars(final Set<String> names, final Heap heap) {
        List<IVar> varList = heap.getVars();
        varList.stream().filter(v -> names.contains(v.getName()))
                .forEach(v -> v.setEnable(false));
    }

    /**
     * Get names of vars assigned with anonymous or lambda.
     *
     * @param heap
     * @return
     */
    public Set<String> collectAnonNames(final Heap heap) {
        Set<String> names = new HashSet<>();
        List<Pack> enabledPacks = heap.getPacks().stream().filter(p -> {
            return nonNull(p.getVar()) && p.getVar().isEnable()
                    && !p.getVar().isField();
        }).collect(Collectors.toList());

        for (Pack enabledPack : enabledPacks) {
            if (enabledPack.is(Nature.ANONYMOUS)) {
                names.add(enabledPack.getVar().getName());
            }
        }
        return names;
    }
}
