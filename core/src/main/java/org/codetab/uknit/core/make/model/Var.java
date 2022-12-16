package org.codetab.uknit.core.make.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class Var implements IVar {

    protected Kind kind;

    // present name
    protected String name;

    // original name before any name change
    protected String oldName;

    protected Type type;
    protected ITypeBinding typeBinding;
    protected boolean mock;
    protected boolean created;
    protected boolean enable;
    protected Optional<Boolean> enforce;
    protected boolean deepStub;
    protected Map<String, Object> properties;

    @Inject
    public Var(@Assisted final Kind kind, @Assisted final String name,
            @Assisted final Type type, @Assisted final boolean mock) {
        this.kind = kind;
        this.name = name;
        this.oldName = name;
        this.type = type;
        this.mock = mock;
        this.enable = true; // enable by default
        this.enforce = Optional.empty();
        this.deepStub = false;
        this.created = false;
        properties = new HashMap<>();
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
    public String getOldName() {
        return oldName;
    }

    @Override
    public void setOldName(final String oldName) {
        this.oldName = oldName;
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
    public ITypeBinding getTypeBinding() {
        return typeBinding;
    }

    @Override
    public void setTypeBinding(final ITypeBinding typeBinding) {
        this.typeBinding = typeBinding;
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
    public void setEnforce(final boolean enforce) {
        this.enforce = Optional.ofNullable(enforce);
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
            clone.setEnforce(enforce.get());
        }
        clone.setOldName(oldName);
        return clone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, deepStub, enable, enforce, kind, mock,
                name, oldName, type);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Var other = (Var) obj;
        return created == other.created && deepStub == other.deepStub
                && enable == other.enable
                && Objects.equals(enforce, other.enforce) && kind == other.kind
                && mock == other.mock && Objects.equals(name, other.name)
                && Objects.equals(oldName, other.oldName)
                && Objects.equals(type, other.type);
    }

    @Override
    public String toString() {
        return "Var [name=" + name + ", type=" + type + ", kind=" + kind
                + ", mock=" + mock + ", created=" + created + "]";
    }

    @Override
    public void setProperty(final String propertyName, final Object data) {
        properties.put(propertyName, data);
    }

    @Override
    public Object getProperty(final String propertyName) {
        return properties.get(propertyName);
    }

    /**
     * Update fields of this with the other.
     *
     * @param other
     */
    public void updateStates(final Var other) {
        created = other.created;
        mock = other.mock;

        properties.clear();
        properties.putAll(other.properties);
    }
}
