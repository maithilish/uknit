package org.codetab.uknit.core.make.model;

import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

public interface IVar {

    enum Kind {
        FIELD, PARAMETER, LOCAL, RETURN, INFER
    }

    enum Nature {
        REALISH, COLLECTION, OFFLIMIT, VARARG
    }

    void setKind(Kind kind);

    Kind getKind();

    boolean is(Kind kind);

    String getName();

    void setName(String name);

    String getOldName();

    void setOldName(String name);

    String getDefinedName();

    Type getType();

    void setType(Type type);

    boolean isMock();

    void setMock(boolean mock);

    boolean isEffectivelyReal();

    boolean isCreated();

    void setCreated(boolean created);

    boolean isEnable();

    void setEnable(boolean enable);

    void setEnforce(boolean enforce);

    Optional<Boolean> getEnforce();

    boolean isDeepStub();

    void setDeepStub(boolean deepStub);

    void setProperty(String propertyName, Object data);

    Object getProperty(String propertyName, Object defaultVal);

    void addNature(Nature nature);

    List<Nature> getNatures();

    boolean is(Nature nature);

    IVar deepCopy();

    ITypeBinding getTypeBinding();

    void setTypeBinding(ITypeBinding typeBinding);

    default boolean isField() {
        return is(Kind.FIELD);
    }

    default boolean isParameter() {
        return is(Kind.PARAMETER);
    }

    default boolean isLocalVar() {
        return is(Kind.LOCAL);
    }

    default boolean isInferVar() {
        return is(Kind.INFER);
    }

    default boolean isReturnVar() {
        return is(Kind.RETURN);
    }

    void setInitializer(Optional<Initializer> initializer);

    Optional<Initializer> getInitializer();
}
