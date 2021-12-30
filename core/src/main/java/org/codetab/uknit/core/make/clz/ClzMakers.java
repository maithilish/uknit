package org.codetab.uknit.core.make.clz;

import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

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

}
