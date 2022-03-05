package org.codetab.uknit.core.make.method.visit;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.Expression;

public class VarEnabler {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private VarEnablers varEnablers;

    /**
     * Update enable field of vars.
     * <p>
     * It collects the names of vars used whens, verifies and return var. Then
     * it marks all the vars that are not in names list as disabled. Next, it
     * collects the initializer expressions assigned to enabled vars and marks
     * the vars used by such expression also as enabled. Finally, it overrides
     * enable state with state - true or false - of enforce field.
     * @param heap
     */
    public void updateVarEnableState(final Heap heap) {

        // disable vars that are not used in when, verify and return
        Set<String> names = new HashSet<>();
        varEnablers.collectVarNamesOfWhens(names, heap);
        varEnablers.collectVarNamesOfVerifies(names, heap);
        varEnablers.collectVarNamesOfReturn(names, heap);

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
        List<IVar> localVars = heap.getVars(
                v -> (v.isInferVar() || v.isLocalVar() || v.isReturnVar())
                        || v.isParameter());
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
        IVar var = heap.findVar(name);
        var.setEnforce(value);
    }
}
