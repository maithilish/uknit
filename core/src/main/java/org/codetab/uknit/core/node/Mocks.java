package org.codetab.uknit.core.node;

import static java.util.Objects.nonNull;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.TypeNameException;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

public class Mocks {

    @Inject
    private Types types;
    @Inject
    private Resolver resolver;
    @Inject
    private Configs configs;

    /**
     * Mockable - class without uknit.createInstance config.
     * <p>
     * UnMockables - primitives, array or unable to get class name.
     *
     * @param type
     * @return
     */
    public boolean isMockable(final Type type) {
        if (type.isPrimitiveType() || type.isArrayType()) {
            return false;
        }
        try {
            String typeName = types.getTypeName(type);
            if (types.isUnmodifiable(typeName)) {
                return false;
            }
            String key = String.join(".", "uknit.createInstance", typeName);
            String instanceType = configs.getConfig(key);
            if (nonNull(instanceType)
                    && instanceType.equalsIgnoreCase("mock")) {
                return true;
            }
            if (nonNull(instanceType)) {
                return false;
            }
            ITypeBinding typeBind = resolver.resolveBinding(type);
            if (nonNull(typeBind) && typeBind.isEnum()) {
                return false;
            }
        } catch (TypeNameException e) {
            return false;
        }
        return true;
    }

    /**
     * Whether to stage infer var.
     * @param varIsMock
     *            - var whose method is invoked.
     * @param returnIsMock
     *            - object returned by method.
     * @return
     */
    public boolean isInferVarStageable(final boolean varIsMock,
            final boolean returnIsMock) {
        boolean varIsReal = !varIsMock;
        boolean returnIsReal = !returnIsMock;
        // Mock returns Mock
        if (varIsMock && returnIsMock) {
            return true;
        }
        // Mock returns Real
        if (varIsMock && returnIsReal) {
            return true;
        }
        // Real returns Mock
        if (varIsReal && returnIsMock) {
            return false;
        }
        // Real returns Real
        if (varIsReal && returnIsReal) {
            return false;
        }
        return false;
    }

}
