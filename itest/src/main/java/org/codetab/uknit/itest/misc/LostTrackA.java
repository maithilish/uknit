package org.codetab.uknit.itest.misc;

public class LostTrackA {

    private Synchronizer synchronizer;

    public final Object getProperty(final String key) {
        beginRead(false);
        try {
            return "foo";
        } finally {
            endRead();
        }
    }

    public final Synchronizer getSynchronizer() {
        final Synchronizer sync = synchronizer;
        return sync != null ? sync : sync;
    }

    protected void beginRead(final boolean optimize) {
        getSynchronizer().beginRead();
    }

    protected void endRead() {
        getSynchronizer().endRead();
    }
}

interface Synchronizer {

    void beginRead();

    void endRead();

    void beginWrite();

    void endWrite();
}
