package org.codetab.uknit.core.make.method.detect;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.method.visit.Patcher;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Insert;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;

/**
 * Creates inserts and collect in heap. Inserts are used to generate list.add(.)
 * and map.put(..) statements.
 *
 * @author m
 *
 */
public class Inserter {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Inserters inserters;
    @Inject
    private ModelFactory factory;
    @Inject
    private Patcher patcher;
    @Inject
    private VarNames varNames;
    @Inject
    private Nodes nodes;
    @Inject
    private Types types;
    @Inject
    private Mocks mocks;
    @Inject
    private ModelFactory modelFactory;

    /**
     * For each expVar create, if insertable, Insert and collect in heap.
     * <p>
     * Inserts for collection access statements such as list.get(0) or
     * map.get(key) etc., are handled here. The for-each nodes are processed in
     * next method as it will not invoke get().
     * @param heap
     */
    // public void processOld(final Heap heap) {
    // List<ExpVar> expVars = heap.getVarExps();
    //
    // for (ExpVar expVar : expVars) {
    //
    // Optional<Invoke> oInvoke = heap.findInvoke(expVar.getRightExp());
    //
    // if (oInvoke.isPresent() && nonNull(oInvoke.get().getCallVar())) {
    //
    // Invoke invoke = oInvoke.get();
    // IVar callVar = invoke.getCallVar();
    // String clzName = types.getClzName(callVar.getType());
    //
    // if (nonNull(clzName) && expVar.getLeftVar().isPresent()
    // && inserters.isInsertable(callVar, clzName,
    // invoke.getExp())) {
    //
    // Optional<String> insertMethod =
    // inserters.getInsertMethod(callVar, clzName);
    // if (insertMethod.isPresent()) {
    //
    // /*
    // * resolve any mi collection access such as
    // * map.get(foo.get())
    // */
    // Expression rExp = expVar.getRightExp();
    // if (nodes.is(rExp, MethodInvocation.class)) {
    // rExp = patcher.copyAndPatch(
    // nodes.as(rExp, MethodInvocation.class),
    // heap);
    // }
    //
    // List<IVar> args = inserters.getArgs(callVar, clzName,
    // expVar.getLeftVar().get(), rExp, heap);
    // List<IVar> usedVars = inserters.getUsedVars(callVar,
    // expVar.getLeftVar().get(), rExp, heap);
    //
    // Insert insert = factory.createInsert(callVar,
    // insertMethod.get(), args, usedVars);
    // heap.getInserts().add(insert);
    //
    // LOG.trace("{}", insert);
    // LOG.trace("args {}", args.stream().map(v -> v.getName())
    // .collect(Collectors.joining(",")));
    // LOG.trace("usedVars {}",
    // usedVars.stream().map(v -> v.getName())
    // .collect(Collectors.joining(",")));
    // }
    // }
    // }
    // }
    // }

    /**
     * For each Invoke create Insert and collect in heap, if insertable and
     * expVar and leftVar is present and callVar clzName is resolved.
     * <p>
     * Inserts for collection access statements such as list.get(0) or
     * map.get(key) etc., are handled here. The for-each nodes are processed in
     * next method as it will not invoke get().
     * @param heap
     */
    public void process(final Heap heap) {

        for (Invoke invoke : heap.getInvokes()) {

            Optional<ExpVar> expVarO = heap.findByRightExp(invoke.getExp());

            if (nonNull(invoke.getCallVar()) && expVarO.isPresent()) {

                ExpVar expVar = expVarO.get();
                IVar callVar = invoke.getCallVar();
                String clzName = types.getClzName(callVar.getType());

                if (nonNull(clzName) && expVar.getLeftVar().isPresent()
                        && inserters.isInsertable(callVar, clzName,
                                invoke.getExp())) {

                    Optional<String> insertMethod =
                            inserters.getInsertMethod(callVar, clzName);
                    if (insertMethod.isPresent()) {

                        /*
                         * resolve any mi collection access such as
                         * map.get(foo.get())
                         */
                        Expression rExp = expVar.getRightExp();
                        if (nodes.is(rExp, MethodInvocation.class)) {
                            rExp = patcher.copyAndPatch(
                                    nodes.as(rExp, MethodInvocation.class),
                                    heap);
                        }

                        List<IVar> args = inserters.getArgs(callVar, clzName,
                                expVar.getLeftVar().get(), rExp, heap);
                        List<IVar> usedVars = inserters.getUsedVars(callVar,
                                expVar.getLeftVar().get(), rExp, heap);

                        Insert insert = factory.createInsert(callVar,
                                insertMethod.get(), args, usedVars);
                        heap.getInserts().add(insert);

                        LOG.trace("{}", insert);
                        LOG.trace("args {}", args.stream().map(v -> v.getName())
                                .collect(Collectors.joining(",")));
                        LOG.trace("usedVars {}",
                                usedVars.stream().map(v -> v.getName())
                                        .collect(Collectors.joining(",")));
                    }
                }

            }
        }
    }

