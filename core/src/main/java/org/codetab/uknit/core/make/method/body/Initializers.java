package org.codetab.uknit.core.make.method.body;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeGroups;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class Initializers {

    @Inject
    private VarNames varNames;
    @Inject
    private DefinedInitialzer definedInitialzer;
    @Inject
    private EnumInitializer enumInitializer;
    @Inject
    private DerivedInitialzer derivedInitialzer;
    @Inject
    private Patcher patcher;
    @Inject
    private Nodes nodes;

    public String getInitializer(final IVar var, final Heap heap) {
        String initializer = null;
        Optional<Expression> iniExp =
                definedInitialzer.getInitializer(var, heap);
        Optional<String> enumIni = enumInitializer.getInitializer(var, heap);

        if (iniExp.isPresent() && definedInitialzer.isAllowed(iniExp.get())) {
            Expression exp = patcher.copyAndPatch(iniExp.get(), heap);
            initializer = exp.toString();
        } else if (iniExp.isPresent()
                && nodes.is(iniExp.get(), SimpleName.class)) {
            initializer = nodes.getName(iniExp.get());
        } else if (enumIni.isPresent()) {
            initializer = enumIni.get();
        } else {
            initializer = derivedInitialzer.deriveInitializer(var, heap);
        }

        if (nonNull(initializer) && initializer.contains("${metasyntatic}")) {
            initializer = initializer.replace("${metasyntatic}",
                    varNames.getMetaSyntantics());
        }
        return initializer;
    }

    public Optional<Expression> getInitializerExp(final IVar var,
            final Heap heap) {
        return definedInitialzer.getInitializer(var, heap);
    }
}

/**
 * Initializer defined in source.
 * @author Maithilish
 *
 */
class DefinedInitialzer {
    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Methods methods;
    @Inject
    private NodeGroups nodeGroups;

    public boolean isAllowed(final Expression exp) {
        List<Class<?>> clzs = nodeGroups.allowedAsInitializer();
        for (Class<?> clz : clzs) {
            if (nodes.is(exp, clz)) {
                return true;
            }
        }
        if (nodes.is(exp, MethodInvocation.class) && methods
                .isStaticCall(nodes.as(exp, MethodInvocation.class))) {
            return true;
        }
        return false;
    }

    /**
     * The ExpVar maps Expression to an IVar, IVar to Expression or Expression
     * to Expression. This method gets initializer for following combinations.
     *
     * var = var, example: x = y
     *
     * var = exp, example: x = a[0]
     *
     * exp = var, example: a[0] = i;
     *
     * exp = exp, example: a[0] = a[1]
     *
     * @param var
     * @param heap
     * @return
     */
    public Optional<Expression> getInitializer(final IVar var,
            final Heap heap) {
        Optional<Pack> pack =
                packs.findByVarName(var.getName(), heap.getPacks());
        Expression iniExp = pack.get().getExp();
        /**
         * Casted initializer var type is already changed in Packer.packVars(),
         * so discard the cast from initializer. <code>
         * Object o = new Foo();
         * Foo foo = (Foo) o;
         * </code> the type o is set to Foo instead of Object.
         */
        if (nonNull(iniExp) && nodes.is(iniExp, CastExpression.class)) {
            CastExpression ce = nodes.as(iniExp, CastExpression.class);
            if (nodes.is(ce.getExpression(), SimpleName.class)) {
                iniExp = ce.getExpression();
            }
        }
        return Optional.ofNullable(iniExp);
    }
}

/**
 * Derive initializer based on config or type.
 * @author Maithilish
 *
 */
class DerivedInitialzer {
    @Inject
    private Configs configs;
    @Inject
    private Types types;
    @Inject
    private Resolver resolver;

    public String deriveInitializer(final IVar var, final Heap heap) {
        Type type = var.getType();
        boolean deep = var.isDeepStub();

        // initialize to configured type
        String typeName = types.getTypeName(type);
        String key = String.join(".", "uknit.createInstance", typeName);
        String initializer = configs.getConfig(key);

        if (isNull(initializer) && type.isArrayType()) {
            initializer =
                    configs.getConfig("uknit.createInstance.arrayType", "{}");
        }

        // default createInstance config is set as mock by user
        if (nonNull(initializer) && initializer.equalsIgnoreCase("mock")) {
            initializer = null;
        }

        if (resolver.isTypeVariable(type)) {
            initializer = "STEPIN";
        }

        // initialize to mock
        if (isNull(initializer)) {
            if (var.isMock()) {
                // mock but created is effectively real, can't test as mock
                if (var.isCreated()) {
                    initializer = "STEPIN";
                } else {
                    // mock initialiser
                    String initializerForMock;
                    if (deep) {
                        initializerForMock = configs
                                .getConfig("uknit.createInstance.mockDeep");
                    } else {
                        initializerForMock =
                                configs.getConfig("uknit.createInstance.mock");
                    }

                    if (initializerForMock.equals("null")) {
                        initializer = null;
                    } else {
                        initializer =
                                initializerForMock.replace("${type}", typeName);
                    }
                }
            } else {
                initializer = "STEPIN";
            }
        }
        return initializer;
    }
}

class EnumInitializer {

    @Inject
    private Resolver resolver;
    @Inject
    private Packs packs;

    public Optional<String> getInitializer(final IVar var, final Heap heap) {
        Optional<Invoke> invo =
                packs.findInvokeByName(var.getName(), heap.getPacks());
        ITypeBinding typeBind = null;
        if (invo.isPresent()) {
            Invoke invoke = invo.get();
            Optional<ReturnType> exrto = invoke.getReturnType();
            if (exrto.isPresent()) {
                typeBind = exrto.get().getTypeBinding();
            }
        } else {
            typeBind = resolver.resolveBinding(var.getType());
        }
        if (nonNull(typeBind) && typeBind.isEnum()) {
            String packg = typeBind.getPackage().getName();
            String qName = typeBind.getQualifiedName();
            IVariableBinding[] fields = typeBind.getDeclaredFields();
            if (fields.length > 1 && fields[0].getName().contains("$VALUES")) {
                String firstEnumConstant = fields[1].getName();
                String enumName =
                        qName.replace(String.join(".", packg, ""), "");
                return Optional
                        .of(String.join(".", enumName, firstEnumConstant));
            }
        }
        return Optional.empty();
    }
}
