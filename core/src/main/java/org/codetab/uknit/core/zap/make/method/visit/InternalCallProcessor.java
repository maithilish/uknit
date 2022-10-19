package org.codetab.uknit.core.zap.make.method.visit;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.parse.SourceParser;
import org.codetab.uknit.core.zap.make.method.MethodMaker;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.codetab.uknit.core.zap.make.model.IVar;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

public class InternalCallProcessor {

    @Inject
    private SourceParser sourceParser;
    @Inject
    private ParamArgMapper paramArgMapper;
    @Inject
    private Methods methods;
    @Inject
    private DInjector di;

    /**
     * Process the internal method in a new method maker, without staging the
     * method itself. MethodMaker.processMethod() passes new Heap to visitor.
     * Assume internal method's statements are as if the statements of calling
     * method.
     * <p>
     * The map paramArgMap maps IMC parameters to calling args and passed to
     * method maker. If arg name is same as parameter then arg var is used in
     * IMC. When name are not same, then patch is staged so that all occurrences
     * of parameters in IMC statements are patched to arg name. IMC parameters
     * are ignored in VarProcesoor.stageLocalVars() and they are not staged as
     * we use arg var in IMC.
     * @param methodBinding
     *            of the internal method invocation
     * @param arguments
     *            of internal method invocation
     * @param heap
     * @return
     */
    public Optional<IVar> process(final IMethodBinding methodBinding,
            final List<Expression> arguments, final Heap heap) {
        ITypeBinding declClz = methodBinding.getDeclaringClass();
        String clzName = declClz.getName();
        String clzPkg = declClz.getPackage().getName();
        String key = methodBinding.getKey();

        // get invoked internal method declaration from the cu or super cu
        Optional<CompilationUnit> cu = sourceParser.fetchCu(clzPkg, clzName);
        if (cu.isPresent()) {
            MethodDeclaration methodDecl =
                    (MethodDeclaration) cu.get().findDeclaringNode(key);
            /*
             * method parameter with type parameter results in key mismatch, try
             * to find node through IMethodBinding.
             */
            if (isNull(methodDecl)) {
                methodDecl = (MethodDeclaration) cu.get()
                        .findDeclaringNode(methodBinding);
            }
            /*
             * process the internal method in a new method maker, without
             * staging the method itself. MethodMaker.processMethod() passes new
             * Heap to visitor. Assume internal method's statements are as if
             * the statements of calling method.
             */
            MethodMaker methodMaker = di.instance(MethodMaker.class);
            boolean ignorePrivate = false; // internal methods may be private
            if (methodMaker.isStageable(methodDecl, ignorePrivate)) {
                boolean internalMethod = true;

                paramArgMapper.setArguments(arguments);
                paramArgMapper.setParameters(methods.getParameters(methodDecl));

                Map<String, String> paramArgMap = null;
                if (paramArgMapper.hasVarargsParameter()) {
                    paramArgMap = paramArgMapper.getVarargsParamArgMap(heap);
                } else {
                    paramArgMap = paramArgMapper.getParamArgMap(heap);
                }

                methodMaker.processMethod(methodDecl, paramArgMap,
                        internalMethod, heap);

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
 * Map IMC parameter to calling args.
 * @author Maithilish
 *
 */
class ParamArgMapper {

    @Inject
    private Nodes nodes;
    @Inject
    private Expressions expressions;

    private List<Expression> arguments;
    private List<SingleVariableDeclaration> parameters;

    /**
     * Map IMC parameter name to calling arg name.
     * @param heap
     * @return param to arg map
     */
    public Map<String, String> getParamArgMap(final Heap heap) {
        checkState(arguments.size() == parameters.size(),
                "arg parameter list size mismatch");

        Map<String, String> paramArgMap = new HashMap<>();

        for (int i = 0; i < arguments.size(); i++) {
            Expression arg = arguments.get(i);

            String paramName = nodes.getName(parameters.get(i).getName());
            Optional<String> argName = expressions.mapExpToName(arg, heap);

            if (argName.isPresent()) {
                paramArgMap.put(paramName, argName.get());
            }
        }
        return paramArgMap;
    }

    /**
     * Map IMC parameter name to calling arg name when parameter has a VarArg.
     * @param heap
     * @return param to arg map
     */
    public Map<String, String> getVarargsParamArgMap(final Heap heap) {

        Map<String, String> paramArgMap = new HashMap<>();

        // limit to parameters.size()
        for (int i = 0; i < parameters.size(); i++) {
            Expression arg = arguments.get(i);

            String paramName = nodes.getName(parameters.get(i).getName());
            Optional<String> argName;
            if (i == parameters.size() - 1) {
                if (arguments.size() == parameters.size()) {
                    // check arg and param type for equality ?
                    argName = expressions.mapExpToName(arg, heap);
                } else {
                    // last parameter is VarArg, map arg as parameter name
                    argName = Optional.of(paramName);
                    /// LAST EDIT
                }
            } else {
                argName = expressions.mapExpToName(arg, heap);
            }

            if (argName.isPresent()) {
                paramArgMap.put(paramName, argName.get());
            }
        }
        return paramArgMap;
    }

    public void setArguments(final List<Expression> arguments) {
        this.arguments = arguments;
    }

    public void setParameters(
            final List<SingleVariableDeclaration> parameters) {
        this.parameters = parameters;
    }

    /**
     * Whether last parameter is of varargs type.
     *
     * @return
     */
    public boolean hasVarargsParameter() {
        if (parameters.size() > 0) {
            SingleVariableDeclaration lastParameter =
                    parameters.get(parameters.size() - 1);
            return lastParameter.isVarargs();
        } else {
            return false;
        }
    }
}