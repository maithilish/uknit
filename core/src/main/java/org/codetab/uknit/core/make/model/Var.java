package org.codetab.uknit.core.make.model;

import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class Var implements IVar {

    protected Kind kind;

    // present name
    protected String name;

    // original name before any name change
    protected String realName;

    protected Type type;
    protected boolean mock;
    protected boolean created;
    protected boolean enable;
    protected Optional<Boolean> enforce;
    protected boolean deepStub;

    @Inject
    public Var(@Assisted final Kind kind, @Assisted final String name,
            @Assisted final Type type, @Assisted final boolean mock) {
        this.kind = kind;
        this.name = name;
        this.realName = name;
        this.type = type;
        this.mock = mock;
        this.enable = true; // enable by default
        this.enforce = Optional.empty();
        this.deepStub = false;
        this.created = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getRealName() {
        return realName;
    }

    @Override
    public void setRealName(final String realName) {
        this.realName = realName;
    }

    @Override
    public boolean is(final Kind pkind) {
        return this.kind.equals(pkind);
    }

    @Override
    public void setKind(final Kind kind) {
        this.kind = kind;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(final Type type) {
        this.type = type;
    }

    @Override
    public boolean isMock() {
        return mock;
    }

    @Override
    public void setMock(final boolean mock) {
        this.mock = mock;
    }

    @Override
    public boolean isCreated() {
        return created;
    }

    @Override
    public void setCreated(final boolean created) {
        this.created = created;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(final boolean disable) {
        this.enable = disable;
    }

    @Override
    public Optional<Boolean> getEnforce() {
        return enforce;
    }

    @Override
    public void setEnforce(final Optional<Boolean> enforce) {
        this.enforce = enforce;
    }

    @Override
    public boolean isDeepStub() {
        return deepStub;
    }

    @Override
    public void setDeepStub(final boolean deepStub) {
        this.deepStub = deepStub;
    }

    @Override
    public IVar clone() {
        Var clone = new Var(kind, name, type, mock);
        clone.setCreated(created);
        clone.setDeepStub(deepStub);
        clone.setEnable(enable);
        if (enforce.isPresent()) {
            clone.setEnforce(Optional.ofNullable(enforce.get()));
        }
        clone.setRealName(realName);
        return clone;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Var other = (Var) obj;
        return Objects.equals(name, other.name)
                && Objects.equals(type, other.type)
                && Objects.equals(mock, other.mock)
                && Objects.equals(created, other.created)
                && Objects.equals(enable, other.enable)
                && Objects.equals(deepStub, other.deepStub);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, mock, created, enable, deepStub);
    }

    @Override
    public String toString() {
        return "Var [name=" + name + ", type=" + type + ", kind=" + kind
                + ", mock=" + mock + ", created=" + created + "]";
    }
}
