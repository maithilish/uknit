package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

@Singleton
public class NodeFactory {

    private AST ast;

    @Inject
    private SnippetParser snippetParser;

    public void setAst(final AST ast) {
        this.ast = ast;
    }

    public Modifier createModifier(final ModifierKeyword modifierKeyword) {
        return ast.newModifier(modifierKeyword);
    }

    public List<Modifier> createModifiers(final int flags) {
        @SuppressWarnings("unchecked")
        List<Modifier> modifiers = ast.newModifiers(flags);
        return modifiers;
    }

    public MarkerAnnotation createMarkerAnnotation(final String type) {
        MarkerAnnotation annotation = ast.newMarkerAnnotation();
        annotation.setTypeName(ast.newName(type));
        return annotation;
    }

    @SuppressWarnings("unchecked")
    public NormalAnnotation createNormalAnnotation(final String type,
            final Map<SimpleName, Name> map) {
        NormalAnnotation annotation = ast.newNormalAnnotation();
        annotation.setTypeName(ast.newName(type));
        for (SimpleName key : map.keySet()) {
            MemberValuePair mvp = ast.newMemberValuePair();
            mvp.setName(key);
            mvp.setValue(map.get(key));
            annotation.values().add(mvp);
        }
        return annotation;
    }

    public Name createName(final String name) {
        if (name.contains(".")) {
            String[] parts = name.split("\\.");
            Name pName = null;
            for (int c = 0; c < parts.length; c++) {
                SimpleName sName = ast.newSimpleName(parts[c]);
                if (isNull(pName)) {
                    pName = sName;
                } else {
                    pName = ast.newQualifiedName(pName, sName);
                }
            }
            return pName;
        } else {
            return ast.newSimpleName(name);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends ASTNode> T copyNode(final T node) {
        return (T) ASTNode.copySubtree(ast, node);
    }

    public MethodDeclaration createMethodDecl(final String name) {
        MethodDeclaration md = ast.newMethodDeclaration();
        md.setName(ast.newSimpleName(name));
        Block block = ast.newBlock();
        md.setBody(block);
        return md;
    }

    public Statement createMethodStatement(final String statement) {
        ExpressionStatement stmt =
                (ExpressionStatement) snippetParser.parseStatement(statement);
        MethodInvocation mi = (MethodInvocation) stmt.getExpression();
        MethodInvocation miCopy =
                (MethodInvocation) ASTNode.copySubtree(ast, mi);
        return ast.newExpressionStatement(miCopy);
    }

    public Type createSimpleType(final String name) {
        Code primitiveTypeCode = PrimitiveType.toCode(name);
        if (nonNull(primitiveTypeCode)) {
            return ast.newPrimitiveType(primitiveTypeCode);
        } else {
            return ast.newSimpleType(ast.newName(name));
        }
    }

    public Block createMethodBlock(final List<String> statements) {
        StringBuilder src = new StringBuilder();
        statements.forEach(s -> {
            src.append(s);
            src.append(System.lineSeparator());
        });
        Block block = (Block) snippetParser.parseStatement(src.toString());
        return (Block) ASTNode.copySubtree(ast, block);
    }

    public Block createMethodBlock() {
        return ast.newBlock();
    }

    public Statement createVarDeclStmt(final String name, final String type,
            final String initializer) {
        checkNotNull(name);
        checkNotNull(type);
        checkNotNull(initializer);

        String snippet =
                String.join("", type, " ", name, "=", initializer, ";");
        VariableDeclarationStatement stmt =
                (VariableDeclarationStatement) snippetParser
                        .parseStatement(snippet);
        return (Statement) ASTNode.copySubtree(ast, stmt);
    }

    public Statement createWhenStatement(final String methodSignature,
            final List<IVar> returnVars, final String whenFormat,
            final String returnFormat) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(whenFormat, methodSignature));
        for (IVar returnVar : returnVars) {
            sb.append(String.format(returnFormat, returnVar.getName()));
        }
        sb.append(";");
        Statement whenStmt = snippetParser.parseStatement(sb.toString());
        return (Statement) ASTNode.copySubtree(ast, whenStmt);
    }

    /**
     * Create chained method call verify statement
     * <p>
     * verify(foo.bar()).baz();
     * @param mi
     * @param format
     * @return
     */
    public Statement createVerifyStatement(final MethodInvocation mi,
            final String format) {
        String varName = mi.getExpression().toString();
        String methodName = mi.getName().getFullyQualifiedName();
        @SuppressWarnings("unchecked")
        String args = (String) mi.arguments().stream().map(n -> n.toString())
                .collect(Collectors.joining(","));

        String verify = String.format(format, varName, methodName, args);
        Statement verifyStmt = snippetParser.parseStatement(verify);

        return (Statement) ASTNode.copySubtree(ast, verifyStmt);
    }

    public Statement createCaptureStatement(final String varName,
            final String parameterizedType, final String type,
            final String format) {
        String capture =
                String.format(format, parameterizedType, varName, type);
        Statement captureStmt = snippetParser.parseStatement(capture);
        return (Statement) ASTNode.copySubtree(ast, captureStmt);
    }

    /**
     * Create Test Method call which returns void.
     * @param invokeOn
     * @param methodName
     * @param args
     * @param format
     * @return
     */
    public Statement createCallStmt(final String invokeOn,
            final String methodName, final String args, final String format) {
        String stmtStr = String.format(format, invokeOn, methodName, args);
        Statement stmt = snippetParser.parseStatement(stmtStr);
        return (Statement) ASTNode.copySubtree(ast, stmt);
    }

    /**
     * Create Test Method call with actual return.
     * @param invokeOn
     * @param methodName
     * @param args
     * @param format
     * @return
     */
    public Statement createCallStmt(final String invokeOn,
            final String methodName, final String args, final String returnType,
            final String format) {
        String stmtStr =
                String.format(format, returnType, invokeOn, methodName, args);

        Statement stmt = snippetParser.parseStatement(stmtStr);
        return (Statement) ASTNode.copySubtree(ast, stmt);
    }

    /**
     * Create Test Method Constructor call .
     * @param type
     * @param args
     * @param format
     * @return
     */
    public Statement createCallStmt(final String type, final String args,
            final String format) {
        String stmtStr = String.format(format, type, type, args);
        Statement stmt = snippetParser.parseStatement(stmtStr);
        return (Statement) ASTNode.copySubtree(ast, stmt);
    }

    public Statement createAssertStatement(final String assertStmt) {
        Statement stmt = snippetParser.parseStatement(assertStmt);
        return (Statement) ASTNode.copySubtree(ast, stmt);
    }
}
