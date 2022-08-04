package org.codetab.uknit.core.make.model;

import java.util.Optional;

import org.codetab.uknit.core.node.ArgCapture;
import org.eclipse.jdt.core.dom.ASTNode;
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
            @Assisted("fieldDecl") FieldDeclaration fieldDecl,
            @Assisted("srcFieldDecl") FieldDeclaration srcFieldDecl);

    Parameter createParameter(String name, Type type, boolean mock);

    LocalVar createLocalVar(String name, Type type, boolean mock);

    InferVar createInferVar(String name, Type type, boolean mock);

    ReturnVar createReturnVar(String name, Type type, boolean mock);

    Call createCall(TypeDeclaration clz, SimpleName name, Type returnType,
            MethodDeclaration methodDecl);

    Invoke createInvoke(IVar var, Optional<ExpReturnType> expReturnType,
            Expression exp);

    ExpVar createVarExp(@Assisted("left") Expression leftExp,
            @Assisted("right") Expression rightExp);

    When createWhen(String methodSignature);

    Verify createVerify(MethodInvocation mi, boolean inCtlFlowPath);

    ArgCapture createArgCapture(String name, Type type);

    Cu createCuMap(@Assisted("pkg") String pkg,
            @Assisted("clzName") String clzName,
            @Assisted("srcPath") String srcPath);

    Patch createPatch(ASTNode node, Expression exp, String name, int argIndex);
}
