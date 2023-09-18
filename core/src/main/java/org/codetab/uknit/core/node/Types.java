package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.ResolveException;
import org.codetab.uknit.core.exception.TypeNameException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IntersectionType;
import org.eclipse.jdt.core.dom.ModuleQualifiedName;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NameQualifiedType;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.WildcardType;

public class Types {

    @Inject
    private Nodes nodes;
    @Inject
    private Configs configs;

    private Set<String> unmodifiableTypes;

    public Types() {
        unmodifiableTypes = new HashSet<String>(Arrays.asList("Class",
                "Boolean", "Byte", "Short", "Character", "Integer", "Long",
                "Float", "Double", "String", "Runtime"));
    }

    public boolean isUnmodifiable(final String typeName) {
        return unmodifiableTypes.contains(typeName);
    }

    public String getParameterizedTypeName(final ParameterizedType type) {
        return type.toString();
    }

    public String getTypeName(final Type type) {
        return getTypeName(type, false);
    }

    public String getTypeName(final Type type,
            final boolean parameterizedName) {
        Type namedType = type;
        if (type.isParameterizedType()) {
            if (parameterizedName) {
                return type.toString();
            } else {
                namedType = ((ParameterizedType) type).getType();
            }
        }
        try {
            Method getNameMethod = namedType.getClass().getMethod("getName");
            Name name = (Name) getNameMethod.invoke(namedType);
            // simple or qualified types such as (Arc2D.Float)
            return name.getFullyQualifiedName();
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            if (type.isPrimitiveType()) {
                PrimitiveType pt = (PrimitiveType) type;
                return pt.getPrimitiveTypeCode().toString();
            }
            if (type.isArrayType()) {
                ArrayType at = (ArrayType) type;
                return at.toString();
            }
            if (type.isUnionType()) {
                UnionType ut = (UnionType) type;
                // consider only the first element
                Type ft = (Type) ut.types().get(0);
                return ft.toString();
            }
            /*
             * Wildcard types such as ObjectPool<? extends Connection> pool; Ref
             * itest: generic.Wildcard
             */
            if (type.isWildcardType()) {
                WildcardType wt = (WildcardType) type;
                Type bound = wt.getBound();
                if (nonNull(bound)) {
                    return bound.toString();
                } else {
                    // No bound ? then Object. Ex: Map<String, ?> source
                    return "Object";
                }
            }
            throw new TypeNameException(
                    spaceit(type.getClass().getName(), "has no name"));
        }
    }

    /**
     * Recursively process type and returns is bare name.
     *
     * @param type
     * @return
     */
    public String getTypeNameAsIdentifier(final Type type) {

        if (type.isArrayType()) {
            return getTypeNameAsIdentifier(((ArrayType) type).getElementType());
        }

        if (type.isIntersectionType()) {
            return getTypeNameAsIdentifier(
                    (Type) ((IntersectionType) type).types().get(0));
        }

        if (type.isUnionType()) {
            /*
             * union appears in catch clause and intersection in generic type
             * variables, use only the first element
             */
            return getTypeNameAsIdentifier(
                    (Type) ((UnionType) type).types().get(0));
        }

        if (type.isWildcardType()) {
            return getTypeNameAsIdentifier(((WildcardType) type).getBound());
        }

        if (type.isParameterizedType()) {
            return getTypeNameAsIdentifier(
                    ((ParameterizedType) type).getType());
        }

        // finally, process clean type
        if (type.isPrimitiveType()) {
            return ((PrimitiveType) type).getPrimitiveTypeCode().toString();
        }

        // get name from clean type
        Name name = null;
        if (type.isSimpleType()) {
            name = ((SimpleType) type).getName();
            if (name instanceof ModuleQualifiedName) {
                name = ((ModuleQualifiedName) name).getName();
            }
        }

        if (type.isQualifiedType()) {
            name = ((QualifiedType) type).getName();
        }

        if (type.isNameQualifiedType()) {
            name = ((NameQualifiedType) type).getName();
        }

        // get unqualified name
        if (nonNull(name)) {
            if (name.isQualifiedName()) {
                name = ((QualifiedName) name).getName();
            }
            // now name is really unqualified
            return name.getFullyQualifiedName();
        }

        throw new CodeException(
                nodes.exMessage("unable to derive type name", type));
    }

    public boolean isBoolean(final Type retType) {
        return retType.isPrimitiveType()
                && getTypeName(retType).equals("boolean");
    }

