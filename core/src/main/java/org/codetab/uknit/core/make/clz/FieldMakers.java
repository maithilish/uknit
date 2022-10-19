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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class FieldMakers {

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

    public List<Field> createSrcFields(
            final List<FieldDeclaration> fieldDecls) {
        List<Field> fields = new ArrayList<>();
        for (FieldDeclaration fieldDecl : fieldDecls) {
            VariableDeclarationFragment vdf =
                    (VariableDeclarationFragment) fieldDecl.fragments().get(0);
            String name = nodes.getVariableName(vdf);
            Type type = fieldDecl.getType();
            boolean mock = mocks.isMockable(type);

            Field field = modelFactory.createField(name, type, mock, fieldDecl,
                    fieldDecl);

            field.setEnable(enable(fieldDecl, mock));
            field.setCreated(isCreated(fieldDecl));

            fields.add(field);
        }
        return fields;
    }

    public Field createTestField(final FieldDeclaration fieldDecl,
            final FieldDeclaration srcFieldDecl) {
        VariableDeclarationFragment vdf =
                (VariableDeclarationFragment) fieldDecl.fragments().get(0);
        String name = nodes.getVariableName(vdf);
        Type type = fieldDecl.getType();
        boolean mock = mocks.isMockable(type);
        Field field = modelFactory.createField(name, type, mock, fieldDecl,
                srcFieldDecl);

        field.setEnable(enable(fieldDecl, mock));
        field.setCreated(isCreated(fieldDecl));

        return field;
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

    public boolean enable(final FieldDeclaration fieldDecl,
            final boolean mock) {
        if (modifiers.isStatic(modifiers.getModifiers(fieldDecl))) {
            return false;
        }
        if (nodes.isPrimitiveType(fieldDecl)) {
            return false;
        }
        String typeName = types.getTypeName(fieldDecl.getType());
        if (types.isUnmodifiable(typeName)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        List<VariableDeclarationFragment> fragments = fieldDecl.fragments();
        for (VariableDeclarationFragment vd : fragments) {
            if (!mock && nonNull(vd.getInitializer())) {
                return false;
            }
        }
        return true;
    }

    public boolean isCreated(final FieldDeclaration fieldDecl) {
        @SuppressWarnings("unchecked")
        List<VariableDeclarationFragment> fragments = fieldDecl.fragments();
        for (VariableDeclarationFragment vd : fragments) {
            if (nonNull(vd.getInitializer())) {
                return true;
            }
        }
        return false;
    }

    public void addModifier(final FieldDeclaration fieldDecl,
            final Modifier modifier) {
        @SuppressWarnings("unchecked")
        List<Modifier> fieldModifiers = fieldDecl.modifiers();
        fieldModifiers.add(modifier);
    }

    public void addModifiers(final FieldDeclaration fieldDecl,
            final List<Modifier> modifierList) {
        @SuppressWarnings("unchecked")
        List<Modifier> fieldModifiers = fieldDecl.modifiers();
        fieldModifiers.addAll(modifierList);
    }

    public void addAnnotation(final FieldDeclaration fieldDecl,
            final Annotation annotation) {
        @SuppressWarnings("unchecked")
        List<IExtendedModifier> fieldModifiers = fieldDecl.modifiers();
        fieldModifiers.add(0, annotation);
    }

    public List<VariableDeclarationFragment> getFragments(
            final FieldDeclaration field) {
        @SuppressWarnings("unchecked")
        List<VariableDeclarationFragment> fragments = field.fragments();
        return fragments;
    }

    public void addFieldDecl(final TypeDeclaration clzDecl,
            final FieldDeclaration fieldDecl) {
        @SuppressWarnings("unchecked")
        List<BodyDeclaration> body = clzDecl.bodyDeclarations();
        body.add(fieldDecl);
    }

    public boolean fieldNotExists(final String fieldName,
            final List<BodyDeclaration> body) {
        for (BodyDeclaration bd : body) {
            if (nodes.is(bd, FieldDeclaration.class)) {
                List<VariableDeclarationFragment> fragments =
                        getFragments(nodes.as(bd, FieldDeclaration.class));
                for (VariableDeclarationFragment vdf : fragments) {
                    if (nodes.getName(vdf.getName()).equals(fieldName)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
