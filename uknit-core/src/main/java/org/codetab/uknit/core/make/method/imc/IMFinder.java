package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CriticalException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class IMFinder {

    @Inject
    private MethodComparator methodComparator;

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

                        String clzName = mb.getDeclaringClass().getBinaryName();
                        String clzName2 =
                                mb2.getDeclaringClass().getBinaryName();

                        if (clzName.equals(clzName2)
                                && methodComparator.isEqual(mb, mb2)) {
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

/**
 * Compare two method bindings by name and parameters.
 *
 * @author Maithilish
 *
 */
class MethodComparator {

    public boolean isEqual(final IMethodBinding mb, final IMethodBinding mb2) {

        String clzName1 = mb.getDeclaringClass().getBinaryName();
        String clzName2 = mb2.getDeclaringClass().getBinaryName();
        if (!clzName1.equals(clzName2)) {
            return false;
        }

        String name1 = mb.getName();
        String name2 = mb2.getName();
        if (!name1.equals(name2)) {
            return false;
        }

        boolean paramsEqual = true;
        ITypeBinding[] params1 = mb.getParameterTypes();
        ITypeBinding[] params2 = mb2.getParameterTypes();
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                ITypeBinding param1 = params1[i];
                ITypeBinding param2 = params2[i];
                if (!param1.getBinaryName().equals(param2.getBinaryName())) {
                    paramsEqual = false;
                }
            }
        } else {
            paramsEqual = false;
        }
        return paramsEqual;
    }
}
