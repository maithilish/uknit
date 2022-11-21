package org.codetab.uknit.itest.array;

public class Initializer {

    public String[] initialize() {
        final String[] array = {"hello", "world"};
        return array;
    }

    public String[] initializeAndAssign() {
        final String[] array = {"hello", "world"};
        String[] foo = array;
        return foo;
    }

    public String[] initializeNew() {
        final String[] array = new String[] {"hello", "world"};
        return array;
    }

    /**
     * The initializer {"foo"} not allowed as arg but new String[]{"foo"} is
     * allowed.
     *
     * @param s
     * @return
     */
    public StringBuilder initializeInArg(final StringBuilder s) {
        s.append(new String[] {"hello", "world"});
        return s;
    }

    public int[][] initializeMultiDim() {
        final int[][] array = {{1, 2}, {3, 4}};
        return array;
    }
}
