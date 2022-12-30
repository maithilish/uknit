package org.codetab.uknit.core.make.method.getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * Detects getter and setter methods and collects them in map for post
 * processing. This class is singleton, reset its state before parsing a new
 * class.
 *
 * @author Maithilish
 *
 */
@Singleton
public class GetterSetter {

    @Inject
    private GetterSetters getterSetters;
    @Inject
    private DInjector di;
    @Inject
    private Methods methods;

    private Map<Clz, List<Holder>> getters;
    private Map<Clz, List<Holder>> setters;

    /**
     * Collect all setters and getter methods in maps for post process.
     * @param clzName
     * @param method
     * @param testMethod
     * @param fields
     */
    public void detect(final Clz clz, final MethodDeclaration method,
            final MethodDeclaration testMethod, final List<Field> fields) {
        if (getterSetters.isGetter(method)) {
            if (!getters.containsKey(clz)) {
                getters.put(clz, new ArrayList<>());
            }

            String varName = getterSetters.getVarName(method, "get");
            boolean isMockInjected =
                    getterSetters.isMockInjected(varName, fields);

            Holder holder = new Holder(method, testMethod, isMockInjected);
            getters.get(clz).add(holder);
        }
        if (getterSetters.isSetter(method)) {
            if (!setters.containsKey(clz)) {
                setters.put(clz, new ArrayList<>());
            }

            String varName = getterSetters.getVarName(method, "set");
            boolean isMockInjected =
                    getterSetters.isMockInjected(varName, fields);

            Holder holder = new Holder(method, testMethod, isMockInjected);
            setters.get(clz).add(holder);
        }
    }

    /**
     * Process collected getters and setters. Field which is not mock is not
     * injected, modify its getter. For setter that returns void, call getter
     * and assert statement.
     */
    public void postProcess() {
        /*
         * if mock is not injected, modify getter and insert setter call next to
         * the var declaration. Ex: String name = "Foo"; person.setName(name);
         */
        for (Clz clz : getters.keySet()) {
            for (Holder getterHolder : getters.get(clz)) {
                if (!getterHolder.isMockInjected) {
                    /*
                     * clz under test (self field). For PersonTest, person is
                     * self field.
                     */
                    MethodDeclaration getterTestMethodDecl =
                            getterHolder.testMethodDecl;
                    String objName =
                            getterSetters.getObjName(clz.getTypeDecl());
                    String varName = getterSetters
                            .getVarName(getterTestMethodDecl, "testGet");

                    String contraMethodName = getterSetters
                            .getContraMethodName(getterHolder.methodDecl);
                    // if setter defined
                    if (getterSetters.isMethodDefined(contraMethodName,
                            clz.getTypeDecl())) {
                        /*
                         * use visitor to insert setter call next to var
                         * declaration
                         */
                        GetterVisitor getterVisitor =
                                di.instance(GetterVisitor.class);
                        getterVisitor.init(getterTestMethodDecl, objName,
                                varName, contraMethodName);
                        getterTestMethodDecl.accept(getterVisitor);
                    }
                }
            }
        }

        /*
         * if setter returns void then add get and assert statements
         */
        for (Clz clz : setters.keySet()) {
            for (Holder setterHolder : setters.get(clz)) {
                MethodDeclaration testMethodDeclOfSetter =
                        setterHolder.testMethodDecl;
                if (getterSetters.returnsVoid(setterHolder.methodDecl)) {
                    String objName =
                            getterSetters.getObjName(clz.getTypeDecl());
                    String varName = getterSetters
                            .getVarName(setterHolder.methodDecl, "set");
                    String contraMethodName = getterSetters
                            .getContraMethodName(setterHolder.methodDecl);
                    // if getter defined
                    if (getterSetters.isMethodDefined(contraMethodName,
                            clz.getTypeDecl())) {
                        /*
                         * insert new statements - Object actual =
                         * person.getName(); assertSame(name,actual);
                         */
                        List<Statement> stmts =
                                methods.getStatements(testMethodDeclOfSetter);
                        stmts.add(getterSetters.createGetterCall(objName,
                                contraMethodName));
                        stmts.add(getterSetters.createAssertStatement(varName));
                    }
                }
            }
        }
    }

    /*
     * GetterSetter is singleton, reset its state before parsing a new class
     */
    public void reset() {
        getters = new HashMap<>();
        setters = new HashMap<>();
    }

    private class Holder {
        private MethodDeclaration methodDecl;
        private MethodDeclaration testMethodDecl;
        private boolean isMockInjected;

        Holder(final MethodDeclaration methodDecl,
                final MethodDeclaration testMethodDecl,
                final boolean isMockInjected) {
            // original method (getter or setter)
            this.methodDecl = methodDecl;
            // test method (getter or setter)
            this.testMethodDecl = testMethodDecl;
            this.isMockInjected = isMockInjected;
        }
    }
}

/**
 * GetterVisitor inserts setter call after variable declaration.
 *
 * @author Maithilish
 *
 */
class GetterVisitor extends ASTVisitor {

    private MethodDeclaration getterTestMethodDecl;
    private String objName;
    private String varName;
    private String methodName;

    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private GetterSetters getterSetters;

    public void init(final MethodDeclaration aGetterTestMethodDecl,
            final String aObjName, final String aVarName,
            final String aMethodName) {
        this.getterTestMethodDecl = aGetterTestMethodDecl;
        this.objName = aObjName;
        this.varName = aVarName;
        this.methodName = aMethodName;
    }

    @Override
    public void endVisit(final VariableDeclarationStatement node) {
        @SuppressWarnings("unchecked")
        List<VariableDeclarationFragment> fragments = node.fragments();
        for (VariableDeclarationFragment vdf : fragments) {
            if (nodes.getName(vdf.getName()).equals(varName)) {
                Statement setterCall = getterSetters.createSetterCall(objName,
                        methodName, varName);

                /*
                 * insert setter call, person.setName(name); after this node
                 */
                List<Statement> stmts =
                        methods.getStatements(getterTestMethodDecl);
                stmts.add(stmts.indexOf(node) + 1, setterCall);
            }
        }
    }
}
