package org.codetab.uknit.itest.file;

import java.util.Date;

import org.codetab.uknit.itest.file.Model.Foo;

public class Generic<T>
        extends FooHierarchicalBuilderParameterImpl<Date, String> {

    public String foo(final Foo foo) {
        String config = super.getConfig("foo");
        return foo.format(config);
    }

}

class FooHierarchicalBuilderParameterImpl<U, V>
        extends FooBaseHierarchicalBuilderParameterImpl<U>
        implements IFooHierarchicalBuilderParameter<V> {

    @Override
    public String getConfig(final V key) {
        return null;
    }

}

class FooBaseHierarchicalBuilderParameterImpl<U> {

}

interface IFooHierarchicalBuilderParameter<V> {

    String getConfig(V key);
}
