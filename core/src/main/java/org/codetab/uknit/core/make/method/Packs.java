package org.codetab.uknit.core.make.method;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    @Inject
    private PackIDGenerator idGenerator;

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
     * Find first pack with matching the expression. If exp is name then find
     * first pack with matching name.
     *
     * @param packs
     * @param exp
     * @return
     */
    public Optional<Pack> findByExpOrExpName(final Expression exp,
            final List<Pack> packs) {
        Optional<Pack> pack = findByExp(exp, packs);
        if (pack.isEmpty() && nodes.isName(exp)) {
            pack = packs.stream().filter(p -> {
                return nonNull(p.getExp()) && nodes.isName(p.getExp())
                        && p.getExp().toString().equals(exp.toString());
            }).findFirst();
        }
        return pack;
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
     * Find pack by an expression and return its var.
     *
     * Ex: Returns Pack [var=foo, exp=bar()] for exp bar().
     *
     * @param exp
     * @param packs
     * @return
     */
    public Optional<IVar> findVarByExp(final Expression exp,
            final List<Pack> packs) {
        return findByExp(exp, packs).map(Pack::getVar);
    }

    /**
     * If exp is name then find pack with matching var name and return its var.
     *
     * Ex: Pack 1 [var=foo, exp=bar()], Pack 2 [var=baz, exp=foo] then for exp
     * foo returns Pack 1.
     *
     * @param nameExp
     * @param packs
     * @return
     */
    public Optional<IVar> findVarByName(final Expression nameExp,
            final List<Pack> packs) {

        checkState(nodes.isName(nameExp));

        String name = nodes.getName(nameExp);
        Optional<Pack> packO = findByVarName(name, packs);
        if (packO.isPresent()) {
            return Optional.ofNullable(packO.get().getVar());
        } else {
            return Optional.empty();
        }
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

    public List<Pack> filterPacks(final List<Pack> packs,
            final Predicate<Pack> predicate) {
        return packs.stream().filter(predicate).collect(Collectors.toList());
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
     * Filter packs where initializer is empty and var is not return var.
     *
     * @param packs
     * @return
     */
    public List<Pack> filterNoInitializers(final List<Pack> packs,
            final Predicate<Pack> predicate) {
        List<Pack> list = packs.stream().filter(p -> {
            IVar var = p.getVar();
            return nonNull(var) && var.getInitializer().isEmpty()
                    && !p.getVar().isReturnVar() && predicate.test(p);
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * Filter packs up to the next rename.
     *
     * <code>
     *  P4 [var=state2 (state) ...]
     *  P5 [var=x ...]
     *  P6 [var=state3 (state2) ...]
     *  P7 [var=y ...]
     *  P8 [var=z ...]
     * </code>
     *
     * For tail list P4-P8, the scoped list is P4-P5 as name changes in P6. For
     * tail list P6-P8, the scoped list is P6-P8.
     *
     * @param renamedPack
     * @param tailList
     * @return
     */
    public List<Pack> filterScopePacks(final Pack renamedPack,
            final List<Pack> tailList) {
        List<Pack> scopeList = new ArrayList<>();
        scopeList.add(tailList.get(0));
        if (tailList.size() > 1) {
            String definedName = renamedPack.getVar().getDefinedName();
            for (int i = 1; i < tailList.size(); i++) {
                Pack tailPack = tailList.get(i);
                // break on new avatar of renamed var
                if (nonNull(tailPack.getVar()) && tailPack.getVar()
                        .getDefinedName().equals(definedName)) {
                    scopeList.add(tailPack);
                    break;
                } else {
                    scopeList.add(tailPack);
                }
            }
        }
        return scopeList;
    }

    /**
     * Filter list of packs that contains vars with same defined name.
     *
     * <code>
     * P1 var[name=id, oldName=id, definedName=id]
     * P2 var[name=id2, oldName=id, definedName=id]
     * P3 var[name=date, oldName=date, definedName=date]
     * P4 var[name=id3, oldName=id2, definedName=id]
     * </code>
     *
     * For P1/P2/P4 with defined name id, returns list of P1,P2 and P4. For P3
     * with defined name date, returns list of P3.
     *
     * @param pack
     * @param heap
     * @return
     */
    public List<Pack> filterByDefinedName(final Pack pack, final Heap heap) {
        if (isNull(pack.getVar())) {
            return List.of(pack);
        }

        String definedName = pack.getVar().getDefinedName();
        List<Pack> packs = heap.getPacks().stream().filter(p -> {
            return nonNull(p.getVar())
                    && p.getVar().getDefinedName().equals(definedName);
        }).collect(Collectors.toList());
        return packs;
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

    public List<IVar> asVars(final List<Pack> packList) {
        return packList.stream().map(p -> p.getVar()).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Reverse traverse the list of packs starting from the position of the pack
     * and return first matching pack whose exp is equal to node.
     *
     * Ex: <code>
     * 0 Pack[v=apple, e=foo.x()]
     * 1 Pack[v=grape, e=foo.x()]
     * 2 Pack[e=foo.x().z()]
     * 3 Pack[e=bar.y()]
     * </code> For Pack 2 and node foo.x(), the Pack 1 is returned as it is the
     * nearest that comes before Pack 2 and node matches its exp. Patcher use it
     * to replace foo.x() with name grape in foo.x().z().
     *
     * @param pack
     * @param node
     * @param packs
     * @return
     */
    public Optional<Pack> findPatchPack(final Pack pack, final Expression node,
            final List<Pack> packs) {
        int index = packs.indexOf(pack);
        index--;
        if (index >= 0) {
            for (int i = index; i >= 0; i--) {
                Pack pPack = packs.get(i);
                if (nonNull(pPack.getExp()) && pPack.getExp().equals(node)) {
                    return Optional.of(pPack);
                }
            }
        }
        return Optional.empty();
    }

    public int getId() {
        return idGenerator.getId();
    }

    public void resetIdGenerator() {
        idGenerator.reset();
    }
}

@Singleton
class PackIDGenerator {

    private AtomicInteger seq = new AtomicInteger();

    public int getId() {
        return seq.incrementAndGet();
    }

    public void reset() {
        seq.set(0);
    }
}
