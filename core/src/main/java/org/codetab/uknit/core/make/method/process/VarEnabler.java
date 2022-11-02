package org.codetab.uknit.core.make.method.process;

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
import org.codetab.uknit.core.make.method.visit.Packer;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.Expression;

public class VarEnabler {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private VarEnablers varEnablers;
    @Inject
    private Vars vars;
    @Inject
    private Packer packer;

    /**
     * Update enable field of vars.
     * <p>
     * It collects the names of vars used whens, verifies and return var. Then
     * it marks all the vars that are not in names list as disabled. Next, it
     * collects the initializer expressions assigned to enabled vars and marks
     * the vars used by such expression also as enabled. Finally, it overrides
     * enable state with state - true or false - of enforce field.
     * @param names
     * @param heap
     */
    public void updateVarEnableState(final Set<String> names, final Heap heap) {

        // disable vars that are not used in when, verify and return
        varEnablers.disableVars(names, heap);

        // enable vars used in initializer assigned to vars enabled above
        List<Expression> exps = varEnablers.getInitializers(heap);
        varEnablers.enableVarsUsedInInitializers(exps, heap);

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
        var.setEnforce(value);
    }

    /**
     * Add stand-in local var for disabled but used field.
     * @param names
     * @param heap
     */
    public void addLocalVarForDisabledField(final Set<String> names,
            final Heap heap) {
        for (String name : names) {
            Optional<IVar> field;
            try {
                field = Optional.of(vars.findVarByName(name, heap));
            } catch (VarNotFoundException e) {
                field = Optional.empty();
            }
            if (field.isPresent() && !field.get().isEnable()) {
                IVar var = varEnablers.createStandinVar(field.get());
                packer.packStandinVar(var, true, heap);
            }
        }
    }

    public Set<String> collectUsedVarNames(final Heap heap) {
        Set<String> names = new HashSet<>();
        varEnablers.collectVarNamesOfWhens(names, heap);
        varEnablers.collectVarNamesOfVerifies(names, heap);
        varEnablers.collectVarNamesOfReturn(names, heap);
        return names;
    }
}