    /**
     * Convenient method to get type from exp including null exp
     *
     * @param exp
     * @param ast
     * @return optional type
     */
    public Optional<Type> getType(final Expression exp) {
        Type type = null;
        if (isNull(exp) || (nonNull(exp) && nodes.is(exp, NullLiteral.class))) {
            return Optional.empty();
        }

        /*
         * Get type using getType() method. Only following Expression Nodes have
         * getType() method.
         */
        if (nodes.is(exp, ArrayCreation.class)) {
            type = ((ArrayCreation) exp).getType();
        } else if (nodes.is(exp, CastExpression.class)) {
            type = ((CastExpression) exp).getType();
        } else if (nodes.is(exp, ClassInstanceCreation.class)) {
            type = ((ClassInstanceCreation) exp).getType();
        } else if (nodes.is(exp, CreationReference.class)) {
            type = ((CreationReference) exp).getType();
        } else if (nodes.is(exp, TypeMethodReference.class)) {
            type = ((TypeMethodReference) exp).getType();
        } else if (nodes.is(exp, VariableDeclarationExpression.class)) {
            type = ((VariableDeclarationExpression) exp).getType();
        }

        /*
         * for other expressions without getType() method, resolve binding and
         * get type.
         */
        if (isNull(type)) {
            ITypeBinding typeBinding = exp.resolveTypeBinding();
            if (isNull(typeBinding)) {
                if (nodes.is(exp, NumberLiteral.class)) {
                    typeBinding = exp.getAST().resolveWellKnownType("int");
                } else if (nodes.is(exp, BooleanLiteral.class)) {
                    typeBinding = exp.getAST().resolveWellKnownType("boolean");
                }
            }
            if (nonNull(typeBinding)) {
                type = getType(typeBinding, exp.getAST());
            } else {
                throw new ResolveException(
                        nodes.exMessage("unable to resolve", exp));
            }
        }

        return Optional.ofNullable(type);

    }

    public Type getType(final ITypeBinding typeBinding, final AST ast) {
        checkNotNull(ast);
        checkNotNull(typeBinding);

        if (typeBinding.isPrimitive()) {
            return ast.newPrimitiveType(
                    PrimitiveType.toCode(typeBinding.getName()));
        }

        /**
         * <code> class Foo<T extends A & B> {
         * private Class<? extends T> clz;
         * T createInstance(final Object bean) {
         *      return clz.cast(bean);
         * </code>
         *
         * The TypeBinding for clz.cast() is capture#1-of ? extends T. We get
         * bound of the capture (T extends A & B) and from it bounds (A and B)
         * and return the first type (A) as type.
         */
        if (typeBinding.isCapture()) {
            ITypeBinding wildCard = typeBinding.getWildcard();
            WildcardType capType = ast.newWildcardType();
            ITypeBinding bound = wildCard.getBound();
            if (bound != null) {
                ITypeBinding[] bounds = bound.getTypeBounds();
                if (bounds.length > 0) {
                    return ast.newSimpleType(ast.newName(bounds[0].getName()));
                } else {
                    capType.setBound(getType(bound, ast),
                            wildCard.isUpperbound());
                }
            }
            return capType;

        }

        if (typeBinding.isArray()) {
            Type elType = getType(typeBinding.getElementType(), ast);
            return ast.newArrayType(elType, typeBinding.getDimensions());
        }

        if (typeBinding.isParameterizedType()) {
            ParameterizedType type = ast.newParameterizedType(
                    getType(typeBinding.getErasure(), ast));

            @SuppressWarnings("unchecked")
            List<Type> newTypeArgs = type.typeArguments();
            for (ITypeBinding typeArg : typeBinding.getTypeArguments()) {
                newTypeArgs.add(getType(typeArg, ast));
            }
            return type;
        }
        // Ex: Predicate<? super String> - ? super String is wildcard
        if (typeBinding.isWildcardType()) {
            WildcardType wildcardType = ast.newWildcardType();
            ITypeBinding bound = typeBinding.getBound();
            if (nonNull(bound) && bound.isCapture()) {
                /*
                 * For type binding with capture such as <? super capture#1-of
                 * ?> return wildcard type as <? super Object>. Ref itest:
                 * generic.WildcardCapture.
                 */
                ITypeBinding era = bound.getErasure();
                wildcardType.setBound(getType(era, ast),
                        typeBinding.isUpperbound());
            } else if (nonNull(bound)) {
                wildcardType.setBound(getType(bound, ast),
                        typeBinding.isUpperbound());
            }
            return wildcardType;
        }
        // simple type, unqualified name
        String qualName = typeBinding.getName();
        if ("".equals(qualName)) {
            throw new IllegalArgumentException("No name for type binding.");
        }
        return ast.newSimpleType(ast.newName(qualName));
    }

