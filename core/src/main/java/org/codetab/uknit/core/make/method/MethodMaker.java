package org.codetab.uknit.core.make.method;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.ClzMap;
import org.codetab.uknit.core.make.method.body.BodyMaker;
import org.codetab.uknit.core.make.method.processor.Processor;
import org.codetab.uknit.core.make.method.stage.CallStager;
import org.codetab.uknit.core.make.method.visit.Visitor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Classes;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.tree.TreeNode;
import org.eclipse.jdt.core.dom.ASTNode;
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
    private Processor processor;
    @Inject
    private CallStager callStager;
    @Inject
    private Methods methods;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private BodyMaker bodyMaker;
    @Inject
    private Classes classes;
    @Inject
    private VarNames varNames;

    private ClzMap clzMap;

    private MethodDeclaration testMethod;

    private TypeDeclaration clzDecl;

    private Clz clz;

    public boolean stageMethod(final MethodDeclaration method,
            final List<TreeNode<ASTNode>> ctlPath, final String testNameSuffix,
            final Heap heap) {
        checkNotNull(method);
        checkNotNull(ctlPath);
        checkNotNull(testNameSuffix);
        checkNotNull(heap);

        varNames.resetIndexes();

        String testClzName = methodMakers
                .getTestClzName((TypeDeclaration) method.getParent());
        clzDecl = clzMap.getTypeDecl(testClzName);
        clz = clzMap.getClz(testClzName);

        String testMethodName =
                methodMakers.getTestMethodName(method, clzDecl, testNameSuffix);

        LOG.debug("Method: {}", methods.getMethodSignature(method),
                testMethodName);
        LOG.debug("Test Method: {}", testMethodName);

        testMethod = methodMakers.constructTestMethod(method, testMethodName);
        heap.setTestThrowsException(
                methodMakers.isMethodUnderTestThrowsException(method));

        // FIXME Pack - enable this
        // if (configs.getConfig("uknit.detect.getterSetter", true)) {
            // getterSetter.detect(clz, method, testMethod,
            // clzMap.getFieldsCopy(testClzName));
        // }

        Visitor visitor = di.instance(Visitor.class);
        visitor.setHeap(heap);
        visitor.setImc(false);
        visitor.setCtlPath(ctlPath);
        visitor.setSplitOnControlFlow(
                configs.getConfig("uknit.controlFlow.method.split", true));
        visitor.setMethodReturnType(method.getReturnType2());

        // add method under test call
        callStager.stageCall(method, heap);

        // to set deep stub, create field packs
        heap.getPacks().addAll(methodMakers
                .createFieldPacks(clzMap.getFieldsCopy(testClzName)));
        method.accept(visitor);

        processor.processInfers(heap);

        /*
         * varEnabler.checkEnableState(heap);
         *
         * Set<String> usedNames = varEnabler.collectUsedVarNames(heap);
         * varEnabler.updateVarEnableState(usedNames, heap);
         * varEnabler.addLocalVarForDisabledField(usedNames, heap);
         *
         * clzMap.updateFieldState(testClzName, heap.getVars(IVar::isField));
         *
         * // create inserts for list.add() etc., List<IVar> insertableVars =
         * inserter.filterInsertableVars(heap.getVars());
         * inserter.processInsertableVars(insertableVars, heap);
         * inserter.enableInserts(heap);
         *
         * methodMakers.addThrowsException(testMethod, heap);
         *
         * // TODO - enable this after multi try exception fix //
         * variables.checkVarConsistency(heap.getVars());
         *
         */

        return true;
    }

    public void generateTestMethod(final Heap heap) {
        // generate parameters, infer and local vars
        bodyMaker.generateVarStmts(testMethod, heap);
        // FIXME Pack - enable this
        // bodyMaker.generateReturnVarStmt(testMethod, heap);

        // bodyMaker.generateInserts(testMethod, heap);
        // bodyMaker.generateWhenStmts(testMethod, heap);
        // bodyMaker.generateCallStmt(testMethod, heap);
        // bodyMaker.generateAssertStmt(testMethod, heap);
        // bodyMaker.generateArgCaptureStmts(testMethod, heap);
        // bodyMaker.generateVerifyStmts(testMethod, heap);


        // if (!getterSetter.isGetter(clz, testMethod)
        // && !getterSetter.isSetter(clz, testMethod)) {
        // bodyMaker.generateFailAssertStmt(testMethod, heap);
        // }

        methodMakers.addMethod(clzDecl, testMethod);
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
            final TreeNode<ASTNode> ctlTree) {
        return methodMakers.getTestMethodNameSuffix(ctlPath);
    }

    /**
     * Add before each method.
     *
     * @param node
     * @param beforeAnnotation
     */
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

    public void setClzMap(final ClzMap clzMap) {
        this.clzMap = clzMap;
    }

}
