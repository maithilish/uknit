package org.codetab.uknit.itest.file;

import org.codetab.uknit.itest.file.Model.Duck;

/**
 * Class definition in multi line.
 *
 * @author m
 *
 */
public class MultiLine extends HierarchicalBuilderParametersImpl {

    public String foo(final Duck duck) {
        return super.bar(duck);
    }
}

class HierarchicalBuilderParametersImpl extends FileBasedBuilderParametersImpl

        implements

        HierarchicalBuilderProperties<HierarchicalBuilderParametersImpl> {

    public String bar(final Duck duck) {
        duck.dive("dive in super 1");
        return super.baz(duck);
    }

}

class FileBasedBuilderParametersImpl {

    public String baz(final Duck duck) {
        duck.dive("dive in super 2");
        return duck.fly("dive in super 2");
    }
}

interface HierarchicalBuilderProperties<T> {

}
