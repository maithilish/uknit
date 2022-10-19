package org.codetab.uknit.core.node;

import javax.inject.Inject;

import org.codetab.uknit.core.zap.make.model.Field;
import org.codetab.uknit.core.zap.make.model.IVar;
import org.eclipse.jdt.core.dom.FieldDeclaration;

public class Variables {

    @Inject
    private Modifiers modifiers;

    public boolean isStatic(final IVar var) {
        if (var.isField()) {
            FieldDeclaration srcFieldDecl = ((Field) var).getSrcFieldDecl();
            return modifiers.isStatic(modifiers.getModifiers(srcFieldDecl));
        } else {
            return false;
        }
    }
}
