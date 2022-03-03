package org.codetab.uknit.core.make.method.detect;

import static java.util.Objects.isNull;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.google.common.base.CaseFormat;

public class GetterSetters {

    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Configs configs;

    public boolean isGetter(final MethodDeclaration methodDecl) {

        boolean getter = true;

        if (!nodes.getName(methodDecl.getName()).startsWith("get")) {
            getter = false;
        }
        if (methodDecl.isConstructor() || methodDecl.isCompactConstructor()) {
            getter = false;
        }
        if (methodDecl.parameters().size() != 0) {
            getter = false;
        }
        // returns null or void
        Type returnType = methodDecl.getReturnType2();
        if (isNull(returnType)) {
            getter = false; // may be constructor
        } else {
            if (methods.returnsVoid(methodDecl)) {
                getter = false;
            }
        }
        return getter;
    }

    public boolean isSetter(final MethodDeclaration methodDecl) {
        boolean setter = true;
        if (!nodes.getName(methodDecl.getName()).startsWith("set")) {
            setter = false;
        }
        if (methodDecl.isConstructor() || methodDecl.isCompactConstructor()) {
            setter = false;
        }
        if (methodDecl.parameters().size() != 1) {
            setter = false;
        }
        return setter;
    }

    /**
     * Get name of var passed to setter.
     * @param methodDecl
     * @param removeStr
     * @return
     */
    public String getVarName(final MethodDeclaration methodDecl,
            final String removeStr) {
        List<SingleVariableDeclaration> parameters =
                methods.getParameters(methodDecl);
        /*
         * get var name from setter method parameter else from method name
         */
        if (parameters.size() == 1) {
            return nodes.getName(parameters.get(0).getName());
        } else {
            String name = nodes.getName(methodDecl.getName());
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
                    name.replace(removeStr, ""));
        }
    }

    /**
     * Get name of the object on which getter or setter is invoked.
     * @param typeDecl
     * @return
     */
    public String getObjName(final TypeDeclaration typeDecl) {
        String name = nodes.getName(typeDecl.getName());
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name);
    }

    /**
     * For setter return getter name or vice versa.
     *
     * @param methodDecl
     * @return
     */
    public String getOppositeMethodName(final MethodDeclaration methodDecl) {
        String methodName = nodes.getName(methodDecl.getName());
        String flipName = methodName;
        if (methodName.startsWith("get")) {
            flipName = methodName.replace("get", "set");
        } else if (methodName.startsWith("set")) {
            flipName = methodName.replace("set", "get");
        }
        return flipName;
    }

    /**
     * Whether setter can be deleted. Only setter with no return and single set
     * statement are delete candidates.
     * @param setter
     * @return
     */
    public boolean isDelete(final MethodDeclaration setter,
            final String setterSignature) {
        boolean delete = true;

        Type returnType = setter.getReturnType2();
        if (isNull(returnType)) {
            delete = false;
        } else {
            delete = false;
            if (methods.returnsVoid(setter)) {
                delete = true;
            }
        }

        List<Statement> stmts = methods.getStatements(setter);

        if (stmts.size() == 2) {
            if (!nodes.is(stmts.get(0), VariableDeclarationStatement.class)) {
                delete = false;
            }
            if (!stmts.get(1).toString()
                    .equals(setterSignature + System.lineSeparator())) {
                delete = false;
            }
        } else {
            delete = false;
        }
        return delete;
    }

    /**
     *
     * @param method
     * @param fields
     * @return
     */
    public boolean isMockInjected(final String varName,
            final List<Field> fields) {
        boolean mockInjected = fields.stream()
                .anyMatch(f -> f.getName().equals(varName) && !f.isDisable());
        return mockInjected;
    }

    public boolean returnsVoid(final MethodDeclaration methodDecl) {
        return methods.returnsVoid(methodDecl);
    }

    public Statement createGetterCall(final String objName,
            final String methodName) {
        String format = configs.getFormat("uknit.format.call");
        return nodeFactory.createCallStmt(objName, methodName, "", "Object",
                format);
    }

    public Statement createSetterCall(final String objName,
            final String methodName, final String varName) {
        String format = configs.getFormat("uknit.format.callVoid");
        return nodeFactory.createCallStmt(objName, methodName, varName, format);
    }

    public Statement createAssertStatement(final String varName) {
        String fmt = configs.getConfig("uknit.format.assert.same");
        if (fmt.contains("${expected}")) {
            fmt = fmt.replace("${expected}", varName);
        }
        return nodeFactory.createAssertStatement(fmt);
    }

    public boolean isGetterDefined(final String methodName,
            final TypeDeclaration typeDecl) {
        for (MethodDeclaration methodDecl : typeDecl.getMethods()) {
            if (nodes.getName(methodDecl.getName()).equals(methodName)) {
                return true;
            }
        }
        return false;
    }
}
