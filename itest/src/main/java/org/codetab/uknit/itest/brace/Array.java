package org.codetab.uknit.itest.brace;

import org.codetab.uknit.itest.brace.Model.Bar;
import org.codetab.uknit.itest.brace.Model.Foo;
import org.codetab.uknit.itest.brace.Model.Groups;

public class Array {

    public int assignAccessPrimitive() {
        int[] array = new int[(1)];
        array[(0)] = 10;

        int foo = array[(0)];
        return (foo);
    }

    public int returnAccess() {
        int[] array = new int[1];
        array[(0)] = (10);

        return (array[(0)]);
    }

    public int[][] createIntTwoDimArray() {
        int[][] anArray = new int[(2)][(3)];
        anArray[(0)][(0)] = (100);
        return anArray;
    }

    public String[][] createMultiInferTwoDim(final Groups<String> groups) {
        String[][] array = (new String[(groups.size())][(groups.size())]);
        return array;
    }

    public String[][] returnMultiInferTwoDim(final Groups<String> groups) {
        return (new String[(groups.size())][(groups.size())]);
    }

    public String[] inferInInitialize(final Foo foo, final Bar bar) {
        final String[] array = new String[] {(foo.name()), (bar.name())};
        return array;
    }

    public String[] returnInferInInitialize(final Foo foo, final Bar bar) {
        return new String[] {(foo.name()), (bar.name())};
    }

    public int returnInferInAccess(final Groups<Integer> groups) {
        int[] array = new int[groups.size()];
        (array[(groups.size())]) = 10;

        return (array[(groups.size())]);
    }

    public StringBuilder initializeInArg(final StringBuilder s) {
        s.append((new String[] {("hello"), ("world")}));
        return s;
    }

    public int[][] initializeMultiDim() {
        final int[][] array = {{(1), (2)}, {(3), (4)}};
        return array;
    }

}
