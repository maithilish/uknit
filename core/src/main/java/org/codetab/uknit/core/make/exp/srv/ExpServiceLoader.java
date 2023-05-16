package org.codetab.uknit.core.make.exp.srv;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.exception.CodeException;
import org.eclipse.jdt.core.dom.Expression;

@Singleton
public class ExpServiceLoader {

    @Inject
    private DInjector di;

    private Map<String, ExpService> cache = new HashMap<>();

    /**
     * Load and return an instance of ExpService based on exp class. If exp is
     * MethodInvocation then returns instance of MethodInvocationSrv. Instances
     * are cached for reuse.
     *
     * @param exp
     * @return
     */
    public ExpService loadService(final Expression exp) {
        String clzName = exp.getClass().getSimpleName();
        String pkgName = ExpService.class.getPackageName();
        String qClzName = String.join("", pkgName, ".", clzName, "Srv");
        try {
            ExpService ps;
            if (cache.containsKey(qClzName)) {
                ps = cache.get(qClzName);
            } else {
                ps = (ExpService) di.instance(Class.forName(qClzName));
                cache.put(qClzName, ps);
            }
            return ps;
        } catch (ClassNotFoundException e) {
            String msg = spaceit("ExpService implementation not found for: ",
                    exp.getClass().getSimpleName(), exp.toString());
            throw new CodeException(msg);
        }
    }
}
