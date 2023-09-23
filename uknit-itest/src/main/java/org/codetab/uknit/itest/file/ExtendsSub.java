package org.codetab.uknit.itest.file;

import java.util.Date;

import org.codetab.uknit.itest.file.Model.Foo;
import org.codetab.uknit.itest.file.sub.ISubAnotherHierarchicalBuilderParameter;
import org.codetab.uknit.itest.file.sub.SubHierarchicalBuilderParameterImpl;

public class ExtendsSub<T>

        extends SubHierarchicalBuilderParameterImpl<Date, String>

        implements ISubAnotherHierarchicalBuilderParameter<Date> {

    public String foo(final Foo foo) {
        String config = super.getConfig(new Date());
        return foo.format(config);
    }

}
