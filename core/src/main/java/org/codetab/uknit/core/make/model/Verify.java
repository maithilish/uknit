package org.codetab.uknit.core.make.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.inject.assistedinject.Assisted;

public class Verify {

    /*
     * Method args such method call may be replaced, so we need reference to mi
     * to create the verify statement at the end.
     */
    private MethodInvocation mi;
    private boolean inCtlFlowPath;
    private List<ArgCapture> argCaptures;
    /*
     * -1 unprocessed, 0 processed but another similar verify's times is
     * incremented and this verify is ignored, > 0 verify is called n times
     */
    private int times;

    @Inject
    public Verify(@Assisted final MethodInvocation mi,
            @Assisted final boolean inCtlFlowPath) {
        this.mi = mi;
        this.inCtlFlowPath = inCtlFlowPath;
        argCaptures = new ArrayList<>();
        times = -1;
    }

    public MethodInvocation getMi() {
        return mi;
    }

    public List<ArgCapture> getArgCaptures() {
        return argCaptures;
    }

    public boolean isInCtlFlowPath() {
        return inCtlFlowPath;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(final int times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "Verify [mi=" + mi + ", times=" + times + "]";
    }
}
