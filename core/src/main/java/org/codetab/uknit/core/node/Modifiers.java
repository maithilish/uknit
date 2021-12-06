package org.codetab.uknit.core.node;

import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Modifier;

public class Modifiers {

    @SuppressWarnings("unchecked")
    public List<IExtendedModifier> getModifiers(
            final BodyDeclaration bodyDecl) {
        return bodyDecl.modifiers();
    }

    public boolean isPublic(final List<IExtendedModifier> modifiers) {
        return modifiers.stream().filter(IExtendedModifier::isModifier)
                .anyMatch(m -> {
                    return ((Modifier) m).isPublic();
                });
    }

    public boolean isStatic(final List<IExtendedModifier> modifiers) {
        return modifiers.stream().filter(IExtendedModifier::isModifier)
                .anyMatch(m -> {
                    return ((Modifier) m).isStatic();
                });
    }
}
