package org.codetab.uknit.core.node;

import javax.inject.Singleton;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

@Singleton
public class ClzNodeFactory {

    private AST ast;

    public void setAst(final AST ast) {
        this.ast = ast;
    }

    public PackageDeclaration createPackageDecl(final ASTNode node) {
        PackageDeclaration decl = ast.newPackageDeclaration();
        Name name = ast.newName(
                ((PackageDeclaration) node).getName().getFullyQualifiedName());
        decl.setName(name);
        return decl;
    }

    public ImportDeclaration createImportDecl(final ASTNode node) {
        ImportDeclaration decl = ast.newImportDeclaration();
        Name name = ast.newName(
                ((ImportDeclaration) node).getName().getFullyQualifiedName());
        decl.setName(name);
        return decl;
    }

    public TypeDeclaration createTypeDecl(final AbstractTypeDeclaration node) {
        TypeDeclaration decl = ast.newTypeDeclaration();
        String testClzName =
                String.join("", node.getName().getFullyQualifiedName(), "Test");
        decl.setName(ast.newSimpleName(testClzName));
        return decl;
    }

    public FieldDeclaration createFieldDecl(final Type type,
            final String fieldName) {
        VariableDeclarationFragment vd = ast.newVariableDeclarationFragment();
        vd.setName(ast.newSimpleName(fieldName));
        Type typeCopy = (Type) ASTNode.copySubtree(ast, type);
        FieldDeclaration fieldDecl = ast.newFieldDeclaration(vd);
        fieldDecl.setType(typeCopy);
        return fieldDecl;
    }

    public FieldDeclaration createFieldDecl(
            final VariableDeclarationFragment vd) {
        VariableDeclarationFragment vdCopy =
                (VariableDeclarationFragment) ASTNode.copySubtree(ast, vd);
        return ast.newFieldDeclaration(vdCopy);
    }

    public FieldDeclaration createFieldDecl(final String clzName,
            final String fieldName) {
        VariableDeclarationFragment vd = ast.newVariableDeclarationFragment();
        vd.setName(ast.newSimpleName(fieldName));
        FieldDeclaration fieldDecl = ast.newFieldDeclaration(vd);

        Type type = ast.newSimpleType(ast.newSimpleName(clzName));
        fieldDecl.setType(type);

        return fieldDecl;
    }

}
