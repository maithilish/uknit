package org.codetab.uknit.core.make.method.body.initializer;

import javax.inject.Inject;

import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;

class Factory {

    @Inject
    private DInjector di;
    @Inject
    private Nodes nodes;

    /**
     * Load appropriate initializer service. Parameter var is the var for which
     * initializer is needed and exp is effective exp from which initializer is
     * created.
     *
     * @param exp
     * @return
     */
    public IInitializer createInitializer(final Expression exp) {
        IInitializer iniSrv = null;
        if (nodes.is(exp, MethodInvocation.class)) {
            iniSrv = di.instance(InvokeInitializer.class);
        } else {
            iniSrv = di.instance(ExpInitializer.class);
        }
        return iniSrv;
    }

    public IInitializer createNameInitializer() {
        return di.instance(NameInitializer.class);
    }

    public IInitializer createExpInitializer() {
        return di.instance(ExpInitializer.class);
    }
}
