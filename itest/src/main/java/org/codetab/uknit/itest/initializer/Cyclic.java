package org.codetab.uknit.itest.initializer;

import org.codetab.uknit.itest.initializer.Model.Foo;

public class Cyclic {

    /**
     * When names used in initializers are collected in
     * VarEnablers.collectNamesUsedInInitializers() the following test results
     * in cyclic reference. <code>
     * Pack   11   Var [name=value2, [value], type=Object] Exp [exp=(Object)base]
     * Pack   12   Var [name=base, type=Object] Exp [exp=(String)value]
     * </code>
     *
     * TODO L - output has cyclic var definitions, fix it.
     *
     */
    public void cyclicInInitializersReal() {
        getListReal();
    }

    private void getListReal() {
        final Object value = "foo";
        if (value instanceof String) {
            interpolateReal((String) value);
        } else {
            value.toString();
        }
    }

    private String interpolateReal(final String base) {
        final Object result = interpolateReal((Object) base);
        return result.toString();
    }

    private Object interpolateReal(final Object value) {
        return value;
    }

    /*
     * TODO L - The type of Foo should be update from Object to Foo as
     * interpolateMock((Foo) value) uses it as Foo.
     */
    public String cyclicInInitializersMock(final Object foo) {
        return getListMock(foo);
    }

    private String getListMock(final Object foo) {
        final Object value = foo;
        if (value instanceof Foo) {
            return interpolateMock((Foo) value);
        } else {
            return value.toString();
        }
    }

    private String interpolateMock(final Foo base) {
        final Object result = interpolateMock((Object) base);
        return result.toString();
    }

    private Object interpolateMock(final Object value) {
        return value;
    }

}
