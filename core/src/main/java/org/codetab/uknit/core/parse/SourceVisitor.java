package org.codetab.uknit.core.parse;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.dump.Dumper;
import org.codetab.uknit.core.dump.EndDumper;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.make.clz.ClzMaker;
import org.codetab.uknit.core.make.method.MethodMaker;
import org.codetab.uknit.core.make.method.detect.GetterSetter;
import org.codetab.uknit.core.make.method.visit.ControlFlowVisitor;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.tree.Trees;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class SourceVisitor extends ASTVisitor {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Configs configs;
    @Inject
    private DInjector di;
    @Inject
    private Controller ctl;
    @Inject
    private Dumper methodDumper;
    @Inject
    private EndDumper methodEndDumper;
    @Inject
    private GetterSetter getterSetter;
    @Inject
    private Trees trees;

    private ClzMaker clzMaker;

    public void preProcess() {
        CompilationUnit srcCu = ctl.getSrcCompilationUnit();
        IProblem[] problems = srcCu.getProblems();
        if (problems.length > 0) {
            for (IProblem problem : problems) {
                LOG.error("{}", problem);
            }
            throw new CriticalException(
                    "source has errors, unable to generate test case.");
        }

        clzMaker = ctl.getClzMaker();

        methodDumper.setEnable(configs.getConfig("uknit.dump.method", false));
        methodEndDumper.setEnable(methodDumper.isEnable());
        try {
            methodDumper.open("logs/visit.log");
            methodEndDumper.open("logs/endvisit.log");
        } catch (IOException e) {
            throw new CriticalException(e);
        }

        getterSetter.reset();
    }

    /**
     * Post process after visit of all nodes in the source.
     */
    public void postProcess() {
        clzMaker.annotateFields(configs);
        clzMaker.removeFields(configs);

        if (configs.getConfig("uknit.detect.getterSetter", true)) {
            getterSetter.postProcess();
        }

        try {
            methodDumper.close();
            methodEndDumper.close();
        } catch (IOException e) {
            throw new CriticalException(e);
        }

        CompilationUnit testCu = ctl.getTestCompilationUnit();
        IProblem[] problems = testCu.getProblems();
        if (problems.length > 0) {
            for (IProblem problem : problems) {
                System.out.println(problem);
            }
            throw new CriticalException(
                    "generated test class has errors, unable to write.");
        }
    }

    public void closeDumpers() {
        try {
            methodDumper.close();
            methodEndDumper.close();
        } catch (IOException e) {
            throw new CriticalException(e);
        }
    }

    @Override
    public boolean visit(final PackageDeclaration node) {
        clzMaker.addPackage(node);
        return true;
    }

    @Override
    public boolean visit(final ImportDeclaration node) {
        clzMaker.addImport(node);
        return true;
    }

    @Override
    public boolean visit(final TypeDeclaration node) {
        clzMaker.addClass(node);
        clzMaker.addSelfField(node);
        clzMaker.addFields(node);

        List<Entry<String, String>> superClassNames =
                ctl.getSuperClassMap().get(node);
        if (nonNull(superClassNames)) {
            List<AbstractTypeDeclaration> superTypes =
                    clzMaker.getSuperTypeDeclarations(superClassNames,
                            ctl.getCuCache());
            clzMaker.addSuperFields(node, superTypes);
        }

        MethodMaker methodMaker = di.instance(MethodMaker.class);
        methodMaker.setClzMap(ctl.getClzMaker().getClzMap());

        String beforeAnnotation =
                configs.getConfig("uknit.test.annotation.before", "BeforeEach");
        methodMaker.addBeforeMethod(node, beforeAnnotation);
        return true;
    }

    @Override
    public boolean visit(final MethodDeclaration node) {
        if (methodDumper.isEnable()) {
            node.accept(methodDumper);
        }
        if (methodEndDumper.isEnable()) {
            node.accept(methodEndDumper);
        }
        MethodMaker methodMaker = di.instance(MethodMaker.class);
        methodMaker.setClzMap(ctl.getClzMaker().getClzMap());

        if (methodMaker.isStageable(node)) {

            boolean splitMethodsOnControlFlow =
                    configs.getConfig("uknit.controlFlow.method.split", true);

            if (splitMethodsOnControlFlow) {

                // separate method for each control flow path
                ControlFlowVisitor ctlFlowVisitor =
                        di.instance(ControlFlowVisitor.class);
                ctlFlowVisitor.setup();
                node.accept(ctlFlowVisitor);

                List<TreeNode<ASTNode>> leaves =
                        trees.findLeaves(ctlFlowVisitor.getTree());

                // for each control path create a test method
                for (int i = 0; i < leaves.size(); i++) {

                    // get ctlPath for a leaf.
                    TreeNode<ASTNode> leaf = leaves.get(i);
                    List<TreeNode<ASTNode>> ctlPath =
                            trees.getPathFromRoot(leaf);

                    // suffix such as IfDone, ElseFlag etc.,
                    String suffix = methodMaker.getTestMethodNameSuffix(ctlPath,
                            ctlFlowVisitor.getTree());

                    Heap heap = di.instance(Heap.class);
                    heap.setSelfFieldName(clzMaker.getSelfFieldName()); // SUT

                    // finally stage and generate the test method.
                    if (methodMaker.stageMethod(node, ctlPath, suffix, heap)) {
                        methodMaker.generateTestMethod(heap);
                    }

                }
            } else {
                // ignore control flow path, single test method
                @SuppressWarnings("unchecked")
                List<TreeNode<ASTNode>> ctlPath = di.instance(ArrayList.class);
                String suffix = "";
                Heap heap = di.instance(Heap.class);
                heap.setSelfFieldName(clzMaker.getSelfFieldName()); // SUT
                if (methodMaker.stageMethod(node, ctlPath, suffix, heap)) {
                    methodMaker.generateTestMethod(heap);
                }
            }
        }

        return true;
    }

    // private void logCtlFlowPath(final TreeNode<ASTNode> leaf) {
    // LOG.debug("Control Flow Path");
    // List<TreeNode<ASTNode>> path = trees.getPathFromRoot(leaf);
    // path.stream().forEach(t -> {
    // ASTNode obj = t.getObject();
    // LOG.debug("{} {}", obj.getClass().getSimpleName(),
    // String.format("|%4d|", obj.hashCode()));
    // });
    //
    // if (LOG.isTraceEnabled()) {
    // path.stream().forEach(t -> {
    // ASTNode obj = t.getObject();
    // LOG.trace("{} {} {}", obj.getClass().getSimpleName(),
    // String.format("|%4d|%n", obj.hashCode()),
    // obj.toString());
    // });
    // }
    // }
}
