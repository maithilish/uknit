package org.codetab.uknit.core.make.exp.srv;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
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
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;

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
    @Inject
    private NodeFactory factory;
    @Inject
    private Rejigs rejigs;

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
    public Expression unparenthesize(final Expression node) {
        checkState(node instanceof ArrayAccess);
        ArrayAccess copy = (ArrayAccess) factory.copyNode(node);

        Expression array = wrappers.strip(copy.getArray());
        array = serviceLoader.loadService(array).unparenthesize(array);
        copy.setArray(factory.copyNode(array));

        Expression index = wrappers.strip(copy.getIndex());
        index = serviceLoader.loadService(index).unparenthesize(index);
        copy.setIndex(factory.copyNode(index));

        return copy;
    }

    @Override
    public Expression getValue(final Expression node, final Expression copy,
            final Pack pack, final boolean createValue, final Heap heap) {
        checkState(node instanceof ArrayAccess);

        int index = getIndex(node, copy, pack, heap);
        ArrayInitializer iniExp = getArrayInitializer(node, copy, pack, heap);
        Expression reassignedExp =
                getReassignedValue(node, copy, pack, index, iniExp, heap);

        Expression valueExp = null;
        if (nonNull(reassignedExp)) {
            ExpService expSrv = serviceLoader.loadService(reassignedExp);
            valueExp = expSrv.getValue(reassignedExp,
                    patcher.getCopy(reassignedExp, true, heap), pack,
                    createValue, heap);
        } else if (nonNull(iniExp) && index >= 0) {
            valueExp = wrappers.unpack(safeExps.expressions(iniExp).get(index));
            ExpService expSrv = serviceLoader.loadService(valueExp);
            valueExp = expSrv.getValue(valueExp,
                    patcher.getCopy(valueExp, true, heap), pack, createValue,
                    heap);
        }
        return valueExp;
    }

    public Expression getArrayName(final Expression node, final Expression copy,
            final Pack pack, final Heap heap) {
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

    public int getIndex(final Expression node, final Expression copy,
            final Pack pack, final Heap heap) {
        checkState(node instanceof ArrayAccess);
        Expression indexExp;
        if (isNull(copy)) {
            indexExp = wrappers.unpack(((ArrayAccess) node).getIndex());
        } else {
            indexExp = wrappers.unpack(((ArrayAccess) copy).getIndex());
        }

        boolean createValue = true;
        if (nodes.is(indexExp, MethodInvocation.class)) {
            ExpService expSrv = serviceLoader.loadService(indexExp);
            indexExp = expSrv.getValue(indexExp,
                    patcher.getCopy(indexExp, true, heap), pack, createValue,
                    heap);
        } else {
            while (nonNull(indexExp) && !nodes.is(indexExp, NumberLiteral.class,
                    CharacterLiteral.class)) {
                ExpService expSrv = serviceLoader.loadService(indexExp);
                Expression iExp = expSrv.getValue(indexExp,
                        patcher.getCopy(indexExp, true, heap), pack,
                        createValue, heap);
                // break cyclic SimpleName etc.,
                if (indexExp.equals(iExp)) {
                    break;
                }
                indexExp = iExp;
            }
        }

        if (nonNull(indexExp)) {
            if (nodes.is(indexExp, NumberLiteral.class)) {
                return Integer.valueOf(((NumberLiteral) indexExp).getToken());
            } else if (nodes.is(indexExp, CharacterLiteral.class)) {
                // ex: array['\0']
                char ch = ((CharacterLiteral) indexExp).charValue();
                return ch;
            } else if (nodes.is(indexExp, SimpleName.class)) {
                Optional<Pack> varPackO = packs.findByVarName(
                        nodes.getName(indexExp), heap.getPacks());
                IVar var = packs.getVar(varPackO);
                if (nonNull(var) && var.getInitializer().isPresent()) {
                    Object ini = var.getInitializer().get().getInitializer();
                    if (ini instanceof Expression) {
                        if (nodes.is((Expression) ini, NumberLiteral.class)) {
                            return Integer
                                    .valueOf(((NumberLiteral) ini).getToken());
                        } else if (nodes.is((Expression) ini,
                                CharacterLiteral.class)) {
                            // ex: array['\0']
                            char ch = ((CharacterLiteral) ini).charValue();
                            return ch;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private Expression getReassignedValue(final Expression node,
            final Expression copy, final Pack pack, final int index,
            final ArrayInitializer iniExp, final Heap heap) {
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

        Expression arrayName = getArrayName(node, copy, pack, heap);

        for (int i = scopeList.size() - 1; i >= 0; i--) {
            Optional<Expression> leftExpO = scopeList.get(i).getLeftExp();
            if (leftExpO.isPresent()) {
                Expression leftExp = wrappers.unpack(leftExpO.get());
                if (nodes.is(leftExp, ArrayAccess.class)) {
                    ArrayAccess leftArrayAccess = (ArrayAccess) leftExp;
                    int leftIndex = getIndex(leftArrayAccess,
                            getLeftCopy(leftArrayAccess, scopeList, heap), pack,
                            heap);
                    Expression leftArrayName = getArrayName(leftArrayAccess,
                            getLeftCopy(leftArrayAccess, scopeList, heap), pack,
                            heap);
                    if (index == leftIndex && nodes.getName(arrayName)
                            .equals(nodes.getName(leftArrayName))) {
                        Expression reassignedExp = scopeList.get(i).getExp();
                        if (reassignedExp.equals(node)) {
                            /*
                             * if reassigned exp is node then ignore it. Ref:
                             * itest.reassign.ArrayAccess.
                             */
                            return null;
                        } else {
                            return reassignedExp;
                        }
                    }
                }
            }
        }
        return null;
    }

    private Expression getLeftCopy(final Expression exp,
            final List<Pack> scopeList, final Heap heap) {
        Optional<Pack> packO = packs.findLastByLeftExp(exp, scopeList);
        if (packs.hasPatches(packO)) {
            return patcher.copyAndPatch(packO.get(), heap);
        }
        return exp;
    }

    private ArrayInitializer getArrayInitializer(final Expression node,
            final Expression copy, final Pack pack, final Heap heap) {
        checkState(node instanceof ArrayAccess);

        ArrayInitializer ini = null;
        Expression exp;
        exp = getArrayName(node, copy, pack, heap);

        boolean createValue = false;
        if (nodes.is(exp, ArrayAccess.class)) {
            ExpService expSrv = serviceLoader.loadService(exp);
            ini = ((ArrayAccessSrv) expSrv).getArrayInitializer(exp,
                    patcher.getCopy(exp, true, heap), pack, heap);
        } else {
            /**
             * <code> String[] a = {"foo"}; String[] b = a; foo.append(b[0]) </code>
             * To start, exp is b and expSrv (SimpleNameSrv) returns exp a and
             * in next round for exp a, the expSrv (SimpleNameSrv) returns exp
             * {"foo"} which is ArrayInitilizer and while breaks.
             *
             */
            List<Expression> prevExps = new ArrayList<>();
            prevExps.add(exp);
            while (nonNull(exp)) {
                ExpService expSrv = serviceLoader.loadService(exp);
                exp = expSrv.getValue(exp, patcher.getCopy(exp, true, heap),
                        pack, createValue, heap);
                /*
                 * Cyclic Ex: items = box.getItems(); name returns mi and mi
                 * returns name
                 */
                if (prevExps.contains(exp)) {
                    break;
                }
                prevExps.add(exp);

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
            int index = getIndex(node, copy, pack, heap);
            if (index >= 0) {
                Expression item = safeExps.expressions(ini).get(index);
                if (nodes.is(item, ArrayInitializer.class)) {
                    ini = (ArrayInitializer) item;
                }
            }
        }
        if (isNull(ini) && nodes.is(exp, NullLiteral.class)) {
            throw new NullPointerException(
                    nodes.exMessage("array is null, not allowed to", node));
        }
        return ini;
    }

    @Override
    public <T extends Expression> T rejig(final T node, final Heap heap) {
        checkState(node instanceof ArrayAccess);

        if (rejigs.needsRejig(node)) {
            T copy = factory.copyExp(node);
            ArrayAccess wc = (ArrayAccess) copy;
            // replace any ref to this to CUT name
            rejigs.rejigThisExp(wc::getArray, wc::setArray, heap);
            rejigs.rejigThisExp(wc::getIndex, wc::setIndex, heap);
            return copy;
        } else {
            return node;
        }
    }
}
