package org.codetab.uknit.core.node;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.zap.make.model.Field;
import org.codetab.uknit.core.zap.make.model.IVar;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

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

    public String getVariableName(final VariableDeclaration vd) {
        return vd.getName().getFullyQualifiedName();
    }

    public List<VariableDeclaration> getFragments(
            final VariableDeclarationExpression vde) {
        @SuppressWarnings("unchecked")
        List<VariableDeclaration> list = vde.fragments();
        return list;
    }

    public List<VariableDeclaration> getFragments(
            final VariableDeclarationStatement vds) {
        @SuppressWarnings("unchecked")
        List<VariableDeclaration> list = vds.fragments();
        return list;
    }
}
