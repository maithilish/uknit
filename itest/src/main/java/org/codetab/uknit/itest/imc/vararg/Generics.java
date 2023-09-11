package org.codetab.uknit.itest.imc.vararg;

import java.util.ArrayList;
import java.util.List;

import org.codetab.uknit.itest.imc.vararg.Model.Foo;

import com.google.common.collect.Lists;

/**
 *
 * TODO N - enable itest.
 *
 * @author Maithilish
 *
 */
public class Generics {

    // Type wildcard
    public void wildcard(final Foo foo) {
        wildcardIm(foo, Lists.newArrayList(), Lists.newArrayList("foo"));
    }

    @SafeVarargs
    private void wildcardIm(final Foo foo, final List<? extends String>... va) {
        foo.appendObj(va[0].get(0));
    }

    // Type parameterized
    public void parameterized(final Foo foo) {
        parameterizedIm(foo, Lists.newArrayList(), Lists.newArrayList("foo"));
    }

    @SafeVarargs
    private void parameterizedIm(final Foo foo, final List<String>... va) {
        foo.appendObj(va[0].get(0));
    }

    // wildcard and parameterized array
    @SuppressWarnings("unchecked")
    public void uncheckedWildcardArrays(final Foo foo) {
        // Cannot create a generic array of List<String>, so unchecked
        uncheckedWildcardArraysIm(foo, new List[1], new ArrayList[2]);

    }

    @SafeVarargs
    private void uncheckedWildcardArraysIm(final Foo foo,
            final List<? extends String>[]... va) {
        foo.appendObj(String.valueOf(va[0]));
    }

}
