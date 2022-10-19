package org.codetab.uknit.core.make.model;

import org.codetab.uknit.core.make.model.IVar.Kind;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public interface ModelFactory {

    Field createField(String name, Type type, boolean mock,
            @Assisted("fieldDecl") FieldDeclaration fieldDecl,
            @Assisted("srcFieldDecl") FieldDeclaration srcFieldDecl);

    Cu createCu(@Assisted("pkg") String pkg,
            @Assisted("clzName") String clzName,
            @Assisted("srcPath") String srcPath);

    Var createVar(Kind kind, String name, Type type, boolean mock);

}
