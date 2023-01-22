package org.codetab.uknit.core.node;

import static java.util.Objects.nonNull;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;

public class Misuses {

    @Inject
    private Types types;
    @Inject
    private Nodes nodes;
    @Inject
    private Configs configs;
    @Inject
    private Resolver resolver;

    /**
     * Mockito throws errors for Class type.
     *
     * TODO L - yet to fully implement.
     *
     * @return
     */
    public boolean isMisuse(final IVar var) {
        String clzName = types.getClzName(var.getType());
        if (nonNull(clzName) && clzName.equals("java.lang.Class")) {
            return true;
        }
        return false;
    }

    /**
     * Mockito throws errors for
     *
     * 1. you stub either of: final/private/equals()/hashCode() methods. Those
     * methods *cannot* be stubbed/verified. Mocking methods declared on
     * non-public parent classes is not supported.
     *
     * @return
     */
    public boolean isMisuse(final Invoke invoke) {
        MethodInvocation mi = invoke.getMi();
        if (nonNull(mi)) {
            String methodName = nodes.getName(mi.getName());
            if (methodName.equals("equals") || methodName.equals("hashCode")) {
                return true;
            }
        }

        boolean stubFinal = configs.getConfig("uknit.mockito.stub.final", true);
        if (!stubFinal) {
            int modifier = resolver.resolveMethodBinding(mi).getModifiers();
            if (resolver.hasModifier(modifier, Modifier.FINAL)) {
                return true;
            }
        }

        return false;
    }
}
