package org.codetab.uknit.itest.file.sub;

public class SubHierarchicalBuilderParameterImpl<U, V>

        extends SubBaseHierarchicalBuilderParameterImpl<U>

{

}

class SubBaseHierarchicalBuilderParameterImpl<U>
        implements ISubAnotherHierarchicalBuilderParameter<U> {

    @Override
    public String getConfig(final U key) {
        // TODO Auto-generated method stub
        return null;
    }

}

interface ISubHierarchicalBuilderParameter<V> {

    String getConfig(V key);
}
