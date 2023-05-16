package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.QualifiedName;

/**
 * In JDT, person.id is QualifiedName exp and (person).id is FieldAccess exp.
 *
 * @author Maithilish
 *
 */
public class QualifiedNameSrv implements ExpService {

    @Inject
    private Wrappers wrappers;

    @Override
    public List<Expression> getExps(final Expression exp) {
        checkState(exp instanceof QualifiedName);

        QualifiedName qn = (QualifiedName) exp;

        List<Expression> exps = new ArrayList<>();

        exps.add(wrappers.strip(qn.getQualifier()));
        exps.add(wrappers.strip(qn.getName()));

        return exps;
    }

    @Override
    public Expression getValue(final Expression node, final Heap heap) {
        // TODO Auto-generated method stub
        return null;
    }
}
