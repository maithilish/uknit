package org.codetab.uknit.core.make.clz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
        return fieldMakers.createFields(fieldDecls);
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

                // if not hidden, add field to clzDecl tree (test class)
                if (!srcField.isDisable()) {
                    body.add(fieldDecl);
                }

                Field testField = fieldMakers.createField(fieldDecl,
                        srcField.getFieldDecl());
                testField.setDisable(srcField.isDisable());
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

    public void annotateFields(final List<Field> fieldList,
            final String[] deepStubAnnotation) {
        for (Field field : fieldList) {
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
