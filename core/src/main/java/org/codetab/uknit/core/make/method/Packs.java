package org.codetab.uknit.core.make.method;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

public class Packs {

    @Inject
    private Nodes nodes;

    /**
     * Find first pack matching the expression.
     *
     * @param packs
     * @param exp
     * @return
     */
    public Optional<Pack> findByExp(final Expression exp,
            final List<Pack> packs) {
        return packs.stream().filter(p -> {
            return nonNull(p.getExp()) && p.getExp().equals(exp);
        }).findFirst();
    }

    /**
     * Find first Invoke matching the expression.
     *
     * @param packs
     * @param exp
     * @return
     */
    public Optional<Invoke> findInvokeByExp(final Expression exp,
            final List<Pack> packs) {
        List<Invoke> invokes = filterInvokes(packs);
        return invokes.stream().filter(p -> {
            return nonNull(p.getExp()) && p.getExp().equals(exp);
        }).findFirst();
    }

    /**
     * Find first pack matching the var name.
     *
     * @param packs
     * @param name
     * @return
     */
    public Optional<Pack> findByVarName(final String name,
            final List<Pack> packs) {
        return packs.stream().filter(p -> {
            return nonNull(p.getVar()) && p.getVar().getName().equals(name);
        }).findFirst();
    }

    /**
     * Find first var matching the var name.
     *
     * @param packs
     * @param name
     * @return
     */
    public IVar findVarByName(final String name, final List<Pack> packs) {
        Optional<Pack> packO = packs.stream().filter(p -> {
            return nonNull(p.getVar()) && p.getVar().getName().equals(name);
        }).findFirst();
        if (packO.isPresent()) {
            return packO.get().getVar();
        } else {
            throw new VarNotFoundException(name);
        }
    }

    public Optional<Invoke> findInvokeByName(final String name,
            final List<Pack> packs) {
        List<Invoke> invokes = filterInvokes(packs);
        return invokes.stream().filter(p -> {
            return nonNull(p.getVar()) && p.getVar().getName().equals(name);
        }).findFirst();
    }

    /**
     * Filter pack list where exp is MI and var is null.
     *
     * @param packs
     * @return
     */
    public List<Pack> filterInferPacks(final List<Pack> packs) {
        List<Pack> list = packs.stream().filter(p -> {
            if (isNull(p.getVar())) {
                Expression exp = p.getExp();
                return nonNull(exp) && nodes.is(exp, MethodInvocation.class);
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * Filter pack list where exp is any of classes type.
     *
     * @param packs
     * @return
     */
    public List<Pack> filterPacks(final List<Pack> packs,
            final Class<?>... classes) {
        List<Pack> list = packs.stream().filter(p -> {
            Expression exp = p.getExp();
            return nonNull(exp) && nodes.is(exp, classes);
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * Filter pack list where exp is any of classes type.
     *
     * @param packs
     * @return
     */
    public List<Invoke> filterInvokes(final List<Pack> packs) {
        List<Invoke> list = packs.stream().filter(p -> {
            return p instanceof Invoke;
        }).map(p -> (Invoke) p).collect(Collectors.toList());
        return list;
    }

    /**
     * Get the pack which has var of Kind.RETURN.
     *
     * @param packs
     * @return
     */
    public Optional<Pack> getReturnPack(final List<Pack> packs) {
        return packs.stream()
                .filter(p -> nonNull(p.getVar()) && p.getVar().is(Kind.RETURN))
                .findAny();
    }

    public Optional<Pack> findLastByLeftExp(final Expression exp,
            final List<Pack> packs) {
        return packs.stream().filter(p -> {
            Optional<Expression> lExpO = p.getLeftExp();
            if (lExpO.isPresent()) {
                return lExpO.get().equals(exp);
            } else {
                return false;
            }
        }).reduce((f, s) -> s);
    }

    /**
     * Get var returned by return statement which is used as expected var in
     * assert statement.Throws IllegalStateException if return exp is not mapped
     * to var.
     *
     * @param heap
     * @return
     */
    public Optional<IVar> getExpectedVar(final Heap heap) {
        Optional<Pack> returnPackO = findByVarName("return", heap.getPacks());
        if (returnPackO.isPresent()) {
            if (nodes.is(returnPackO.get().getExp(), SimpleName.class)) {
                String name = nodes.getName(
                        nodes.as(returnPackO.get().getExp(), SimpleName.class));
                return Optional
                        .ofNullable(findVarByName(name, heap.getPacks()));
            } else {
                throw new IllegalStateException(nodes.exMessage(
                        "can't map return exp to var, expected SimpleName but found",
                        returnPackO.get().getExp()));
            }
        } else {
            return Optional.empty();
        }
    }

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
