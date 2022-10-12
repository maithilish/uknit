package org.codetab.uknit.core.node;

import java.util.Map;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class CuFactory {

    @Inject
    private CompilerOptions compilerOptions;

    public CompilationUnit createCompilationUnit(final char[] src) {
        ASTParser parser = ASTParser.newParser(AST.getJLSLatest());
        parser.setSource(src);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        Map<String, String> options = compilerOptions.getOptions();
        parser.setCompilerOptions(options);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        return cu;
    }

    public CompilationUnit createCompilationUnit(final char[] src,
            final String unitName) {

        ASTParser parser = ASTParser.newParser(AST.getJLSLatest());

        String[] cps = System.getProperty("java.class.path").split(":");
        parser.setEnvironment(cps, null, null, true);
        parser.setUnitName(unitName);

        parser.setResolveBindings(true);
        parser.setBindingsRecovery(false);

        parser.setSource(src);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        Map<String, String> options = compilerOptions.getOptions();
        parser.setCompilerOptions(options);

        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        return cu;
    }

}
