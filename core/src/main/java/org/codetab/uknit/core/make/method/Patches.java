package org.codetab.uknit.core.make.method;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

/**
 * This class consists exclusively of methods to find Patch.
 *
 * @author Maithilish
 *
 */
public class Patches {

    /**
     * Get patch for a node and expression.
     * @param node
     * @param exp
     * @return
     */
    public Optional<Patch> findPatch(final ASTNode node, final Expression exp,
            final List<Pack> packs) {
        // node is pack.exp, exp is patch.exp
        Optional<Pack> packO = packs.stream().filter(p -> {
            return nonNull(p.getExp()) && p.getExp().equals(node);
        }).findFirst();
        if (packO.isPresent()) {
            return packO.get().getPatches().stream()
                    .filter(pch -> pch.getExp().equals(exp)).findFirst();
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get list of patches for a node.
     * @param node
     * @return
     */
    public List<Patch> findPatches(final ASTNode node, final List<Pack> packs) {
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
