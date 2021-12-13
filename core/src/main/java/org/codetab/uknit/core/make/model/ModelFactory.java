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

    Field createField(String name, Type type, boolean mock,
            FieldDeclaration fieldDecl);

    Parameter createParameter(String name, Type type, boolean mock);

    Call createCall(TypeDeclaration clz, SimpleName name, Type returnType,
            MethodDeclaration methodDecl);

    LocalVar createLocalVar(String name, Type type, boolean mock);

    InferVar createInferVar(String name, Type type, boolean mock);

    Invoke createInvoke(IVar var, Optional<ExpReturnType> expReturnType,
            MethodInvocation exp);

    ReturnVar createReturnVar(String name, Type type, boolean mock);

    ExpVar createVarExp(@Assisted("left") Expression leftExp,
            @Assisted("right") Expression rightExp);

    When createWhen(String methodSignature);

    Verify createVerify(MethodInvocation mi);

    ArgCapture createArgCapture(String name, Type type);
}
