package org.codetab.uknit.core.node;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ReturnStatement;

public class Literals {

    @Inject
    private Nodes nodes;
    @Inject
    private Braces braces;

    /**
     * Whether literal is used by uknit. Literals may appear in MI, Annotation,
     * ArrayAccess etc., It is of interest if appears in,
     *
     * returnStmt: return "foo";
     *
     * assignment: array[0] = "foo";
     *
     * @param exp
     * @return
     */
    // REVIEW
    public boolean ofInterest(final Expression exp) {
        boolean ofInterest = false;
        ASTNode parent = braces.stripAndGetParent(exp);
        if (nodes.is(parent, ReturnStatement.class)) {
            ofInterest = true;
        }
        if (nodes.is(parent, Assignment.class)) {
            ofInterest = true;
        }
        return ofInterest;
    }
}
