package org.codetab.uknit.core.make.method.detect.insert;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.Insert;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class Inserters {

    @Inject
    private Nodes nodes;
    @Inject
    private InsertVars insertVars;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Types types;

    private Map<Class<?>, List<String>> accessors;
    private Map<Class<?>, String> insertMethods;

    @Inject
    public Inserters(final Configs configs) {
        accessors = new HashMap<>();
        insertMethods = new HashMap<>();

        // get allowed access methods for collections from config
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

        // find insert method for each insertable clz
        for (Class<?> clz : accessors.keySet()) {
            Method[] methods = clz.getMethods();
            boolean isPut = Arrays.stream(methods)
                    .anyMatch(m -> m.getName().equals("put"));
            if (isPut) {
                insertMethods.put(clz, "put");
            } else {
                boolean isAdd = Arrays.stream(methods)
                        .anyMatch(m -> m.getName().equals("add"));
                if (isAdd) {
                    insertMethods.put(clz, "add");
                } else {
                    throw new CodeException(
                            spaceit("unable to find insert method for",
                                    clz.getSimpleName()));
                }
            }
        }
    }

    /**
     * Whether var is real and collection type - List, Set, Map, Queue, Deque,
     * Tree etc., This is a just a type check and lenient as invoked method is
     * not considered.
     * @param var
     * @param clz
     * @return
     */
    public boolean isCollection(final IVar var, final Class<?> clz) {

        checkNotNull(var);
        checkNotNull(clz);

        if (isNull(var) || var.isMock()) {
            return false;
        } else {
            /**
             * Accessor map key is allowed collection class.
             * <p>
             * whether input clz is assignable to collectionClz. Ex: ArrayList
             * (input) is assignable to List (collectionClz) is true reverse is
             * false.
             */
            return accessors.keySet().stream().anyMatch(
                    collectionClz -> collectionClz.isAssignableFrom(clz));
        }
    }

    /**
     * If var is real and it is collection (List,Map,Tree etc.) type and allowed
     * access method such as get(), remove() etc., then return true else false.
     * Ignores methods such as size(), toArray() etc.,
     * <p>
     * The allowed access method are configured via config key prefix
     * uknit.collection.access. Ex: For list get and remove are access methods
     *
     * uknit.collection.access.java.util.List=get,remove
     *
     * @param var
     * @param exp
     * @return
     */
    public boolean isInsertable(final IVar var, final Class<?> clz,
            final Expression exp) {

        checkNotNull(var);
        checkNotNull(clz);
        checkNotNull(exp);

        if (isNull(var) || var.isMock() || !var.isEnable()) {
            return false;
        }
        if (nodes.is(exp, MethodInvocation.class)) {
            String methodName =
                    nodes.getName(((MethodInvocation) exp).getName());

            List<String> allowedMethods = accessors.get(clz);
            if (nonNull(allowedMethods) && allowedMethods.stream()
                    .anyMatch(m -> m.equals(methodName))) {
                return true;
            }
        }
        return false;
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

    /**
     * Get insert method. For Collection type insert method is add and for Map
     * it is put.
     *
     * @param var
     * @return
     */
    public String getInsertMethod(final Class<?> clz) {
        checkNotNull(clz);

        if (insertMethods.containsKey(clz)) {
            return insertMethods.get(clz);
        } else {
            throw new CodeException(spaceit("unable to find insert method for",
                    clz.getSimpleName()));
        }
    }

    public boolean requiresKey(final Class<?> clz) {
        if (insertMethods.containsKey(clz)) {
            return insertMethods.get(clz).equals("put");
        } else {
            throw new CodeException(spaceit("unable to find insert method for",
                    clz.getSimpleName()));
        }
    }

    /**
     * Get first arg in method invoke used by collection. This arg is used as
     * key for put() insert statement. If arg is literal such as StringLiteral
     * or NumberLiteral stage new InferVar and ExpVar.
     * <p>
     * Ex: If source has List<Pet> pets = map.get("foo") then we need to create
     * map.put(..) statement to insert pets to map. The arg "foo" is key arg for
     * which create a new inferVar apple and pets is inserted with
     * map.put(apple, pets).
     * @param collectionVar
     * @param resolvedMi
     * @param heap
     * @return
     */
    public IVar getKeyArg(final IVar collectionVar,
            final MethodInvocation resolvedMi, final Heap heap) {
        checkNotNull(collectionVar);
        checkNotNull(resolvedMi);
        checkNotNull(heap);

        @SuppressWarnings("unchecked")
        List<Expression> miArgs = resolvedMi.arguments();
        if (miArgs.size() > 0) {
            Expression arg1 = miArgs.get(0);
            if (nodes.isName(arg1)) {
                // resolved var
                return heap.findVar(nodes.getName(arg1));
            } else {
                /*
                 * For mi literal arg, such as map.get("foo"), inferVar is
                 * neither created nor patched. As they are required just for
                 * inserts, create inferVar without patching them to mi.
                 */
                final int argIndex = 0;
                Type arg1Type = insertVars.getArgType(collectionVar.getType(),
                        argIndex);
                InferVar inferVar = insertVars.createInsertVarForLiteral(arg1,
                        arg1Type, heap);
                heap.getVars().add(inferVar);
                ExpVar expVar = insertVars.createInsertExpVar(arg1, inferVar);
                heap.getVarExps().add(expVar);
                return inferVar;
            }
        }
        return null;
    }

    public Optional<ExpVar> findFirstAllowedExpVar(final IVar var,
            final Class<?> clz, final Heap heap) {

        List<Invoke> invokes = heap.getInvokes().stream().filter(i -> {
            // static calls such as nonNull(...) results in null callVar
            if (nonNull(i.getCallVar())) {
                return i.getCallVar().getName().equals(var.getName());
            } else {
                return false;
            }
        }).collect(Collectors.toList());

        for (Invoke invoke : invokes) {
            if (isInsertable(var, clz, invoke.getExp())) {
                Optional<ExpVar> expVarO = heap.findByRightExp(invoke.getExp());
                if (expVarO.isPresent()) {
                    return expVarO;
                }
            }
        }
        return Optional.empty();
    }

    public List<ExpVar> findAllowedExpVars(final IVar var, final Class<?> clz,
            final Heap heap) {

        List<Invoke> invokes = heap.getInvokes().stream().filter(i -> {
            // static calls such as nonNull(...) results in null callVar
            if (nonNull(i.getCallVar())) {
                return i.getCallVar().getName().equals(var.getName());
            } else {
                return false;
            }
        }).collect(Collectors.toList());

        List<ExpVar> list = new ArrayList<>();
        for (Invoke invoke : invokes) {
            if (isInsertable(var, clz, invoke.getExp())) {
                heap.findByRightExp(invoke.getExp()).ifPresent(list::add);
            }
        }
        return list;
    }

    public Insert createInsert(final IVar var, final IVar valueVar,
            final IVar keyVar, final String insertMethod) {
        List<IVar> args = new ArrayList<>();
        List<IVar> usedVars = new ArrayList<>();
        if (isNull(keyVar)) {
            args.add(valueVar);
        } else {
            args.add(keyVar);
            args.add(valueVar);
            usedVars.add(keyVar);
        }
        Insert insert =
                modelFactory.createInsert(var, insertMethod, args, usedVars);
        return insert;
    }

    /**
     * Get clz of IVar. Return empty optional if var is null or unable to get
     * clzName for var type or unable get clz for the clzName.
     * <p>
     * The input is var instead of var type so that extra nonNull(var) test is
     * avoided in clients. It is enough to check clz.isPresent().
     * @param var
     * @return
     */
    public Optional<Class<?>> getClz(final IVar var) {
        Class<?> clz = null;
        try {
            if (nonNull(var)) {
                String clzName = types.getClzName(var.getType());
                if (nonNull(clzName)) {
                    clz = Class.forName(clzName);
                }
            }
        } catch (ClassNotFoundException e) {
        }
        return Optional.ofNullable(clz);
    }
}
