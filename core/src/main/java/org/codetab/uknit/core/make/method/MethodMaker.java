package org.codetab.uknit.core.make.method;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.ClzMap;
import org.codetab.uknit.core.make.Variables;
import org.codetab.uknit.core.make.method.body.BodyMaker;
import org.codetab.uknit.core.make.method.stage.CallStager;
import org.codetab.uknit.core.make.method.stage.ParameterStager;
import org.codetab.uknit.core.make.method.visit.UseMarker;
import org.codetab.uknit.core.make.method.visit.Visitor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Classes;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class MethodMaker {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private DInjector di;
    @Inject
    private MethodMakers methodMakers;
    @Inject
    private ParameterStager parameterStager;
    @Inject
    private CallStager callStager;
    @Inject
    private BodyMaker bodyMaker;
    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Classes classes;
    @Inject
    private Variables variables;
    @Inject
    private UseMarker useMarker;

    private ClzMap clzMap;

    private Heap heap;

    private MethodDeclaration methodDecl;

    private TypeDeclaration clzDecl;

    public boolean stageMethod(final MethodDeclaration node) {
        MethodDeclaration methodUnderTest = node;

        LOG.debug("==== method under test: {} ====",
                methods.getMethodName(methodUnderTest));

        variables.resetIndexes();

        if (nodes.is(methodUnderTest.getParent(),
                AnonymousClassDeclaration.class)) {
            LOG.debug("method belongs to an anonymous class, ignore");
            return false;
        }
        if (methodMakers.ignoreMethod(node)) {
            LOG.debug("method {} ignored", node.getName());
            return false;
        }

        String clzName = methodMakers
                .getTestClzName((TypeDeclaration) methodUnderTest.getParent());
        clzDecl = clzMap.getTypeDecl(clzName);

        String testMethodName =
                methodMakers.getTestMethodName(methodUnderTest, clzDecl);

        methodDecl = methodMakers.constructTestMethod(methodUnderTest,
                testMethodName);

        Visitor visitor = di.instance(Visitor.class);
        heap = di.instance(Heap.class);
        visitor.setHeap(heap);

        // add method under test call and parameters
        callStager.stageCall(methodUnderTest, heap);
        parameterStager.stageParameters(methods.getParameters(methodUnderTest),
                heap);

        // to set deep stub, set fields
        heap.getVars().addAll(clzMap.getFields(clzName));

        methodUnderTest.accept(visitor);

        useMarker.mark(heap);
        return true;
    }

    public void addMethod() {
        // generate parameters, infer and local vars
        bodyMaker.generateVarStmts(methodDecl, heap);
        bodyMaker.generateReturnVarStmt(methodDecl, heap);
        bodyMaker.generateWhenStmts(methodDecl, heap);
        bodyMaker.generateCallStmt(methodDecl, heap);
        bodyMaker.generateAssertStmt(methodDecl, heap);
        // bodyMaker.generateArgCaptureStmts(methodDecl, method);
        bodyMaker.generateVerifyStmts(methodDecl, heap);

        methodMakers.addMethod(clzDecl, methodDecl);
    }

    public void addBeforeMethod(final TypeDeclaration node) {
        TypeDeclaration clzUnderTest = classes.asTypeDecl(node);
        String clzName = methodMakers.getTestClzName(clzUnderTest);
        TypeDeclaration clzDecl = clzMap.getTypeDecl(clzName);

        MethodDeclaration md = nodeFactory.createMethodDecl("setUp");
        Annotation annotation =
                nodeFactory.createMarkerAnnotation("BeforeEach");
        methodMakers.addAnnotation(md, annotation);

        Modifier modifier =
                nodeFactory.createModifier(ModifierKeyword.PUBLIC_KEYWORD);
        methodMakers.addModifier(md, modifier);

        Type exception = nodeFactory.createSimpleType("Exception");
        methodMakers.addException(md, exception);

        Statement statement = nodeFactory
                .createMethodStatement("MockitoAnnotations.openMocks(this);");
        methodMakers.addStatement(md, statement);
        methodMakers.addMethod(clzDecl, md);
    }

    public void setClzMap(final ClzMap clzMap) {
        this.clzMap = clzMap;
    }
}
