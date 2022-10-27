package org.codetab.uknit.core.make.method;

import static java.util.Objects.nonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Pack;

public class Heaps {

    /**
     * Get list of vars from packs.
     *
     * @param heap
     * @return
     */
    public List<IVar> getVars(final Heap heap) {
        return heap.getPacks().stream().map(Pack::getVar)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Get list of vars of specific kinds (LOCAL, INFER etc.,)
     *
     * @param heap
     * @param kinds
     * @return
     */
    public List<IVar> getVarsOfKind(final Heap heap, final Kind... kinds) {
        List<Kind> filterKinds = Arrays.asList(kinds);
        return heap.getPacks().stream().map(Pack::getVar)
                .filter(v -> nonNull(v) && filterKinds.contains(v.getKind()))
                .collect(Collectors.toList());
    }

    /**
     * Search for named var is var list and if exist then return next available
     * var name. Search is across all types of vars such as fields, parameters,
     * local, infer vars etc.,
     * <p>
     * For example: if name is date and var named date doesn't exists then
     * returns date else returns date2. If also date2 exists then return date3
     * and so on. It never returns date1 as index 1 is skipped.
     * @param name
     * @return
     */
    public String getIndexedVar(final String name, final List<IVar> vars) {
        /*
         * first name, foo, is without index, rest foo2 foo3 ..., index starts
         * with 1 but 1 itself is ignored
         */
        int index = 1;
        List<IVar> varList =
                vars.stream().filter(v -> v.getName().startsWith(name))
                        .collect(Collectors.toList());
        String varName = name;
        while (true) {
            final String n = varName;
            if (varList.stream().anyMatch(v -> v.getName().equals(n))) {
                index++;
                varName = name.concat(String.valueOf(index));
            } else {
                break;
            }
        }
        return varName;
    }

    // FIXME Pack - remove this
    // public Optional<When> findWhen(final String methodSignature,
    // final List<When> whens) {
    // return whens.stream()
    // .filter(w -> w.getMethodSignature().equals(methodSignature))
    // .findAny();
    // }
}
