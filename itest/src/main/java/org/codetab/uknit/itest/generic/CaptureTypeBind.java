package org.codetab.uknit.itest.generic;

public class CaptureTypeBind {

    /**
     * The newInstance().resolveTypeBinding() returns a capture binding.
     *
     * @return
     * @throws Exception
     */
    public String foo() throws Exception {
        final Class<?> clz = Class.forName("java.lang.String");
        return (String) clz.getConstructor(String.class).newInstance("Foo");
    }
}
