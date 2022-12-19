package org.codetab.uknit.core.make.method.patch.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.ServiceLoader;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

public class Patchers {

    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Packs packs;

    public void patchExpWithName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap,
            final Consumer<Expression> consumer) {
        // exp such as MI.getExpression() may be null
        if (isNull(node)) {
            return;
        }

        Optional<Pack> patchPackO =
                packs.findPatchPack(pack, node, heap.getPacks());
        if (patchPackO.isPresent()) {
            IVar var = patchPackO.get().getVar();
            if (nonNull(var)) {
                /*
                 * Ex: {foo.name()} the ArrayInitializer has MI exp foo.name()
                 * which is inferred to var apple and patch it as {apple}.
                 */
                Name name = node.getAST().newName(var.getName());
                consumer.accept(name);
            } else {
                /*
                 * Ex: {new Foo(bar.name())}; the ArrayInitializer has
                 * ClassInstanceCreation exp new Foo(...) which is not inferred
                 * to any var. However it has a MI exp bar.name() which has to
                 * be patched. Hence, load service for ClassInstanceCreation and
                 * delegate to it to patch the arg bar.name().
                 *
                 * If var is null for exp in patch pack then load service for
                 * node (one of the exp of parent node) and delegate to it.
                 */
                PatchService patchService = serviceLoader.loadService(node);
                patchService.patch(pack, node, copy, heap);
            }
        }

        /*
         * Get IM patch from patches map and apply. Ex: x = internal().get();
         * patch internal() to its return var name.
         */
        IVar var = heap.getPatcher().getPatches().get(node);
        if (nonNull(var)) {
            Name name = node.getAST().newName(var.getName());
            consumer.accept(name);
        }
    }

    /**
     * Patch list of exps such as mi.arguments(). The expsCopy list should be
     * the list obtained from the copy and not any other list otherwise patch is
     * not reflected in node copy. The function passed to the method
     * patchExpWithName() removes the exp and inserts name in its place.
     *
     * @param pack
     * @param exps
     * @param expsCopy
     * @param heap
     */
    public void patchExpsWithName(final Pack pack, final List<Expression> exps,
            final List<Expression> expsCopy, final Heap heap) {
        for (int i = 0; i < exps.size(); i++) {
            final int expIndex = i;
            Expression exp = wrappers.unpack(exps.get(expIndex));
            Expression expCopy = wrappers.unpack(expsCopy.get(expIndex));
            patchExpWithName(pack, exp, expCopy, heap, (name) -> {
                expsCopy.remove(expIndex);
                expsCopy.add(expIndex, name);
            });
        }
    }

    /**
     *
     * Apply pack level patch. Find the patch, from the list, whose index
     * matches index and patch its name to copy with method passed to the
     * consumer.
     *
     * @param node
     * @param copy
     * @param patches
     * @param index
     * @param consumer
     */
    public void patchExpWithName(final Expression node, final Expression copy,
            final List<Patch> patches, final int index,
            final Consumer<Expression> consumer) {
        // exp such as MI.getExpression() may be null
        if (isNull(node)) {
            return;
        }
        Optional<Patch> patchO =
                patches.stream().filter(p -> p.getIndex() == index).findAny();
        if (patchO.isPresent() && nonNull(patchO.get().getVar())) {
            Name name = node.getAST().newName(patchO.get().getVar().getName());
            consumer.accept(name);
        }
    }

    /**
     * Apply patches as done above but for a list of exps. The index starts from
     * the offset.
     *
     * Ex: for MI the getExpression() the index is 0 and for arguments() the
     * offset is 1 and effective index starts from 1 i.e from first arg index is
     * 1, for second it is 2 and so on.
     *
     * @param exps
     * @param expsCopy
     * @param patches
     * @param offset
     */
    public void patchExpsWithName(final List<Expression> exps,
            final List<Expression> expsCopy, final List<Patch> patches,
            final int offset) {
        for (int i = 0; i < exps.size(); i++) {
            final int expIndex = i;
            Expression exp = wrappers.unpack(exps.get(expIndex));
            Expression expCopy = wrappers.unpack(expsCopy.get(expIndex));
            final int index = i + offset;
            patchExpWithName(exp, expCopy, patches, index, (name) -> {
                expsCopy.remove(expIndex);
                expsCopy.add(expIndex, name);
            });
        }
    }
}
