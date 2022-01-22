package org.codetab.uknit.core.make.method.visit;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.ExpReturnType;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;

public class Patchers {

    @Inject
    private ArgPatcher argPatcher;
    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;

    /**
     * Patch expression with var - arg expressions in MethodInvocation,
     * ClassInstanceCreation and ArrayCreation and expression in
     * MethodInvocation and ReturnStatement.
     * <p>
     * foo.x().bar(baz.x()) - apple.bar(orange)
     * <p>
     * return foo.bar() - return apple
     * <p>
     * new String[group.size()] - new String[apple]
     * <p>
     * new Date(person.getDob()) - new Date(apple)
     * @param node
     * @param exp
     * @param name
     * @return
     */
    public boolean patchExpWithVar(final ASTNode node, final Patch patch) {
        checkNotNull(node);
        checkNotNull(patch);

        String name = patch.getName();
        if (nodes.is(node, ReturnStatement.class)) {
            ReturnStatement rs = nodes.as(node, ReturnStatement.class);
            rs.setExpression(rs.getAST().newSimpleName(name));
            return true;
        }
        if (nodes.is(node, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(node, MethodInvocation.class);
            if (patch.getArgIndex() >= 0) {
                @SuppressWarnings("unchecked")
                List<Expression> args = mi.arguments();
                argPatcher.patch(args, patch);
            } else {
                mi.setExpression(mi.getAST().newSimpleName(name));
            }
            return true;
        }
        if (nodes.is(node, ClassInstanceCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ClassInstanceCreation.class).arguments();
            argPatcher.patch(args, patch);
            return true;
        }
        if (nodes.is(node, ArrayCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ArrayCreation.class).dimensions();
            argPatcher.patch(args, patch);
            return true;
        }
        throw nodes.unexpectedException(node);
    }

    public int getArgIndex(final ASTNode node, final Expression exp) {
        checkNotNull(node);
        checkNotNull(exp);

        if (nodes.is(node, ReturnStatement.class)) {
            return -1;
        }
        if (nodes.is(node, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(node, MethodInvocation.class);
            @SuppressWarnings("unchecked")
            List<Expression> args = mi.arguments();
            if (args.contains(exp)) {
                return args.indexOf(exp);
            } else {
                return -1;
            }
        }
        if (nodes.is(node, ClassInstanceCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ClassInstanceCreation.class).arguments();
            if (args.contains(exp)) {
                return args.indexOf(exp);
            } else {
                return -1;
            }
        }
        if (nodes.is(node, ArrayCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ArrayCreation.class).dimensions();
            if (args.contains(exp)) {
                return args.indexOf(exp);
            } else {
                return -1;
            }
        }
        throw nodes.unexpectedException(node);
    }

    public List<Expression> getExps(final ASTNode node) {
        checkNotNull(node);

        List<Expression> exps = new ArrayList<>();
        if (nodes.is(node, ReturnStatement.class)) {
            ReturnStatement rs = nodes.as(node, ReturnStatement.class);
            exps.add(rs.getExpression());
        } else if (nodes.is(node, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(node, MethodInvocation.class);
            exps.add(mi.getExpression());
            @SuppressWarnings("unchecked")
            List<Expression> args = mi.arguments();
            exps.addAll(args);
        } else if (nodes.is(node, ClassInstanceCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ClassInstanceCreation.class).arguments();
            exps.addAll(args);
        } else if (nodes.is(node, ArrayCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ArrayCreation.class).dimensions();
            exps.addAll(args);
        }
        return exps;
    }

    public boolean patchable(final MethodInvocation mi, final Invoke invoke) {
        boolean patch = true;
        if (nodes.is(mi.getParent(), MethodInvocation.class)) {
            /*
             * static calls: return Byte.valueOf("100"); replace
             * date.compareTo(LocalDate.now()) == 1; don't replace
             */

            if (methods.isStaticCall(mi)) {
                patch = false;
            }

            /*
             * return s2.append(file.getName().toLowerCase()); toLowerCase on
             * string is real returning real, don't replace it.
             */
            Optional<ExpReturnType> ro = invoke.getExpReturnType();
            if (ro.isPresent()) {
                boolean returnReal = !ro.get().isMock();
                IVar var = invoke.getCallVar();
                boolean varReal = !(nonNull(var) && var.isMock());
                if (varReal && returnReal) {
                    patch = false;
                }
            }
        }
        return patch;
    }
}
