package org.codetab.uknit.core.make.method;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.SimpleName;

/**
 * This class consists exclusively of methods to find or work with IVar.
 *
 * @author Maithilish
 *
 */
public class Vars {

    @Inject
    private Nodes nodes;

    /**
     * Get list of vars from packs.
     *
     * @param heap
     * @return
     */
    public List<IVar> getVars(final Heap heap) {
        return heap.getVars();
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
        return heap.getVars().stream()
                .filter(v -> filterKinds.contains(v.getKind()))
                .collect(Collectors.toList());
    }

    public List<IVar> filterVars(final Heap heap,
            final Predicate<IVar> predicate) {
        return heap.getVars().stream().filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Find first var matching the var name.
     *
     * @param packs
     * @param name
     * @return
     */
    public IVar findVarByName(final String name, final Heap heap) {
        Optional<IVar> varO = heap.getVars().stream()
                .filter(v -> v.getName().equals(name)).findFirst();
        if (varO.isPresent()) {
            return varO.get();
        } else {
            throw new VarNotFoundException(name);
        }
    }

    /**
     * Get var returned by return statement which is used as expected var in
     * assert statement.Throws IllegalStateException if return exp is not mapped
     * to var.
     *
     * @param returnPack
     * @param packs
     * @return
     */
    public Optional<IVar> getExpectedVar(final Optional<Pack> returnPack,
            final Heap heap) {
        if (returnPack.isPresent()) {
            if (nodes.is(returnPack.get().getExp(), SimpleName.class)) {
                String name = nodes.getName(
                        nodes.as(returnPack.get().getExp(), SimpleName.class));
                return Optional.ofNullable(findVarByName(name, heap));
            } else {
                throw new IllegalStateException(nodes.exMessage(
                        "can't map return exp to var, expected SimpleName but found",
                        returnPack.get().getExp()));
            }
        } else {
            return Optional.empty();
        }
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
}
