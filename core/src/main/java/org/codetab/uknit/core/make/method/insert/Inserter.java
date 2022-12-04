package org.codetab.uknit.core.make.method.insert;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Insert;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Variables;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

@Singleton
public class Inserter {

    @Inject
    private Variables variables;
    @Inject
    private Vars vars;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Inserters inserters;
    @Inject
    private ForEachStrategy forEachStrategy;

    private List<EnhancedForStatement> enForNodes;
    private Map<IVar, IVar> forEachVars;

    // REVIEW
    public void setup() {
        enForNodes = new ArrayList<>();
        forEachVars = new HashMap<>();
    }

    public void collectEnhancedFor(final EnhancedForStatement node,
            final boolean imc) {
        // REVIEW
        node.setProperty("codetab.inImc", imc);
        enForNodes.add(node);
    }

    public void processEnhancedFor(final EnhancedForStatement node,
            final Heap heap) {
        Expression exp = node.getExpression();
        String loopVarName = variables.getVariableName(node.getParameter());
        IVar loopVar = vars.findVarByName(loopVarName, heap);

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
            // REVIEW
            // if (internalMethod && heap.useArgVar(name)) {
            // name = heap.getArgName(name);
            // }

            IVar collectionVar = vars.findVarByName(name, heap);
            forEachVars.put(collectionVar, loopVar);
        }
        if (nodes.is(exp, MethodInvocation.class)
                || nodes.is(exp, SuperMethodInvocation.class)) {
            Optional<Invoke> invokeO =
                    packs.findInvokeByExp(exp, heap.getPacks());
            if (invokeO.isPresent()) {
                IVar collectionVar = null;
                Optional<IVar> callVarO = invokeO.get().getCallVar();
                if (callVarO.isPresent()) {
                    Optional<Class<?>> clz = inserters.getClz(callVarO.get());
                    if (clz.isPresent() && inserters
                            .isCollection(callVarO.get(), clz.get())) {
                        /*
                         * callVar of invoke is the collection var. Ex:
                         * map.keySet(), here we are not considering the Set
                         * returned by keySet() as we insert to the map.
                         */
                        collectionVar = callVarO.get();
                    } else {
                        /*
                         * callVar is not collection var, may be the return var
                         * of invoke is the collection var. Ex:
                         * holder.getList(), we are going to insert to the list
                         * not the holder.
                         */
                        IVar returnVar = invokeO.get().getVar();
                        if (nonNull(returnVar)) {
                            Optional<Class<?>> returnVarClz =
                                    inserters.getClz(returnVar);
                            if (returnVarClz.isPresent()
                                    && inserters.isCollection(returnVar,
                                            returnVarClz.get())) {
                                collectionVar = returnVar;
                            }
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
     *
     * @param varList
     * @return
     */
    public List<IVar> filterInsertableVars(final List<IVar> varList) {
        List<IVar> insertableVars = new ArrayList<>();
        for (IVar var : varList) {
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
                Optional<Insert> insertO = Optional.empty();
                if (forEachVars.containsKey(var)) {
                    // strategy - forEach
                    IVar loopVar = forEachVars.get(var);
                    insertO = forEachStrategy.process(var, clz.get(), loopVar,
                            heap);
                    System.out.println("forEachStrategy " + var);
                } else {
                    // strategy - invoke
                    // insertO = invokeStrategy.process(var, clz.get(), heap);
                    System.out.println("invokeStrategy " + var);
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

    public List<EnhancedForStatement> getEnhancedForNodes() {
        return enForNodes;
    }
}
