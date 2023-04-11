package org.codetab.uknit.core.node;

import static java.util.Objects.isNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Pack;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class Arrays {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private SafeExps safeExps;
    @Inject
    private Types types;
    @Inject
    private Wrappers wrappers;

    /**
     * For an ArrayAccess exp, find the ArrayInitializer exp and return value
     * from the initializer. Ex: int[] a = {10,20}; a[1]; the ini exp is {10,20}
     * and for a[1] value is 20.
     *
     * @param exp
     * @param heap
     * @return
     */
    public Optional<Expression> getValue(final ArrayAccess exp,
            final Heap heap) {
        Optional<Expression> value = Optional.empty();
        String array = getArrayName(exp);
        Optional<Pack> arrayPackO = packs.findByVarName(array, heap.getPacks());
        if (arrayPackO.isPresent() && arrayPackO.get().hasExp()
                && arrayPackO.get().getExp() instanceof ArrayInitializer) {
            ArrayInitializer arrayIni =
                    (ArrayInitializer) arrayPackO.get().getExp();
            int index = getIndex(exp);
            value = Optional.ofNullable(
                    wrappers.unpack(safeExps.expressions(arrayIni).get(index)));
        }
        return value;
    }

    /**
     * For an ArrayAccess exp, return value from the array initializer. Ex:
     * int[] a = {10,20}; a[1]; the ini exp is {10,20} and for a[1] value is 20.
     *
     * TODO N - returns value for literals and simple name, implement for other
     * exps.
     *
     * @param exp
     * @param heap
     * @return
     */
    public Optional<Expression> getValue(final ArrayAccess exp,
            final ArrayInitializer iniExp, final Heap heap) {
        int index = getIndex(exp);
        int iniItems = iniExp.expressions().size();
        if (index >= iniItems) {
            throw new CriticalException(spaceit("array initializer size is",
                    iniItems, "but trying to access item", index + 1));
        }
        Expression value =
                wrappers.unpack(safeExps.expressions(iniExp).get(index));
        if (nodes.is(value, SimpleName.class)) {
            Optional<Pack> var =
                    packs.findByVarName(nodes.getName(value), heap.getPacks());
            if (var.isPresent()) {
                value = var.get().getExp();
            }
        }
        return Optional.ofNullable(value);
    }

    // REVIEW
    public Optional<Expression> getValue(final ArrayAccess exp,
            final ArrayCreation arrayCreation, final Heap heap) {
        ArrayInitializer arrayIni = arrayCreation.getInitializer();
        if (isNull(arrayIni)) {
            return Optional.empty();
        } else {
            return getValue(exp, arrayIni, heap);
        }
    }

    public Optional<Type> getType(final ArrayAccess exp, final Heap heap) {
        Optional<Type> type = Optional.empty();
        Optional<Expression> value = getValue(exp, heap);
        if (value.isPresent()) {
            type = types.getType(value.get());
        }
        return type;
    }

    public ITypeBinding getTypeBinding(final ArrayAccess exp, final Heap heap) {
        ITypeBinding binding = null;
        Optional<Expression> value = getValue(exp, heap);
        if (value.isPresent()) {
            binding = value.get().resolveTypeBinding();
        }
        return binding;
    }

    public int getIndex(final ArrayAccess exp) {
        Expression indexExp = wrappers.unpack(exp.getIndex());
        // REVIEW - handle other types
        if (nodes.is(indexExp, NumberLiteral.class)) {
            return Integer.parseInt(((NumberLiteral) indexExp).getToken());
        } else {
            throw new CodeException(nodes.noImplmentationMessage(indexExp));
        }
    }

    public String getArrayName(final ArrayAccess exp) {
        Expression arrayExp = exp.getArray();
        // REVIEW - handle other types
        if (nodes.isName(arrayExp)) {
            return nodes.getName(arrayExp);
        } else {
            throw new CodeException(nodes.noImplmentationMessage(arrayExp));
        }
    }

}
