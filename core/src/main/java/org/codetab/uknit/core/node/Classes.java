package org.codetab.uknit.core.node;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;

import com.google.common.base.CaseFormat;

public class Classes {

    public String getClzAsFieldName(final AbstractTypeDeclaration typeDecl) {
        String clzName = typeDecl.getName().getFullyQualifiedName();
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, clzName);
    }

    public String getClzName(final AbstractTypeDeclaration clzDecl) {
        return clzDecl.getName().getFullyQualifiedName();
    }

    /**
     * Get test class name for a TypeDeclaration.
     * @param clzDecl
     * @return
     */
    public String getTestClzName(final AbstractTypeDeclaration clzDecl) {
        return String.join("", clzDecl.getName().getFullyQualifiedName(),
                "Test");
    }
}
