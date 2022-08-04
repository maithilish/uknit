package org.codetab.uknit.core.make.method.visit;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.tree.TreeNode;
import org.codetab.uknit.core.tree.Trees;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;

import com.google.common.base.CaseFormat;

/**
 * Visit nodes in a control flow path and derive test method name suffix.
 * Example: for a method foo() then default test method is testFoo(...). For
 * same method and a control path, if(done){...}, the suffix is IfDone and test
 * method becomes testFooIfDone(...).
 *
 * @author m
 *
 */
public class NameVisitor extends ASTVisitor {

    @Inject
    private Nodes nodes;
    @Inject
    private Types types;
    @Inject
    private Configs configs;
    @Inject
    private Trees trees;

    private String name; // variable name such as done, flag etc.,
    private String classifier; // Try, If etc.,

    private TreeNode<ASTNode> ctlNode;
    private List<TreeNode<ASTNode>> ctlPath; // active path
    private TreeNode<ASTNode> ctlTree; // tree of all paths

    public void setup(final TreeNode<ASTNode> node,
            final List<TreeNode<ASTNode>> path, final TreeNode<ASTNode> tree) {
        this.ctlPath = path;
        this.ctlNode = node;
        this.ctlTree = tree;
        name = "";
        classifier = "";
    }

    @Override
    public boolean visit(final IfStatement ifStmt) {
        // for some other if stmt, do nothing
        if (!ctlNode.getObject().equals(ifStmt)) {
            return true;
        }
        int blockIndex = ctlPath.indexOf(ctlNode) + 1;
        if (blockIndex < ctlPath.size()) {
            ASTNode block =
                    ctlPath.get(ctlPath.indexOf(ctlNode) + 1).getObject();
            if (block.equals(ifStmt.getThenStatement())) {
                classifier = configs.getConfig(
                        "uknit.controlFlow.method.name.suffix.if", "If");
            } else if (block.equals(ifStmt.getElseStatement())) {
                classifier = configs.getConfig(
                        "uknit.controlFlow.method.name.suffix.else", "Else");
            }
        }
        Expression exp = ifStmt.getExpression();
        if (nodes.is(exp, SimpleName.class)) {
            name = nodes.getName(exp);
        }
        return false;
    }

    @Override
    public boolean visit(final TryStatement tryStmt) {
        // for some other try, do nothing
        if (!ctlNode.getObject().equals(tryStmt)) {
            return true;
        }

        int blockIndex = ctlPath.indexOf(ctlNode) + 1;

        if (blockIndex < ctlPath.size()) {
            ASTNode block =
                    ctlPath.get(ctlPath.indexOf(ctlNode) + 1).getObject();

            if (block.equals(tryStmt.getBody())) {
                // index the multiple try in a method
                String tryCount = "";
                int pos = trees.positionInTree(ctlTree, ctlNode,
                        TryStatement.class) + 1;
                if (pos > 1) {
                    tryCount = String.valueOf(pos);
                }

                classifier = configs.getConfig(
                        "uknit.controlFlow.method.name.suffix.try", "Try");
                classifier = String.join("", classifier, tryCount);
            }
        }
        return false;
    }

    @Override
    public boolean visit(final CatchClause catchClause) {
        // for some other catch, do nothing
        if (!ctlNode.getObject().equals(catchClause)) {
            return true;
        }
        int blockIndex = ctlPath.indexOf(ctlNode) + 1;
        if (blockIndex < ctlPath.size()) {
            ASTNode block =
                    ctlPath.get(ctlPath.indexOf(ctlNode) + 1).getObject();
            if (block.equals(catchClause.getBody())) {
                Type exType = catchClause.getException().getType();
                classifier = configs.getConfig(
                        "uknit.controlFlow.method.name.suffix.catch", "Catch");
                classifier =
                        String.join("", classifier, types.getTypeName(exType));
            }
        }
        return false;
    }

    /*
     * Convert lower camel to upper camel and return.
     */
    public String getSuffix() {
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, name);
        return String.join("", classifier, name);
    }
}
