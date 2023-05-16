package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;

/**
 * In JDT, (person).id is FieldAccess exp and person.id is QualifiedName exp.
 *
 * @author Maithilish
 *
 */
public class FieldAccessSrv implements ExpService {

    @Inject
    private Wrappers wrappers;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof FieldAccess);

        FieldAccess fa = (FieldAccess) node;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(fa.getExpression()));
        exps.add(wrappers.strip(fa.getName()));

        return exps;
    }

    @Override
    public Expression getValue(final Expression node, final Heap heap) {
        // TODO Auto-generated method stub
        return null;
    }
}
