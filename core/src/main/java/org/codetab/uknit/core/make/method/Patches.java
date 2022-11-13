package org.codetab.uknit.core.make.method;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.eclipse.jdt.core.dom.Expression;

/**
 * This class consists exclusively of methods to find Patch.
 *
 * @author Maithilish
 *
 */
public class Patches {

    /**
     * Find patch from list of patches which matches node and patchExp.
     *
     * @param node
     * @param patchExp
     * @param patches
     * @return
     */
    public Optional<Patch> findPatch(final Expression node,
            final Expression patchExp, final List<Patch> patches) {
        return patches.stream().filter(
                p -> p.getNode().equals(node) && p.getExp().equals(patchExp))
                .findFirst();
    }

    /**
     * Find the pack for the node and return the patches of the pack.
     *
     * @param node
     * @param packs
     * @return
     */
    public List<Patch> findPatches(final Expression node,
            final List<Pack> packs) {
        // node is pack.exp
        Optional<Pack> packO = packs.stream().filter(p -> {
            return nonNull(p.getExp()) && p.getExp().equals(node);
        }).findFirst();

        if (packO.isPresent()) {
            return packO.get().getPatches();
        } else {
            return new ArrayList<>();
        }
    }
}
