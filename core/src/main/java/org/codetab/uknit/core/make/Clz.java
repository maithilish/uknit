package org.codetab.uknit.core.make;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codetab.uknit.core.make.model.Field;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
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
    private AbstractTypeDeclaration typeDecl; // CUT - class under test
    private TypeDeclaration testTypeDecl; // test class
    private PackageDeclaration packageDecl;
    private List<ImportDeclaration> imports;

    /*
     * Fields defined in source. The method, IM and super method gets a copy of
     * this. In parsing phase, fields are added to definedFields and no further
     * changes are made to it.
     */
    private List<Field> definedFields = new ArrayList<>();
    /*
     * Working copy of defined fields. It working copy for a class. Once method,
     * IM and super method is processed, they update this and generation phase
     * uses it to output.
     */
    private List<Field> fields;

    public String getTestClzName() {
        return testClzName;
    }

    public void setTestClzName(final String name) {
        this.testClzName = name;
    }

    public AbstractTypeDeclaration getTypeDecl() {
        return typeDecl;
    }

    public void setTypeDecl(final AbstractTypeDeclaration typeDecl) {
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

    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    public void addDefinedField(final Field field) {
        definedFields.add(field);
    }

    public List<Field> getDefinedFields() {
        return Collections.unmodifiableList(definedFields);
    }

    @Override
    public String toString() {
        return "Clz [name=" + testClzName + "]";
    }

}
