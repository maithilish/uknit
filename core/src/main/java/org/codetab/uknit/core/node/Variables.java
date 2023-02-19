package org.codetab.uknit.core.node;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.make.model.IVar;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
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

    public boolean isPrimitiveType(final FieldDeclaration fieldDecl) {
        return fieldDecl.getType().isPrimitiveType();
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

    /**
     * Get initializer from field decl that contains single vdf. Ex: String
     * foo="x";. Throws error for multi vdf such as String foo, bar="x".
     *
     * @param fieldDecl
     * @return initializer if exits else null
     */
    public Expression getInitializer(final FieldDeclaration fieldDecl) {
        @SuppressWarnings("unchecked")
        List<VariableDeclarationFragment> vdfs = fieldDecl.fragments();
        checkState(vdfs.size() == 1);
        VariableDeclarationFragment vdf = vdfs.get(0);
        return vdf.getInitializer();
    }

    public void addModifier(final FieldDeclaration fieldDecl,
            final ModifierKeyword modifierKeyword) {
        Modifier modifier = fieldDecl.getAST().newModifier(modifierKeyword);
        @SuppressWarnings("unchecked")
        List<Modifier> modifierList = fieldDecl.modifiers();
        modifierList.add(modifier);
    }
}
