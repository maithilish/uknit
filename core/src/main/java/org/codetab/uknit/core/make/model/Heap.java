package org.codetab.uknit.core.make.model;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.zap.make.model.Call;

public class Heap {

    @Inject
    private List<Pack> packs;

    // name of class under test
    private String cutName;

    // test method call
    private Call call;

    // any MI in CUT throws exception
    private boolean testThrowsException;

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
}
