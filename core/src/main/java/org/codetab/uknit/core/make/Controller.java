package org.codetab.uknit.core.make;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.make.clz.ClzMaker;
import org.codetab.uknit.core.node.ClzNodeFactory;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.zap.make.method.VarNames;
import org.codetab.uknit.core.zap.make.model.Cu;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

@Singleton
public class Controller {

    @Inject
    private ClzMaker clzMaker;
    @Inject
    private CuFactory cuFactory;

    @Inject
    private VarNames varNames;

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private ClzNodeFactory clzNodeFactory;

    private CompilationUnit testCompilationUnit;
    private CompilationUnit srcCompilationUnit;

    /*
     * cache of class and super class cus.
     */
    private List<Cu> cuCache;

    private Map<AbstractTypeDeclaration, List<Entry<String, String>>> superClassMap;

    public void setup() {
        char[] src = new String("").toCharArray(); // blank cu
        testCompilationUnit = cuFactory.createCompilationUnit(src);

        clzMaker.setCu(testCompilationUnit);
        nodeFactory.setAst(testCompilationUnit.getAST());
        clzNodeFactory.setAst(testCompilationUnit.getAST());

        varNames.setup();
        cuCache = new ArrayList<>();
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

    public List<Cu> getCuCache() {
        return cuCache;
    }

    public void setSuperClassMap(
            final Map<AbstractTypeDeclaration, List<Entry<String, String>>> map) {
        this.superClassMap = map;
    }

    public Map<AbstractTypeDeclaration, List<Entry<String, String>>> getSuperClassMap() {
        return superClassMap;
    }

}
