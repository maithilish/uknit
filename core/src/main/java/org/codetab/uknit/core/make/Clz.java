package org.codetab.uknit.core.make;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.zap.make.model.Field;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Holds class under test and test class outline such as package, imports,
 * typeDecl and fields.
 * @author Maithilish
 *
 */
public class Clz {

    private String testClzName;
    private TypeDeclaration typeDecl; // class under test - CUT
    private TypeDeclaration testTypeDecl; // test class
    private PackageDeclaration packageDecl;
    private List<ImportDeclaration> imports;
    private List<Field> fields = new ArrayList<>();

    public String getTestClzName() {
        return testClzName;
    }

    public void setTestClzName(final String name) {
        this.testClzName = name;
    }

    public TypeDeclaration getTypeDecl() {
        return typeDecl;
    }

    public void setTypeDecl(final TypeDeclaration typeDecl) {
        this.typeDecl = typeDecl;
    }

    public TypeDeclaration getTestTypeDecl() {
        return testTypeDecl;
    }

    public void setTestTypeDecl(final TypeDeclaration testTypeDecl) {
        this.testTypeDecl = testTypeDecl;
    }

    public PackageDeclaration getPackageDecl() {
        return packageDecl;
    }

    public void setPackageDecl(final PackageDeclaration packageDecl) {
        this.packageDecl = packageDecl;
    }

    public List<ImportDeclaration> getImports() {
        return imports;
    }

    public void setImports(final List<ImportDeclaration> imports) {
        this.imports = imports;
    }

    public List<Field> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Clz [name=" + testClzName + "]";
    }
}
