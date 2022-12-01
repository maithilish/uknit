package org.codetab.uknit.itest.brace;

import java.util.Locale;

public class Recursive {

    public int array1() {
        int[] array = new int[(3)];
        array[(2)] = (10);
        int foo = array[(2)];
        return (foo);
    }

    public int array2() {
        int[] array = new int[(3)];
        ((array[((1))])) = 20;
        (array[(0)]) = ((array[((1))]));
        int foo = (array[(0)]);
        return (foo);
    }

    public int array3() {
        int[] array = new int[(3)];
        ((array[((2))])) = (30);
        ((array[((1))])) = ((array[((2))]));
        (array[(0)]) = ((array[((1))]));
        int foo = (array[(0)]);
        return (foo);
    }

    public int returnArrayAccess() {
        int[] array = new int[(3)];
        ((array[((2))])) = (30);
        ((array[((1))])) = ((array[((2))]));
        (array[(0)]) = ((array[((1))]));
        return (array[(0)]);
    }

    public String invokeInit(final Locale locale) {
        String[] array = new String[(3)];
        ((array[((2))])) = ("x");
        ((array[((1))])) = (locale).getDisplayName();
        (array[(0)]) = ((array[((1))]));
        String foo = (array[(0)]);
        return (foo);
    }
}
