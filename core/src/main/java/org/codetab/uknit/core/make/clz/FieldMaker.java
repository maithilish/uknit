package org.codetab.uknit.core.make.clz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.ClzMap;
import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.node.ClzNodeFactory;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.google.common.collect.Lists;

public class FieldMaker {

    @Inject
    private FieldMakers fieldMakers;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private ClzNodeFactory clzNodeFactory;

    public List<Field> getSrcFields(final TypeDeclaration srcClz) {
        List<FieldDeclaration> fieldDecls =
                Lists.newArrayList(srcClz.getFields());
        fieldMakers.expandFieldFragments(fieldDecls);
        return fieldMakers.createSrcFields(fieldDecls);
    }

    public List<Field> addFieldDeclsToTestClz(final List<Field> fieldList,
            final TypeDeclaration srcClz, final String testClzName,
            final TypeDeclaration testClz) {

        List<Field> testFields = new ArrayList<>();

        for (Field srcField : fieldList) {
            String fieldName = srcField.getName();
            Type type = srcField.getType();
            Modifier modifier =
                    nodeFactory.createModifier(ModifierKeyword.PRIVATE_KEYWORD);

            @SuppressWarnings("unchecked")
            List<BodyDeclaration> body = testClz.bodyDeclarations();
            if (fieldMakers.fieldNotExists(fieldName, body)) {
                // new fieldDecl for clzDecl (test class)
                FieldDeclaration fieldDecl =
                        clzNodeFactory.createFieldDecl(type, fieldName);

                @SuppressWarnings("unchecked")
                List<Modifier> modifiers = fieldDecl.modifiers();
                modifiers.add(modifier);

                // if enabled, add field to clzDecl tree (test class)
                if (srcField.isEnable()) {
                    body.add(fieldDecl);
                }

                Field testField = fieldMakers.createTestField(fieldDecl,
                        srcField.getFieldDecl());
                testField.setEnable(srcField.isEnable());
                testFields.add(testField);
            }
        }
        return testFields;
    }

    public void addFieldsToClzMap(final List<Field> fields,
            final String testClzName, final ClzMap clzMap) {
        for (Field field : fields) {
            clzMap.addField(testClzName, field);
        }
    }

    public void annotateFields(final Clz clz,
            final String[] deepStubAnnotation) {
        for (Field field : clz.getFields()) {
            FieldDeclaration fieldDecl = field.getFieldDecl();
            Annotation annotation = null;
            if (field.isDeepStub()) {
                Map<SimpleName, Name> map = new HashMap<>();
                SimpleName key = (SimpleName) nodeFactory
                        .createName(deepStubAnnotation[0]);
                Name value = nodeFactory.createName(deepStubAnnotation[1]);
                map.put(key, value);
                annotation = nodeFactory.createNormalAnnotation("Mock", map);
            } else {
                annotation = nodeFactory.createMarkerAnnotation("Mock");
            }
            fieldMakers.addAnnotation(fieldDecl, annotation);
        }
    }

    /**
     * If field is not mock (even when uninitialised), then remove it from test
     * clz. Example: removes the field private List<String> list (with or
     * without initialization) in FieldIT test.
     *
     * @param clz
     */
    public void removeFields(final Clz clz) {
        TypeDeclaration testClz = clz.getTestTypeDecl();
        List<Field> fieldList = clz.getFields();
        for (Field field : fieldList) {
            if (!field.isMock()) {
                FieldDeclaration fieldDecl = field.getFieldDecl();
                testClz.bodyDeclarations().remove(fieldDecl);
                // as field is removed set it as disabled.
                field.setEnable(false);
            }
        }
    }

    public void addSelfField(final String srcClzName,
            final TypeDeclaration testClz, final String fieldName) {
        Modifier modifier =
                nodeFactory.createModifier(ModifierKeyword.PRIVATE_KEYWORD);
        Annotation annotation =
                nodeFactory.createMarkerAnnotation("InjectMocks");

        FieldDeclaration fieldDecl =
                clzNodeFactory.createFieldDecl(srcClzName, fieldName);
        fieldMakers.addAnnotation(fieldDecl, annotation);
        fieldMakers.addModifier(fieldDecl, modifier);
        fieldMakers.addFieldDecl(testClz, fieldDecl);
    }
}
