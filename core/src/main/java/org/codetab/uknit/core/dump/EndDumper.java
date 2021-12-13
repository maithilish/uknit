package org.codetab.uknit.core.dump;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.*;

import com.google.common.base.Strings;

/**
 * Visits (end visit methods) all nodes and dumps to a file.
 *
 * @author Maithilish
 *
 */
public class EndDumper extends ASTVisitor {

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
        final int dashLength = 60;
        String header = String.format("%s%n", Strings.repeat("-", dashLength));

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
    public void endVisit(final AnnotationTypeDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final AnnotationTypeMemberDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final AnonymousClassDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final ArrayAccess node) {

        dump(node);
    }

    @Override
    public void endVisit(final ArrayCreation node) {

        dump(node);
    }

    @Override
    public void endVisit(final ArrayInitializer node) {

        dump(node);
    }

    @Override
    public void endVisit(final ArrayType node) {

        dump(node);
    }

    @Override
    public void endVisit(final AssertStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final Assignment node) {

        dump(node);
    }

    @Override
    public void endVisit(final Block node) {

        dump(node);
    }

    @Override
    public void endVisit(final BlockComment node) {

        dump(node);
    }

    @Override
    public void endVisit(final BooleanLiteral node) {

        dump(node);
    }

    @Override
    public void endVisit(final BreakStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final CastExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final CatchClause node) {

        dump(node);
    }

    @Override
    public void endVisit(final CharacterLiteral node) {

        dump(node);
    }

    @Override
    public void endVisit(final ClassInstanceCreation node) {

        dump(node);
    }

    @Override
    public void endVisit(final CompilationUnit node) {

        dump(node);
    }

    @Override
    public void endVisit(final ConditionalExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final ConstructorInvocation node) {

        dump(node);
    }

    @Override
    public void endVisit(final ContinueStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final CreationReference node) {

        dump(node);
    }

    @Override
    public void endVisit(final DoStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final EmptyStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final EnhancedForStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final EnumConstantDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final EnumDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final ExportsDirective node) {

        dump(node);
    }

    @Override
    public void endVisit(final ExpressionMethodReference node) {

        dump(node);
    }

    @Override
    public void endVisit(final ExpressionStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final Dimension node) {

        dump(node);
    }

    @Override
    public void endVisit(final FieldAccess node) {

        dump(node);
    }

    @Override
    public void endVisit(final FieldDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final ForStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final IfStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final ImportDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final InfixExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final InstanceofExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final Initializer node) {

        dump(node);
    }

    @Override
    public void endVisit(final Javadoc node) {

        dump(node);
    }

    @Override
    public void endVisit(final LabeledStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final LambdaExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final LineComment node) {

        dump(node);
    }

    @Override
    public void endVisit(final MarkerAnnotation node) {

        dump(node);
    }

    @Override
    public void endVisit(final MemberRef node) {

        dump(node);
    }

    @Override
    public void endVisit(final MemberValuePair node) {

        dump(node);
    }

    @Override
    public void endVisit(final MethodRef node) {

        dump(node);
    }

    @Override
    public void endVisit(final MethodRefParameter node) {

        dump(node);
    }

    @Override
    public void endVisit(final MethodDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final MethodInvocation node) {

        dump(node);
    }

    @Override
    public void endVisit(final Modifier node) {

        dump(node);
    }

    @Override
    public void endVisit(final ModuleDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final ModuleModifier node) {

        dump(node);
    }

    @Override
    public void endVisit(final NameQualifiedType node) {

        dump(node);
    }

    @Override
    public void endVisit(final NormalAnnotation node) {

        dump(node);
    }

    @Override
    public void endVisit(final NullLiteral node) {

        dump(node);
    }

    @Override
    public void endVisit(final NumberLiteral node) {

        dump(node);
    }

    @Override
    public void endVisit(final OpensDirective node) {

        dump(node);
    }

    @Override
    public void endVisit(final PackageDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final ParameterizedType node) {

        dump(node);
    }

    @Override
    public void endVisit(final ParenthesizedExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final PatternInstanceofExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final PostfixExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final PrefixExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final PrimitiveType node) {

        dump(node);
    }

    @Override
    public void endVisit(final ProvidesDirective node) {

        dump(node);
    }

    @Override
    public void endVisit(final QualifiedName node) {

        dump(node);
    }

    @Override
    public void endVisit(final QualifiedType node) {

        dump(node);
    }

    @Override
    public void endVisit(final ModuleQualifiedName node) {

        dump(node);
    }

    @Override
    public void endVisit(final RequiresDirective node) {

        dump(node);
    }

    @Override
    public void endVisit(final RecordDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final ReturnStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final SimpleName node) {

        dump(node);
    }

    @Override
    public void endVisit(final SimpleType node) {

        dump(node);
    }

    @Override
    public void endVisit(final SingleMemberAnnotation node) {

        dump(node);
    }

    @Override
    public void endVisit(final SingleVariableDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final StringLiteral node) {

        dump(node);
    }

    @Override
    public void endVisit(final SuperConstructorInvocation node) {

        dump(node);
    }

    @Override
    public void endVisit(final SuperFieldAccess node) {

        dump(node);
    }

    @Override
    public void endVisit(final SuperMethodInvocation node) {

        dump(node);
    }

    @Override
    public void endVisit(final SuperMethodReference node) {

        dump(node);
    }

    @Override
    public void endVisit(final SwitchCase node) {

        dump(node);
    }

    @Override
    public void endVisit(final SwitchExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final SwitchStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final SynchronizedStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final TagElement node) {

        dump(node);
    }

    @Override
    public void endVisit(final TextBlock node) {

        dump(node);
    }

    @Override
    public void endVisit(final TextElement node) {

        dump(node);
    }

    @Override
    public void endVisit(final ThisExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final ThrowStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final TryStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final TypeDeclaration node) {

        dump(node);
    }

    @Override
    public void endVisit(final TypeDeclarationStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final TypeLiteral node) {

        dump(node);
    }

    @Override
    public void endVisit(final TypeMethodReference node) {

        dump(node);
    }

    @Override
    public void endVisit(final TypeParameter node) {

        dump(node);
    }

    @Override
    public void endVisit(final UnionType node) {

        dump(node);
    }

    @Override
    public void endVisit(final UsesDirective node) {

        dump(node);
    }

    @Override
    public void endVisit(final IntersectionType node) {

        dump(node);
    }

    @Override
    public void endVisit(final VariableDeclarationExpression node) {

        dump(node);
    }

    @Override
    public void endVisit(final VariableDeclarationStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final VariableDeclarationFragment node) {

        dump(node);
    }

    @Override
    public void endVisit(final WhileStatement node) {

        dump(node);
    }

    @Override
    public void endVisit(final WildcardType node) {

        dump(node);
    }

    @Override
    public void endVisit(final YieldStatement node) {

        dump(node);
    }

}
