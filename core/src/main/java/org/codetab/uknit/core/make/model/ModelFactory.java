package org.codetab.uknit.core.make.model;

import org.codetab.uknit.core.make.model.IVar.Kind;
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

    Cu createCu(@Assisted("pkg") String pkg,
            @Assisted("clzName") String clzName,
            @Assisted("srcPath") String srcPath);

    Field createField(String name, Type type, boolean mock,
            @Assisted("fieldDecl") FieldDeclaration fieldDecl,
            @Assisted("srcFieldDecl") FieldDeclaration srcFieldDecl);

    Var createVar(Kind kind, String name, Type type, boolean mock);

    Pack createPack(IVar var, Expression exp, boolean inCtlPath);

    Invoke createInvoke(IVar var, Expression exp, boolean inCtlPath);

    Patch createPatch(ASTNode node, Expression exp, String name, int expIndex);

    Call createCall(TypeDeclaration clz, SimpleName name, Type returnType,
            MethodDeclaration methodDecl);

    When createWhen(String methodSignature, IVar callVar);

    Verify createVerify(MethodInvocation mi, boolean inCtlFlowPath);

    ArgCapture createArgCapture(String name, Type type);
}