    public List<Type> getExceptionTypes(final Type type) {
        List<Type> types = new ArrayList<>();
        if (type.getClass().isAssignableFrom(SimpleType.class)) {
            types.add(type);
        }
        if (type.getClass().isAssignableFrom(UnionType.class)) {
            @SuppressWarnings("unchecked")
            List<Type> unionTypes = ((UnionType) type).types();
            types.addAll(unionTypes);
        }
        return types;
    }

    /**
     * Get FQ Class name from type. If type is resolvable, get clz name from
     * binding else try to get it from well known types. Returns null if not
     * able to find clz name.
     * @param var
     * @return clz name or null.
     */
    public String getClzName(final Type type) {
        String clzName = null;
        ITypeBinding typeBinding = type.resolveBinding();
        if (nonNull(typeBinding)) {
            clzName = typeBinding.getBinaryName();
        } else {
            Optional<String> kName = configs.getKnownTypeFqn(getTypeName(type));
            if (kName.isPresent()) {
                clzName = kName.get();
            }
        }
        return clzName;
    }

    /**
     * Return type for Class Name (Fully Qualified) such as java.lang.String,
     * java.lang.Object
     * @param ast
     * @return
     */
    public Type getType(final String clzName, final AST ast) {
        return ast.newSimpleType(ast.newName(clzName));
    }

    /**
     * Return type of the node. For regular types use node.getType() and special
     * cases call this method.
     *
     * @param node
     * @return
     */
    public Type getType(final ASTNode node) {
        if (nodes.is(node, SingleVariableDeclaration.class)) {
            SingleVariableDeclaration svd =
                    nodes.as(node, SingleVariableDeclaration.class);
            // REVIEW Z - handle all types of var args and write tests
            if (svd.isVarargs()) {
                AST ast = svd.getAST();
                if (svd.getType().isPrimitiveType()) {
                    PrimitiveType pt = (PrimitiveType) svd.getType();
                    return ast.newArrayType(
                            ast.newPrimitiveType(pt.getPrimitiveTypeCode()));
                } else if (svd.getType().isArrayType()) {
                    ArrayType at = (ArrayType) svd.getType();
                    Type et = at.getElementType();
                    int dims = at.getDimensions();
                    Type t = null;
                    if (et.isPrimitiveType()) {
                        t = ast.newPrimitiveType(
                                ((PrimitiveType) et).getPrimitiveTypeCode());
                    } else {
                        t = ast.newSimpleType(ast.newName(getTypeName(et)));
                    }
                    ArrayType type = ast.newArrayType(t, dims);
                    return type;
                } else {
                    String clzName = getTypeName(svd.getType());
                    return ast.newArrayType(
                            ast.newSimpleType(ast.newName(clzName)));
                }
            }
            return svd.getType();
        }
        throw new CodeException(nodes.noImplmentationMessage(node));
    }

    /**
     * Whether type is capable of returning any value. The void type can't
     * return value.
     *
     * @param type
     * @return
     */
    public boolean capableToReturnValue(final Type type) {
        boolean returnsValue = true;
        if (type.isPrimitiveType() && ((PrimitiveType) type)
                .getPrimitiveTypeCode().equals(PrimitiveType.VOID)) {
            returnsValue = false;
        }
        return returnsValue;
    }

    /**
     * The effective type of a node. If node's parent is CastExpression then
     * cast type is returned else the input type is returned.
     *
     * @param node
     * @param type
     * @return
     */
    public Type getEffectiveType(final ASTNode node, final Type type) {
        Type effectiveType = type;
        if (nodes.is(node.getParent(), CastExpression.class)) {
            CastExpression ce =
                    nodes.as(node.getParent(), CastExpression.class);
            effectiveType = ce.getType();
        }
        return effectiveType;
    }

    /**
     * Is type is java.lang.Object.
     *
     * @param type
     * @return
     */
    public boolean isObject(final Type type) {
        return getTypeName(type).equals("Object");
    }
}
