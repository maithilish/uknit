package org.codetab.uknit.core.make;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.make.clz.ClzMaker;
import org.codetab.uknit.core.node.ClzNodeFactory;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.CompilationUnit;

@Singleton
public class Controller {

    @Inject
    private ClzMaker clzMaker;
    @Inject
    private CuFactory cuFactory;

    @Inject
    private Variables variables;

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private ClzNodeFactory clzNodeFactory;

    private CompilationUnit testCu;
    private CompilationUnit srcCu;

    public void setup() {
        char[] src = new String("").toCharArray(); // blank cu
        testCu = cuFactory.createCompilationUnit(src);

        clzMaker.setCompilationUnit(testCu);
        nodeFactory.setAst(testCu.getAST());
        clzNodeFactory.setAst(testCu.getAST());

        variables.setup();
    }

    public ClzMaker getClzMaker() {
        return clzMaker;
    }

    public CompilationUnit getTestCompilationUnit() {
        return testCu;
    }

    public CompilationUnit getSrcCompilationUnit() {
        return srcCu;
    }

    public void setSrcCompilationUnit(final CompilationUnit srcCu) {
        this.srcCu = srcCu;
    }
}
