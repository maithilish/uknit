package org.codetab.uknit.core.zap.make.method.detect.insert;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.zap.make.method.VarNames;
import org.codetab.uknit.core.zap.make.model.ExpVar;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.codetab.uknit.core.zap.make.model.IVar;
import org.codetab.uknit.core.zap.make.model.InferVar;
import org.codetab.uknit.core.zap.make.model.ModelFactory;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;

/**
 * In method invokes, such as map.get("foo"), literals are not patched to infer
 * vars. They are mapped to vars while processing invokes just to create
 * inserts. Unlike normal infer vars, the patches are not staged for these vars
 * and mi is not patched i.e. "foo" is not patched to var.
 * @author Maithilish
 *
 */
public class InsertVars {

    @Inject
    private Mocks mocks;
    @Inject
    private Nodes nodes;
    @Inject
    private Types types;
    @Inject
    private VarNames varNames;
    @Inject
    private ModelFactory modelFactory;

    /**
     * Create infer var for StringLiteral, NumberLiteral, and CharacterLiteral.
     * @param exp
     * @param type
     * @param heap
     * @return
     */
    public InferVar createInsertVarForLiteral(final Expression exp,
            final Type type, final Heap heap) {

        checkNotNull(exp);
        checkNotNull(type);
        checkNotNull(heap);

        List<Class<?>> clzz = List.of(StringLiteral.class, NumberLiteral.class,
                CharacterLiteral.class);
        if (clzz.stream().noneMatch(c -> nodes.is(exp, c))) {
            throw new CodeException(nodes.noImplmentationMessage(exp));
        }

        String name = varNames.getInferVarName(Optional.empty(), heap);
        InferVar inferVar =
                modelFactory.createInferVar(name, type, mocks.isMockable(type));
        return inferVar;
    }

    /**
     * Create exp var. Ex: apple (leftVar) = foo (right expression).
     * @param rightExp
     * @param leftVar
     * @return
     */
    public ExpVar createInsertExpVar(final Expression rightExp,
            final IVar leftVar) {

        checkNotNull(rightExp);
        checkNotNull(leftVar);

        ExpVar expVar = modelFactory.createVarExp(null, rightExp);
        expVar.setLeftVar(leftVar);
        return expVar;
    }

    /**
     * Return type arg from ParameterizedType or java.lang.Object for simple *
     * type.
     * <p>
     * Ex: for List<String, Date> returns type String.
     * @param type
     * @param argIndex
     * @return
     */
    public Type getTypeArg(final Type type, final int argIndex) {

        checkNotNull(type);

        Type argType = null;
        if (nodes.is(type, ParameterizedType.class)) {
            argType = (Type) ((ParameterizedType) type).typeArguments()
                    .get(argIndex);
        } else if (nodes.is(type, SimpleType.class)) {
            argType = types.getType("java.lang.Object", type.getAST());
        } else {
            throw new CodeException(nodes.noImplmentationMessage(argType));
        }
        return argType;
    }

    /**
     * Return arg type from ParameterizedType or java.lang.Object for simple
     * type.
     * @param type
     * @param argIndex
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Type> getTypeArgs(final Type type) {
        checkNotNull(type);

        List<Type> typeArgs = new ArrayList<>();
        if (nodes.is(type, ParameterizedType.class)) {
            typeArgs.addAll(((ParameterizedType) type).typeArguments());
        } else if (nodes.is(type, SimpleType.class)) {
            typeArgs.add(types.getType("java.lang.Object", type.getAST()));
        } else {
            throw new CodeException(nodes.noImplmentationMessage(type));
        }

        return typeArgs;
    }

    /**
     * When requireKey collections, such as Map, is used in forEach then either
     * for key or value is missing depending whether keySet() or values() method
     * invoked. We create InferVar for the missing. Ex: Take Map<String, Date>
     * then,
     * <p>
     * for(String key : map.keySet()) {..} - keySet() returns key as loop var
     * but the value var is missing.
     * <p>
     * for(Date date : map.values()) {..} - values() returns date the value as
     * loop var but the key var is missing.
     *
     * @param var
     * @param rightExp
     * @param heap
     * @return
     */
    public Optional<IVar> createPutInferVar(final IVar var,
            final Expression rightExp, final Heap heap) {
        if (nodes.is(rightExp, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(rightExp, MethodInvocation.class);
            String invokedMethod = nodes.getName(mi.getName());

            Type callType = var.getType();
            Type type = null;
            if (nodes.is(callType, ParameterizedType.class)) {
                if (invokedMethod.equals("keySet")) {
                    type = (Type) ((ParameterizedType) callType).typeArguments()
                            .get(1);
                } else {
                    type = (Type) ((ParameterizedType) callType).typeArguments()
                            .get(0);
                }
            }
            // if not generic collection then type is Object
            if (nodes.is(callType, SimpleType.class)) {
                type = types.getType("java.lang.Object", callType.getAST());
            }

            String name = varNames.getInferVarName(Optional.empty(), heap);
            InferVar inferVar = modelFactory.createInferVar(name, type,
                    mocks.isMockable(type));
            return Optional.of(inferVar);
        }
        return Optional.empty();
    }
}
