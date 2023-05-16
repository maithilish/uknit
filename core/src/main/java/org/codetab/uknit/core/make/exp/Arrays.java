package org.codetab.uknit.core.make.exp;

import static java.util.Objects.isNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.exp.srv.ArrayAccessSrv;
import org.codetab.uknit.core.make.exp.srv.ExpServiceLoader;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

public class Arrays {

    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private Types types;
    @Inject
    private Wrappers wrappers;
    @Inject
    private ExpManager expManager;
    @Inject
    private ExpServiceLoader expServiceLoader;

    /**
     * For an ArrayAccess exp, find the ArrayInitializer exp and return value
     * from the initializer. Ex: int[] a = {10,20}; a[1]; the ini exp is {10,20}
     * and for a[1] value is 20.
     *
     * @param arrayAccess
     * @param heap
     * @return
     */
    public Optional<Expression> getValue(final ArrayAccess arrayAccess,
            final Heap heap) {
        return Optional.ofNullable(expManager.getValue(arrayAccess, heap));
    }

    /**
     * Get array name of array access.
     *
     * @param arrayAccess
     * @param heap
     * @return
     */
    public String getArrayName(final ArrayAccess arrayAccess, final Heap heap) {
        Expression arrayName = wrappers.unpack(arrayAccess.getArray());
        if (nodes.isSimpleName(arrayName)) {
            return nodes.getName(arrayName);
        } else if (nodes.is(arrayName, ArrayAccess.class)) {
            return getArrayName((ArrayAccess) arrayName, heap);
        } else {
            Expression nameExp = expManager.getValue(arrayName, heap);
            if (nodes.isName(nameExp)) {
                return nodes.getName(nameExp);
            } else {
                return null;
            }
        }
    }

    /**
     * Get index of an array access.
     *
     * @param arrayAccess
     * @param heap
     * @return
     */
    public int getIndex(final ArrayAccess arrayAccess, final Heap heap) {
        ArrayAccessSrv srv =
                (ArrayAccessSrv) expServiceLoader.loadService(arrayAccess);
        return srv.getIndex(arrayAccess, heap);
    }

    /**
     * Get array type from array access item or if not defined then type of
     * array access. Ex: Object names = {"foo"}; names[0]; Get type of "foo"
     * which is String, if unable to get type for "foo" then get type of array
     * access names[0] which is Object (the type of array).
     *
     * @param arrayAccess
     * @param heap
     * @return
     */
    public Optional<Type> getType(final ArrayAccess arrayAccess,
            final Heap heap) {
        Optional<Type> type = Optional.empty();
        Optional<Expression> valueO = getValue(arrayAccess, heap);
        if (valueO.isPresent()) {
            Expression value = valueO.get();
            if (nodes.isSimpleName(value)) {
                Optional<IVar> varO =
                        packs.findVarByName(value, heap.getPacks());
                if (varO.isPresent()) {
                    type = Optional.ofNullable(varO.get().getType());
                }
            } else {
                type = types.getType(value);
            }
        }

        if (type.isEmpty()) {
            /*
             * If unable to get type of value then return type of array access.
             * For int[] indexes; indexes[0]; the type is int and for String[]
             * names; names[1]; the type is String.
             */
            type = types.getType(arrayAccess);
        }
        return type;
    }

    /**
     * Get type binding of value returned by array access. See
     * {@link #getType(ArrayAccess, Heap)}.
     *
     * @param arrayAccess
     * @param heap
     * @return
     */
    public ITypeBinding getTypeBinding(final ArrayAccess arrayAccess,
            final Heap heap) {
        ITypeBinding binding = null;
        Optional<Expression> value = getValue(arrayAccess, heap);
        if (value.isPresent()) {
            binding = value.get().resolveTypeBinding();
        }

        if (isNull(binding)) {
            binding = arrayAccess.resolveTypeBinding();
        }
        return binding;
    }

}
