package org.codetab.uknit.core.make.method.detect;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class Inserters {

    @Inject
    private Nodes nodes;
    @Inject
    private InsertVars insertVars;

    private Map<Class<?>, List<String>> accessors;

    @Inject
    public Inserters(final Configs configs) {
        accessors = new HashMap<>();

        String prefix = "uknit.collection.access";
        Properties props = configs.getProperties(prefix);

        for (Object key : props.keySet()) {
            try {
                String clzName = (String) key;
                String value = (String) props.get(key);
                List<String> values =
                        new ArrayList<String>(Arrays.asList(value.split(",")));
                accessors.put(Class.forName(clzName), values);
            } catch (ClassNotFoundException e) {
                throw new CriticalException(e);
            }
        }
    }

    /**
     * If var is real and it is collection or map type and method is get() then
     * return true else false.
     *
     * @param var
     * @param exp
     * @return
     */
    public boolean isInsertable(final IVar var, final String clzName,
            final Expression exp) {

        checkNotNull(var);
        checkNotNull(clzName);
        checkNotNull(exp);

        if (isNull(var) || var.isMock()) {
            return false;
        }
        if (!nodes.is(exp, MethodInvocation.class)) {
            return false;
        }
        MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
        String methodName = nodes.getName(mi.getName());

        try {
            Class<?> clz = Class.forName(clzName);
            List<String> allowedMethods = accessors.get(clz);

            if (nonNull(allowedMethods) && allowedMethods.stream()
                    .anyMatch(m -> m.equals(methodName))) {
                return true;
            }
        } catch (ClassNotFoundException e) {
        }

        return false;
    }

    /**
     * Get insert method. For Collection type attach method is add and for Map
     * it is put.
     *
     * @param var
     * @return
     */
    public Optional<String> getInsertMethod(final IVar var,
            final String clzName) {
        checkNotNull(var);
        checkNotNull(clzName);

        Optional<String> attachMethod = Optional.empty();
        if (var.isMock()) {
            return attachMethod;
        }
        try {
            Class<?> clz = Class.forName(clzName);
            if (Collection.class.isAssignableFrom(clz)) {
                attachMethod = Optional.of("add");
            }
            if (Map.class.isAssignableFrom(clz)) {
                attachMethod = Optional.of("put");
            }
        } catch (ClassNotFoundException e) {
        }
        return attachMethod;
    }

    /**
     * Get args for insert stmt.
     * <p>
     * String value = list.get(0), args: value
     * <p>
     * String value = map.get(key), args: key and value
     * <p>
     * String value = map.get(person.getName()) and person.getName() returns
     * inferVar apple then args: apple and value
     *
     * @param callVar
     * @param leftVar
     * @param rightExp
     * @param heap
     * @return
     */
    public List<IVar> getArgs(final IVar callVar, final String clzName,
            final IVar leftVar, final Expression rightExp, final Heap heap) {

        checkNotNull(clzName);

        IVar key = null;
        try {
            if (nonNull(clzName)) {
                Class<?> clz = Class.forName(clzName);
                /*
                 * For map clz get first arg (key)
                 */
                if (Map.class.isAssignableFrom(clz)) {
                    if (nodes.is(rightExp, MethodInvocation.class)) {
                        MethodInvocation miCopy =
                                nodes.as(rightExp, MethodInvocation.class);
                        @SuppressWarnings("unchecked")
                        List<Expression> miArgs = miCopy.arguments();
                        if (miArgs.size() > 0) {
                            Expression arg1 = miArgs.get(0);

                            if (nodes.isName(arg1)) {
                                // resolved to var
                                key = heap.findVar(nodes.getName(arg1));
                            } else {
                                // not resolved, string or number literal etc.,
                                final int argIndex = 0;
                                Type arg1Type = insertVars.getArgType(
                                        callVar.getType(), argIndex);
                                key = insertVars.createInsertVar(arg1, arg1Type,
                                        heap);
                                heap.getVars().add(key);

                                ExpVar expVar = insertVars
                                        .createInsertExpVar(arg1, key);
                                heap.getVarExps().add(expVar);
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
        }

        /*
         * For list.add(.) add left var, for map.put(..) add first arg (key) and
         * left var (value)
         */
        List<IVar> args = new ArrayList<>();
        if (nonNull(key)) {
            args.add(key);
        }
        args.add(leftVar);
        return args;
    }

    /**
     * Get list of vars that are used in the original stmt such as map.get(key).
     * Note: the args is list of vars used in corresponding generated stmt such
     * as map.put(key,value).
     * <p>
     * String value = list.get(0), no used vars
     * <p>
     * String value = map.get(key), usedVars: key
     * <p>
     * String value = map.get(person.getName()) and person.getName() returns
     * inferVar apple then usedVars: apple
     *
     * @param callVar
     * @param leftVar
     * @param rightExp
     * @param heap
     * @return
     */
    public List<IVar> getUsedVars(final IVar callVar, final IVar leftVar,
            final Expression rightExp, final Heap heap) {
        List<IVar> usedVars = new ArrayList<>();
        if (nodes.is(rightExp, MethodInvocation.class)) {
            MethodInvocation miCopy =
                    nodes.as(rightExp, MethodInvocation.class);
            @SuppressWarnings("unchecked")
            List<Expression> miArgs = miCopy.arguments();
            for (Expression arg : miArgs) {
                if (nodes.is(arg, SimpleName.class)) {
                    try {
                        usedVars.add(heap.findVar(nodes.getName(arg)));
                    } catch (IllegalStateException e) {
                    }
                } else if (nodes.is(arg, NumberLiteral.class)) {
                    // list.get(0) - ignore
                    nodes.as(arg, NumberLiteral.class);
                }
            }
        }
        return usedVars;
    }

    /**
     * If input exp is MethodInvocation, then return invoked method. Otherwise
     * returns blank.
     * @param exp
     * @return
     */
    public String getInvokedMethod(final Expression exp) {
        checkNotNull(exp);
        if (nodes.is(exp, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(exp, MethodInvocation.class);
            return nodes.getName(mi.getName());
        }
        return "";
    }

}
