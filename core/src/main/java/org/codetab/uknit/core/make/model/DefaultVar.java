package org.codetab.uknit.core.make.model;

import org.eclipse.jdt.core.dom.Type;

public class DefaultVar implements IVar {

    protected String name;
    protected Type type;
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

}
