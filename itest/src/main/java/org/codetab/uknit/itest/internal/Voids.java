package org.codetab.uknit.itest.internal;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Voids {

    private static final MBeanServer SERVER = getPlatformMBeanServer();

    private final ObjectName objectName = null;

    // TODO N - improve the test
    private static MBeanServer getPlatformMBeanServer() {
        try {
            return ManagementFactory.getPlatformMBeanServer();
        } catch (final LinkageError | Exception e) {
            return null;
        }
    }

    public void registerMBean(final Object object) {
        if (SERVER == null || objectName == null) {
            return;
        }
        try {
            SERVER.registerMBean(object, objectName);
        } catch (final LinkageError | Exception e) {
        }
    }

    public void callSimilarlyNamedMethods() {
        imc("a");
        imc(2);
    }

    private void imc(final String name) {
        return;
    }

    private void imc(final int name) {
        return;
    }
}
