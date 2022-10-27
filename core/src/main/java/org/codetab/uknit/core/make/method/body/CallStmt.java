package org.codetab.uknit.core.make.method.body;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.Heaps;
import org.codetab.uknit.core.make.model.Call;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.node.Classes;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Statement;

public class CallStmt {

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Types types;
    @Inject
    private Heaps heaps;
    @Inject
    private Methods methods;
    @Inject
    private Classes classes;
    @Inject
    private Configs configs;

    public Statement createStmts(final Heap heap) {
        Call call = heap.getCall();

        String methodName = call.getName().getFullyQualifiedName();

        List<IVar> parameters = heaps.getVarsOfKind(heap, Kind.PARAMETER);
        String args = parameters.stream().map(p -> p.getName())
                .collect(Collectors.joining(","));

        String invokeOn;
        if (methods.isStaticCall(call.getMethodDecl())) {
            invokeOn = classes.getClzName(call.getClz());
        } else {
            invokeOn = classes.getClzAsFieldName(call.getClz());
        }

        String format;
        if (call.getMethodDecl().isConstructor()) {
            format = configs.getFormat("uknit.format.callConstructor");
            String type = call.getClz().getName().getFullyQualifiedName();
            return nodeFactory.createCallStmt(type, args, format);
        } else {
            boolean returnVoid = false;
            if (call.getReturnType().isPrimitiveType()) {
                PrimitiveType pt = (PrimitiveType) call.getReturnType();
                if (pt.getPrimitiveTypeCode() == PrimitiveType.VOID) {
                    returnVoid = true;
                }
            }
            if (returnVoid) {
                format = configs.getFormat("uknit.format.callVoid");
                return nodeFactory.createCallStmt(invokeOn, methodName, args,
                        format);
            } else {
                format = configs.getFormat("uknit.format.call");
                // String actual = foo.bar(...)
                boolean parameterizedName = true;
                String returnType = types.getTypeName(call.getReturnType(),
                        parameterizedName);
                format = configs.getFormat("uknit.format.call");
                return nodeFactory.createCallStmt(invokeOn, methodName, args,
                        returnType, format);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void addStmt(final MethodDeclaration methodDecl,
            final Statement stmt) {
        methodDecl.getBody().statements().add(stmt);
    }
}
