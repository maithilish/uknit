package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Foo;

class ArrayAccess {

    /*
     * for strObjs[0] uknit generates String var but mockito expects Object.
     */
    public void arrayItemType(final Foo foo) {
        Object[] objs = {10, 20};
        int[] ints = {10, 20};
        Object[] strObjs = {"foo", "bar"};
        String[] strings = {"foox", "barx"};
        foo.append(objs[0]);
        foo.append(ints[1]);
        foo.append(strObjs[0]);
        foo.append(strings[1]);
    }

    public void accessWithVar(final Foo foo) {
        int a = 0;
        int b = 1;
        int i = 1;
        int j = 0;
        int m = 0;
        int n = 1;
        String[] c1 = new String[] {("x"), "y"};
        String[] c2 = c1;
        String[] c3 = c2;
        String[] cities = c3;
        int[] indexes = {i, j};
        int[] indexes1 = new int[] {(a), (b)};

        foo.append(cities[indexes[indexes1[m]]]);
        foo.append(cities[indexes[indexes1[n]]]);

        // braces
        foo.append((cities)[(indexes)[indexes1[(m)]]]);
        foo.append(((cities)[((indexes)[((indexes1)[(n)])])]));
    }

    public void arrayNameIsExpression(final Foo foo) {
        boolean flg = false;
        String[] cities = {"foo", "bar"};
        String[] states = {"foox", "barx"};
        int[] indexes = {1, 0};

        foo.append((flg ? cities : states)[indexes[1]]);

        flg = true;

        foo.append((flg ? cities : states)[indexes[1]]);
    }
}
