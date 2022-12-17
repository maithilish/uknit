package org.codetab.uknit.core.make.method.patch.service;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.patch.ServiceLoader;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

public class Patchers {

    @Inject
    private ServiceLoader serviceLoader;
    @Inject
    private Wrappers wrappers;

    public void patchExpWithName(final Expression node, final Expression copy,
            final Map<Expression, IVar> patches,
            final Consumer<Expression> consumer) {
        IVar var = patches.get(node);
        if (nonNull(var)) {
            Name name = node.getAST().newName(var.getName());
            consumer.accept(name);
        } else {
            PatchService patchService = serviceLoader.loadService(node);
            patchService.patch(node, copy, patches);
        }
    }

    public void patchExpsWithName(final List<Expression> exps,
            final List<Expression> expsCopy,
            final Map<Expression, IVar> patches) {
        for (int i = 0; i < exps.size(); i++) {
            final int expIndex = i;
            Expression exp = wrappers.unpack(exps.get(expIndex));
            Expression expCopy = wrappers.unpack(expsCopy.get(expIndex));
            patchExpWithName(exp, expCopy, patches, (name) -> {
                expsCopy.remove(expIndex);
                expsCopy.add(expIndex, name);
            });
        }
    }
}
