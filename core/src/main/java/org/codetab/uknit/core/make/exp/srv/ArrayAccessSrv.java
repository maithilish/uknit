package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.SafeExps;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;

public class ArrayAccessSrv implements ExpService {

    @Inject
    private Wrappers wrappers;
    @Inject
    private Nodes nodes;
    @Inject
    private ExpServiceLoader serviceLoader;
    @Inject
    private SafeExps safeExps;
    @Inject
    private Patcher patcher;
    @Inject
    private Packs packs;

    @Override
    public List<Expression> getExps(final Expression node) {
        checkState(node instanceof ArrayAccess);

        List<Expression> exps = new ArrayList<>();

        ArrayAccess aa = (ArrayAccess) node;
        exps.add(wrappers.strip(aa.getArray()));
        exps.add(wrappers.strip(aa.getIndex()));

        return exps;
    }

    @Override
    public Expression getValue(final Expression node, final Heap heap) {
        checkState(node instanceof ArrayAccess);

        Expression an = ((ArrayAccess) node).getArray();
        Expression indexExp = wrappers.unpack(((ArrayAccess) node).getIndex());
        Expression valueExp = null;

        while (nonNull(indexExp) && !nodes.is(indexExp, NumberLiteral.class)) {
            ExpService expSrv = serviceLoader.loadService(indexExp);
            indexExp = expSrv.getValue(indexExp, heap);
        }

        ArrayInitializer iniExp = getArrayInitializer(node, heap);
        if (nonNull(iniExp) && nonNull(indexExp)) {
            int index = Integer.valueOf(((NumberLiteral) indexExp).getToken());
            valueExp = wrappers.unpack(safeExps.expressions(iniExp).get(index));
            ExpService expSrv = serviceLoader.loadService(valueExp);
            valueExp = expSrv.getValue(valueExp, heap);
        }
        return valueExp;
    }

    public ArrayInitializer getArrayInitializer(final Expression node,
            final Heap heap) {
        checkState(node instanceof ArrayAccess);

        ArrayInitializer ini = null;

        Expression exp = wrappers.unpack(((ArrayAccess) node).getArray());
        if (nodes.isSimpleName(exp)) {
            Optional<Pack> nodePack = packs.findByExp(node, heap.getPacks());
            if (nodePack.isPresent()) {
                Expression pExp = patcher.copyAndPatch(nodePack.get(), heap);
                exp = wrappers.unpack(((ArrayAccess) pExp).getArray());
            }
        }

        if (nodes.is(exp, ArrayAccess.class)) {
            ExpService expSrv = serviceLoader.loadService(exp);
            ini = ((ArrayAccessSrv) expSrv).getArrayInitializer(exp, heap);
        } else {
            while (nonNull(exp)) {
                ExpService expSrv = serviceLoader.loadService(exp);
                exp = expSrv.getValue(exp, heap);

                // loop until array initializer is found or value exp is null
                if (nonNull(exp) && nodes.is(exp, ArrayInitializer.class,
                        ArrayCreation.class)) {
                    Optional<Pack> iniPack =
                            packs.findByExp(exp, heap.getPacks());
                    if (iniPack.isPresent()) {
                        Expression iniExp;
                        if (iniPack.get().hasPatches()) {
                            iniExp = patcher.copyAndPatch(iniPack.get(), heap);
                        } else {
                            iniExp = iniPack.get().getExp();
                        }
                        if (nodes.is(iniExp, ArrayInitializer.class)) {
                            ini = (ArrayInitializer) iniExp;
                            break;
                        } else if (nodes.is(iniExp, ArrayCreation.class)) {
                            ini = ((ArrayCreation) iniExp).getInitializer();
                            break;
                        }
                    }
                }
            }
        }
        if (nonNull(ini) && nodes.is(node, ArrayAccess.class)) {
            int index = getIndex(node, heap);
            Expression item = safeExps.expressions(ini).get(index);
            if (nodes.is(item, ArrayInitializer.class)) {
                ini = (ArrayInitializer) item;
            }
        }
        return ini;
    }

    public int getIndex(final Expression node, final Heap heap) {
        checkState(node instanceof ArrayAccess);

        Expression indexExp = wrappers.unpack(((ArrayAccess) node).getIndex());

        while (nonNull(indexExp) && !nodes.is(indexExp, NumberLiteral.class)) {
            ExpService expSrv = serviceLoader.loadService(indexExp);
            indexExp = expSrv.getValue(indexExp, heap);
        }

        if (nonNull(indexExp)) {
            return Integer.valueOf(((NumberLiteral) indexExp).getToken());
        } else {
            return -1;
        }
    }
}
