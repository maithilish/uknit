package org.codetab.uknit.core.make.method.patch.old;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Patches;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.make.model.Patch.Kind;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.NodeGroups;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

/**
 * Direct modification to src node affects the subsequent internal calls
 * (private calls). Instead, keep the source node unmodified and create patch,
 * in visit, for later replacement and collect them in heap. Defer actual
 * patching to statements generate phase.
 * <p>
 * To generate statements - when, verify and initializer - the copyAndPatch()
 * method creates copy of the source node and patch its infer expressions and
 * copy is used.
 * @author Maithilish
 *
 */
public class PatcherOld {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private Patches patches;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Patchers patchers;
    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeGroups nodeGroups;
    @Inject
    private NodeFactory nodeFactory;

    /**
     * When inner expressions such as MethodInvocation returns InferVar then in
     * outer node replace it with infer var. Patches map expression to
     * corresponding var.
     *
     * <code>
     *   sb.append(file.getName().toLowerCase())
     *   emits
     *   when(file.getName()).thenReturn(apple);
     *   when(sb.append(apple.toLowerCase())).thenReturn(stringBuilder);
     * </code>
     *
     * For outer MI stage patch to replace file.getName() with apple.
     *
     * @param node
     * @param exps
     * @param heap
     * @return
     */
    public List<Patch> creatInvokePatches(final ASTNode node,
            final List<Expression> exps, final Heap heap) {
        List<Patch> patchList = new ArrayList<>();

        final Map<Expression, Invoke> patchables = new HashMap<>();
        for (Expression exp : exps) {
            /*
             * Ex: sb.append(file.getName().toLowerCase()), if file.getName()
             * returns inferVar apple then when should be
             * when(sb.append(apple.toLowerCase())).thenReturn(...). Create a
             * patch for exp file.getName() to apple.
             *
             * When exp is CastExp strip it to find invoke. Ex: a[(int)foo.obj];
             * the exp is (int) foo.obj() and find invoke for foo.obj().
             */
            Optional<Invoke> invokeO = packs
                    .findInvokeByExp(wrappers.unpack(exp), heap.getPacks());
            if (invokeO.isPresent() && nonNull(invokeO.get().getVar())) {
                if (patchers.patchable(invokeO.get())) {
                    patchables.put(exp, invokeO.get());
                }
            }
        }

        for (Expression exp : patchables.keySet()) {
            /*
             * Ex: sb.append(file.getName().toLowerCase()), if file.getName()
             * returns inferVar apple then when should be
             * when(sb.append(apple.toLowerCase())).thenReturn(...). Create a
             * patch for exp file.getName() to apple.
             */
            Invoke invoke = patchables.get(exp);
            // if when returns inferVar, then replace
            String name = invoke.getVar().getName();
            int expIndex = patchers.getExpIndex(node, exp);
            Patch patch = modelFactory.createPatch(Kind.INVOKE, node,
                    invoke.getExp(), name, expIndex);
            patchList.add(patch);
        }
        return patchList;
    }

    /**
     * Creates patch if renamedVar is used in node (pack.exp) or its exps.
     *
     * @param node
     * @param exps
     * @param namesMap
     * @param heap
     * @return
     */
    public List<Patch> creatVarPatches(final Expression node,
            final List<Expression> exps, final Pack renamedPack) {
        List<Patch> patchList = new ArrayList<>();
        if (nodes.isName(node)) {
            /*
             * The node (pack.exp) is name and equals to renamedVar oldName then
             * create patch with new name from renamedPack's name. Ex: return
             * foo; if renamedPack is [name: foo2, oldName: foo], then create
             * patch [node: foo, exp: foo, name: foo2]
             */
            if (nodes.getName(node).equals(renamedPack.getVar().getOldName())) {
                String newName = renamedPack.getVar().getName();
                int expIndex = 0;
                Patch patch = modelFactory.createPatch(Kind.VAR, node, node,
                        newName, expIndex);
                patchList.add(patch);
            }
        } else {
            /*
             * if node is not name then create patch for its exps. Ex:
             * instant.compare(other); if renamedPack is [name: instant,
             * oldName: instant2], then create patch [node:
             * instant.compare(other), exp: instant, name: instant2]. Then in
             * another iteration if renamedPack is [name: other, oldName:
             * other2], then create patch [node: instant.compare(other), exp:
             * other, name: other2].
             */
            for (Expression exp : exps) {
                if (nodes.isName(exp)) {
                    if (nodes.getName(exp)
                            .equals(renamedPack.getVar().getOldName())) {
                        String newName = renamedPack.getVar().getName();
                        int expIndex = patchers.getExpIndex(node, exp);
                        Patch patch = modelFactory.createPatch(Kind.VAR, node,
                                exp, newName, expIndex);
                        patchList.add(patch);
                    }
                }
            }
        }
        return patchList;
    }

