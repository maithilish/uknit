package org.codetab.uknit.core.node;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Methods {

    @Inject
    private Nodes nodes;
    @Inject
    private SnippetParser snippetParser;
    @Inject
    private Resolver resolver;

    public String getMethodName(final MethodDeclaration method) {
        return method.getName().getFullyQualifiedName();
    }

    public List<MethodDeclaration> getMethodsByName(
            final TypeDeclaration typeDecl, final String name) {
        List<MethodDeclaration> list = new ArrayList<>();
        MethodDeclaration[] methods = typeDecl.getMethods();
        for (int i = 0; i < methods.length; i++) {
            MethodDeclaration method = methods[i];
            if (method.getName().getFullyQualifiedName().equals(name)) {
                list.add(method);
            }
        }
        return list;
    }

    /**
     * When Test Method of same name exists, find and return next index starting
     * from 2. For first method returns blank string.
     * @param typeDecl
     * @param name
     * @return
     */
    public String getMethodsNameNextIndex(final TypeDeclaration typeDecl,
            final String name) {
        int nextIndex = 1;
        MethodDeclaration[] methods = typeDecl.getMethods();
        for (int i = 0; i < methods.length; i++) {
            MethodDeclaration method = methods[i];
            String methodName = method.getName().getFullyQualifiedName();
            if (methodName.startsWith(name)) {
                String suffix = methodName.replace(name, "");
                try {
                    if (suffix.equals("")) {
                        nextIndex = 2;
                    } else {
                        int index = Integer.parseInt(suffix);
                        if (index >= nextIndex) {
                            nextIndex = index + 1;
                        }
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        }
        if (nextIndex > 1) {
            return String.valueOf(nextIndex);
        } else {
            return "";
        }
    }

    public boolean isMain(final MethodDeclaration node) {
        return (node.getModifiers() & Modifier.STATIC) > 0
                && node.getName().getFullyQualifiedName().equals("main");
    }

    public boolean isConstructor(final MethodDeclaration node) {
        return node.isConstructor();
    }

    @SuppressWarnings("unchecked")
    public List<SingleVariableDeclaration> getParameters(
            final MethodDeclaration methodDecl) {
        return methodDecl.parameters();
    }

    /**
     * Whether exp is argument of mi.
     * @param mi
     * @param arg
     * @return
     */
    public boolean isAnArgument(final Expression arg,
            final MethodInvocation mi) {
        return mi.arguments().contains(arg);
    }

    @SuppressWarnings("unchecked")
    public void replaceArg(final MethodInvocation mi, final Expression arg,
            final String replace) {
        int index = mi.arguments().indexOf(arg);
        if (index >= 0) {
            Expression exp = (Expression) ASTNode.copySubtree(mi.getAST(),
                    snippetParser.parseExpression(replace));
            mi.arguments().remove(index);
            mi.arguments().add(index, exp);
        }
    }

    public boolean isStaticCall(final Expression expression) {
        if (!nodes.is(expression, MethodInvocation.class)) {
            return false;
        }

        MethodInvocation mi = nodes.as(expression, MethodInvocation.class);
        Expression exp = mi.getExpression();
        // method of this object
        if (isNull(exp) || nodes.is(exp, SimpleName.class)) {
            return (resolver.resolveMethodBinding(mi).getModifiers()
                    & Modifier.STATIC) > 0;
        }
        boolean isStatic = false;
        // if any part of chain call is static then isStatic is true
        while (nonNull(exp) && nodes.is(exp, MethodInvocation.class)) {
            MethodInvocation miExp = nodes.as(exp, MethodInvocation.class);
            if ((resolver.resolveMethodBinding(miExp).getModifiers()
                    & Modifier.STATIC) > 0) {
                isStatic = true;
            }
            exp = miExp.getExpression();
        }
        return isStatic;
    }

    public boolean isStaticCall(final MethodDeclaration methodDecl) {
        return (methodDecl.getModifiers() & Modifier.STATIC) > 0;
    }

    public boolean isPrivate(final MethodDeclaration methodDecl) {
        return (methodDecl.getModifiers() & Modifier.PRIVATE) > 0;
    }

    public List<Statement> getStatements(final MethodDeclaration methodDecl) {
        @SuppressWarnings("unchecked")
        List<Statement> stmts = methodDecl.getBody().statements();
        return stmts;
    }

    /**
     * Get var name of method invocation. If chained call, get top name.
     * @param mi
     * @return
     */
    public Optional<String> getVarName(final MethodInvocation mi) {
        String varName = null;
        Expression exp = mi.getExpression();
        while (nonNull(exp) && nodes.is(exp, MethodInvocation.class)) {
            exp = nodes.as(exp, MethodInvocation.class).getExpression();
        }
        if (nonNull(exp) && nodes.is(exp, SimpleName.class)) {
            varName = nodes.getName(exp);
        }
        return Optional.ofNullable(varName);
    }

    /**
     * Get argument list of {@link MethodInvocation}
     * @param mi
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<Expression> getArguments(final MethodInvocation mi) {
        return mi.arguments();
    }

    /**
     * Get argument list of {@link SuperMethodInvocation}
     * @param superMi
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<Expression> getArguments(final SuperMethodInvocation smi) {
        return smi.arguments();
    }

    /**
     * Get {@link SimpleName} named expression and arguments of
     * {@link MethodInvocation}
     * @param mi
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<String> getNames(final MethodInvocation mi) {
        List<String> names = new ArrayList<>();
        Expression exp = mi.getExpression();
        if (nonNull(exp) && nodes.is(exp, SimpleName.class)) {
            names.add(nodes.getName(exp));
        }
        List<Expression> args = mi.arguments();
        for (Expression arg : args) {
            if (nodes.is(arg, SimpleName.class)) {
                names.add(nodes.getName(arg));
            }
            if (nodes.is(arg, InfixExpression.class)) {
                InfixExpression infix = nodes.as(arg, InfixExpression.class);
                List<Expression> exps = new ArrayList<>();
                exps.add(infix.getLeftOperand());
                exps.add(infix.getRightOperand());
                exps.addAll(infix.extendedOperands());
                exps.stream().filter(e -> nodes.is(e, SimpleName.class))
                        .forEach(e -> names.add(nodes.getName(e)));
            }
        }
        return names;
    }

    public boolean returnsVoid(final MethodDeclaration methodDecl) {
        Type returnType = methodDecl.getReturnType2();
        if (isNull(returnType)) {
            return false; // constructor
        }
        return returnType.isPrimitiveType()
                && nodes.as(returnType, PrimitiveType.class)
                        .getPrimitiveTypeCode().equals(PrimitiveType.VOID);
    }
}
