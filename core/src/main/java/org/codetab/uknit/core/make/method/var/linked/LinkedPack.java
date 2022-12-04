package org.codetab.uknit.core.make.method.var.linked;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;

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
    private Expressions expressions;

    /**
     * Returns list of packs where vars are linked. Ex: <code>
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
     * @param packList
     * @return
     */
    public List<Pack> getLinkedVarPacks(final Pack pack,
            final List<Pack> packList) {

        List<Pack> linkedPacks = new ArrayList<>();
        linkedPacks.add(pack);

        if (nonNull(pack.getExp())) {
            // as long as exp is Name or SimpleName continues else terminates
            String name = expressions.getName(pack.getExp());
            if (nonNull(name)) {
                Optional<Pack> linkedPackO =
                        packs.findByVarName(name, packList);
                if (linkedPackO.isPresent()) {
                    // recursively find matching packs and on return add
                    linkedPacks.addAll(
                            getLinkedVarPacks(linkedPackO.get(), packList));
                }
            }
        }
        return linkedPacks;
    }

}
