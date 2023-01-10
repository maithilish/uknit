package org.codetab.uknit.core.make.method.body;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;

/**
 * FIXME Pack - whether this stmt is required.
 *
 * @author Maithilish
 *
 */
public class ReturnVarStmt {

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Types types;
    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;

    public Optional<Statement> createStmt(final Heap heap) {

        Optional<Pack> returnPackO =
                packs.findByVarName("return", heap.getPacks());

        Statement stmt = null;
        if (returnPackO.isPresent()) {
            IVar var = returnPackO.get().getVar();
            Expression exp = returnPackO.get().getExp();

            /*
             * return this; then CUT field is already added to test class
             */
            boolean isSelfField = false;
            if (nodes.is(exp, ThisExpression.class)) {
                isSelfField = true;
            }

            if (var.isEnable() && !isSelfField) {
                String initializer = "\"not set\"";
                if (var.getInitializer().isPresent()) {
                    initializer = var.getInitializer().get().getInitializer()
                            .toString();
                }
                Type type = var.getType();
                String typeLiteral;
                if (type.isParameterizedType()) {
                    typeLiteral = types.getParameterizedTypeName(
                            (ParameterizedType) var.getType());
                } else {
                    typeLiteral = types.getTypeName(var.getType());
                }
                stmt = nodeFactory.createVarDeclStmt(var.getName(), typeLiteral,
                        initializer);
            }
        }
        return Optional.ofNullable(stmt);
    }

    @SuppressWarnings("unchecked")
    public void addStmt(final MethodDeclaration methodDecl,
            final Optional<Statement> stmt) {
        if (stmt.isPresent()) {
            methodDecl.getBody().statements().add(stmt.get());
        }
    }
}
