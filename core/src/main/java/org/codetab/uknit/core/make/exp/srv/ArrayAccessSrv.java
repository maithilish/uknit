package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
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
import org.eclipse.jdt.core.dom.CharacterLiteral;
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
    public Expression getValue(final Expression node, final Pack pack,
            final Heap heap) {
        checkState(node instanceof ArrayAccess);

        int index = getIndex(node, pack, heap);
        ArrayInitializer iniExp = getArrayInitializer(node, pack, heap);
        Expression reassignedExp =
                getReassignedValue(node, pack, index, iniExp, heap);

        Expression valueExp = null;
        if (nonNull(reassignedExp)) {
            ExpService expSrv = serviceLoader.loadService(reassignedExp);
            valueExp = expSrv.getValue(reassignedExp, pack, heap);
        } else if (nonNull(iniExp) && index >= 0) {
            valueExp = wrappers.unpack(safeExps.expressions(iniExp).get(index));
            ExpService expSrv = serviceLoader.loadService(valueExp);
            valueExp = expSrv.getValue(valueExp, pack, heap);
        }
        return valueExp;
    }

    public Expression getArrayName(final Expression node, final Pack pack,
            final Heap heap) {
        Expression exp;
        if (pack.hasPatches() && node.equals(pack.getExp())) {
            /*
             * Ex itest: internal.ArrayAccess.argParamSame(). The array[] is
             * renamed as b, the node.getArray() is array and for patchedNode it
             * is b.
             */
            Expression patchedNode = patcher.copyAndPatch(pack, heap);
            exp = wrappers.unpack(((ArrayAccess) patchedNode).getArray());
        } else {
            exp = wrappers.unpack(((ArrayAccess) node).getArray());
        }
        return exp;
    }

    public int getIndex(final Expression node, final Pack pack,
            final Heap heap) {
        checkState(node instanceof ArrayAccess);

        Expression indexExp = wrappers.unpack(((ArrayAccess) node).getIndex());

        while (nonNull(indexExp) && !nodes.is(indexExp, NumberLiteral.class,
                CharacterLiteral.class)) {
            ExpService expSrv = serviceLoader.loadService(indexExp);
            indexExp = expSrv.getValue(indexExp, pack, heap);
        }

        if (nonNull(indexExp)) {
            if (nodes.is(indexExp, NumberLiteral.class)) {
                return Integer.valueOf(((NumberLiteral) indexExp).getToken());
            } else if (nodes.is(indexExp, CharacterLiteral.class)) {
                // ex: array['\0']
                char ch = ((CharacterLiteral) indexExp).charValue();
                return ch;
            }
        }
        return -1;
    }

    private Expression getReassignedValue(final Expression node,
            final Pack pack, final int index, final ArrayInitializer iniExp,
            final Heap heap) {
        if (index < 0) {
            return null;
        }
        List<Pack> scopeList = packs.headList(pack, heap.getPacks());
        if (nonNull(iniExp)) {
            Optional<Pack> iniPackO = packs.findByExp(iniExp, scopeList);
            if (iniPackO.isPresent()) {
                int start = scopeList.indexOf(iniPackO.get());
                scopeList = scopeList.subList(start, scopeList.size());
            }
        }

        Expression arrayName = getArrayName(node, pack, heap);

        for (int i = scopeList.size() - 1; i >= 0; i--) {
            Optional<Expression> leftExpO = scopeList.get(i).getLeftExp();
            if (leftExpO.isPresent()
                    && nodes.is(leftExpO.get(), ArrayAccess.class)) {
                ArrayAccess leftExp = (ArrayAccess) leftExpO.get();
                int leftIndex = Integer.valueOf(
                        ((NumberLiteral) wrappers.unpack(leftExp.getIndex()))
                                .getToken());
                Expression leftArrayName = getArrayName(leftExp, pack, heap);
                if (index == leftIndex && nodes.getName(arrayName)
                        .equals(nodes.getName(leftArrayName))) {
                    return scopeList.get(i).getExp();
                }
            }
        }
        return null;
    }

    private ArrayInitializer getArrayInitializer(final Expression node,
            final Pack pack, final Heap heap) {
        checkState(node instanceof ArrayAccess);

        ArrayInitializer ini = null;
        Expression exp;
        exp = getArrayName(node, pack, heap);

        if (nodes.is(exp, ArrayAccess.class)) {
            ExpService expSrv = serviceLoader.loadService(exp);
            ini = ((ArrayAccessSrv) expSrv).getArrayInitializer(exp, pack,
                    heap);
        } else {
            /**
             * <code> String[] a = {"foo"}; String[] b = a; foo.append(b[0]) </code>
             * To start, exp is b and expSrv (SimpleNameSrv) returns exp a and
             * in next round for exp a, the expSrv (SimpleNameSrv) returns exp
             * {"foo"} which is ArrayInitilizer and while breaks.
             *
             */
            while (nonNull(exp)) {
                ExpService expSrv = serviceLoader.loadService(exp);
                exp = expSrv.getValue(exp, pack, heap);

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
                    } else if (nodes.is(exp, ArrayInitializer.class)) {
                        /*
                         * Embedded initializer. Ex: String[] a = { new String[]
                         * {"foo"}[0] }; The {"foo"} is embedded and a[0]
                         * returns "foo".
                         */
                        ini = (ArrayInitializer) exp;
                        break;
                    } else {
                        new CodeException(spaceit("pack not found for", exp));
                    }
                }
            }
        }
        if (nonNull(ini) && nodes.is(node, ArrayAccess.class)) {
            int index = getIndex(node, pack, heap);
            Expression item = safeExps.expressions(ini).get(index);
            if (nodes.is(item, ArrayInitializer.class)) {
                ini = (ArrayInitializer) item;
            }
        }
        return ini;
    }
}
