package org.codetab.uknit.core.make.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Heap implements Listener {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private List<Pack> packs;

    /*
     * List of vars held by packs. The packsDirty field is set to true when ever
     * items are added to packs or var is changed. Cache is rebuilt when
     * packsDirty is true.
     */
    private List<IVar> varCache;

    private boolean packsDirty = true;

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
        return Collections.unmodifiableList(packs);
    }

    public void addPack(final Pack pack) {
        packs.add(pack);
        pack.setListener(this);
        packsDirty = true;
    }

    public void addPacks(final List<Pack> packList) {
        packs.addAll(packList);
        packList.forEach(p -> p.setListener(this));
        packsDirty = true;
    }

    public void addPacks(final int index, final List<Pack> packList) {
        packs.addAll(index, packList);
        packList.forEach(p -> p.setListener(this));
        packsDirty = true;
    }

    public void removePack(final Pack pack) {
        packs.remove(pack);
        packsDirty = true;
    }

    public List<IVar> getVars() {
        // if packsDirty then rebuild varCache
        if (packsDirty) {
            varCache = packs.stream().map(Pack::getVar).filter(Objects::nonNull)
                    .collect(Collectors.toList());
            packsDirty = false;
        }
        return Collections.unmodifiableList(varCache);
    }

    @Override
    public void packVarChanged() {
        packsDirty = true;
    }

    public void setAsserted(final boolean asserted) {
        this.asserted = asserted;
    }

    public boolean isAsserted() {
        return asserted;
    }

    public void tracePacks(final String message) {
        LOG.trace("{}", message);
        for (Pack pack : getPacks()) {
            LOG.trace("{} {}", pack, pack.getPatches());
        }
        LOG.trace("");
    }
}
