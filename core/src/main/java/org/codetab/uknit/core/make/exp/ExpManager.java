package org.codetab.uknit.core.make.exp;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.srv.ExpService;
import org.codetab.uknit.core.make.exp.srv.ExpServiceLoader;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.Expression;

public class ExpManager {

    @Inject
    private ExpServiceLoader serviceLoader;

    public Expression getValue(final Expression exp, final Pack pack,
            final Heap heap) {
        ExpService expService = serviceLoader.loadService(exp);
        return expService.getValue(exp, pack, heap);
    }
}
