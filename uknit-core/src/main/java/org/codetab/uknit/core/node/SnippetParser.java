package org.codetab.uknit.core.node;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Statement;

@Singleton
public class SnippetParser {

    private static final Logger LOG = LogManager.getLogger();

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
            try {
                Block block = (Block) node;
                Statement stmt = (Statement) block.statements().get(0);
                return stmt;
            } catch (Exception e) {
                LOG.error("unable to parse: {}", snippet);
                // for debug breakpoint
                throw e;
            }
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
