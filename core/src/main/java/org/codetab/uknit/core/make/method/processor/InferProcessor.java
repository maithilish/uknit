package org.codetab.uknit.core.make.method.processor;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.TypeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class InferProcessor {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Resolver resolver;
    @Inject
    private Expressions expressions;
    @Inject
    private InferFactory inferFactory;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private NodeFactory nodeFactory;

    /**
     * Create and set infer var for all packs in the list. The packs in the list
     * should have MI exp.
     *
     * @param inferPacks
     * @param heap
     */
    public void createInfers(final List<Pack> inferPacks, final Heap heap) {
        for (Pack pack : inferPacks) {
            if (nonNull(pack.getVar())) {
                throw new IllegalStateException(nodes.exMessage(
                        "unable to create infer var, var exists for",
                        pack.getExp()));
            }

            resolver.getExpReturnType(pack.getExp()).ifPresentOrElse(ert -> {
                Type type = ert.getType();
                IVar inferVar = inferFactory.createInfer(type, heap);
                pack.setVar(inferVar);
            }, () -> {
                throw new TypeException(
                        "unable to get exp return type for: " + pack.getExp());
            });
        }
    }

    /**
     * Create and set infer var for MI in return pack. Replaces return pack's
     * exp with infer var's name expression.
     *
     * @param returnPack
     * @param heap
     */
    public void createInferForReturn(final Pack returnPack, final Heap heap) {

        Expression exp = returnPack.getExp();
        if (nodes.is(exp, SimpleName.class)) {
            return;
        }

        Type type = returnPack.getVar().getType();
        IVar inferVar = null;
        if (nodes.is(exp, MethodInvocation.class)) {
            inferVar = inferFactory.createInfer(type, heap);
        }

        // Instance creation new Foo(), Array creation, Literals "foo", 5
        if (expressions.isCreation(exp)) {
            inferVar = inferFactory.createInfer(type, heap);
        }

        if (nodes.is(exp, ArrayAccess.class)) {
            inferVar = inferFactory.createInfer(type, heap);
        }

        if (nonNull(inferVar)) {
            Optional<Pack> packO =
                    packs.findLastByLeftExp(heap.getPacks(), exp);
            Pack inferPack = null;
            if (packO.isPresent()) {
                if (isNull(packO.get().getVar())) {
                    packO.get().setVar(inferVar);
                } else {
                    throw new IllegalStateException(
                            nodes.exMessage("var exists for", exp));
                }
            } else {
                inferPack = modelFactory.createPack(inferVar, exp);
                heap.addPack(inferPack);
            }

            // replace return pack exp with infer var name
            Name varName = nodeFactory.createName(inferPack.getVar().getName());
            returnPack.setExp(varName);
        } else {
            throw new CodeException(nodes.noImplmentationMessage(exp));
        }
    }
}
