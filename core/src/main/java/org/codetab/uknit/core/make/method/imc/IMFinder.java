package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;

import org.codetab.uknit.core.exception.CriticalException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class IMFinder {

    /**
     * Find method decl for IM call.
     *
     * @param methodBind
     *            binding derived from IM invoke.
     * @param cu
     *            CompilationUnit that holds the method decl for IM invoke.
     * @return
     */
    public MethodDeclaration findMethodDecl(final IMethodBinding methodBind,
            final CompilationUnit cu) {

        // find through key
        String key = methodBind.getKey();
        MethodDeclaration methodDecl =
                (MethodDeclaration) cu.findDeclaringNode(key);

        /*
         * method parameter with type parameter results in key mismatch, try to
         * find node through IMethodBinding.
         */
        if (isNull(methodDecl)) {
            methodDecl = (MethodDeclaration) cu.findDeclaringNode(methodBind);
        }

        /*
         * Find through brute force. Ex: method such as final T
         * getDelegateInternal(). Both find through key and binding fails in
         * this case. Loop through all method decl in cu to find the match.
         */
        if (isNull(methodDecl)) {
            IMethodBinding mb = methodBind.getMethodDeclaration();
            String methodName = mb.getName();

            @SuppressWarnings("unchecked")
            List<AbstractTypeDeclaration> types = cu.types();
            for (AbstractTypeDeclaration type : types) {
                @SuppressWarnings("unchecked")
                List<BodyDeclaration> bodies = type.bodyDeclarations();
                for (BodyDeclaration body : bodies) {
                    if (body instanceof MethodDeclaration) {
                        MethodDeclaration md = ((MethodDeclaration) body);
                        IMethodBinding mb2 =
                                md.resolveBinding().getMethodDeclaration();
                        String methodName2 = mb2.getName();

                        String clzName = mb.getDeclaringClass().getBinaryName();
                        String clzName2 =
                                mb2.getDeclaringClass().getBinaryName();

                        if (clzName.equals(clzName2)
                                && methodName.equals(methodName2.toString())) {
                            if (isNull(methodDecl)) {
                                methodDecl = md;
                            } else {
                                throw new CriticalException(spaceit(
                                        "multiple method decl found for IM",
                                        methodName));
                            }
                        }
                    }
                }
            }
        }

        return methodDecl;
    }

}
