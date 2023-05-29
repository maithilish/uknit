package org.codetab.uknit.core.make.method.patch.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.method.patch.ServiceLoader;
import org.codetab.uknit.core.make.method.var.linked.LinkedPack;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.ThisExpression;

public class Patchers {

    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Packs packs;
    @Inject
    private Methods methods;
    @Inject
    private Nodes nodes;
    @Inject
    private Patcher patcher;
    @Inject
    private LinkedPack linkedPack;

    /**
     * Patch invokes in an expression.
     *
     * @param pack
     * @param node
     * @param copy
     * @param heap
     * @param consumer
     */
    public void patchExpWithName(final Pack pack, final Expression node,
            final Expression copy, final Heap heap,
            final Consumer<Expression> consumer) {

        // exp returned by MI.getExpression() may be null
        if (isNull(node)) {
            return;
        }

        Optional<Pack> patchPackO =
                packs.findPatchPack(pack, node, heap.getPacks());

        boolean recursivePatch = false;
        if (patchPackO.isPresent()) {
            if (packs.hasVar(patchPackO)) {
                IVar var = packs.getVar(patchPackO);
                /*
                 * Ex: {foo.name()} the ArrayInitializer has MI exp foo.name()
                 * which is inferred to var apple and patch it as {apple}.
                 *
                 * Don't patch static call as we don't own instance returned.
                 * Ex: path.compareTo(Path.of(Statics.getName("foo")));
                 * Statics.getName() is not patched.
                 */
                if (methods.isStaticCall(node)) {
                    recursivePatch = true;
                } else {
                    Name name = node.getAST().newName(var.getName());
                    consumer.accept(name);
                }
            } else {
                recursivePatch = true;
            }
            if (recursivePatch) {
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
        } else {
            /*
             * Some exp may not have patchPack but still they need to patched
             * for ThisExpression. Ex: foo.append(this.cities[this.indexes[0]]);
             * the this.indexes[0] doesn't have pack but this has to be patched
             * to CUT name. Ref itest: exp.value.ArrayAccess.arrayIndexHasThis()
             */
            PatchService patchService = serviceLoader.loadService(node);
            patchService.patch(pack, node, copy, heap);
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
     * Same as above but patches list of exps such as mi.arguments(). The
     * expsCopy list should be the list obtained from the copy and not any other
     * list otherwise patch is not reflected in node copy. The function passed
     * to the method patchExpWithName() removes the exp and inserts name in its
     * place.
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
    public void patchExpWithPackPatches(final Pack pack, final Expression node,
            final Expression copy, final List<Patch> patches, final int index,
            final Consumer<Expression> consumer) {
        // exp such as MI.getExpression() may be null
        if (isNull(node) || patches.isEmpty()) {
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
     * Apply pack level patches as done above but for a list of exps. The index
     * starts from the offset.
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
    public void patchExpsWithPackPatches(final Pack pack,
            final List<Expression> exps, final List<Expression> expsCopy,
            final List<Patch> patches, final int offset, final Heap heap) {
        for (int i = 0; i < exps.size(); i++) {
            final int expIndex = i;
            Expression exp = wrappers.unpack(exps.get(expIndex));
            Expression expCopy = wrappers.unpack(expsCopy.get(expIndex));
            final int index = i + offset;
            if (patches.isEmpty()) {
                // no patches at pack level, try to patch exp level patches
                Optional<Pack> expPack =
                        packs.findNearestByExp(pack, exp, heap.getPacks());
                if (expPack.isPresent()
                        && !expPack.get().getPatches().isEmpty()) {
                    // REVIEW - why aa excluded
                    if (!(exp instanceof ArrayAccess)) {
                        Expression e =
                                patcher.copyAndPatchNames(expPack.get(), heap);
                        expsCopy.remove(expIndex);
                        expsCopy.add(expIndex, e);
                    }
                }
            } else if (nodes.isName(exp)) {
                patchExpWithPackPatches(pack, exp, expCopy, patches, index,
                        (name) -> {
                            expsCopy.remove(expIndex);
                            expsCopy.add(expIndex, name);
                        });
            }
        }

    }

    // REVIEW - extract inner logic of this and next method to separate class
    public void patchValue(final List<Expression> args,
            final List<Expression> argsCopy, final Heap heap) {
        for (int i = 0; i < args.size(); i++) {
            Expression exp = wrappers.unpack(args.get(i));
            Expression copy = wrappers.unpack(argsCopy.get(i));
            /*
             * simple name part from any of id (SimpleName), foo.id (QName),
             * (foo).id (FieldAccess), this.id (FieldAccess)
             */
            SimpleName name = nodes.getSimpleName(exp);
            if (nonNull(name)) {
                if (nodes.is(exp, FieldAccess.class)) {
                    FieldAccess fa = nodes.as(exp, FieldAccess.class);
                    if (nodes.is(fa.getExpression(), ThisExpression.class)) {
                        SimpleName cut =
                                copy.getAST().newSimpleName(heap.getCutName());
                        fa.setExpression(cut);
                    }
                } else {
                    Optional<Pack> packO = packs.findByVarName(
                            nodes.getName(name), heap.getPacks());
                    if (packO.isPresent()) {
                        /*
                         * reverse traverse linked pack and get the first
                         * available initializer.
                         */
                        Optional<Initializer> initializer = linkedPack
                                .getLinkedInitializer(packO.get(), heap);

                        /*
                         * if initializer is expression then patch the MI's exp
                         * and args
                         */
                        if (initializer.isPresent() && initializer.get()
                                .getInitializer() instanceof Expression) {
                            Expression iniExp = (Expression) initializer.get()
                                    .getInitializer();
                            Expression iniExpCopy = (Expression) ASTNode
                                    .copySubtree(iniExp.getAST(), iniExp);
                            argsCopy.set(i, iniExpCopy);
                        }
                    }
                }
            } else if (nodes.isLiteral(exp)) {
                /*
                 * If exp is parenthesized then set unpacked exp as arg. EX: To
                 * patch foo.append(("foo")) as foo.append("foo"). If copy is
                 * child of parenthesized exp then we can't set it to argsCopy
                 * list as it can't be child of another node. Create new copy
                 * and set it.
                 *
                 * BooleanLiteral, CharacterLiteral, NullLiteral, NumberLiteral,
                 * StringLiteral, TypeLiteral (Person.class).
                 */
                if (!args.get(i).equals(exp)) {
                    // if arg != exp then it is parenthesized
                    Expression newCopy = (Expression) ASTNode
                            .copySubtree(copy.getAST(), copy);
                    argsCopy.set(i, newCopy);
                }
            } else {
                PatchService patchService = serviceLoader.loadService(exp);
                patchService.patchValue(exp, copy, heap);
            }
        }
    }

    // REVIEW
    public void patchValue(final Expression node, final Expression nodeCopy,
            final Heap heap, final Consumer<Expression> consumer) {
        if (isNull(node)) {
            return;
        }

        Expression exp = wrappers.unpack(node);
        Expression copy = wrappers.unpack(nodeCopy);
        /*
         * simple name part from any of id (SimpleName), foo.id (QName),
         * (foo).id (FieldAccess), this.id (FieldAccess)
         */
        SimpleName name = nodes.getSimpleName(exp);
        if (nonNull(name)) {
            if (nodes.is(exp, FieldAccess.class)) {
                FieldAccess fa = nodes.as(exp, FieldAccess.class);
                if (nodes.is(fa.getExpression(), ThisExpression.class)) {
                    SimpleName cut =
                            copy.getAST().newSimpleName(heap.getCutName());
                    fa.setExpression(cut);
                }
            } else {
                Optional<Pack> packO = packs.findByVarName(nodes.getName(exp),
                        heap.getPacks());
                if (packO.isPresent()) {
                    /*
                     * reverse traverse linked pack and get the first available
                     * initializer.
                     */
                    Optional<Initializer> initializer =
                            linkedPack.getLinkedInitializer(packO.get(), heap);

                    /*
                     * if initializer is expression then patch the MI's exp and
                     * args
                     */
                    if (initializer.isPresent() && initializer.get()
                            .getInitializer() instanceof Expression) {
                        Expression iniExp =
                                (Expression) initializer.get().getInitializer();
                        Expression iniExpCopy = (Expression) ASTNode
                                .copySubtree(iniExp.getAST(), iniExp);
                        consumer.accept(iniExpCopy);
                    }
                }
            }
        } else if (nodes.isLiteral(exp)) {
            /*
             * If exp is parenthesized then set unpacked exp as arg. EX: To
             * patch ("foo").concat("x") as foo.concat("x"). If copy is child of
             * parenthesized exp then we can't set it to argsCopy list as it
             * can't be child of another node. Create new copy and set it.
             *
             * BooleanLiteral, CharacterLiteral, NullLiteral, NumberLiteral,
             * StringLiteral, TypeLiteral (Person.class).
             */
            if (!node.equals(exp)) {
                // if node != exp then it is parenthesized
                Expression newCopy =
                        (Expression) ASTNode.copySubtree(copy.getAST(), copy);
                consumer.accept(newCopy);
            }
        } else {
            PatchService patchService = serviceLoader.loadService(exp);
            patchService.patchValue(exp, copy, heap);
        }
    }
}
