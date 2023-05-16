package org.codetab.uknit.core.make.exp;

import java.util.List;

import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Provides unchecked lists to avoid @SuppressWarnings("unchecked") everywhere.
 *
 * @author Maithilish
 *
 */
public class SafeExps {

    public List<Expression> expressions(final ArrayInitializer node) {
        @SuppressWarnings("unchecked")
        List<Expression> exps = node.expressions();
        return exps;
    }

}
