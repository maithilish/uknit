package org.codetab.uknit.core.make.model;

import java.util.List;

import javax.inject.Inject;

public class Heap {

    @Inject
    private List<Pack> packs;

    // name of class under test
    private String cutName;

    // test method call
    private Call call;

    // any MI in CUT throws exception
    private boolean testThrowsException;

    // is assert stmt created in test method
    private boolean asserted;

    public void setCutName(final String cutName) {
        this.cutName = cutName;
    }

    public String getCutName() {
        return cutName;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(final Call call) {
        this.call = call;
    }

    public void setTestThrowsException(final boolean throwsException) {
        this.testThrowsException = throwsException;
    }

    public boolean isTestThrowsException() {
        return testThrowsException;
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void addPack(final Pack pack) {
        packs.add(pack);
    }

    public void setAsserted(final boolean asserted) {
        this.asserted = asserted;
    }

    public boolean isAsserted() {
        return asserted;
    }
}
