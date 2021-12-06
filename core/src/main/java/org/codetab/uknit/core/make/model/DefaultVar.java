package org.codetab.uknit.core.make.model;

import org.eclipse.jdt.core.dom.Type;

public class DefaultVar implements IVar {

    protected String name;
    protected Type type;
    protected boolean mock;
    protected boolean used;
    protected boolean exposed;
    protected boolean deepStub;

    public DefaultVar() {
        this.used = true;
        this.exposed = true;
        this.deepStub = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
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
    public boolean isUsed() {
        return used;
    }

    @Override
    public void setUsed(final boolean used) {
        this.used = used;
    }

    @Override
    public boolean isExposed() {
        return exposed;
    }

    @Override
    public void setExposed(final boolean exposed) {
        this.exposed = exposed;
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
