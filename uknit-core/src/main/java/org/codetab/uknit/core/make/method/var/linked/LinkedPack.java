package org.codetab.uknit.core.make.method.var.linked;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.Name;

/**
 * Methods to get packs are linked in some way.
 *
 * @author Maithilish
 *
 */
public class LinkedPack {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private Expressions expressions;
    @Inject
    private Patcher patcher;

    /**
     * Returns list of packs where vars are linked from head list. Ex: <code>
     *  Date date = foo.date();
        Date date2 = foo.date();
        Date date3 = date2;
        Date date4 = date3;
     * </code> for date4 pack, the linked packs includes date2, date3 and date4
     * but not date.
     *
     * Note: As long as the exp of the pack yields Name or SimpleName then packs
     * are recursively searched for pack with matching name and if found added
     * to list. Recursion terminates when exp is not name, but some other exp.
     * The linkedPacks has at least one pack as first pack is input pack itself.
     * The order of linkedPacks is reverse of the pack list in heap (or reverse
     * of order of visit) with latest pack first.
     *
     * @param pack
     * @param heap
     * @return
     */
    public List<Pack> getLinkedVarPacks(final Pack pack, final Heap heap) {

        List<Pack> linkedPacks = new ArrayList<>();
        linkedPacks.add(pack);

        /*
         * no linked packs if exp is FieldAccess such as return this.city; Pack
         * [var="apple", exp=this.city]
         */
        if (nonNull(pack.getExp())
                && !nodes.is(pack.getExp(), FieldAccess.class)) {
            // as long as exp is Name or SimpleName continues else terminates
            Expression patchedExp = patcher.copyAndPatch(pack, heap);
            String name = expressions.getName(patchedExp);
            if (nonNull(name)) {
                // get linked packs from head list
                List<Pack> headList = packs.headList(pack, heap.getPacks());
                Optional<Pack> linkedPackO =
                        packs.findByVarName(name, headList);
                if (linkedPackO.isPresent()) {
                    /*
                     * if pack is not linkedPack (cyclic, to avoid
                     * StackOverflow), recursively find matching packs and add
                     */
                    if (!linkedPackO.get().equals(pack)) {
                        linkedPacks.addAll(
                                getLinkedVarPacks(linkedPackO.get(), heap));
                    }
                }
            }
        }
        return linkedPacks;
    }

    /**
     * Reverse traverse linked packs and return the first initializer found else
     * empty. If initializer is var name then find and return initializer of the
     * var.
     *
     * @param pack
     * @param heap
     * @return
     */
    public Optional<Initializer> getLinkedInitializer(final Pack pack,
            final Heap heap) {
        List<Pack> linkedPacks = getLinkedVarPacks(pack, heap);
        for (int i = linkedPacks.size() - 1; i >= 0; i--) {
            Pack linkedPack = linkedPacks.get(i);
            Optional<Initializer> initializer =
                    linkedPack.getVar().getInitializer();
            if (initializer.isPresent()) {
                /*
                 * if ini is name then get the var and returns its ini. Ex:
                 * P2[var=apple, exp=array[0], ini=a] P1[var=a, ini="foo"] then
                 * ini of apple is foo.
                 */
                if (initializer.get().getInitializer() instanceof Name) {
                    String name = nodes.getName(
                            (Expression) initializer.get().getInitializer());
                    Optional<Pack> targetVar =
                            packs.findByVarName(name, heap.getPacks());
                    if (targetVar.isPresent() && targetVar.get().hasVar()) {
                        return targetVar.get().getVar().getInitializer();
                    }
                }
                return initializer;
            }
        }
        return Optional.empty();
    }

}
