package org.codetab.uknit.core.make.model;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.node.ArgCapture;
import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.inject.assistedinject.Assisted;

public class Verify {

    /*
     * Method args such method call may be replaced, so we need reference to mi
     * to create the verify statement at the end.
     */
    private MethodInvocation mi;
    private List<ArgCapture> argCaptures;

    @Inject
    public Verify(@Assisted final MethodInvocation mi) {
        this.mi = mi;
    }

    public MethodInvocation getMi() {
        return mi;
    }

    public List<ArgCapture> getArgCaptures() {
        return argCaptures;
    }

    public void addArgCaptures(final List<ArgCapture> argCaptures) {
        if (isNull(this.argCaptures)) {
            this.argCaptures = new ArrayList<>();
        }
        this.argCaptures.addAll(argCaptures);
    }

    @Override
    public String toString() {
        return "Verify [mi=" + mi + "]";
    }
}
