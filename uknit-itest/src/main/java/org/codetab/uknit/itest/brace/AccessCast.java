package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Foo;

class AccessCast {

    public String assignAccessPrimitive() {
        Object[] array = new Object[(1)];
        (array[(0)]) = "foo";

        String foo = ((String) array[(0)]);
        return foo;
    }

    public int returnAccess() {
        Object[] array = new Object[(1)];
        (array[(0)]) = 10;

        return (((int) (array[(0)])));
    }

    /**
     * STEPIN - two similar verifies are merged and this makes var of one verify
     * unused. Remove the unused one manually.
     *
     * The var is enabled/disabled before output generation and verify merge
     * happens in output phase and this results in dangling var.
     *
     * @param foo
     */
    public void declareTwoArray(final Foo foo) {
        Object[] array = {("foo"), (("bar"))};
        foo.append(((String) (array[(0)])));
        foo.append(((String) (array[(1)])));
        Object[] b = {(("foox")), ((new String("barx")))};
        foo.append((String) b[(0)]);
        foo.append((String) b[(1)]);
        foo.append(((String) (array[(0)])));
    }

    // reassign array with initializer is not allowed

    public void arrayParameter(final Foo foo, final Object[] array) {
        foo.append(((String) (array[(0)])));
        foo.append(((String) (array[(1)])));
        Object[] b = {(("foox")), ((new String("barx")))};
        foo.append((String) b[(0)]);
        foo.append((String) b[(1)]);
        foo.append(((String) (array[(0)])));
    }

    public Object accessInfix(final boolean flag) {
        Object[] array = {((10)), ((20))};
        Object foo = flag ? ((array[(1)])) : array[(0)];
        return foo;
    }

    public Object returnAccessInfix(final boolean flag) {
        Object[] array = {((10)), ((20))};
        return (flag) ? ((array[(0)])) : (array[(1)]);
    }

    public Object returnInitializedAccess() {
        Object[] array = {((10)), ((20))};
        return (array[(0)]);
    }
}
