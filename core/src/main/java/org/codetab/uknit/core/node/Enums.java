package org.codetab.uknit.core.node;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;

public class Enums {

    public List<FieldDeclaration> getFields(final EnumDeclaration srcEnum) {
        List<FieldDeclaration> fields = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<BodyDeclaration> bodyDecls = srcEnum.bodyDeclarations();
        for (BodyDeclaration bodyDecl : bodyDecls) {
            if (bodyDecl instanceof FieldDeclaration) {
                fields.add((FieldDeclaration) bodyDecl);
            }
        }
        return fields;
    }

}
