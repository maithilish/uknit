package org.codetab.uknit.core.make.method.visit;

import java.util.List;

import org.codetab.uknit.core.make.model.Patch;
import org.eclipse.jdt.core.dom.Expression;

public class ArgPatcher {

    public void patch(final List<Expression> args, final Patch patch) {
        int index = patch.getArgIndex();
        if (index >= 0) {
            args.remove(index);
            args.add(index,
                    patch.getExp().getAST().newSimpleName(patch.getName()));
        }
    }
}
