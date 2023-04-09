package org.codetab.uknit.core.make.model;

import java.util.List;

import org.codetab.uknit.core.make.model.IVar.Kind;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public interface ModelFactory {

    Cu createCu(@Assisted("pkg") String pkg,
            @Assisted("clzName") String clzName,
            @Assisted("srcPath") String srcPath);

    Field createField(String name, Type type, boolean mock,
            @Assisted("fieldDecl") FieldDeclaration fieldDecl,
            @Assisted("srcFieldDecl") FieldDeclaration srcFieldDecl);

    Var createVar(Kind kind, String name, Type type, boolean mock);

    Pack createPack(int id, IVar var, Expression exp, boolean inCtlPath);

    Invoke createInvoke(int id, IVar var, Expression exp, boolean inCtlPath);

    Patch createPatch(org.codetab.uknit.core.make.model.Patch.Kind kind,
            String definedName, IVar var, int index);

    Call createCall(AbstractTypeDeclaration clz, SimpleName name,
            Type returnType, MethodDeclaration methodDecl);

    When createWhen(String methodSignature, IVar callVar);

    Verify createVerify(MethodInvocation mi, boolean inCtlFlowPath);

    ArgCapture createArgCapture(String name, Type type);

    Load createLoad(IVar var, String call, @Assisted("args") List<IVar> args,
            @Assisted("usedVars") List<IVar> usedVars);

    Initializer createInitializer(
            org.codetab.uknit.core.make.model.Initializer.Kind kind,
            Object initializer);
}
