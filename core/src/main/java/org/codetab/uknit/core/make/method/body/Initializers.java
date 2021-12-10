package org.codetab.uknit.core.make.method.body;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.Variables;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;

public class Initializers {

    @Inject
    private Variables variables;
    @Inject
    private DefinedInitialzer definedInitialzer;
    @Inject
    private DerivedInitialzer derivedInitialzer;

    public String getInitializer(final IVar var, final Heap heap) {
        String initializer = null;
        Optional<Expression> iniExp =
                definedInitialzer.getInitializer(var, heap);

        if (iniExp.isPresent() && definedInitialzer.isAllowed(iniExp.get())) {
            initializer = iniExp.get().toString();
        } else {
            initializer = derivedInitialzer.deriveInitializer(var, heap);
        }

        if (nonNull(initializer) && initializer.contains("${metasyntatic}")) {
            initializer = initializer.replace("${metasyntatic}",
                    variables.getMetaSyntantics());
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
    private Methods methods;

    Class<?> allowed[] = {NumberLiteral.class, StringLiteral.class,
            TypeLiteral.class, CharacterLiteral.class, NullLiteral.class,
            ClassInstanceCreation.class, ArrayCreation.class,
            PrefixExpression.class, PostfixExpression.class,
            InfixExpression.class};

    public boolean isAllowed(final Expression exp) {
        for (Class<?> clz : allowed) {
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
        Expression initializerExp = null;
        String varName = var.getName();
        Expression rExp = null;
        while (true) {
            final String name = varName;
            final Expression exp = rExp;

            // get carry that matches leftVar or leftExp
            Optional<ExpVar> o = heap.getVarExps().stream().filter(e -> {
                boolean match = false;

                // find by var
                Optional<IVar> lvo = e.getLeftVar();
                if (nonNull(name) && lvo.isPresent()
                        && lvo.get().getName().equals(name)) {
                    match = true;
                }

                /*
                 * find by expression - expression instances from different
                 * statements are not equal so compare their string
                 * representation as workaround
                 */
                Expression le = e.getLeftExp();
                if (nonNull(exp) && nonNull(le)
                        && le.toString().equals(exp.toString())) {
                    match = true;
                }
                return match;
            }).findAny();

            if (o.isPresent()) {
                ExpVar expVar = o.get();
                Optional<IVar> rvo = expVar.getRightVar();
                // if rightVar is present get its name
                if (rvo.isPresent()) {
                    varName = rvo.get().getName();
                } else {
                    varName = null;
                }
                // else get rightExp
                rExp = expVar.getRightExp();
                if (nonNull(rExp)) {
                    // if SimpleName the it is var else it is initializer
                    if (nodes.is(rExp, SimpleName.class)) {
                        varName = nodes.getName(rExp);
                    } else {
                        initializerExp = rExp;
                    }
                }
            } else {
                // no more match, break
                break;
            }
        }
        return Optional.ofNullable(initializerExp);
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

        // initialize to mock
        if (isNull(initializer)) {
            // mock initialiser
            String initializerForMock;
            if (deep) {
                initializerForMock =
                        configs.getConfig("uknit.createInstance.mockDeep");
            } else {
                initializerForMock =
                        configs.getConfig("uknit.createInstance.mock");
            }

            if (initializerForMock.equals("null")) {
                initializer = null;
            } else {
                initializer = initializerForMock.replace("${type}", typeName);
            }
        }
        return initializer;
    }
}
