package org.codetab.uknit.core.make.method;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.ClzMap;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.make.method.body.BodyMaker;
import org.codetab.uknit.core.make.method.getter.GetterSetter;
import org.codetab.uknit.core.make.method.imc.Cyclic;
import org.codetab.uknit.core.make.method.process.PostProcessor;
import org.codetab.uknit.core.make.method.visit.Visitor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.util.StringUtils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
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
    private Controller ctl;
    @Inject
    private MethodMakers methodMakers;
    @Inject
    private PostProcessor postProcessor;
    @Inject
    private CallCreator callCreator;
    @Inject
    private Methods methods;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private BodyMaker bodyMaker;
    @Inject
    private VarNames varNames;
    @Inject
    private Heaps heaps;
    @Inject
    private Vars vars;
    @Inject
    private Packs packs;
    @Inject
    private GetterSetter getterSetter;
    @Inject
    private MethodContext methodContext;
    @Inject
    private Cyclic cyclic;

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
                .getTestClzName((AbstractTypeDeclaration) method.getParent());
        clzDecl = clzMap.getTypeDecl(testClzName);
        clz = clzMap.getClz(testClzName);

        String testMethodName =
                methodMakers.getTestMethodName(method, clzDecl, testNameSuffix);

        /*
         * If user config uknit.output.filter is defined then process only if
         * the test method name matches the filter, other methods are not
         * processed. Use this to output only one of the ctl path.
         */
        String testFilter = configs.getConfig("uknit.output.filter");
        if (nonNull(testFilter) && !testFilter.equals(testMethodName)) {
            LOG.debug(
                    "config uknit.output.filter is enabled, test not generated for {}",
                    testMethodName);
            return false;
        }

        heap.setMut(method);
        heap.setTestClzName(testClzName);
        heap.setTestMethodName(testMethodName);
        heap.setIm(false);

        // initialize method call tree and save it in method context
        methodContext.init();
        TreeNode<MethodDeclaration> rootCallNode =
                cyclic.createCallHierarchy(method);
        methodContext.setCallHierarchy(rootCallNode);

        String mutSignature = methods.getMethodSignature(method);
        ctl.setMUTSignature(mutSignature);
        LOG.debug("CUT: {}", StringUtils.capitalize(heap.getCutName()));
        LOG.debug("Method: {}", mutSignature);
        LOG.debug("Test Method: {}", testMethodName);

        testMethod = methodMakers.constructTestMethod(method, testMethodName);
        heap.setTestThrowsException(
                methodMakers.isMethodUnderTestThrowsException(method));

        if (configs.getConfig("uknit.detect.getterSetter", true)) {
            getterSetter.detect(clz, method, testMethod,
                    clzMap.getDefinedFieldsCopy(testClzName));
        }

        Visitor visitor = di.instance(Visitor.class);
        visitor.setHeap(heap);
        visitor.setCtlPath(ctlPath);
        visitor.setReturned(false);
        visitor.setMethodReturnType(method.getReturnType2());

        /*
         * when uknit.controlFlow.method.split is false, uKnit outputs test for
         * first branch. Even if config is false, treat is as true so that
         * verifies in other branches are marked with never().
         */
        visitor.setSplitOnControlFlow(true);

        heap.setup();
        packs.resetIdGenerator();

        // add method under test call
        callCreator.createCall(method, heap);

        // to set deep stub, create field packs
        heap.addPacks(methodMakers.createFieldPacks(
                clzMap.getDefinedFieldsCopy(testClzName), heap));
        method.accept(visitor);

        postProcessor.process(heap);

        clzMap.updateFieldState(testClzName,
                vars.getVarsOfKind(heap, Kind.FIELD));

        heaps.debugPacks("Heap after process", heap);

        heaps.debugPatches("Patch Map", heap);

        cyclic.debugCallHierarchy(methodContext.getCallHierarchy());

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
     * @param im
     * @param heap
     * @param internalHeap2
     * @return
     */
    public boolean processMethod(final MethodDeclaration method,
            final Invoke invoke, final boolean im, final Heap heap,
            final Heap internalHeap) {

        checkNotNull(method);
        checkNotNull(invoke);
        checkNotNull(im);
        checkNotNull(heap);
        checkNotNull(internalHeap);

        String methodName = methods.getMethodName(method);
        LOG.debug("= process internal method: {} =", methodName);

        // if IM is cyclic then return without processing it
        if (cyclic.isCyclic(heap.getMut(), method,
                methodContext.getCallHierarchy())) {
            return true;
        }

        Visitor visitor = di.instance(Visitor.class);
        visitor.setHeap(internalHeap);
        visitor.setMethodReturnType(method.getReturnType2());

        internalHeap.setup();
        internalHeap.setIm(im);

        // IM is not cyclic, add it to call hierarchy and continue to process
        cyclic.addCallHierarchyNode(internalHeap.getMut(), heap.getMut(),
                methodContext.getCallHierarchy());

        // stage call for this method
        callCreator.createCall(method, internalHeap);

        method.accept(visitor);

        postProcessor.processInternalMethod(invoke, internalHeap, heap);

        heaps.debugPacks("Heap after IM process", heap);

        heaps.debugPatches("Patch Map", heap);

        return true;
    }

    public void generateTestMethod(final Heap heap) {
        // generate parameters, infer and local vars
        bodyMaker.generateVarStmts(testMethod, heap);

        bodyMaker.generateLoads(testMethod, heap);
        bodyMaker.generateWhenStmts(testMethod, heap);
        bodyMaker.generateCallStmt(testMethod, heap);
        bodyMaker.generateAssertStmt(testMethod, heap);
        bodyMaker.generateArgCaptureStmts(testMethod, heap);
        bodyMaker.generateVerifyStmts(testMethod, heap);

        if (!getterSetter.isGetter(clz, testMethod)
                && !getterSetter.isSetter(clz, testMethod)) {
            bodyMaker.generateFailAssertStmt(testMethod, heap);
        }

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
    public void addBeforeMethod(final AbstractTypeDeclaration node,
            final String beforeAnnotation) {
        AbstractTypeDeclaration clzUnderTest = node;
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
