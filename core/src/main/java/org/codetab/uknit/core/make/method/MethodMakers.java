package org.codetab.uknit.core.make.method;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.visit.NameVisitor;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.util.StringUtils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.google.common.base.CaseFormat;

public class MethodMakers {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Methods methods;
    @Inject
    private Types types;
    @Inject
    private Nodes nodes;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Configs configs;
    @Inject
    private NameVisitor nameVisitor;

    public String getClzName(final TypeDeclaration clzDecl) {
        return clzDecl.getName().getFullyQualifiedName();
    }

    public String getTestClzName(final TypeDeclaration clzDecl) {
        return String.join("", clzDecl.getName().getFullyQualifiedName(),
                "Test");
    }

    public void addModifier(final MethodDeclaration methodDecl,
            final Modifier modifier) {
        @SuppressWarnings("unchecked")
        List<Modifier> modifiers = methodDecl.modifiers();
        modifiers.add(modifier);
    }

    public void addAnnotation(final MethodDeclaration fieldDecl,
            final Annotation annotation) {
        @SuppressWarnings("unchecked")
        List<IExtendedModifier> modifiers = fieldDecl.modifiers();
        modifiers.add(annotation);
    }

    public void addMethod(final TypeDeclaration clzDecl,
            final MethodDeclaration methodDecl) {
        @SuppressWarnings("unchecked")
        List<BodyDeclaration> body = clzDecl.bodyDeclarations();
        body.add(methodDecl);
    }

    public void addStatement(final MethodDeclaration methodDecl,
            final Statement statement) {
        Block block = methodDecl.getBody();
        @SuppressWarnings("unchecked")
        List<Statement> stmts = block.statements();
        stmts.add(statement);
    }

    public void setBlock(final MethodDeclaration md, final Block block) {
        md.setBody(block);
    }

    public void addException(final MethodDeclaration md, final Type exception) {
        @SuppressWarnings("unchecked")
        List<Type> exceptions = md.thrownExceptionTypes();
        exceptions.add(exception);
    }

    public String getTestMethodName(final MethodDeclaration method,
            final TypeDeclaration clzDecl, final String nameSuffix) {
        String methodName = methods.getMethodName(method);
        String testMethodName = String.join("", "test",
                CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, methodName));

        if (methods.getMethodsByName(clzDecl, testMethodName).size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(methodName);
            @SuppressWarnings("unchecked")
            List<SingleVariableDeclaration> params = method.parameters();
            for (SingleVariableDeclaration param : params) {
                String typeName = types.getTypeName(param.getType());
                sb.append(StringUtils.capitalize(typeName));
            }
            testMethodName = String.join("", "test", CaseFormat.LOWER_CAMEL
                    .to(CaseFormat.UPPER_CAMEL, sb.toString()));
        }
        testMethodName = String.join("", testMethodName, nameSuffix);
        return String.join("", testMethodName,
                methods.getMethodsNameNextIndex(clzDecl, testMethodName));
    }

    @SuppressWarnings("unchecked")
    public MethodDeclaration constructTestMethod(
            final MethodDeclaration methodUnderTest,
            final String testMethodName) {

        MethodDeclaration methodDecl =
                nodeFactory.createMethodDecl(testMethodName);

        Annotation annotation = nodeFactory.createMarkerAnnotation("Test");
        methodDecl.modifiers().add(annotation);

        Modifier modifier =
                nodeFactory.createModifier(ModifierKeyword.PUBLIC_KEYWORD);
        methodDecl.modifiers().add(modifier);

        if (!methodUnderTest.thrownExceptionTypes().isEmpty()) {
            Type exception = nodeFactory.createSimpleType("Exception");
            methodDecl.thrownExceptionTypes().add(exception);
        }
        Block block = nodeFactory.createBlock();
        methodDecl.setBody(block);
        return methodDecl;
    }

    public boolean ignoreMethod(final MethodDeclaration node,
            final boolean ignorePrivate) {
        boolean ignore = false;
        if (methods.isMain(node)) {
            ignore = configs.getConfig("uknit.ignore.method.main", true);
            if (ignore) {
                LOG.debug("method {} is main, ignored", node.getName());
            }
        }
        if (methods.isConstructor(node)) {
            ignore = configs.getConfig("uknit.ignore.method.constructor", true);
            if (ignore) {
                LOG.debug("method {} is constructor, ignored", node.getName());
            }
        }
        if (ignorePrivate && methods.isPrivate(node)) {
            ignore = configs.getConfig("uknit.ignore.method.private", true);
            if (ignore) {
                LOG.debug("method {} is private, ignored", node.getName());
            }
        }
        return ignore;
    }

    public boolean isLocalClassMethod(final MethodDeclaration node) {
        ASTNode parent = node.getParent();
        while (parent != null) {
            if (nodes.is(parent, MethodDeclaration.class)) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public boolean isInnerClassMethod(final MethodDeclaration node) {
        int nestCount = 0;
        ASTNode parent = node.getParent();
        while (parent != null) {
            if (nodes.is(parent, TypeDeclaration.class)) {
                nestCount++;
            }
            parent = parent.getParent();
        }
        return nestCount > 1;
    }

    public boolean isAnonymousClassMethod(final MethodDeclaration node) {
        return nodes.is(node.getParent(), AnonymousClassDeclaration.class);
    }

    /**
     * Return suffix for test method. Example: for a method foo() then default
     * test method is testFoo(...). For same method and a control path,
     * if(done){...}, the suffix is IfDone and test method becomes
     * testFooIfDone(...).
     * @param ctlPath
     * @param ctlTree
     * @return
     */
    public String getTestMethodNameSuffix(final List<TreeNode<ASTNode>> ctlPath,
            final int lastCtlNodeIndex, final TreeNode<ASTNode> ctlTree) {

        TreeNode<ASTNode> ctlTNode = ctlPath.get(lastCtlNodeIndex);
        nameVisitor.setup(ctlTNode, ctlPath, ctlTree);

        // visit and construct suffix
        ASTNode ctlNode = ctlTNode.getObject();
        ctlNode.accept(nameVisitor);

        return nameVisitor.getSuffix();
    }

    /**
     * Return index of last control flow node in ctlPath list.
     * @param ctlPath
     * @return
     */
    public int getLastCtlNodeIndex(final List<TreeNode<ASTNode>> ctlPath) {
        int index = -1;
        for (int i = 0; i < ctlPath.size(); i++) {
            ASTNode node = ctlPath.get(i).getObject();
            if (node instanceof IfStatement || node instanceof TryStatement
                    || node instanceof CatchClause) {
                index = i;
            }
        }
        return index;
    }
}
