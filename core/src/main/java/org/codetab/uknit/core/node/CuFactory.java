package org.codetab.uknit.core.node;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class CuFactory {

    @Inject
    private CompilerOptions compilerOptions;

    public CompilationUnit createCompilationUnit(final char[] src) {
        ASTParser parser = ASTParser.newParser(AST.JLS16);
        parser.setSource(src);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setCompilerOptions(compilerOptions.getOptions());

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        return cu;
    }

    public CompilationUnit createCompilationUnit(final char[] src,
            final String unitName) {

        ASTParser parser = ASTParser.newParser(AST.JLS16);

        String[] cps = System.getProperty("java.class.path").split(":");
        parser.setEnvironment(cps, null, null, true);
        parser.setUnitName(unitName);

        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);

        parser.setSource(src);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        parser.setCompilerOptions(compilerOptions.getOptions());

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        return cu;
    }
}
