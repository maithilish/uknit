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
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.output.Console;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.tree.Trees;
import org.codetab.uknit.core.zap.make.method.detect.getter.GetterSetter;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Visitor to visit pkg, import, and clz of source class. The method block is
 * visited by make.method.visit.Visitor.
 *
 * @author Maithilish
 *
 */
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
    @Inject
    private Console console;

    private ClzMaker clzMaker;

    private boolean testable;

    /**
     * Preprocess, check errors in source. Dump method visit if dump is enabled.
     * Dumps visit.log and endvisit.log are useful to understand the visit
     * sequence.
     */
    public void preProcess() {
        testable = true;
        CompilationUnit srcCu = ctl.getSrcCompilationUnit();
        IProblem[] problems = srcCu.getProblems();
        if (problems.length > 0) {
            for (IProblem problem : problems) {
                LOG.error("{}", problem);
            }
            throw new CriticalException(
                    "source has errors, unable to generate test case.");
        } else {
            LOG.info("no errors found in source cu");
        }

        clzMaker = ctl.getClzMaker();

        methodDumper.setEnable(configs.getConfig("uknit.dump.method", false));
        methodEndDumper.setEnable(methodDumper.isEnable());
        try {
            LOG.info("open method dumpers");
            methodDumper.open("logs/visit.log");
            methodEndDumper.open("logs/endvisit.log");
        } catch (IOException e) {
            throw new CriticalException(e);
        }

        getterSetter.reset();
    }

    /**
     * Post process after visit of all nodes in the source. Check for errors in
     * generated test class.
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
                console.print(problem.toString());
            }
            throw new CriticalException(
                    "generated test class has errors, unable to write.");
        } else {
            LOG.info("test class generated, no errors found");
        }
    }

    public void closeDumpers() {
        try {
            LOG.info("close method dumpers");
            methodDumper.close();
            methodEndDumper.close();
        } catch (IOException e) {
            throw new CriticalException(e);
        }
    }

    /**
     * Visit pkgDecl and add pkg to test class.
     */
    @Override
    public boolean visit(final PackageDeclaration node) {
        LOG.debug("add package declaration to test class");
        return true;
    }

    /**
     * Visit importDecl and add imports to test class.
     */
    @Override
    public boolean visit(final ImportDeclaration node) {
        clzMaker.addImport(node);
        return true;
    }

    /**
     * Visit classDecl and add test class.
     */
    @Override
    public boolean visit(final TypeDeclaration node) {
        LOG.debug("add class declaration to test class");
        if (node.isInterface()) {
            LOG.warn("{} is an interface, no test generated", node.getName());
            testable = false;
            return true;
        } else {
            testable = true;
        }
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

    /**
     * Visit methodDecl and find ctl flow paths. For each path stage test
     * method.
     */
    @Override
    public boolean visit(final MethodDeclaration node) {
        if (methodDumper.isEnable()) {
            node.accept(methodDumper);
        }
        if (methodEndDumper.isEnable()) {
            node.accept(methodEndDumper);
        }
        /*
         * don't process method if interface
         */
        if (!testable) {
            return true;
        }
        MethodMaker methodMaker = di.instance(MethodMaker.class);
        methodMaker.setClzMap(ctl.getClzMaker().getClzMap());

        if (methodMaker.isStageable(node)) {

            boolean splitMethodsOnControlFlow =
                    configs.getConfig("uknit.controlFlow.method.split", true);

            if (splitMethodsOnControlFlow) {

                // separate method for each control flow path
                PathFinder ctlFlowVisitor = di.instance(PathFinder.class);
                ctlFlowVisitor.setup();
                node.accept(ctlFlowVisitor);

                // ctlFlowVisitor.enableNodes(ctlFlowVisitor.getTree(), true);
                ctlFlowVisitor.enableUncoveredNodes(ctlFlowVisitor.getTree());

                LOG.debug("Flow Tree [+ enabled path]{}", trees.prettyPrintTree(
                        ctlFlowVisitor.getTree(), "", "", null));

                List<TreeNode<ASTNode>> leaves =
                        trees.findEnabledLeaves(ctlFlowVisitor.getTree());

                LOG.info("method {}, ctl flow paths {}", node.getName(),
                        leaves.size());

                // for each control path create a test method
                for (int i = 0; i < leaves.size(); i++) {

                    // get ctlPath for a leaf.
                    TreeNode<ASTNode> leaf = leaves.get(i);
                    List<TreeNode<ASTNode>> ctlPath =
                            trees.getPathFromRoot(leaf);

                    LOG.debug("==== generate test method for flow path ===={}",
                            trees.prettyPrintPath(ctlFlowVisitor.getTree(),
                                    ctlPath, "", "", null));

                    // suffix such as IfDone, ElseFlag etc.,
                    String suffix = methodMaker.getTestMethodNameSuffix(ctlPath,
                            ctlFlowVisitor.getTree());

                    Heap heap = di.instance(Heap.class);
                    // class under test - CUT
                    heap.setCutName(clzMaker.getCutName());

                    // finally stage and generate the test method.
                    if (methodMaker.stageMethod(node, ctlPath, suffix, heap)) {
                        methodMaker.generateTestMethod(heap);

                    }
                }
            } else {
                LOG.debug(
                        "ctl flow path disabled, generate single test method");
                @SuppressWarnings("unchecked")
                List<TreeNode<ASTNode>> ctlPath = di.instance(ArrayList.class);
                String suffix = "";
                Heap heap = di.instance(Heap.class);
                // set class under test name
                heap.setCutName(clzMaker.getCutName());
                if (methodMaker.stageMethod(node, ctlPath, suffix, heap)) {
                    methodMaker.generateTestMethod(heap);
                }
            }
        }

        return true;
    }
}
