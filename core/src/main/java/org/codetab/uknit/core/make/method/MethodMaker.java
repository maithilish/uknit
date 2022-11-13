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
import org.codetab.uknit.core.make.method.imc.Merger;
import org.codetab.uknit.core.make.method.process.CallCreator;
import org.codetab.uknit.core.make.method.process.Processor;
import org.codetab.uknit.core.make.method.visit.Visitor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
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
    private CallCreator callCreator;
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
    @Inject
    private Merger merger;

    private ClzMap clzMap;

    private MethodDeclaration testMethod;

    private TypeDeclaration clzDecl;

    @SuppressWarnings("unused")
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
        callCreator.createCall(method, heap);

        // to set deep stub, create field packs
        heap.addPacks(methodMakers
                .createFieldPacks(clzMap.getFieldsCopy(testClzName)));
        method.accept(visitor);

        processor.process(heap);
        processor.processInvokes(heap);
        processor.processWhenVerify(heap);
        processor.processVarState(heap);

        heap.tracePacks("Packs after post visit processing");

        /*
         * clzMap.updateFieldState(testClzName, heap.getVars(IVar::isField));
         *
         * // create inserts for list.add() etc., List<IVar> insertableVars =
         * inserter.filterInsertableVars(heap.getVars());
         * inserter.processInsertableVars(insertableVars, heap);
         * inserter.enableInserts(heap);
         */

        methodMakers.addThrowsException(testMethod, heap);

        /*
         * // FIXME Pack - enable this after multi try exception fix //
         * variables.checkVarConsistency(heap.getVars());
         *
         */
        // FIXME Pack - sanity checks return is mapped to var

        return true;
    }

    /**
     * Process IM without staging it. Use separate Visitor and newly initialized
     * Heap to collect the IMC items. On completion, heap contents are merged
     * with the main heap.
     * <p>
     * The InternalCallProcessor.process() explains the use of paramArgMap.
     * @param method
     * @param paramArgMap
     * @param invoke
     * @param internalMethod
     * @param heap
     * @param internalHeap2
     * @return
     */
    public boolean processMethod(final MethodDeclaration method,
            final Invoke invoke, final boolean internalMethod, final Heap heap,
            final Heap internalHeap) {

        checkNotNull(method);
        checkNotNull(invoke);
        checkNotNull(internalMethod);
        checkNotNull(heap);
        checkNotNull(internalHeap);

        LOG.debug("= process method: {} =", methods.getMethodName(method));

        Visitor visitor = di.instance(Visitor.class);
        visitor.setHeap(internalHeap);
        visitor.setImc(internalMethod);
        visitor.setMethodReturnType(method.getReturnType2());

        // stage call for this method
        callCreator.createCall(method, internalHeap);

        method.accept(visitor);

        /*
         * process infer, returnInfer, IMC etc., but when, verify and var state
         * are not processed here and these are later processed by caller.
         */
        processor.process(internalHeap);

        // merge packs of internal heap to the caller heap.
        merger.merge(invoke, heap, internalHeap);

        // after merge, resolve any var name conflict
        processor.processVarNameChange(internalHeap);

        heap.tracePacks("Internal Packs after post visit process");

        // List<IVar> insertableVars =
        // inserter.filterInsertableVars(internalHeap.getVars());
        // inserter.processInsertableVars(insertableVars, internalHeap);

        // heap.merge(internalHeap);

        // variables.checkVarConsistency(heap.getVars());

        return true;
    }

    public void generateTestMethod(final Heap heap) {
        // generate parameters, infer and local vars
        bodyMaker.generateVarStmts(testMethod, heap);

        // bodyMaker.generateReturnVarStmt(testMethod, heap);
        // FIXME Pack - enable this
        // bodyMaker.generateInserts(testMethod, heap);
        bodyMaker.generateWhenStmts(testMethod, heap);
        bodyMaker.generateCallStmt(testMethod, heap);
        bodyMaker.generateAssertStmt(testMethod, heap);
        // bodyMaker.generateArgCaptureStmts(testMethod, heap);
        bodyMaker.generateVerifyStmts(testMethod, heap);

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
