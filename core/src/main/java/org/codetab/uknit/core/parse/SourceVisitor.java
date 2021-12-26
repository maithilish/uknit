package org.codetab.uknit.core.parse;

import java.io.IOException;

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
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
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
    }

    public void postProcess() {
        clzMaker.annotateFields(configs);

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
        clzMaker.addSuperClassFields(node);
        MethodMaker methodMaker = di.instance(MethodMaker.class);
        methodMaker.setClzMap(ctl.getClzMaker().getClzMap());
        methodMaker.addBeforeMethod(node);
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
            Heap heap = di.instance(Heap.class);
            if (methodMaker.stageMethod(node, heap)) {
                methodMaker.generateTestMethod(heap);
            }
        }

        return true;
    }
}
