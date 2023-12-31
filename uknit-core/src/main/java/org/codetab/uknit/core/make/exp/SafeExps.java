package org.codetab.uknit.core.make.exp;

import java.util.List;

import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

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

    public List<Expression> getArgs(final ClassInstanceCreation node) {
        @SuppressWarnings("unchecked")
        List<Expression> args = node.arguments();
        return args;
    }

    public List<Expression> getArgs(final MethodInvocation node) {
        @SuppressWarnings("unchecked")
        List<Expression> args = node.arguments();
        return args;
    }

    public List<Expression> getArgs(final SuperMethodInvocation node) {
        @SuppressWarnings("unchecked")
        List<Expression> args = node.arguments();
        return args;
    }

    public List<Expression> getDims(final ArrayCreation node) {
        @SuppressWarnings("unchecked")
        List<Expression> dims = node.dimensions();
        return dims;
    }

    public List<Expression> getExps(final ArrayInitializer node) {
        @SuppressWarnings("unchecked")
        List<Expression> exps = node.expressions();
        return exps;
    }

    public List<Expression> getExtendedOperands(final InfixExpression infix) {
        @SuppressWarnings("unchecked")
        List<Expression> opers = infix.extendedOperands();
        return opers;
    }

}
