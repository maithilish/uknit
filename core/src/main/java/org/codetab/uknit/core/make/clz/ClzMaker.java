package org.codetab.uknit.core.make.clz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.ClzMap;
import org.codetab.uknit.core.make.model.Cu;
import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.node.Classes;
import org.codetab.uknit.core.node.ClzNodeFactory;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
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
    @Inject
    private FieldMaker fieldMaker;
    @Inject
    private Nodes nodes;
    @Inject
    private Configs configs;

    // test class cu
    private CompilationUnit cu;

    // class under test - CUT
    private String cutName;

    public void addPackage(final ASTNode packageDecl) {
        cu.setPackage(clzNodeFactory.createPackageDecl(packageDecl));
    }

    public void addImport(final ImportDeclaration importDecl) {
        ImportDeclaration decl = clzNodeFactory.createImportDecl(importDecl);
        clzMakers.addImport(cu, decl);
    }

    /**
     * Create test class and add it to clzMap.
     * @param typeDecl
     */
    public void addClass(final TypeDeclaration typeDecl) {
        TypeDeclaration testTypeDecl = clzNodeFactory.createTypeDecl(typeDecl);
        clzMakers.addType(cu, testTypeDecl);

        String clzModifier =
                configs.getConfig("uknit.test.class.modifier", "default");

        if (clzModifier.equalsIgnoreCase("public")) {
            Modifier modifier =
                    nodeFactory.createModifier(ModifierKeyword.PUBLIC_KEYWORD);
            clzMakers.addModifier(testTypeDecl, modifier);
        }

        Clz clz = di.instance(Clz.class);

        String clzName = testTypeDecl.getName().getFullyQualifiedName();
        clz.setTestClzName(clzName);
        clz.setTypeDecl(typeDecl);
        clz.setTestTypeDecl(testTypeDecl);
        clz.setPackageDecl(cu.getPackage());
        clz.setImports(clzMakers.getImports(cu));

        clzMap.put(clzName, clz);
    }

    /**
     * Add self field (InjectMocks) - class under test
     * @return
     */
    public void addSelfField(final TypeDeclaration srcClz) {
        String srcClzName = classes.getClzName(srcClz);
        String testClzName = classes.getTestClzName(srcClz);
        TypeDeclaration testClz = clzMap.getTypeDecl(testClzName);

        String fieldName = classes.getClzAsFieldName(srcClz);

        fieldMaker.addSelfField(srcClzName, testClz, fieldName);
        cutName = fieldName;
    }

    public void addFields(final TypeDeclaration srcClz) {
        String testClzName = classes.getTestClzName(srcClz);
        TypeDeclaration testClz = clzMap.getTypeDecl(testClzName);

        List<Field> srcFields = fieldMaker.getSrcFields(srcClz);
        List<Field> testFields = fieldMaker.addFieldDeclsToTestClz(srcFields,
                srcClz, testClzName, testClz);
        fieldMaker.addFieldsToClzMap(testFields, testClzName, clzMap);
    }

    public void addSuperFields(final TypeDeclaration srcClz,
            final List<AbstractTypeDeclaration> superTypes) {

        String testClzName = classes.getTestClzName(srcClz);
        TypeDeclaration testClz = clzMap.getTypeDecl(testClzName);

        for (AbstractTypeDeclaration asuperType : superTypes) {
            if (nodes.is(asuperType, TypeDeclaration.class)) {
                TypeDeclaration superType =
                        nodes.as(asuperType, TypeDeclaration.class);
                List<Field> srcFields = fieldMaker.getSrcFields(superType);
                List<Field> testFields = fieldMaker.addFieldDeclsToTestClz(
                        srcFields, srcClz, testClzName, testClz);
                fieldMaker.addFieldsToClzMap(testFields, testClzName, clzMap);
            }
        }
    }

    public List<AbstractTypeDeclaration> getSuperTypeDeclarations(
            final List<Entry<String, String>> superClassNames,
            final List<Cu> cuList) {
        List<AbstractTypeDeclaration> superTypeDecls = new ArrayList<>();
        for (Entry<String, String> entry : superClassNames) {
            String pkg = entry.getKey();
            String clz = entry.getValue();
            Optional<Cu> ocu =
                    cuList.stream().filter(c -> c.getPkg().equals(pkg)
                            && c.getClzNames().contains(clz)).findAny();
            if (ocu.isPresent()) {
                CompilationUnit scu = ocu.get().getCu();
                @SuppressWarnings("unchecked")
                List<AbstractTypeDeclaration> stypes = scu.types();
                Optional<AbstractTypeDeclaration> sType =
                        stypes.stream().filter(t -> t.getName()
                                .getFullyQualifiedName().equals(clz)).findAny();
                if (sType.isPresent()) {
                    superTypeDecls.add(sType.get());
                }
            }
        }
        return superTypeDecls;
    }

    public void annotateFields() {
        String[] deepStubAnnotation =
                configs.getConfig("uknit.annotation.chainCall").split("=");
        for (String clzName : clzMap.keySet()) {
            Clz clz = clzMap.get(clzName);
            fieldMaker.annotateFields(clz, deepStubAnnotation);
        }
    }

    /**
     * Remove unnecessary fields that are not injected, as they are to be
     * initialized by class under test.
     * @param configs
     */
    public void removeFields() {
        for (String clzName : clzMap.keySet()) {
            Clz clz = clzMap.get(clzName);
            fieldMaker.removeFields(clz);
        }
    }

    public ClzMap getClzMap() {
        return clzMap;
    }

    public void setCu(final CompilationUnit cu) {
        this.cu = cu;
    }

    /**
     * Get name of class under test.
     *
     * @return
     */
    public String getCutName() {
        return cutName;
    }
}
