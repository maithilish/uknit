package org.codetab.uknit.itest.initializer;

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
    public void cyclicInInitializers() {
        getList();
    }

    private void getList() {
        final Object value = "foo";
        if (value instanceof String) {
            interpolate((String) value);
        } else {
            value.toString();
        }
    }

    private String interpolate(final String base) {
        final Object result = interpolate((Object) base);
        return result.toString();
    }

    private Object interpolate(final Object value) {
        return value;
    }
}
