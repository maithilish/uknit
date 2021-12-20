package org.codetab.uknit.core.make.clz;

import java.util.List;

import org.codetab.uknit.core.make.model.Field;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.google.common.collect.Lists;

public class ClzMakers {

    public void addImport(final CompilationUnit cu,
            final ImportDeclaration importDecl) {
        @SuppressWarnings("unchecked")
        List<ImportDeclaration> imports = cu.imports();
        imports.add(importDecl);
    }

    public void addModifier(final TypeDeclaration typeDecl,
            final Modifier modifier) {
        @SuppressWarnings("unchecked")
        List<Modifier> modifiers = typeDecl.modifiers();
        modifiers.add(modifier);
    }

    public void addModifier(final FieldDeclaration fieldDecl,
            final Modifier modifier) {
        @SuppressWarnings("unchecked")
        List<Modifier> modifiers = fieldDecl.modifiers();
        modifiers.add(modifier);
    }

    public void addAnnotation(final FieldDeclaration fieldDecl,
            final Annotation annotation) {
        @SuppressWarnings("unchecked")
        List<IExtendedModifier> modifiers = fieldDecl.modifiers();
        modifiers.add(0, annotation);
    }

    public void addType(final CompilationUnit cu,
            final TypeDeclaration typeDecl) {
        @SuppressWarnings("unchecked")
        List<TypeDeclaration> types = cu.types();
        types.add(typeDecl);
    }

    @SuppressWarnings("unchecked")
    public List<ImportDeclaration> getImports(final CompilationUnit cu) {
        return cu.imports();
    }

    public void addFieldDecl(final TypeDeclaration clzDecl,
            final FieldDeclaration fieldDecl) {
        @SuppressWarnings("unchecked")
        List<BodyDeclaration> body = clzDecl.bodyDeclarations();
        body.add(fieldDecl);
    }

    @SuppressWarnings("unchecked")
    public List<VariableDeclarationFragment> getFragments(
            final FieldDeclaration field) {
        return field.fragments();
    }

    public List<Field> getFields(final TypeDeclaration clz,
            final Fields fields) {
        List<FieldDeclaration> fieldDecls = Lists.newArrayList(clz.getFields());

        // remove unmockable types
        // fields.removeStaticFields(fieldDecls);
        // fields.removePrimitiveFields(fieldDecls);
        // fields.removeUnmodifiableFields(fieldDecls);

        // expand multi fields declaration
        fields.expandFieldFragments(fieldDecls);
        fields.removeInstantiatedFields(fieldDecls);

        return fields.createFields(fieldDecls);
    }
}
