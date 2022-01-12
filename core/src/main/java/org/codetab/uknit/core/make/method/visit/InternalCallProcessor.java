package org.codetab.uknit.core.make.method.visit;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.method.MethodMaker;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.parse.SourceParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

public class InternalCallProcessor {

    @Inject
    private SourceParser sourceParser;
    @Inject
    private ArgParamBlender argParamBlender;
    @Inject
    private Methods methods;
    @Inject
    private DInjector di;
    @Inject
    private Resolver resolver;

    public Optional<IVar> process(final MethodInvocation mi, final Heap heap) {
        IMethodBinding methodBind = resolver.resolveMethodBinding(mi);
        ITypeBinding declClz = methodBind.getDeclaringClass();
        String clzName = declClz.getName();
        String clzPkg = declClz.getPackage().getName();
        String key = methodBind.getKey();

        // get invoked internal method declaration from the cu or super cu
        Optional<CompilationUnit> cu = sourceParser.fetchCu(clzPkg, clzName);
        if (cu.isPresent()) {
            MethodDeclaration methodDecl =
                    (MethodDeclaration) cu.get().findDeclaringNode(key);

            /*
             * process the internal method in a new method maker, without
             * staging the method itself. Existing heap is passed to new maker
             * so that the internals of the method are staged in it. Assume
             * internal method's statements are as if the statements of calling
             * method.
             */
            MethodMaker methodMaker = di.instance(MethodMaker.class);
            boolean ignorePrivate = false; // internal methods may be private
            if (methodMaker.isStageable(methodDecl, ignorePrivate)) {
                boolean internalMethod = true;
                methodMaker.processMethod(methodDecl, internalMethod, heap);

                // blend calling args and internal method's parameters
                argParamBlender.setArguments(methods.getArguments(mi));
                argParamBlender
                        .setParameters(methods.getParameters(methodDecl));
                argParamBlender.fix(heap);

                // now heap holds internal method's return var
                Optional<IVar> expectedVar = heap.getExpectedVar();
                if (expectedVar.isPresent()) {
                    heap.setExpectedVar(Optional.empty());
                    IVar var = heap.findVar(expectedVar.get().getName());
                    return Optional.of(var);
                }
            }
        }
        return Optional.empty();
    }
}

/**
 * Coalesce call args and internal method parameters.
 * @author Maithilish
 *
 */
class ArgParamBlender {

    @Inject
    private Nodes nodes;

    private List<Expression> arguments;
    private List<SingleVariableDeclaration> parameters;

    public void fix(final Heap heap) {
        checkState(arguments.size() == parameters.size(),
                "arg parameter list size mismatch");

        for (int i = 0; i < arguments.size(); i++) {
            Expression arg = arguments.get(i);
            if (nodes.is(arg, SimpleName.class)) {
                String argName = nodes.getName(nodes.as(arg, SimpleName.class));
                String paramName = nodes.getName(parameters.get(i).getName());
                IVar paramVar = heap.findVar(paramName);
                if (argName.equals(paramName)) {
                    List<IVar> vars = heap.getVars();
                    int index = vars.lastIndexOf(paramVar);
                    // deduplicate
                    vars.remove(index);
                } else {
                    // assign arg to param
                    paramVar.setValue(argName);
                }
            }
        }
    }

    public void setArguments(final List<Expression> arguments) {
        this.arguments = arguments;
    }

    public void setParameters(
            final List<SingleVariableDeclaration> parameters) {
        this.parameters = parameters;
    }
}
