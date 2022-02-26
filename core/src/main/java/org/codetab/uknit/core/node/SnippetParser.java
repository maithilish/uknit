package org.codetab.uknit.core.node;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Statement;

@Singleton
public class SnippetParser {

    private ASTParser parser;

    @Inject
    private CompilerOptions compilerOptions;

    public SnippetParser() {
        parser = ASTParser.newParser(AST.getJLSLatest());
    }

    public Statement parseStatement(final String snippet) {
        parser.setKind(ASTParser.K_STATEMENTS);
        parser.setSource(snippet.toCharArray());

        parser.setCompilerOptions(compilerOptions.getOptions());

        ASTNode node = parser.createAST(null);
        if (node.getNodeType() == ASTNode.BLOCK) {
            Block block = (Block) node;
            return (Statement) block.statements().get(0);
        }
        return (Statement) node;
    }

    public Expression parseExpression(final String snippet) {
        parser.setKind(ASTParser.K_EXPRESSION);
        parser.setSource(snippet.toCharArray());

        parser.setCompilerOptions(compilerOptions.getOptions());

        return (Expression) parser.createAST(null);
    }
}
