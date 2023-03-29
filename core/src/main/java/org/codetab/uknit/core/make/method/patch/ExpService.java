package org.codetab.uknit.core.make.method.patch;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.service.PatchService;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

/**
 * Get exps of an exp.
 *
 * FIXME N - move this and srv.getExps() to method package as visit package uses
 * this. May be cached for performance.
 *
 * REVIEW - the above
 *
 * @author Maithilish
 *
 */
public class ExpService {

    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Nodes nodes;

    /**
     * Recursively list names in an exp.
     *
     * @param exp
     * @return
     */
    public List<Name> listNames(final Expression exp) {

        checkNotNull(exp);

        List<Name> names = new ArrayList<>();
        PatchService srv = serviceLoader.loadService(exp);
        List<Expression> expParts = srv.getExps(exp);

        /*
         * Services such as StringLiteralSrv etc., returns itself as exps. Don't
         * process such exps to avoid stack overflow.
         */
        if (expParts.size() == 1 && nonNull(expParts.get(0))
                && expParts.get(0).equals(exp)) {
            return names;
        }
        for (Expression expPart : expParts) {
            if (nodes.isName(expPart)) {
                names.add((Name) expPart);
            } else if (nonNull(expPart)) {
                names.addAll(listNames(expPart));
            }
        }
        return names;
    }
}
