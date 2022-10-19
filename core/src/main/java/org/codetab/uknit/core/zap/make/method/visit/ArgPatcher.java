package org.codetab.uknit.core.zap.make.method.visit;

import java.util.List;

import org.codetab.uknit.core.zap.make.model.Patch;
import org.eclipse.jdt.core.dom.Expression;

public class ArgPatcher {

    public void patch(final List<Expression> args, final Patch patch) {
        int index = patch.getExpIndex();
        if (index >= 0) {
            args.remove(index);
            args.add(index,
                    patch.getExp().getAST().newSimpleName(patch.getName()));
        }
    }
}
