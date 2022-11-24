package org.codetab.uknit.core.make.method;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * This class consists exclusively of methods to find or filter Pack and Invoke.
 *
 * @author Maithilish
 *
 */
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
     * Find the pack whose var's oldName matches the name.
     *
     * @param name
     * @param packs
     * @return
     */
    public Optional<Pack> findByVarOldName(final String name,
            final List<Pack> packs) {
        return packs.stream().filter(p -> {
            return nonNull(p.getVar()) && p.getVar().getOldName().equals(name);
        }).findFirst();
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
            final List<Class<? extends Expression>> classes) {
        List<Pack> list = packs.stream().filter(p -> {
            Expression exp = p.getExp();
            return nonNull(exp) && nodes.is(exp, classes);
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * Filter packs by predicate.
     *
     * @param heap
     * @param predicate
     * @return
     */
    public List<Pack> filterPacks(final Heap heap,
            final Predicate<Pack> predicate) {
        return heap.getPacks().stream().filter(predicate)
                .collect(Collectors.toList());
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
     * Filter pack list based on var kinds.
     *
     * @param packs
     * @param kinds
     * @return
     */
    public List<Pack> filterByVarKinds(final List<Pack> packs,
            final Kind... kinds) {
        List<Kind> filterKinds = Arrays.asList(kinds);
        return packs.stream()
                .filter(p -> nonNull(p.getVar())
                        && filterKinds.contains(p.getVar().getKind()))
                .collect(Collectors.toList());
    }

    /**
     * Filter pack list where var is renamed.
     *
     * @param packs
     * @return
     */
    public List<Pack> filterIsVarRenamed(final List<Pack> packs) {
        List<Pack> list = packs.stream().filter(p -> {
            IVar var = p.getVar();
            return nonNull(var) && !var.getName().equals(var.getOldName());
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * Return the sublist of packs starting from the pack (including the pack)
     * to the end of packs.
     *
     * @param pack
     * @param packs
     * @return
     */
    public List<Pack> tailList(final Pack pack, final List<Pack> packs) {
        int start = packs.indexOf(pack);
        int end = packs.size();
        return packs.subList(start, end);
    }

    /**
     * Return the sublist of packs up to the pack (excluding the pack itself).
     *
     * @param pack
     * @param packs
     * @return
     */
    public List<Pack> headList(final Pack pack, final List<Pack> packs) {
        int start = 0;
        int end = packs.indexOf(pack);
        return packs.subList(start, end);
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
}
