package org.codetab.uknit.core.make;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.clz.ClzMaker;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.model.Cu;
import org.codetab.uknit.core.node.ClzNodeFactory;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

@Singleton
public class Controller {

    private static final Logger LOG = LogManager.getLogger();

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
     * cache of class and super class CU(s).
     */
    private List<Cu> cuCache;

    private Map<AbstractTypeDeclaration, List<Entry<String, String>>> superClassMap;

    public void setup() {
        LOG.info("setup controller");
        LOG.info("create empty CU for test class");

        char[] src = new String("").toCharArray();
        testCompilationUnit = cuFactory.createCompilationUnit(src);

        clzMaker.setCu(testCompilationUnit);
        nodeFactory.setAst(testCompilationUnit.getAST());
        clzNodeFactory.setAst(testCompilationUnit.getAST());

        varNames.setup();
        LOG.info("var names initialized");

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
