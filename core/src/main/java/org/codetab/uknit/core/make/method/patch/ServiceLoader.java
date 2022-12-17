package org.codetab.uknit.core.make.method.patch;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.eclipse.jdt.core.dom.Expression;

@Singleton
public class ServiceLoader {

    @Inject
    private DInjector di;

    private Map<String, PatchService> cache = new HashMap<>();

    /**
     * Load and return an instance of PatchService based on exp class. If exp is
     * MethodInvocation then returns instance of MethodInvocationSrv. Instances
     * are reused through cache.
     *
     * @param exp
     * @return
     */
    public PatchService loadService(final Expression exp) {
        String clzName = exp.getClass().getSimpleName();
        String pkgName = PatchService.class.getPackageName();
        String qClzName = String.join("", pkgName, ".", clzName, "Srv");
        try {
            PatchService ps;
            if (cache.containsKey(qClzName)) {
                ps = cache.get(qClzName);
            } else {
                ps = (PatchService) di.instance(Class.forName(qClzName));
                cache.put(qClzName, ps);
            }
            return ps;
        } catch (ClassNotFoundException e) {
            String msg = spaceit("PatchService implementation not found for: ",
                    exp.getClass().getSimpleName(), exp.toString());
            throw new CodeException(msg);
        }
    }
}
