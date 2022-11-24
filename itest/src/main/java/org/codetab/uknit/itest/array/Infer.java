package org.codetab.uknit.itest.array;

import java.util.List;

import org.codetab.uknit.itest.array.Model.Bar;
import org.codetab.uknit.itest.array.Model.Foo;
import org.codetab.uknit.itest.array.Model.Groups;

public class Infer {

    public String[] create(final Groups<String> groups) {
        String[] array = new String[3];
        return array;
    }

    // infer var in array creation
    public String[] createInfer(final Groups<String> groups) {
        String[] array = new String[groups.size()];
        return array;
    }

    public String[] returnCreateInfer(final Groups<String> groups) {
        return new String[groups.size()];
    }

    public String[][] createTwoDim(final Groups<String> groups) {
        String[][] array = new String[3][2];
        return array;
    }

    // infer var in array creation
    public String[][] createInferTwoDim(final Groups<String> groups) {
        String[][] array = new String[groups.size()][1];
        return array;
    }

    public String[][] createMultiInferTwoDim(final Groups<String> groups) {
        String[][] array = new String[groups.size()][groups.size()];
        return array;
    }

    public String[][] returnCreateMultiInferTwoDim(
            final Groups<String> groups) {
        return new String[groups.size()][groups.size()];
    }

    public String[] createArrayInMethodCall(final List<String> list) {
        String[] array = list.toArray(new String[0]);
        return array;
    }

    public String[] returnArrayInMethodCall(final List<String> list) {
        return list.toArray(new String[0]);
    }

    public String[] inferInInitialize(final Foo foo, final Bar bar) {
        final String[] array = new String[] {foo.name(), bar.name()};
        return array;
    }

    public String[] returnInferInInitialize(final Foo foo, final Bar bar) {
        return new String[] {foo.name(), bar.name()};
    }

    public int inferInAccess(final Groups<Integer> groups) {
        int[] array = new int[groups.size()];
        array[groups.size()] = 10;

        int foo = array[groups.size()];
        return foo;
    }

    public int returnInferInAccess(final Groups<Integer> groups) {
        int[] array = new int[groups.size()];
        array[groups.size()] = 10;

        return array[groups.size()];
    }
}
