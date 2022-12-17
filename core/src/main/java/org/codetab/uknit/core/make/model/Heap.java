package org.codetab.uknit.core.make.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.load.Loader;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class Heap implements Listener {

    @Inject
    private List<Pack> packs;

    @Inject
    private Patcher patcher;
    @Inject
    private Loader loader;

    /*
     * List of vars held by packs. The packsDirty field is set to true when ever
     * items are added to packs or var is changed. Cache is rebuilt when
     * packsDirty is true.
     */
    private List<IVar> varCache;

    private boolean packsDirty = true;

    // name of class under test
    private String cutName;

    /**
     * MUT - method under test. In IM Heap it is the internal method not the
     * calling method.
     */
    private MethodDeclaration mut;

    // test method call
    private Call call;

    // any MI in CUT throws exception
    private boolean testThrowsException;

    // is assert stmt created in test method
    private boolean asserted;

    public void setup() {
        patcher.setup();
    }

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

    public Patcher getPatcher() {
        return patcher;
    }

    public Loader getLoader() {
        return loader;
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

    public MethodDeclaration getMut() {
        return mut;
    }

    public void setMut(final MethodDeclaration mut) {
        this.mut = mut;
    }
}
