package org.codetab.uknit.core.dump;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.*;

import com.google.common.base.Strings;

/**
 * Visits all nodes and dumps to a file.
 *
 * @author Maithilish
 *
 */
public class Dumper extends ASTVisitor {

    private static final Logger LOG = LogManager.getLogger();

    private FileWriter writer;

    private boolean enable = false;

    public void open(final String file) throws IOException {
        if (enable) {
            writer = new FileWriter(file);
        }
    }

    public void close() throws IOException {
        if (enable) {
            writer.close();
        }
    }

    private void dump(final ASTNode node) {
        String name = node.getClass().getSimpleName();
        String text = node.toString();
        int type = node.getNodeType();
        try {
            dump(type, name, text);
        } catch (IOException e) {
            LOG.error("{}", e);
        }
    }

    private void dump(final int type, final String name, final String text)
            throws IOException {

        String header = String.format("%s%n", Strings.repeat("-", 60));

        String fmt;
        switch (type) {
        case ASTNode.METHOD_DECLARATION:
            writer.write(header);
            fmt = "%-30s%n%n%s%n";
            break;
        case ASTNode.BLOCK:
            fmt = "%n%-30s%n%s%n";
            break;
        default:
            fmt = "%-30s - %s%n";
            break;
        }
        writer.write(String.format(fmt, name, text));
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(final boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean visit(final AnnotationTypeDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final AnnotationTypeMemberDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final AnonymousClassDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ArrayAccess node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ArrayCreation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ArrayInitializer node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ArrayType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final AssertStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final Assignment node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final Block node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final BlockComment node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final BooleanLiteral node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final BreakStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final CastExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final CatchClause node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final CharacterLiteral node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ClassInstanceCreation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final CompilationUnit node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ConditionalExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ConstructorInvocation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ContinueStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final CreationReference node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final Dimension node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final DoStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final EmptyStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final EnhancedForStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final EnumConstantDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final EnumDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ExportsDirective node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ExpressionMethodReference node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ExpressionStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final FieldAccess node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final FieldDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ForStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final IfStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ImportDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final InfixExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final Initializer node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final InstanceofExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final IntersectionType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final Javadoc node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final LabeledStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final LambdaExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final LineComment node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final MarkerAnnotation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final MemberRef node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final MemberValuePair node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final MethodRef node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final MethodRefParameter node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final MethodDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final MethodInvocation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final Modifier node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ModuleDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ModuleModifier node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final NameQualifiedType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final NormalAnnotation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final NullLiteral node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final NumberLiteral node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final OpensDirective node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final PackageDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ParameterizedType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ParenthesizedExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final PatternInstanceofExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final PostfixExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final PrefixExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ProvidesDirective node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final PrimitiveType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final QualifiedName node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final QualifiedType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ModuleQualifiedName node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final RequiresDirective node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final RecordDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ReturnStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SimpleName node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SimpleType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SingleMemberAnnotation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SingleVariableDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final StringLiteral node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SuperConstructorInvocation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SuperFieldAccess node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SuperMethodInvocation node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SuperMethodReference node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SwitchCase node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SwitchExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SwitchStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final SynchronizedStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TagElement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TextBlock node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TextElement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ThisExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final ThrowStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TryStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TypeDeclaration node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TypeDeclarationStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TypeLiteral node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TypeMethodReference node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final TypeParameter node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final UnionType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final UsesDirective node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final VariableDeclarationExpression node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final VariableDeclarationStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final VariableDeclarationFragment node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final WhileStatement node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final WildcardType node) {
        dump(node);
        return true;
    }

    @Override
    public boolean visit(final YieldStatement node) {
        dump(node);
        return true;
    }
}
