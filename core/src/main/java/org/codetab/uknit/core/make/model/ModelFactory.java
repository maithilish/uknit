package org.codetab.uknit.core.make.model;

import java.util.Optional;

import org.codetab.uknit.core.node.ArgCapture;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.google.inject.assistedinject.Assisted;

public interface ModelFactory {

    public Field createField(String name, Type type, boolean mock,
            FieldDeclaration fieldDecl);

    public Parameter createParameter(String name, Type type, boolean mock);

    public Call createCall(TypeDeclaration clz, SimpleName name,
            Type returnType, MethodDeclaration methodDecl);

    public LocalVar createLocalVar(String name, Type type, boolean mock);

    public InferVar createInferVar(String name, Type type, boolean mock);

    public Invoke createInvoke(IVar var, Optional<ExpReturnType> expReturnType,
            MethodInvocation exp);

    public ReturnVar createReturnVar(String name, Type type, boolean mock);

    public ExpVar createVarExp(@Assisted("left") Expression leftExp,
            @Assisted("right") Expression rightExp);

    public When createWhen(String methodSignature);

    public Verify createVerify(MethodInvocation mi);

    public ArgCapture createArgCapture(String name, Type type);
}
