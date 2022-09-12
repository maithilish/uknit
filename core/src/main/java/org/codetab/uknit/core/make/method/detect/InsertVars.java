package org.codetab.uknit.core.make.method.detect;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.method.VarNames;
import org.codetab.uknit.core.make.model.ExpVar;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.InferVar;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;
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
 * @author m
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
    public InferVar createInsertVar(final Expression exp, final Type type,
            final Heap heap) {

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
     * Return arg type from ParameterizedType or java.lang.Object for simple
     * type.
     * @param type
     * @param argIndex
     * @return
     */
    public Type getArgType(final Type type, final int argIndex) {

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
}
