package org.codetab.uknit.core.make.method.visit;

import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

public class ArgReplacer {

    public void replace(final List<Expression> args, final Expression exp,
            final String name) {
        int index = args.indexOf(exp);
        if (index >= 0) {
            args.remove(index);
            args.add(index, exp.getAST().newSimpleName(name));
        }
    }
}
