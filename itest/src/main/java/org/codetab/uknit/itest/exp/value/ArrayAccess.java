package org.codetab.uknit.itest.exp.value;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * access the reassigned array item
     *
     * STEPIN - new String(...) is not evaluated and similar verifies are not
     * merged.
     * @param foo
     */
    public void reassignArrayItemSameValue(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        foo.append(array[1]);

        array[0] = new String("foo");
        array[1] = "bar";
        foo.append(array[0]);
        foo.append(array[1]);
    }

    // access the reassigned array item
    public void reassignArrayItem(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        foo.append(array[1]);

        array[0] = new String("foox");
        array[1] = "barx";
        foo.append(array[0]);
        foo.append(array[1]);
    }

    // access the array whose item is array creation
    public void accessArrayCreation(final Foo foo) {
        // embedded array initializer
        String[] array = {new String[] {"foo", "bar"}[0],
                new String[] {"foox", "barx"}[1]};
        foo.append(array[0]);
        foo.append(array[1]);
    }

    // access with index which is array creation
    public void arrayIndexIsArrayCreation(final Foo foo) {
        String[] array = {new String[] {"foo", "bar"}[0],
                new String[] {"foox", "barx"}[1]};
        foo.append(array[new int[] {0, 1}[0]]);
        foo.append(array[new int[] {0, 1}[1]]);
    }

    // access the array which is array creation
    public void arrayExpIsArrayCreation(final Foo foo) {
        foo.append(new String[] {"foo", "bar"}[0]);
        foo.append(new String[] {"foox", "barx"}[1]);
    }

    /**
     * Access the array whose items are boolean
     *
     * TODO L - verifies of (foo).append() and foo.append() are not merged. Fix.
     *
     * @param foo
     */
    public void accessBoolean(final Foo foo, final File file) {
        boolean[] array =
                {true, false, file.canExecute(), Boolean.valueOf(true)};
        foo.append(array[0]);
        foo.append(array[1]);
        foo.append((array[(2)]));
        foo.append((array[(3)]));

        (foo).append(array[0]);
    }

    // access the array whose items are casted
    public void accessCast(final Foo foo) {
        Object a = "foo";
        Object b = "foox";
        Object c = "baz";
        Object d = "qux";
        String[] strings = {(String) a, "bar"};
        Object[] objs = {(String) b, "barx"};
        Object[] creation =
                new Object[] {(String) new Object[] {(String) c, "barx"}[0],
                        (((String) (new Object[] {((String) (d))}[(0)])))};

        foo.append(strings[0]);
        foo.append(strings[1]);
        foo.append((String) objs[0]);
        foo.append(((String) (objs[(1)])));

        foo.append(((String) (creation[(0)])));
        foo.append(((String) (creation[(1)])));
    }

    /**
     * STEPIN - for objs[1] and objs[2] uknit generates String var but mockito
     * expects Object.
     *
     * @param foo
     */
    public void arrayIndexIsCast(final Foo foo) {
        Object[] objs = {"foo", new String("bar")};
        Object[] indexes = {0, 1};

        foo.append(objs[(int) indexes[0]]);
        foo.append(objs[((int) (indexes)[(1)])]);
    }

    // can't cast array of a type to another
    // public void arrayExpIsCast(final Foo foo) {
    // Object[] objs = {"foo", new String("bar")};
    //
    // foo.append(((String[]) objs)[0]);
    // foo.append(((((String[]) objs))[(1)]));
    //
    // objs[0] = new String("foox");
    // objs[1] = "barx";
    // String[] strings = (String[]) objs;
    // foo.append(strings[0]);
    // foo.append(strings[1]);
    //
    // objs[0] = new String("baz");
    // objs[1] = "qux";
    // String[] names = ((String[]) (objs));
    // foo.append(names[0]);
    // foo.append(names[1]);
    // }

    // access the array whose items are chars
    public void accessChar(final Foo foo, final Character ch) {
        char[] chars = {'a', ch.charValue(), Character.valueOf('c'),
                (((ch).charValue())), (Character.valueOf(('d')))};

        foo.append(chars[1]);
        foo.append(chars[2]);

        foo.append((chars[(0)]));
        foo.append((chars[(1)]));
        foo.append((chars[(2)]));
        foo.append((chars[(3)]));
        foo.append((chars[(4)]));
    }

    public void arrayIndexIsChar(final Foo foo) {
        String[] cities = {"foo", "bar", "baz"};

        char[] indexes = {'\1', ('\2'), '\0'};

        foo.append(cities[indexes['\0']]);
        foo.append(cities[indexes['\1']]);
        foo.append(cities[(indexes[('\2')])]);
    }

    /**
     * Access the array whose items are instance creation
     *
     * STEPIN - for new String("..") i.e. objs[1] uknit generates String var but
     * mockito expects Object.
     *
     * @param foo
     */
    public void accessClassInstanceCreation(final Foo foo) {
        Object[] objs = {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()};

        foo.append(objs[0]);
        foo.append(objs[1]);
        foo.append(objs[2]);

        objs = new Object[] {new String("barx"), (new File("foox")),
                (new HashMap<String, LocalTime>())};

        foo.append(objs[0]);
        foo.append(objs[1]);
        foo.append((objs[(2)]));
    }

    /**
     * STEPIN - for new String("..") i.e. objs[1] uknit generates String var but
     * mockito expects Object.
     *
     * @param foo
     */
    public void arrayIndexIsClassInstanceCreation(final Foo foo) {
        Object[] objs = {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()};

        foo.append(objs[new int[] {0, 1, 2}[0]]);
        foo.append(objs[new int[] {0, 1, 2}[1]]);
        foo.append(objs[new int[] {0, 1, 2}[2]]);

        objs = new Object[] {new String("barx"), (new File("foox")),
                (new HashMap<String, LocalTime>())};

        foo.append(objs[new int[] {0, 1, 2}[0]]);
        foo.append(objs[new int[] {0, ((1)), 2}[1]]);
        foo.append(objs[((new int[] {(0), (1), (2)})[(2)])]);
    }

    /**
     * STEPIN - add cast to var initializer. For new String("bar") i.e. array[1]
     * uknit generates String var but mockito expects Object.
     *
     * @param foo
     */
    public void arrayNameIsClassInstanceCreation(final Foo foo) {
        foo.append(new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[0]);
        foo.append(new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[1]);
        foo.append(new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[2]);
    }

    // access array whose items are conditional
    public void accessConditional(final Foo foo) {
        boolean flg = false;
        String[] cities = {flg ? "foo" : "bar", flg ? "bar" : "foo"};

        foo.append(cities[0]);
        foo.append(cities[1]);

        flg = true;
        cities = new String[] {((flg) ? ("foox") : "barx"),
                flg ? "barx" : "foox"};
        foo.append(cities[0]);
        foo.append(cities[1]);
    }

    public void indexIsconditional(final Foo foo) {

        String[] cities = {"foo", "bar"};
        int[] indexes = {1, 0};

        boolean flg = false;
        foo.append(cities[flg ? 1 : 0]);

        flg = true;
        foo.append(cities[indexes[flg ? indexes[0] : indexes[1]]]);

        flg = false;
        foo.append(
                (cities)[((indexes)[((flg) ? (indexes)[(0)] : indexes[(1)])])]);
    }

    public void arrayExpressionIsConditional(final Foo foo) {
        boolean flg = false;
        String[] cities = {"foo", "bar"};
        String[] states = {"foox", "barx"};

        int[] indexes = {1, 0};

        foo.append(flg ? cities : states[indexes[1]]);
        foo.append((((flg) ? (cities) : (states))[((indexes)[(0)])]));

        flg = true;
        foo.append((flg ? cities : states)[flg ? 0 : 1]);
    }

}
