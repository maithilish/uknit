package org.codetab.uknit.core.make.method.load;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.TypeNameException;
import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Load;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Variables;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class Loader {

    @Inject
    private Variables variables;
    @Inject
    private Vars vars;
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Loaders loaders;
    @Inject
    private ForEachStrategy forEachStrategy;
    @Inject
    private InvokeStrategy invokeStrategy;

    private List<EnhancedForStatement> enForNodes = new ArrayList<>();;
    private Map<IVar, IVar> forEachVars = new HashMap<>();
    private List<Load> loads = new ArrayList<>();

    /**
     * Collects EnhancedForStatement nodes in visit phase.
     *
     * @param node
     * @param imc
     */
    public void collectEnhancedFor(final EnhancedForStatement node,
            final Heap heap) {
        node.setProperty("codetab.inImc", heap.isIm());
        enForNodes.add(node);
    }

    /**
     * Collects collection var and loop var of the EnhancedForStatement node in
     * the forEachVars.
     *
     * Ex: for(String name : names), the names is collection var and name is
     * loop var.
     *
     * @param node
     * @param heap
     */
    public void processEnhancedFor(final EnhancedForStatement node,
            final Heap heap) {
        Expression exp = node.getExpression();
        String loopVarName = variables.getVariableName(node.getParameter());
        IVar loopVar = null;
        try {
            loopVar = vars.findVarByName(loopVarName, heap);
        } catch (VarNotFoundException e) {
            loopVar = vars.findVarByOldName(loopVarName, heap);
        }

        if (nodes.is(exp, SimpleName.class)) {
            /*
             * The simple name is the collection var. No need to validate it as
             * compiler will allow only Iterable in for each statement.
             */
            String name = nodes.getName(exp);
            IVar collectionVar = null;
            try {
                collectionVar = vars.findVarByName(name, heap);
            } catch (VarNotFoundException e) {
                collectionVar = vars.findVarByOldName(name, heap);
            }
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
                    Optional<Class<?>> clz = loaders.getClz(callVarO.get());
                    if (clz.isPresent() && loaders.isCollection(callVarO.get(),
                            clz.get())) {
                        /*
                         * callVar of invoke is the collection var. Ex:
                         * map.keySet(), here we are not considering the Set
                         * returned by keySet() as we have to load to the map.
                         */
                        collectionVar = callVarO.get();
                    } else {
                        /*
                         * callVar is not collection var, may be the return var
                         * of invoke is the collection var. Ex:
                         * holder.getList(), we are going to load to the list
                         * not the holder.
                         */
                        IVar returnVar = invokeO.get().getVar();
                        if (nonNull(returnVar)) {
                            Optional<Class<?>> returnVarClz =
                                    loaders.getClz(returnVar);
                            if (returnVarClz.isPresent()
                                    && loaders.isCollection(returnVar,
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
        // loop vars are enforced in enableLoads()
    }

    /**
     * Filter loadable vars from vars.
     *
     * @param varList
     * @return
     */
    public List<IVar> filterLoadableVars(final List<IVar> varList) {
        List<IVar> loadableVars = new ArrayList<>();
        for (IVar var : varList) {
            try {
                Optional<Class<?>> clz = loaders.getClz(var);
                if (clz.isPresent() && loaders.isCollection(var, clz.get())) {
                    loadableVars.add(var);
                }
            } catch (TypeNameException e) {

            }
        }
        return loadableVars;
    }

    /**
     * Apply ForEach or Invoke strategy to each loadable var and create loads.
     * As list contains only the loadable var, no need to check whether it is
     * loadable.
     * @param loadableVars
     * @param heap
     */
    public void processLoadableVars(final List<IVar> loadableVars,
            final Heap heap) {
        for (IVar var : loadableVars) {
            Optional<Class<?>> clz = loaders.getClz(var);
            if (clz.isPresent()) {
                if (loaders.isCollection(var, clz.get())) {
                    var.addNature(Nature.COLLECTION);
                }
                Optional<Load> loadO = Optional.empty();
                if (forEachVars.containsKey(var)) {
                    // strategy - forEach
                    IVar loopVar = forEachVars.get(var);
                    loadO = forEachStrategy.process(var, clz.get(), loopVar,
                            heap);
                } else {
                    // strategy - invoke
                    loadO = invokeStrategy.process(var, clz.get(), heap);
                }

                if (loadO.isPresent()) {
                    /*
                     * if any arg ( key/value loaded to collection) is mock but
                     * realish then remove realish so that mock is added to
                     * collection otherwise tries to add real.
                     *
                     * Ex: File file = holder.getMap().get(key); here file is
                     * mock but its nature is realish as map.get() make it so
                     * because Map is real. If nature is not removed from file
                     * its initialiser is set to STEPIN instead of mock.
                     */
                    loadO.get().getArgs().forEach(v -> {
                        if (v.isMock() && v.is(Nature.REALISH)) {
                            v.getNatures().remove(Nature.REALISH);
                        }
                    });
                    loads.add(loadO.get());
                }
            }
        }
    }

    /**
     * Loads are disabled by default. Enable them selectively. First enable all
     * vars used by the loads. Next, if first arg is enabled then enable all
     * loads with matching first arg. Also, enable second arg if exists.
     *
     * @param heap
     */
    public void enableLoads(final Heap heap) {
        /*
         * For example, map.get(key); the var key may be enabled or disabled
         * depending on other factors. The processVarState() which is called
         * after enabledLoads(), disables load vars as they are not used by
         * when/verify. As load uses the vars to generate stmt
         * map.put(key,value), set enforce as true instead of enable. *
         */
        for (Load load : loads) {
            load.getUsedVars().forEach(v -> v.setEnforce(true));
        }
        /*
         * String key = list.get(0); String name = list.get(0); return key; The
         * returned var, key, is enabled so enable its load
         */
        for (Load load : loads) {
            List<IVar> args = load.getArgs();
            IVar arg1 = args.get(0);
            if (arg1.isEnable()) {
                loads.stream().filter(i -> i.getArgs().get(0).equals(arg1))
                        .forEach(i -> i.setEnable(true));
                /*
                 * for load vars use enforce instead of enable as
                 * processVarState() disables these vars.
                 */
                if (args.size() > 1) {
                    // enable both args
                    args.get(0).setEnforce(true);
                    args.get(1).setEnforce(true);
                } else {
                    args.get(0).setEnforce(true);
                }
            }
        }
    }

    /**
     * Merge all forEachVars of the other loader to this loader.
     *
     * @param other
     */
    public void merge(final Loader other) {
        forEachVars.putAll(other.forEachVars);
    }

    public List<EnhancedForStatement> getEnhancedForNodes() {
        return enForNodes;
    }

    public List<Load> getLoads() {
        return loads;
    }

    public Map<IVar, IVar> getForEachVars() {
        return forEachVars;
    }

    public boolean usedByLoader(final IVar var) {
        boolean used = false;
        for (Load load : loads) {
            if (load.getArgs().contains(var)) {
                used = true;
            }
        }
        return used;
    }
}
