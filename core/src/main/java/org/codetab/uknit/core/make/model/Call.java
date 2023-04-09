package org.codetab.uknit.core.make.model;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class Call {

    private AbstractTypeDeclaration clz;
    private SimpleName name;
    private Type returnType;
    private MethodDeclaration methodDecl;

    @Inject
    public Call(@Assisted final AbstractTypeDeclaration clz,
            @Assisted final SimpleName name,
            @Assisted @Nullable final Type returnType,
            @Assisted final MethodDeclaration methodDecl) {
        this.clz = clz;
        this.name = name;
        this.returnType = returnType;
        this.methodDecl = methodDecl;
    }

    public AbstractTypeDeclaration getClz() {
        return clz;
    }

    public SimpleName getName() {
        return name;
    }

    public Type getReturnType() {
        return returnType;
    }

    public MethodDeclaration getMethodDecl() {
        return methodDecl;
    }

    @Override
    public String toString() {
        return "Call [clz=" + clz.getName() + ", methodName=" + name
                + ", returnType=" + returnType + "]";
    }

}
