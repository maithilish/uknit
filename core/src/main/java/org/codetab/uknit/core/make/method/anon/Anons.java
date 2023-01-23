package org.codetab.uknit.core.make.method.anon;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.Type;

public class Anons {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Types types;
    @Inject
    private Resolver resolver;

    /**
     * Get type of anon, lambda or var.
     *
     * @param exp
     * @param heap
     * @return
     */
    public Type getAnonymousType(final Expression exp, final Heap heap) {
        Type type = null;
        if (nodes.is(exp, ClassInstanceCreation.class)) {
            ClassInstanceCreation cic =
                    nodes.as(exp, ClassInstanceCreation.class);
            if (nonNull(cic.getAnonymousClassDeclaration())) {
                type = cic.getType();
            }
        } else if (nodes.is(exp, LambdaExpression.class)) {
            type = types.getType(resolver.resolveTypeBinding(exp),
                    exp.getAST());
        } else if (nodes.isName(exp)) {
            Optional<Pack> packO =
                    packs.findByVarName(nodes.getName(exp), heap.getPacks());
            if (packO.isPresent() && packO.get().is(Nature.ANONYMOUS)
                    && nonNull(packO.get().getVar())) {
                type = packO.get().getVar().getType();
            }
        }
        return type;
    }
}
