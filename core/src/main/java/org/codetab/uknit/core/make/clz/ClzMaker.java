package org.codetab.uknit.core.make.clz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.ClzMap;
import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.node.Classes;
import org.codetab.uknit.core.node.ClzNodeFactory;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClzMaker {

    @Inject
    private ClzMap clzMap;
    @Inject
    private DInjector di;
    @Inject
    private ClzMakers clzMakers;
    @Inject
    private ClzNodeFactory clzNodeFactory;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Classes classes;

    // test class cu
    private CompilationUnit cu;

    public void addPackage(final ASTNode packageDecl) {
        cu.setPackage(clzNodeFactory.createPackageDecl(packageDecl));
    }

    public void addImport(final ImportDeclaration importDecl) {
        ImportDeclaration decl = clzNodeFactory.createImportDecl(importDecl);
        clzMakers.addImport(cu, decl);
    }

    public void addClass(final TypeDeclaration node) {
        TypeDeclaration typeDecl = clzNodeFactory.createTypeDecl(node);
        clzMakers.addType(cu, typeDecl);

        Modifier modifier =
                nodeFactory.createModifier(ModifierKeyword.PUBLIC_KEYWORD);
        clzMakers.addModifier(typeDecl, modifier);

        Clz clz = di.instance(Clz.class);

        String clzName = typeDecl.getName().getFullyQualifiedName();
        clz.setName(clzName);
        clz.setTypeDecl(typeDecl);
        clz.setPackageDecl(cu.getPackage());
        clz.setImports(clzMakers.getImports(cu));

        clzMap.put(clzName, clz);
    }

    /**
     * Add self field (InjectMocks)
     */
    public void addSelfField(final TypeDeclaration node) {
        TypeDeclaration clzUnderTest = classes.asTypeDecl(node);
        String clzUnderTestName = classes.getClzName(clzUnderTest);
        String clzName = classes.getTestClzName(clzUnderTest);
        TypeDeclaration clzDecl = clzMap.getTypeDecl(clzName);

        String fieldName = classes.getClzAsFieldName(clzUnderTest);
        Modifier modifier =
                nodeFactory.createModifier(ModifierKeyword.PRIVATE_KEYWORD);
        Annotation annotation =
                nodeFactory.createMarkerAnnotation("InjectMocks");

        FieldDeclaration fieldDecl =
                clzNodeFactory.createFieldDecl(clzUnderTestName, fieldName);
        clzMakers.addAnnotation(fieldDecl, annotation);
        clzMakers.addModifier(fieldDecl, modifier);
        clzMakers.addFieldDecl(clzDecl, fieldDecl);
    }

    public void addField(final TypeDeclaration node) {
        TypeDeclaration clzUnderTest = classes.asTypeDecl(node);
        String clzName = classes.getTestClzName(clzUnderTest);
        TypeDeclaration clzDecl = clzMap.getTypeDecl(clzName);

        Fields fields = di.instance(Fields.class);
        List<Field> fieldList = clzMakers.getFields(clzUnderTest, fields);
        // create new fieldDecl and add to test class
        fieldList.forEach(clzUnderTestField -> {
            String fieldName = clzUnderTestField.getName();
            Type type = clzUnderTestField.getType();
            Modifier modifier =
                    nodeFactory.createModifier(ModifierKeyword.PRIVATE_KEYWORD);
            FieldDeclaration fieldDecl =
                    clzNodeFactory.createFieldDecl(type, fieldName);
            clzMakers.addModifier(fieldDecl, modifier);
            clzMakers.addFieldDecl(clzDecl, fieldDecl);

            Field field = fields.createFields(fieldDecl);
            clzMap.addField(clzName, field);
        });
    }

    public void annotateFields(final Configs configs) {

        String[] deepStubAnnotation =
                configs.getConfig("uknit.annotation.chainCall").split("=");

        for (String clzName : clzMap.keySet()) {
            Clz clz = clzMap.get(clzName);
            List<Field> fields = clz.getFields();
            for (Field field : fields) {
                FieldDeclaration fieldDecl = field.getFieldDecl();
                Annotation annotation = null;
                if (field.isDeepStub()) {
                    Map<SimpleName, Name> map = new HashMap<>();
                    SimpleName key = (SimpleName) nodeFactory
                            .createName(deepStubAnnotation[0]);
                    Name value = nodeFactory.createName(deepStubAnnotation[1]);
                    map.put(key, value);
                    annotation =
                            nodeFactory.createNormalAnnotation("Mock", map);
                } else {
                    annotation = nodeFactory.createMarkerAnnotation("Mock");
                }
                clzMakers.addAnnotation(fieldDecl, annotation);
            }
        }
    }

    public ClzMap getClzMap() {
        return clzMap;
    }

    public void setCu(final CompilationUnit cu) {
        this.cu = cu;
    }
}
