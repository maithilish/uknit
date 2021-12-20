package org.codetab.uknit.itest.array;

import java.util.List;

import org.codetab.uknit.itest.model.Groups;

public class CreateInfer {

    public String[] create(final Groups<String> groups) {
        String[] array = new String[3];
        return array;
    }

    // infer var in array creation
    public String[] createInfer(final Groups<String> groups) {
        String[] array = new String[groups.size()];
        return array;
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

    public String[] createArrayInMethodCall(final List<String> list) {
        String[] array = list.toArray(new String[0]);
        return array;
    }
}
