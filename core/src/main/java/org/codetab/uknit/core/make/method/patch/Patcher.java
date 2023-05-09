package org.codetab.uknit.core.make.method.patch;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.make.model.Patch.Kind;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class Patcher {

    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Nodes nodes;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Wrappers wrappers;

    private Map<Expression, IVar> patches;

    /**
     * Loads service based on Expression type which creates new copy of exp and
     * patches it.
     *
     * Keep following things in mind while coding the service.
     *
     * Remove the cast and parenthesized exp with Wrappers.unpack() method. In
     * getExps() method use Wrappers.strip() which removes only the Parenthesise
     * from the exp.
     *
     * Don't unpack the list of exps such as MI.arguments() etc., as we need the
     * list returned by the copy to patch. The unpack on list creates a new list
     * from the original list and any patch applied on new list will not reflect
     * in the node copy. For such lists, unpack each exp individually in the for
     * loop.
     *
     * @param pack
     * @param heap
     * @return
     */
    public Expression copyAndPatch(final Pack pack, final Heap heap) {
        checkNotNull(pack.getExp());

        Expression node = pack.getExp();
        Expression copy = (Expression) ASTNode
                .copySubtree(pack.getExp().getAST(), pack.getExp());

        // apply invoke patch
        PatchService patchService = serviceLoader.loadService(pack.getExp());
        patchService.patch(pack, node, copy, heap);

        // apply rename patches
        patchService.patchName(pack, node, copy, heap);

        return copy;
    }

    /**
     * Patch rename patches. Invoke patches are not applied.
     *
     * @param pack
     * @param heap
     * @return
     */
    public Expression copyAndPatchNames(final Pack pack, final Heap heap) {
        checkNotNull(pack.getExp());

        Expression node = pack.getExp();
        Expression copy = (Expression) ASTNode
                .copySubtree(pack.getExp().getAST(), pack.getExp());

        // apply rename patches
        PatchService patchService = serviceLoader.loadService(pack.getExp());
        patchService.patchName(pack, node, copy, heap);

        return copy;
    }

    /**
     * Patches the MI and returns its call expression. Call expression is null
     * for IMC, super, this and static calls.
     *
     * @param invoke
     * @param heap
     * @return
     */
    public Optional<Expression> copyAndPatchCallExp(final Invoke invoke,
            final Heap heap) {
        Optional<Expression> callExpCopyO;
        Expression invokeExp = invoke.getExp();
        if (nodes.is(invokeExp, MethodInvocation.class)) {
            // exp is null for IMC with or without super or this keyword
            callExpCopyO = Optional
                    .ofNullable(((MethodInvocation) copyAndPatch(invoke, heap))
                            .getExpression());
        } else if (nodes.is(invokeExp, SuperMethodInvocation.class)) {
            callExpCopyO = Optional.empty();
        } else {
            throw new CodeException(nodes.noImplmentationMessage(invokeExp));
        }
        return callExpCopyO;
    }

    /**
     * Patch for chained IM Call. Ex: name = internal().getName(), this results
     * in Pack[v=apple, e=foo] and Pack[v=name, e=internal().getName()]. The
     * Patch[n=foo,e=internal()] is used to patch internal() MI exp in second
     * pack.
     *
     * There is no separate pack for IM call itself as the return var name of IM
     * is assigned as exp to calling pack. It is not possible to find the patch
     * name for internal() call from the pack list. Hence each IM call patch is
     * added to patch map. As each IM invoke exp is unique we can find the
     * relevant patch with exp as key. For each invoke of internal()
     * MethodInvocation with unique hash is created AST. Ex: x = internal(); y =
     * internal(); results in two entries in patches map.
     *
     * @param exp
     * @param var
     */
    public void addPatch(final Expression exp, final IVar var) {
        if (!patches.containsKey(exp)) {
            patches.put(exp, var);
        }
    }

    /**
     * For each exp parts of pack's exp, if exp is name and matches the old name
     * of renamed var, add patch to the pack.
     *
     * Ex: foo.bar(foo.get()) if foo is renamed as foo2 then two patches are
     * added to the pack, Patch[v=foo-foo2, 0] and Patch[v=foo-foo2, 1]. The
     * first one makes the MI as foo2.bar(foo.get()) and second patch makes it
     * foo2.bar(foo2.get()). Both patches are added in a single call as renamed
     * var is same for both exps.
     *
     * Ex: foo.bar(zoo.get()), if var foo is renamed as foo3 and var zoo as zoo4
     * then this method is called twice as there are two renamed var. Two
     * patches, Patch[foo->foo3,0] and Patch[zoo->zoo4,1] are added to the pack
     * in separate calls. In copyAndPatch(), the first patch makes the MI as
     * foo3.bar(zoo.get()) and second one results in patched exp
     * foo3.bar(zoo4.get()).
     *
     * It is not possible to logically derive the renamed var in some cases
     * hence explicit patches are added to the pack. See design notes for
     * details.
     *
     * @param pack
     * @param renamedVar
     * @param heap
     */
    public void addVarRenamePatch(final Pack renamedPack, final Pack pack,
            final IVar renamedVar, final Heap heap) {
        /**
         * Create patch only if both renamed pack and pack are in same path. If
         * renamed var is in a path then patch any applicable packs in that path
         * but do not patch any applicable pack in other paths. <code>
         *
         * P1 path A (renamed pack)
         * P2 path A (applicable pack)
         * P3 path B (applicable pack)
         *
         * In path A test, both P1 and P2 are in ctl path so create patch
         * for P2 (P1 true == P2 true) but P3 is not in ctl path so don't
         * create patch for P3 (P1 true != P3 false)
         *
         * In path B test, both P1 and P2 are not in ctl path then also create
         * patch for P2 (P1 false == P2 false), however P3 is in ctl path but
         * don't create patch for P3 (P1 false != P3 true)         *
         * </code>
         *
         * Ref itest IfElseTest.testIfElseFooIfCanSwim()
         */
        if (renamedPack.isInCtlPath() != pack.isInCtlPath()) {
            return;
        }

        Expression exp = pack.getExp();
        PatchService patchService = serviceLoader.loadService(exp);
        List<Expression> exps = patchService.getExps(exp);
        for (int i = 0; i < exps.size(); i++) {
            Expression expression = wrappers.unpack(exps.get(i));
            String definedName = renamedVar.getDefinedName();
            String oldName = renamedVar.getOldName();
            if (nodes.isName(expression)) {
                String expName = nodes.getName(expression);
                if (expName.equals(definedName)) {
                    Patch patch = modelFactory.createPatch(Kind.VAR,
                            definedName, renamedVar, i);
                    pack.addPatch(patch);
                } else if (expName.equals(oldName)) {
                    Patch patch = modelFactory.createPatch(Kind.VAR, oldName,
                            renamedVar, i);
                    pack.addPatch(patch);
                }
            }
        }
    }

    public void setup() {
        patches = new HashMap<>();
    }

    public void merge(final Patcher other) {
        patches.putAll(other.patches);
    }

    public Map<Expression, IVar> getPatches() {
        return Collections.unmodifiableMap(patches);
    }
}
