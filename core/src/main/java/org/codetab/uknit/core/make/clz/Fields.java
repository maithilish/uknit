package org.codetab.uknit.core.make.clz;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.ClzNodeFactory;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Modifiers;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Fields {

    @Inject
    private ModelFactory modelFactory;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private ClzNodeFactory clzNodeFactory;
    @Inject
    private Types types;
    @Inject
    private Modifiers modifiers;
    @Inject
    private Nodes nodes;
    @Inject
    private Mocks mocks;

    public List<Field> createFields(final List<FieldDeclaration> fieldDecls) {
        List<Field> fields = new ArrayList<>();
        for (FieldDeclaration fieldDecl : fieldDecls) {
            VariableDeclarationFragment vdf =
                    (VariableDeclarationFragment) fieldDecl.fragments().get(0);
            String name = nodes.getVariableName(vdf);
            Type type = fieldDecl.getType();
            boolean mock = mocks.isMockable(type);

            boolean hide = false;
            if (modifiers.isStatic(modifiers.getModifiers(fieldDecl))) {
                hide = true;
            }
            if (nodes.isPrimitiveType(fieldDecl)) {
                hide = true;
            }
            String typeName = types.getTypeName(fieldDecl.getType());
            if (types.isUnmodifiable(typeName)) {
                hide = true;
            }

            Field field = modelFactory.createField(name, type, mock, fieldDecl);
            field.setHidden(hide);
            fields.add(field);
        }
        return fields;
    }

    public Field createField(final FieldDeclaration fieldDecl) {
        VariableDeclarationFragment vdf =
                (VariableDeclarationFragment) fieldDecl.fragments().get(0);
        String name = nodes.getVariableName(vdf);
        Type type = fieldDecl.getType();
        boolean mock = mocks.isMockable(type);

        boolean hide = false;
        if (modifiers.isStatic(modifiers.getModifiers(fieldDecl))) {
            hide = true;
        }
        if (nodes.isPrimitiveType(fieldDecl)) {
            hide = true;
        }
        String typeName = types.getTypeName(fieldDecl.getType());
        if (types.isUnmodifiable(typeName)) {
            hide = true;
        }

        Field field = modelFactory.createField(name, type, mock, fieldDecl);
        field.setHidden(hide);
        return field;
    }

    /**
     * Static fields are not mocked.
     * @param fieldDecls
     */
    public void removeStaticFields(final List<FieldDeclaration> fieldDecls) {
        fieldDecls.removeIf(fieldDecl -> {
            return modifiers.isStatic(modifiers.getModifiers(fieldDecl));
        });
    }

    /**
     * Primitives are unmockable.
     * @param fieldDecls
     */
    public void removePrimitiveFields(final List<FieldDeclaration> fieldDecls) {
        fieldDecls.removeIf(fieldDecl -> {
            return nodes.isPrimitiveType(fieldDecl);
        });
    }

    /**
     * Remove unmockable types such as String.
     * @param fieldDecls
     */
    public void removeUnmodifiableFields(
            final List<FieldDeclaration> fieldDecls) {
        fieldDecls.removeIf(fieldDecl -> {
            String typeName = types.getTypeName(fieldDecl.getType());
            return types.isUnmodifiable(typeName);
        });
    }

    /**
     * Remove fields that are instantiated.
     * @param fieldDecls
     */
    public void removeInstantiatedFields(
            final List<FieldDeclaration> fieldDecls) {
        List<FieldDeclaration> removeFieldDecls = new ArrayList<>();
        for (FieldDeclaration fieldDecl : fieldDecls) {
            @SuppressWarnings("unchecked")
            List<VariableDeclarationFragment> fragments = fieldDecl.fragments();
            for (VariableDeclarationFragment vd : fragments) {
                Expression ie = vd.getInitializer();
                if (nonNull(ie)) {
                    removeFieldDecls.add(fieldDecl);
                }
            }
        }
        fieldDecls.removeIf(f -> removeFieldDecls.contains(f));
    }

    /**
     * Expand multiple fields declared in a declaration.
     */
    public void expandFieldFragments(final List<FieldDeclaration> fieldDecls) {

        List<FieldDeclaration> newFieldDecls = new ArrayList<>();
        List<FieldDeclaration> removeFieldDecls = new ArrayList<>();

        for (FieldDeclaration fieldDecl : fieldDecls) {
            @SuppressWarnings("unchecked")
            List<VariableDeclarationFragment> fragments = fieldDecl.fragments();
            if (fragments.size() > 1) {
                // create new field for each fragment
                for (VariableDeclarationFragment vdf : fragments) {
                    FieldDeclaration newFieldDecl =
                            clzNodeFactory.createFieldDecl(vdf);
                    Type type = nodeFactory.copyNode(fieldDecl.getType());
                    newFieldDecl.setType(type);
                    newFieldDecls.add(newFieldDecl);
                }
                removeFieldDecls.add(fieldDecl);
            }
        }
        fieldDecls.removeIf(f -> removeFieldDecls.contains(f));
        fieldDecls.addAll(newFieldDecls);
    }
}