    /**
     * Copy and Patches MI or SMI and returns the calling exp.
     * <p>
     * foo.bar() - Optional.of(foo) (calling exp is foo and bar is method name)
     *
     * super.bar() - Optional.empty (IMC)
     *
     * this.bar() - Optional.empty (IMC)
     *
     * bar() - Optional.empty (IMC)
     *
     * @param invocationExp
     * @param heap
     * @return
     */
    public Optional<Expression> getPatchedCallExp(final Invoke invoke,
            final Heap heap) {

        Optional<Expression> patchedExpO;
        Expression invocationExp = invoke.getExp();
        if (nodes.is(invocationExp, MethodInvocation.class)) {
            // exp is null for IMC with or without super or this keyword
            patchedExpO = Optional
                    .ofNullable(((MethodInvocation) copyAndPatch(invoke, heap))
                            .getExpression());
        } else if (nodes.is(invocationExp, SuperMethodInvocation.class)) {
            patchedExpO = Optional.empty();
        } else {
            throw new CodeException(
                    nodes.noImplmentationMessage(invocationExp));
        }
        return patchedExpO;
    }

    /**
     * Updates patch name to new name. For example if var is renamed as [name:
     * option, oldName: option2], then the patches with name as option are
     * updated to option2.
     *
     * If patch exp is name and patch is already updated to reflect the name
     * change then don't update it again. Ex: pack [name: instantX, oldName:
     * instant] and another pack [name: instant, oldName: instant3]. If a patch
     * exists to rename instantX as instant then don't create another patch for
     * that pack to update instant as instant3 otherwise instantX is wrongly
     * patched to instant3 instead of instant. Ref itest:
     * internal.InternalNestedArg.mockDiffNameC().
     *
     * Another way to do this is to create a var patch and apply it after
     * applying the invoke patch. As the final result is same, we directly
     * update the var name in invoke patch.
     *
     * @param pack
     * @param namesMap
     * @param heap
     */
    public void updatePatchName(final Pack pack, final String oldName,
            final String newName, final Heap heap) {

        for (Patch patch : pack.getPatches()) {

            String patchName = patch.getName();

            if (nonNull(newName)) {
                if (nodes.isName(patch.getExp())) {
                    /*
                     * If a var pack is renamed, then the patches that refers
                     * renamed var may or may not have updated to new name.
                     * Update the patch name only if not updated.
                     *
                     * Ex: Patch [name foo3, node: foo.bar(), exp: foo] exists
                     * for Pack [exp foo.bar()] and the patch updates foo.bar()
                     * to foo3.bar(). Suppose the Pack [var foo] is renamed to
                     * foo3 then patch is updated as patch name is already set
                     * to renamed name. However patch name is foo2 then patch is
                     * not updated as name is yet to be updated. The
                     * patchExpName is compared with oldName to ensure only the
                     * applicable patch is touched.
                     *
                     */
                    String patchExpName = nodes.getName(patch.getExp());
                    if (patchExpName.equals(oldName)
                            && !patchName.equals(newName)) {
                        patch.setName(newName);
                    }
                } else if (patchName.equals(oldName)) {
                    /*
                     * set new name if patch exp is not name as there is no
                     * other way to compare the patch with renamedPack.
                     */
                    patch.setName(newName);
                }
            }
        }
    }

