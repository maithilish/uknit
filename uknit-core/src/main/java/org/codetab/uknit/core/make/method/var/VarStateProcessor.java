package org.codetab.uknit.core.make.method.var;

import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;

public class VarStateProcessor {

    @Inject
    private VarEnabler varEnabler;

    public Set<String> process(final Heap heap) {
        varEnabler.checkEnableState(heap);

        /*
         * disable vars that are not used in when, verify and return
         */
        Set<String> usedNames = varEnabler.collectUsedVarNames(heap);
        varEnabler.updateVarEnableState(usedNames, heap);

        varEnabler.enableVarsUsedInInitializers(heap);

        Set<String> linkedNames = varEnabler.collectLinkedVarNames(heap);
        varEnabler.enableVars(linkedNames, heap);

        Set<String> linkedFieldNames = varEnabler.collectLinkedFieldNames(heap);
        usedNames.addAll(linkedFieldNames);

        // Var initialized with anonymous or lambda are not required.
        Set<String> anonNames = varEnabler.collectAnonNames(heap);
        varEnabler.disableVars(anonNames, heap);
        usedNames.removeAll(anonNames);

        varEnabler.enableFromEnforce(heap);

        return usedNames;
    }
}
