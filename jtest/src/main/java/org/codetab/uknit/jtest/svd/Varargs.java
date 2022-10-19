package org.codetab.uknit.jtest.svd;

import java.util.Map;

/**
 * Single variable declaration (SVD) nodes are used in formal parameter lists,
 * enhanced for and catch clauses. They are not used for field declarations and
 * regular variable declaration. Initializer is not allowed.
 *
 * <pre>
 * SingleVariableDeclaration:
 *    { ExtendedModifier } Type {Annotation} [ <b>...</b> ] Identifier { Dimension } [ <b>=</b> Expression ]
 * </pre>
 * <p>
 *
 * TODO H - fix commented tests
 *
 * @author m
 *
 */
public class Varargs {

    // public String callWithSingleVarargs(final Map<String, String> names) {
    // return internalWithVarargs(names, "foo");
    // }
    //
    // public String callWithTwoVarargs(final Map<String, String> names) {
    // return internalWithVarargs(names, "foo", "bar");
    // }

    public String callWithArrayOfAnotherName(final Map<String, String> names) {
        String[] keysx = {"foo", "bar"};
        return internalWithVarargs(names, keysx);
    }

    public String callWithArrayOfSameName(final Map<String, String> names) {
        String[] keys = {"foo", "bar"};
        return internalWithVarargs(names, keys);
    }

    private String internalWithVarargs(final Map<String, String> names,
            final String... keys) {
        String name = null;
        for (final String key : keys) {
            name = names.get(key);
        }
        return name;
    }

    public String methodWithVarargs(final Map<String, String> names,
            final String... keys) {
        return names.get(keys[0]);
    }
}
