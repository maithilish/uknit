package org.codetab.uknit.core.make.exp;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.srv.ExpService;
import org.codetab.uknit.core.make.exp.srv.ExpServiceLoader;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;

public class ExpManager {

    @Inject
    private ExpServiceLoader serviceLoader;

    public Expression getValue(final Expression exp, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        ExpService expService = serviceLoader.loadService(exp);
        return expService.getValue(exp, copy, pack, createValue, heap);
    }

    public Expression unparenthesize(final Expression exp) {
        ExpService expService = serviceLoader.loadService(exp);
        return expService.unparenthesize(exp);
    }

    public List<Expression> getExps(final Expression exp) {
        ExpService expService = serviceLoader.loadService(exp);
        return expService.getExps(exp);
    }

    public <T extends Expression> T rejig(final T exp, final Heap heap) {
        ExpService expService = serviceLoader.loadService(exp);
        return expService.rejig(exp, heap);
    }

}
