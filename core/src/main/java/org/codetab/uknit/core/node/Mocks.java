package org.codetab.uknit.core.node;

import static java.util.Objects.nonNull;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.TypeNameException;
import org.eclipse.jdt.core.dom.Type;

public class Mocks {

    @Inject
    private Types types;
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
            if (nonNull(instanceType)) {
                return false;
            }
        } catch (TypeNameException e) {
            return false;
        }
        return true;
    }

    public boolean isInferVarStageable(final boolean varIsMock,
            final boolean returnIsMock) {
        // Mock returns Mock
        if (varIsMock && returnIsMock) {
            return true;
        }
        // Mock returns Real
        if (varIsMock && !returnIsMock) {
            return true;
        }
        // Real returns Mock
        if (!varIsMock && returnIsMock) {
            return true;
        }
        // Real returns Real
        if (!varIsMock && !returnIsMock) {
            return true;
        }
        return false;
    }

}
