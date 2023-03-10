package org.codetab.uknit.itest.superclass;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

class Voids extends SuperVoids {

    // TODO N - improve the tests
    private final MBeanServer SERVER = super.getPlatformMBeanServer();

    private final ObjectName objectName = null;

    public void registerMBean(final Object object) {
        if (SERVER == null || objectName == null) {
            return;
        }
        try {
            SERVER.registerMBean(object, objectName);
        } catch (final LinkageError | Exception e) {
        }
    }

}

class SuperVoids {

    public MBeanServer getPlatformMBeanServer() {
        try {
            return ManagementFactory.getPlatformMBeanServer();
        } catch (final LinkageError | Exception e) {
            return null;
        }
    }
}
