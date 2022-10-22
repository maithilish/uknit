package org.codetab.uknit.core.make.method;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

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
    public Optional<Pack> findByExp(final List<Pack> packs,
            final Expression exp) {
        return packs.stream().filter(p -> {
            if (nonNull(p.getExp())) {
                return p.getExp().equals(exp);
            } else {
                return false;
            }
        }).findFirst();
    }

    /**
     * Find first pack matching the var name.
     *
     * @param packs
     * @param name
     * @return
     */
    public Optional<Pack> findByVarName(final List<Pack> packs,
            final String name) {
        return packs.stream().filter(p -> {
            if (nonNull(p.getVar())) {
                return p.getVar().getName().equals(name);
            } else {
                return false;
            }
        }).findFirst();
    }

    /**
     * Filter pack list where exp is MI.
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

    public Optional<Pack> findLastByLeftExp(final List<Pack> packs,
            final Expression exp) {
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
