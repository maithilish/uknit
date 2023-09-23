package org.codetab.uknit.itest.invoke;

import java.io.File;

import org.codetab.uknit.itest.invoke.Model.Foo;

public class SameInstance {

    public void invokeOnSameInstance(final Foo foo, final File f1,
            final File f2) {
        File a = f1;
        File b = f1;
        File c = f2;
        foo.appendString(a.getAbsolutePath());
        foo.appendString(a.getAbsolutePath());
        foo.appendString(((a).getAbsolutePath()));
        foo.appendString(b.getAbsolutePath());
        foo.appendString(((b).getAbsolutePath()));
        foo.appendString(c.getAbsolutePath());
    }
}
