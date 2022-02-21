package org.codetab.uknit.core.make.model;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.jdt.core.dom.Type;

public class DefaultVar implements IVar {

    protected String name;
    protected Type type;
    protected String value;
    protected boolean mock;
    protected boolean created;
    protected boolean used;
    protected boolean hidden;
    protected boolean deepStub;

    public DefaultVar() {
        this.used = true;
        this.hidden = false;
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
    public boolean isUsed() {
        return used;
    }

    @Override
    public void setUsed(final boolean used) {
        this.used = used;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
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
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public void setValue(final String value) {
        this.value = value;
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
        DefaultVar other = (DefaultVar) obj;
        return Objects.equals(name, other.name)
                && Objects.equals(type, other.type)
                && Objects.equals(value, other.value)
                && Objects.equals(mock, other.mock)
                && Objects.equals(created, other.created)
                && Objects.equals(used, other.used)
                && Objects.equals(hidden, other.hidden)
                && Objects.equals(deepStub, other.deepStub);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, value, mock, created, used, hidden,
                deepStub);
    }
}
