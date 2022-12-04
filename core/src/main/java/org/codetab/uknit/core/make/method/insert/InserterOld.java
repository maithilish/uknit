package org.codetab.uknit.core.make.method.insert;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Insert;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

/**
 * Creates inserts and collect in heap. Inserts are used to generate list.add(.)
 * and map.put(..) statements.
 *
 * @author m
 *
 */
@Singleton
public class InserterOld {

    @Inject
    private Inserters inserters;
    @Inject
    private Nodes nodes;

    @Inject
    private InvokeStrategy invokeStrategy;
    @Inject
    private ForEachStrategy forEachStrategy;

    private Map<IVar, IVar> forEachVars = new HashMap<>();

    /**
     * The collections used in For Each is Expression, it may be var or method
     * invocations etc., Collect loop var and the collection (from the
     * expression) in a map for later processing.
     * @param node
     * @param internalMethod
     * @param heap
     */
    public void processForEach(final EnhancedForStatement node,
            final boolean internalMethod, final Heap heap) {
        Expression exp = node.getExpression();
        IVar loopVar = heap.findVar(nodes.getVariableName(node.getParameter()));
        if (nodes.is(exp, SimpleName.class)) {
            /*
             * The simple name is the collection var. No need to validate it as
             * compiler will allow only Iterable in for each statement.
             */
            String name = nodes.getName(exp);

            /*
             * When IMC parameter is mapped to calling arg, then use arg instead
             * of parameter.
             */
            if (internalMethod && heap.useArgVar(name)) {
                name = heap.getArgName(name);
            }

            IVar collectionVar = heap.findVar(name);
            forEachVars.put(collectionVar, loopVar);
        }
        if (nodes.is(exp, MethodInvocation.class)
                || nodes.is(exp, SuperMethodInvocation.class)) {
            Optional<Invoke> invokeO = heap.findInvoke(exp);
            if (invokeO.isPresent()) {
                IVar collectionVar = null;
                IVar callVar = invokeO.get().getCallVar();
                Optional<Class<?>> clz = inserters.getClz(callVar);
                if (clz.isPresent()
                        && inserters.isCollection(callVar, clz.get())) {
                    /*
                     * callVar of invoke is the collection var. Ex:
                     * map.keySet(), here we are not considering the Set
                     * returned by keySet() as we insert to the map.
                     */
                    collectionVar = callVar;
                } else {
                    /*
                     * callVar is not collection var, may be the return var of
                     * invoke is the collection var. Ex: holder.getList(), we
                     * are going to insert to the list not the holder.
                     */
                    Optional<IVar> returnVar = invokeO.get().getReturnVar();
                    if (returnVar.isPresent()) {
                        Optional<Class<?>> returnVarClz =
                                inserters.getClz(returnVar.get());
                        if (returnVarClz.isPresent() && inserters.isCollection(
                                returnVar.get(), returnVarClz.get())) {
                            collectionVar = returnVar.get();
                        }
                    }
                }
                if (nonNull(collectionVar)) {
                    forEachVars.put(collectionVar, loopVar);
                }
            }
        }
    }

    /**
     * Filter insertable vars from vars.
     * @param vars
     * @return
     */
    public List<IVar> filterInsertableVars(final List<IVar> vars) {
        List<IVar> insertableVars = new ArrayList<>();
        for (IVar var : vars) {
            Optional<Class<?>> clz = inserters.getClz(var);
            if (clz.isPresent() && inserters.isCollection(var, clz.get())) {
                insertableVars.add(var);
            }
        }
        return insertableVars;
    }

    /**
     * Apply ForEach or Invoke strategy to each insertable var and create
     * inserts. As list contains only the insertable var, no need to check
     * whether it is insertable.
     * @param insertableVars
     * @param heap
     */
    public void processInsertableVars(final List<IVar> insertableVars,
            final Heap heap) {
        for (IVar var : insertableVars) {
            Optional<Class<?>> clz = inserters.getClz(var);
            if (clz.isPresent()) {
                Optional<Insert> insertO = null;
                if (forEachVars.containsKey(var)) {
                    // strategy - forEach
                    IVar loopVar = forEachVars.get(var);
                    insertO = forEachStrategy.process(var, clz.get(), loopVar,
                            heap);
                } else {
                    // strategy - invoke
                    insertO = invokeStrategy.process(var, clz.get(), heap);
                }
                insertO.ifPresent(heap.getInserts()::add);
            }
        }
    }

    /**
     * Inserts are disabled by default. Enable them selectively. First enable
     * all vars used by the inserts. Next, if first arg is enabled then enable
     * all inserts with matching first arg. Also, enable second arg if exists.
     * @param heap
     */
    public void enableInserts(final Heap heap) {
        List<Insert> inserts = heap.getInserts();

        /**
         * For example, map.get(key); the var key may be enabled or disabled
         * depending on other factors. But insert uses it to generate stmt
         * map.put(key,value), so we safely enable it.
         */
        for (Insert insert : inserts) {
            insert.getUsedVars().forEach(v -> v.setEnable(true));
        }
        /**
         * String key = list.get(0); String name = list.get(0); return key;
         * <p>
         * The returned var, key, is enabled so enable its insert
         *
         */
        for (Insert insert : inserts) {
            List<IVar> args = insert.getArgs();
            IVar arg1 = args.get(0);
            if (arg1.isEnable()) {
                inserts.stream().filter(i -> i.getArgs().get(0).equals(arg1))
                        .forEach(i -> i.setEnable(true));
                // enable second args, if any.
                if (args.size() > 1) {
                    args.get(1).setEnable(true);
                }
            }
        }
    }
}
