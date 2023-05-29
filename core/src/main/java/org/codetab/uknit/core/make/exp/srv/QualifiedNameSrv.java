package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
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
    @Inject
    private NodeFactory factory;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private Initializers initializers;

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
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof QualifiedName);
        QualifiedName copy = (QualifiedName) factory.copyNode(node);

        Name qualifer = (Name) wrappers.strip(copy.getQualifier());
        qualifer = (Name) serviceLoader.loadService(qualifer)
                .unparenthesize(qualifer);
        copy.setQualifier(factory.copyNode(qualifer));

        // parenthesise is not allowed for name

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof QualifiedName);
        Expression value = initializers.getInitializerAsExpression(node,
                createValue, heap);
        return value;
    }
}
