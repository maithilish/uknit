package org.codetab.uknit.core.make.method;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.ClzMap;
import org.codetab.uknit.core.make.method.body.BodyMaker;
import org.codetab.uknit.core.make.method.detect.GetterSetter;
import org.codetab.uknit.core.make.method.stage.CallStager;
import org.codetab.uknit.core.make.method.visit.VarEnabler;
import org.codetab.uknit.core.make.method.visit.Visitor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Classes;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.Annotation;
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
    private Configs configs;
    @Inject
    private MethodMakers methodMakers;
    @Inject
    private CallStager callStager;
    @Inject
    private BodyMaker bodyMaker;
    @Inject
    private Methods methods;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Classes classes;
    @Inject
    private VarNames varNames;
    @Inject
    private VarEnabler varEnabler;
    @Inject
    private GetterSetter getterSetter;

    private ClzMap clzMap;

    private MethodDeclaration methodDecl;

    private TypeDeclaration clzDecl;

    public boolean stageMethod(final MethodDeclaration method,
            final Heap heap) {

        checkNotNull(method);
        checkNotNull(heap);

        LOG.debug("==== method under test: {} ====",
                methods.getMethodName(method));

        varNames.resetIndexes();

        String testClzName = methodMakers
                .getTestClzName((TypeDeclaration) method.getParent());
        clzDecl = clzMap.getTypeDecl(testClzName);
        Clz clz = clzMap.getClz(testClzName);

        String testMethodName = methodMakers.getTestMethodName(method, clzDecl);

        methodDecl = methodMakers.constructTestMethod(method, testMethodName);

        if (configs.getConfig("uknit.detect.getterSetter", true)) {
            getterSetter.detect(clz, method, methodDecl,
                    clzMap.getFields(testClzName));
        }

        Visitor visitor = di.instance(Visitor.class);
        visitor.setHeap(heap);
        visitor.setInternalMethod(false);

        // add method under test call
        callStager.stageCall(method, heap);

        // to set deep stub, set fields
        heap.getVars().addAll(clzMap.getFields(testClzName));

        method.accept(visitor);

        varEnabler.checkEnableState(heap);
        varEnabler.updateVarEnableState(heap);

        // TODO - enable this after multi try exception fix
        // variables.checkVarConsistency(heap.getVars());

        return true;
    }

    /**
     * Process internal method without staging it. Use separate Visitor and
     * newly initialized Heap to collect the IMC items. On completion, heap
     * contents are merged with the main heap.
     * <p>
     * The InternalCallProcessor.process() explains the use of paramArgMap.
     * @param method
     * @param paramArgMap
     * @param internalMethod
     * @param heap
     * @return
     */
    public boolean processMethod(final MethodDeclaration method,
            final Map<String, String> paramArgMap, final boolean internalMethod,
            final Heap heap) {

        checkNotNull(method);
        checkNotNull(heap);

        LOG.debug("= process method: {} =", methods.getMethodName(method));

        Heap internalHeap = di.instance(Heap.class);
        internalHeap.initialize(heap);
        internalHeap.setParamArgMap(paramArgMap);

        Visitor visitor = di.instance(Visitor.class);
        visitor.setHeap(internalHeap);
        visitor.setInternalMethod(internalMethod);

        // stage call for this method
        callStager.stageCall(method, internalHeap);

        method.accept(visitor);

        heap.merge(internalHeap);

        // TODO - enable this after multi try exception fix
        // variables.checkVarConsistency(heap.getVars());

        return true;
    }

    public void generateTestMethod(final Heap heap) {
        // generate parameters, infer and local vars
        bodyMaker.generateVarStmts(methodDecl, heap);
        bodyMaker.generateReturnVarStmt(methodDecl, heap);
        bodyMaker.generateWhenStmts(methodDecl, heap);
        bodyMaker.generateCallStmt(methodDecl, heap);
        bodyMaker.generateAssertStmt(methodDecl, heap);
        bodyMaker.generateArgCaptureStmts(methodDecl, heap);
        bodyMaker.generateVerifyStmts(methodDecl, heap);

        methodMakers.addMethod(clzDecl, methodDecl);
    }

    public void addBeforeMethod(final TypeDeclaration node,
            final String beforeAnnotation) {
        TypeDeclaration clzUnderTest = classes.asTypeDecl(node);
        String clzName = methodMakers.getTestClzName(clzUnderTest);
        TypeDeclaration testClzDecl = clzMap.getTypeDecl(clzName);

        MethodDeclaration md = nodeFactory.createMethodDecl("setUp");
        Annotation annotation =
                nodeFactory.createMarkerAnnotation(beforeAnnotation);
        methodMakers.addAnnotation(md, annotation);

        Modifier modifier =
                nodeFactory.createModifier(ModifierKeyword.PUBLIC_KEYWORD);
        methodMakers.addModifier(md, modifier);

        Type exception = nodeFactory.createSimpleType("Exception");
        methodMakers.addException(md, exception);

        Statement statement = nodeFactory
                .createMethodStatement("MockitoAnnotations.openMocks(this);");
        methodMakers.addStatement(md, statement);
        methodMakers.addMethod(testClzDecl, md);
    }

    public void setClzMap(final ClzMap clzMap) {
        this.clzMap = clzMap;
    }

    /**
     * Anonymous class method, Inner class method, Local class method,
     * constructor, main and (optionally) private methods are not stageable.
     * @param node
     * @return
     */
    public boolean isStageable(final MethodDeclaration node,
            final boolean ignorePrivate) {
        boolean stage = true;
        if (methodMakers.isAnonymousClassMethod(node)) {
            LOG.debug("{} anonymous class method, ignore", node.getName());
            stage = false;
        }
        if (methodMakers.isLocalClassMethod(node)) {
            LOG.debug("{} local class method, ignore", node.getName());
            stage = false;
        }
        if (methodMakers.isInnerClassMethod(node)) {
            LOG.debug("{} inner class method, ignore", node.getName());
            stage = false;
        }
        // constructor, main or private methods
        if (methodMakers.ignoreMethod(node, ignorePrivate)) {
            LOG.debug("method {} ignored", node.getName());
            stage = false;
        }
        return stage;
    }

    public boolean isStageable(final MethodDeclaration node) {
        boolean ignorePrivate = true;
        return isStageable(node, ignorePrivate);
    }
}
