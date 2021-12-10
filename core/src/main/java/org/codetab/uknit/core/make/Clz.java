package org.codetab.uknit.core.make;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.core.make.model.Field;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Holds test class outline such as package, imports, typeDecl and fields.
 * @author Maithilish
 *
 */
public class Clz {

    private String name;
    private TypeDeclaration typeDecl;
    private PackageDeclaration packageDecl;
    private List<ImportDeclaration> imports;
    private List<Field> fields = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public TypeDeclaration getTypeDecl() {
        return typeDecl;
    }

    public void setTypeDecl(final TypeDeclaration typeDecl) {
        this.typeDecl = typeDecl;
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
        return "Clz [name=" + name + "]";
    }
}