    /**
     * Direct modification to the src node corrupts and makes it unsuitable to
     * handle multiple IM or Ctl Flow Path tests. To generate statements - when,
     * verify and initializer - create copy of the source node, patch its infer
     * exp, var name changes and return patched copy. The copy is not resolvable
     * and it is only useful to find the var names or structure of exp after
     * patch or to generate output!
     *
     * @param <T>
     * @param node
     * @param heap
     * @return copy of node.
     */
    public Expression copyAndPatch(final Pack pack, final Heap heap) {
        checkNotNull(pack.getExp());

        /*
         * node is packs exp and exps is exps of the node. Ex: For Pack exp
         * foo.opt(bar) the node is foo.opt(bar) and exps are foo and bar. The
         * opt is just a name.
         */
        Expression node = pack.getExp();

        /*
         * if pack exp is name and no patch is present return the name, but if
         * patch exists then return patch name as patched exp.
         */
        if (nodes.isSimpleName(node)) {
            Optional<Patch> patchO =
                    patches.findPatch(node, node, pack.getPatches());
            if (patchO.isEmpty()) {
                return node;
            } else {
                return nodeFactory.createName(patchO.get().getName());
            }
        }

        Expression nodeCopy =
                (Expression) ASTNode.copySubtree(node.getAST(), node);

        List<Expression> exps = patchers.getExps(node);
        List<Expression> expCopies = patchers.getExps(nodeCopy);

        for (int i = 0; i < exps.size(); i++) {
            /*
             * When exp is CastExp strip it to find invoke. Ex: a[(int)foo.obj];
             * the exp is (int) foo.obj() and find invoke for foo.obj().
             */
            Expression patchExp = wrappers.unpack(exps.get(i));
            /*
             * Find the pack for node and from its patch list get patch for
             * patchExp. Ex: for node - bar.locale(foo.lang()) and get patch for
             * exp - foo.lang() and apply.
             */
            Optional<Patch> patch =
                    patches.findPatch(node, patchExp, pack.getPatches());
            if (patch.isPresent()) {
                patchers.patchExpWithVar(nodeCopy, patch.get());
            }

            /**
             * The exps of the exp may have patches. For each exp, find the pack
             * and get its patches and apply. The above block may have already
             * replaced some of the exp, but for some exp patches may be in its
             * own pack which are patched here. Some of the ex:
             *
             * foo.size() > 1 ? "foo" + foo.cntry() : foo.lang() + "foo"
             *
             * new String[foo.size() + 1];
             *
             * String lang = foo.lang("en_" + foo.cntry());
             *
             * Foo[] list = {new Foo(bar.zoo())};
             *
             */
            List<Patch> replacerList =
                    patches.findPatches(patchExp, heap.getPacks());
            Expression expCopy = expCopies.get(i);
            replacerList.forEach(r -> patchers.patchExpWithVar(expCopy, r));
        }
        return nodeCopy;
    }

    /**
     * Filters list of packs to contains only the exp nodes which can contains
     * MI and SMI; then filter any IMC calls from it.
     *
     * IMC collapses to its return var so there nothing to patch. Ex: int x =
     * foo(y) collapses to int x = apple;
     *
     * @param heap
     * @return
     */
    public List<Pack> getInvokePatchables(final Heap heap) {

        // get list of packs whose exp can contain invoke exp
        List<Pack> packList = packs.filterPacks(heap.getPacks(),
                nodeGroups.nodesWithInvoke());

        /*
         * The list also includes IM calls. The IMC is replaced with a return
         * var and strictly patches as not required for it. Ex: internal(foo,
         * foo.size()).
         *
         * However when IM is arg of an Invoke then we need to patch the invoke
         * arg with IM return and patch is required for it which may be used in
         * intermediate processing. Ex: internal1(internal2(foo))
         *
         */
        return packList;
    }
}
