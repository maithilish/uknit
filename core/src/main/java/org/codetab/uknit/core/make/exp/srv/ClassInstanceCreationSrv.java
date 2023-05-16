package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Arguments;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;

public class ClassInstanceCreationSrv implements ExpService {

    @Inject
    private Arguments arguments;
    @Inject
    private Wrappers wrappers;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ClassInstanceCreation);

        ClassInstanceCreation cic = (ClassInstanceCreation) node;

        List<Expression> exps = new ArrayList<>();

        List<Expression> args = arguments.getArgs(cic);
        args.forEach(a -> exps.add(wrappers.strip(a)));

        return exps;
    }

    @Override
    public Expression getValue(final Expression node, final Heap heap) {
        /*
         * If exp is new String("foo") then value is new String("foo") as uKnit
         * doesn't evaluate the exp.
         */
        return node;
    }
}
