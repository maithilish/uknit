package org.codetab.uknit.core.make;

import java.util.HashMap;
import java.util.Map;

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

    private CompilationUnit testCompilationUnit;
    private CompilationUnit srcCompilationUnit;

    /*
     * cache of super class cu. Key is QName - packgeName + clzName
     * org.codetab.uknit.itest.extend.Base
     */
    private Map<String, CompilationUnit> cuCache;

    public void setup() {
        char[] src = new String("").toCharArray(); // blank cu
        testCompilationUnit = cuFactory.createCompilationUnit(src);

        clzMaker.setCu(testCompilationUnit);
        nodeFactory.setAst(testCompilationUnit.getAST());
        clzNodeFactory.setAst(testCompilationUnit.getAST());

        variables.setup();
        cuCache = new HashMap<>();
    }

    public ClzMaker getClzMaker() {
        return clzMaker;
    }

    public CompilationUnit getTestCompilationUnit() {
        return testCompilationUnit;
    }

    public CompilationUnit getSrcCompilationUnit() {
        return srcCompilationUnit;
    }

    public void setSrcCompilationUnit(final CompilationUnit srcCu) {
        this.srcCompilationUnit = srcCu;
    }

    public Map<String, CompilationUnit> getCuCache() {
        return cuCache;
    }
}
