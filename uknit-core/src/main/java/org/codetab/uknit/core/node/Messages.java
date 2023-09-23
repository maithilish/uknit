package org.codetab.uknit.core.node;

import org.eclipse.jdt.core.dom.ASTNode;

public class Messages {

    private Messages() {
    }

    public static String noImpl(final String format, final ASTNode node) {
        return String.format(format, node.getClass().getSimpleName());
    }

}
