package org.codetab.uknit.core.make.method.load;

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
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Load;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;

public class Loaders {

    @Inject
    private Nodes nodes;
    @Inject
    private LoadVars loadVars;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Types types;
    @Inject
    private Packs packs;
    @Inject
    private Vars vars;

    private Map<Class<?>, List<String>> accessors;
    private Map<Class<?>, String> loadMethods;

    @Inject
    public Loaders(final Configs configs) {
        accessors = new HashMap<>();
        loadMethods = new HashMap<>();

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

        // find load method for each loadable clz
        for (Class<?> clz : accessors.keySet()) {
            Method[] methods = clz.getMethods();
            boolean isPut = Arrays.stream(methods)
                    .anyMatch(m -> m.getName().equals("put"));
            if (isPut) {
                loadMethods.put(clz, "put");
            } else {
                boolean isAdd = Arrays.stream(methods)
                        .anyMatch(m -> m.getName().equals("add"));
                if (isAdd) {
                    loadMethods.put(clz, "add");
                } else {
                    throw new CodeException(
                            spaceit("unable to find load method for",
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
             * (input) is assignable to List (collectionClz) is true, reverse is
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
    public boolean isLoadable(final IVar var, final Class<?> clz,
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
     * Get load method. For Collection type load method is add and for Map it is
     * put.
     *
     * @param var
     * @return
     */
    public String getLoadMethod(final Class<?> clz) {
        checkNotNull(clz);

        if (loadMethods.containsKey(clz)) {
            return loadMethods.get(clz);
        } else {
            throw new CodeException(spaceit("unable to find load method for",
                    clz.getSimpleName()));
        }
    }

    /**
     * If collection has put method then return true else false.
     * @param clz
     * @return
     */
    public boolean requiresKey(final Class<?> clz) {
        if (loadMethods.containsKey(clz)) {
            return loadMethods.get(clz).equals("put");
        } else {
            throw new CodeException(spaceit("unable to find load method for",
                    clz.getSimpleName()));
        }
    }

    /**
     * Get first arg in method invoke used by collection. This arg is used as
     * key for put() load statement. If arg is literal such as StringLiteral or
     * NumberLiteral stage new Pack.
     * <p>
     * Ex: If source has List<Pet> pets = map.get("foo") then we need to create
     * map.put(..) statement to load pets to map. The arg "foo" is key arg for
     * which create a new inferVar apple and pets is loaded with map.put(apple,
     * pets).
     * @param collectionVar
     * @param patchedMi
     * @param heap
     * @return
     */
    public IVar getKeyArg(final IVar collectionVar, final MethodInvocation mi,
            final MethodInvocation patchedMi, final Heap heap) {
        checkNotNull(collectionVar);
        checkNotNull(mi);
        checkNotNull(patchedMi);
        checkNotNull(heap);

        @SuppressWarnings("unchecked")
        List<Expression> miArgs = patchedMi.arguments();
        if (miArgs.size() > 0) {
            Expression arg1 = miArgs.get(0);
            if (nodes.isName(arg1)) {
                // resolved var
                return vars.findVarByName(nodes.getName(arg1), heap);
            } else if (nodes.is(arg1, ArrayAccess.class)) {
                /*
                 * FIXME N - ArrayAccess arg in MI is not patched.
                 */
                ArrayAccess aa =
                        nodes.as(mi.arguments().get(0), ArrayAccess.class);
                Optional<Pack> packO = packs.findByExp(aa, heap.getPacks());
                if (packO.isPresent() && nonNull(packO.get().getVar())) {
                    return packO.get().getVar();
                } else {
                    throw new IllegalStateException(
                            "infer var not staged for array access" + aa);
                }
            } else {
                /*
                 * For mi literal arg, such as map.get("foo"), inferVar is
                 * neither created nor patched. As they are required just for
                 * loads, create inferVar without patching them to mi.
                 */
                final int argIndex = 0;
                Type arg1Type =
                        loadVars.getTypeArg(collectionVar.getType(), argIndex);
                IVar loadVar =
                        loadVars.createLoadVarForLiteral(arg1, arg1Type, heap);
                Pack pack = loadVars.createLoadPack(arg1, loadVar, heap.isIm());
                heap.addPack(pack);
                return loadVar;
            }
        }
        return null;
    }

    /**
     * Find first allowed Invoke for the var via invoke call var. Return the
     * first Invoke with allowed access method such as get() etc., Ex: There are
     * two invokes and pack on collection map - map.size() and map.get(key). If
     * var is map, search for invokes with call var equals map and return the
     * map.get() pack as size() is not allowed access method.
     * @param var
     * @param clz
     * @param heap
     * @return
     */
    public Optional<Pack> findFirstAllowedPack(final IVar var,
            final Class<?> clz, final Heap heap) {
        checkNotNull(var);
        checkNotNull(clz);
        checkNotNull(heap);

        List<Invoke> invokes =
                packs.filterInvokes(heap.getPacks()).stream().filter(i -> {
                    // static calls such as nonNull(...) results in null callVar
                    if (i.getCallVar().isPresent()) {
                        return i.getCallVar().get().getName()
                                .equals(var.getName());
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());

        for (Invoke invoke : invokes) {
            if (isLoadable(var, clz, invoke.getExp())) {
                Optional<Pack> packO =
                        packs.findByExp(invoke.getExp(), heap.getPacks());
                if (packO.isPresent()) {
                    return packO;
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Find all allowed Invokes for the var via invoke call var. Ex: There are
     * three invokes and pack on collection map - map.size(), map.get(key) and
     * map.remove(key). If var is map, search for invokes with call var equals
     * map and return the map.get() and map.remove() pack as size() is not
     * allowed access method.
     * @param var
     * @param clz
     * @param heap
     * @return
     */
    public List<Invoke> findAllowedPacks(final IVar var, final Class<?> clz,
            final Heap heap) {
        checkNotNull(var);
        checkNotNull(clz);
        checkNotNull(heap);

        List<Invoke> invokes =
                packs.filterInvokes(heap.getPacks()).stream().filter(i -> {
                    // static calls such as nonNull(...) results in null callVar
                    if (i.getCallVar().isPresent()) {
                        return i.getCallVar().get().getName()
                                .equals(var.getName());
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList());
        List<Invoke> allowedInvokes =
                invokes.stream().filter(i -> isLoadable(var, clz, i.getExp()))
                        .collect(Collectors.toList());
        return allowedInvokes;
    }

    /**
     * Create load.
     * @param var
     * @param valueVar
     * @param keyVar
     * @param loadMethod
     * @return
     */
    public Load createLoad(final IVar var, final IVar valueVar,
            final IVar keyVar, final String loadMethod) {
        checkNotNull(var);
        checkNotNull(valueVar);
        checkNotNull(loadMethod);

        List<IVar> args = new ArrayList<>();
        List<IVar> usedVars = new ArrayList<>();
        if (isNull(keyVar)) {
            args.add(valueVar);
        } else {
            args.add(keyVar);
            args.add(valueVar);
            usedVars.add(keyVar);
        }
        Load load = modelFactory.createLoad(var, loadMethod, args, usedVars);
        return load;
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

    /**
     * From the list of invokes return the var whose type is equals to type arg.
     * Ex: For the var Map<String, Date> the type args are String and Date. Find
     * var whose type is either String or Date.
     *
     * @param invokes
     * @param var
     * @param heap
     * @return
     */
    public Optional<IVar> findPutInferVar(final List<Invoke> invokes,
            final IVar var, final Heap heap) {

        // type args of ParameterizedType
        List<Type> typeArgs = loadVars.getTypeArgs(var.getType());

        for (Invoke pack : invokes) {
            IVar packVar = pack.getVar();
            if (nonNull(packVar)) {
                Type type = packVar.getType();
                if (typeArgs.stream().anyMatch(
                        ta -> ta.toString().equals(type.toString()))) {
                    return Optional.of(packVar);
                }
            }
        }
        return Optional.empty();
    }
}
