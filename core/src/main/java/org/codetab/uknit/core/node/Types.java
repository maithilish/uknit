package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.TypeNameException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.WildcardType;

public class Types {

    @Inject
    private Nodes nodes;

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
            if (nodes.is(name, QualifiedName.class)) {
                name = nodes.as(name, QualifiedName.class).getName();
            }
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
            throw new TypeNameException(
                    spaceit(type.getClass().getName(), "has no name"));
        }
    }

    public boolean isBoolean(final Type retType) {
        return retType.isPrimitiveType()
                && getTypeName(retType).equals("boolean");
    }

    public Type getType(final ITypeBinding typeBinding, final AST ast) {
        checkNotNull(ast);
        checkNotNull(typeBinding);

        if (typeBinding.isPrimitive()) {
            return ast.newPrimitiveType(
                    PrimitiveType.toCode(typeBinding.getName()));
        }

        if (typeBinding.isCapture()) {
            ITypeBinding wildCard = typeBinding.getWildcard();
            WildcardType capType = ast.newWildcardType();
            ITypeBinding bound = wildCard.getBound();
            if (bound != null) {
                capType.setBound(getType(bound, ast), wildCard.isUpperbound());
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
        // in Predicate<? super String> - ? super String is wildcard
        if (typeBinding.isWildcardType()) {
            WildcardType wildcardType = ast.newWildcardType();
            ITypeBinding bound = typeBinding.getBound();
            if (bound != null) {
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

}