    /**
     * The expression used in for-each are processed here. They normally doesn't
     * involve access method such as list.get(0) etc.,
     * @param node
     * @param heap
     */
    public void process(final EnhancedForStatement node, final Heap heap) {
        IVar callVar = null;
        IVar leftVar = heap.findVar(nodes.getVariableName(node.getParameter()));
        Expression exp = node.getExpression();
        List<IVar> args = new ArrayList<>();
        if (nodes.is(exp, SimpleName.class)) {
            callVar = heap.findVar(nodes.getName(exp));
            args.add(leftVar);
        } else if (nodes.is(exp, MethodInvocation.class)
                || nodes.is(exp, SuperMethodInvocation.class)) {
            /*
             * for(String key : map.keySet()), keySet() returns a Set but we
             * have to insert to map.
             */
            Optional<Invoke> oInvoke = heap.findInvoke(exp);
            if (oInvoke.isPresent()) {
                Invoke invoke = oInvoke.get();
                callVar = invoke.getCallVar();
                if (nonNull(callVar)) {
                    String clzName = types.getClzName(callVar.getType());
                    if (isNull(clzName)) {
                        // if clzName is null, then returnVar may be collection
                        // type
                        if (invoke.getReturnVar().isPresent()) {
                            callVar = invoke.getReturnVar().get();
                        }
                    } else {
                        /*
                         * if callVar is not insertable then returnVar may be
                         * collection type
                         */
                        if (!inserters.isInsertable(callVar, clzName, exp)) {
                            if (invoke.getReturnVar().isPresent()) {
                                callVar = invoke.getReturnVar().get();
                            }
                        }
                    }
                } else {
                    if (invoke.getReturnVar().isPresent()) {
                        callVar = invoke.getReturnVar().get();
                    }
                }
                String clzName = types.getClzName(callVar.getType());
                Type callType = callVar.getType();
                Optional<String> insertMethod =
                        inserters.getInsertMethod(callVar, clzName);
                /*
                 * Create and add an inferVar for map.put(inferVar, leftVar),
                 * for list.add(leftVar) it not required.
                 */
                if (insertMethod.isPresent()
                        && insertMethod.get().equals("put")) {
                    String invokedMethod =
                            inserters.getInvokedMethod(invoke.getExp());
                    Type type = null;
                    if (nodes.is(callType, ParameterizedType.class)) {
                        if (invokedMethod.equals("keySet")) {
                            type = (Type) ((ParameterizedType) callType)
                                    .typeArguments().get(1);
                        } else {
                            type = (Type) ((ParameterizedType) callType)
                                    .typeArguments().get(0);
                        }
                    }
                    // if not generic collection then type is Object
                    if (nodes.is(callType, SimpleType.class)) {
                        type = types.getType("java.lang.Object",
                                callType.getAST());
                    }
                    String name =
                            varNames.getInferVarName(Optional.empty(), heap);
                    InferVar inferVar = modelFactory.createInferVar(name, type,
                            mocks.isMockable(type));
                    heap.getVars().add(inferVar);
                    if (invokedMethod.equals("keySet")) {
                        args.add(leftVar);
                        args.add(inferVar);
                    } else {
                        args.add(inferVar);
                        args.add(leftVar);
                    }
                } else {
                    args.add(leftVar);
                }
            }
        }

        List<IVar> usedVars = new ArrayList<>();
        if (args.size() > 0) {
            usedVars.add(args.get(0));
        }

        String clzName = types.getClzName(callVar.getType());
        if (nonNull(clzName)) {
            Optional<String> insertMethod =
                    inserters.getInsertMethod(callVar, clzName);
            if (insertMethod.isPresent()) {
                Insert insert = factory.createInsert(callVar,
                        insertMethod.get(), args, usedVars);
                heap.getInserts().add(insert);
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
