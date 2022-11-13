package org.codetab.uknit.core.make.method.imc;

import static java.util.Objects.isNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.di.DInjector;
import org.codetab.uknit.core.make.method.MethodMaker;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.parse.SourceParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class InternalCallProcessor {

    @Inject
    private SourceParser sourceParser;
    @Inject
    private InternalCalls internalCalls;
    @Inject
    private DInjector di;
    @Inject
    private Resolver resolver;

    /**
     * Process the internal method in a new method maker, without creating the
     * method outline for internal method.
     * <p>
     * This method is called for each internal invoke by post visit process()
     * call in stageMethod(). The post visit processing is suspended and
     * internal call process is done in new set of MethodMaker, Visitor and Heap
     * (internalHeap). Post visit processing of internal heap is done
     * immediately except processing of when, verify and var state. This
     * recursively handles any internal call invoked in the internal call. On
     * return the internalHeap packs are merged with MUT heap selectively.
     * Finally, suspended post visit processing of MUT is resumed to process
     * when, verify and var state.
     * <p>
     * The post processing (infer, patch etc.,) happens in MUT and also in
     * internal methods after respective visit, but processing of when, verify
     * and var state happens collectively only in the end. See Processor class
     * for details.
     * <p>
     * Notes: The internal method's statements are effectively the statements of
     * calling method. We can replace internal call with internal method
     * statements. Additionally, when calling arg names and internal method
     * parameter names differs, new mapping variables are required.
     *
     * @param methodBinding
     *            of the internal method invocation
     * @param arguments
     *            of internal method invocation
     * @param heap
     * @return
     */
    public boolean process(final Invoke invoke, final Heap heap) {

        IMethodBinding methodBinding =
                resolver.resolveMethodBinding(invoke.getExp());
        ITypeBinding declClz = methodBinding.getDeclaringClass();
        Optional<CompilationUnit> cu = sourceParser.fetchCu(declClz);

        if (cu.isEmpty()) {
            return false;
        }

        // get invoked IMC
        String key = methodBinding.getKey();
        MethodDeclaration methodDecl =
                (MethodDeclaration) cu.get().findDeclaringNode(key);
        /*
         * method parameter with type parameter results in key mismatch, try to
         * find node through IMethodBinding.
         */
        if (isNull(methodDecl)) {
            methodDecl = (MethodDeclaration) cu.get()
                    .findDeclaringNode(methodBinding);
        }
        /*
         * Create new MethodMaker and Heap. The new internalHeap, initialized
         * with Field Packs, is passed to method maker which collects packs of
         * IM in it. On return the packs of internalHeap is merged with heap of
         * MUT selectively.
         */
        MethodMaker methodMaker = di.instance(MethodMaker.class);
        boolean ignorePrivate = false; // internal methods may be private

        if (methodMaker.isStageable(methodDecl, ignorePrivate)) {
            boolean internalMethod = true;

            Heap internalHeap = di.instance(Heap.class);
            internalCalls.initInternalHeap(heap, internalHeap);

            methodMaker.processMethod(methodDecl, invoke, internalMethod, heap,
                    internalHeap);

            return true;
        }
        return false;
    }
}
