package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class Expressions {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Nodes nodes;

    /**
     * Get name from various types of expression. InfixExpression is not handled
     * as it has left and right exp.
     * @param exp
     * @return name or null if not found
     */
    public String getName(final Expression exp) {
        checkNotNull(exp);

        String name = null;
        if (nodes.is(exp, SimpleName.class)) {
            name = nodes.getName(exp);
        } else if (nodes.is(exp, QualifiedName.class)) {
            name = nodes.getName(exp);
        } else if (nodes.is(exp, CastExpression.class)) {
            Expression castExp =
                    nodes.as(exp, CastExpression.class).getExpression();
            if (nonNull(castExp)) {
                name = getName(castExp);
            }
        } else if (nodes.is(exp, ParenthesizedExpression.class)) {
            Expression parExp = nodes.as(exp, ParenthesizedExpression.class)
                    .getExpression();
            if (nonNull(parExp)) {
                name = getName(parExp);
            }
        } else if (nodes.is(exp, PrefixExpression.class)) {
            Expression preExp =
                    nodes.as(exp, PrefixExpression.class).getOperand();
            if (nonNull(preExp)) {
                name = getName(preExp);
            }
        } else if (nodes.is(exp, PostfixExpression.class)) {
            Expression postExp =
                    nodes.as(exp, PostfixExpression.class).getOperand();
            if (nonNull(postExp)) {
                name = getName(postExp);
            }
        }
        return name;
    }

    public Optional<String> mapExpToName(final Expression arg,
            final Heap heap) {
        String argName = null;
        if (nodes.is(arg, SimpleName.class)) {
            argName = nodes.getName(arg);
        } else if (nodes.is(arg, MethodInvocation.class)
                || nodes.is(arg, SuperMethodInvocation.class)) {
            Optional<IVar> var = heap.findLeftVarByRightExp(arg);
            if (var.isPresent()) {
                argName = var.get().getName();
            }
        } else if (nodes.isCreation(arg)) {
            LOG.debug("arg {} is creation node, ignore",
                    arg.getClass().getSimpleName());
        } else {
            throw new CodeException(nodes.noImplmentationMessage(arg));
        }
        return Optional.ofNullable(argName);
    }

    public void collectNames(final Expression exp, final List<String> names,
            final Properties properties) {

        checkNotNull(exp);

        String name = getName(exp);
        if (nonNull(name)) {
            names.add(name);
        }

        if (nodes.is(exp, InfixExpression.class)) {
            InfixExpression inFix = nodes.as(exp, InfixExpression.class);
            collectNames(inFix.getLeftOperand(), names, properties);

            if (properties.getProperty("add.infixRight")
                    .equalsIgnoreCase("true")) {
                collectNames(inFix.getRightOperand(), names, properties);
            }
            @SuppressWarnings("unchecked")
            List<Expression> opers = inFix.extendedOperands();
            opers.forEach(o -> collectNames(o, names, properties));
        }

        if (nodes.is(exp, MethodInvocation.class)) {

            MethodInvocation mi = nodes.as(exp, MethodInvocation.class);

            Expression miExp = mi.getExpression();
            if (nonNull(miExp)) {
                collectNames(miExp, names, properties);
            }

            names.add(nodes.getName(mi.getName()));
            if (properties.getProperty("add.methodInvokeArgs")
                    .equalsIgnoreCase("true")) {
                @SuppressWarnings("unchecked")
                List<Expression> args = mi.arguments();
                for (Expression arg : args) {
                    collectNames(arg, names, properties);
                }
            }
        }
    }
}
