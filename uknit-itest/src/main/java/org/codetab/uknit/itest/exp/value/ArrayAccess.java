package org.codetab.uknit.itest.exp.value;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.codetab.uknit.itest.exp.value.Model.StaticBox;

class ArrayAccess {

    /*
     * for strObjs[0] uknit generates String var but mockito expects Object.
     */
    public void arrayItemType(final Foo foo) {
        Object[] objs = {10, 20};
        int[] ints = {10, 20};
        Object[] strObjs = {"foo", "bar"};
        String[] strings = {"foox", "barx"};
        foo.appendObj(objs[0]);
        foo.appendObj(ints[1]);
        foo.appendObj(strObjs[0]);
        foo.appendString(strings[1]);
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

        foo.appendString(cities[indexes[indexes1[m]]]);
        foo.appendString(cities[indexes[indexes1[n]]]);

        // braces
        foo.appendString((cities)[(indexes)[indexes1[(m)]]]);
        foo.appendString(((cities)[((indexes)[((indexes1)[(n)])])]));
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
        foo.appendString(array[1]);

        array[0] = new String("foo");
        array[1] = "bar";
        foo.appendString(array[0]);
        foo.appendString(array[1]);
    }

    public void reassignIndex(final Foo foo) {
        String[] cities = {"foo", "bar", "baz"};

        int index = 0;
        foo.appendString(cities[index]);

        index = 1;
        foo.appendString(cities[index]);

        index = 2;
        foo.appendString(cities[index]);
    }

    // access the reassigned array item
    public void reassignArrayItem(final Foo foo) {
        String[] array = {"foo", new String("bar")};
        foo.appendString(array[1]);

        array[0] = new String("foox");
        array[1] = "barx";
        foo.appendString(array[0]);
        foo.appendString(array[1]);
    }

    // access the array whose item is array creation
    public void accessArrayCreation(final Foo foo) {
        // embedded array initializer
        String[] array = {new String[] {"foo", "bar"}[0],
                new String[] {"foox", "barx"}[1]};
        foo.appendString(array[0]);
        foo.appendString(array[1]);
    }

    // access with index which is array creation
    public void arrayIndexIsArrayCreation(final Foo foo) {
        String[] array = {new String[] {"foo", "bar"}[0],
                new String[] {"foox", "barx"}[1]};
        foo.appendString(array[new int[] {0, 1}[0]]);
        foo.appendString(array[new int[] {0, 1}[1]]);
    }

    // access the array which is array creation
    public void arrayExpIsArrayCreation(final Foo foo) {
        foo.appendString(new String[] {"foo", "bar"}[0]);
        foo.appendString(new String[] {"foox", "barx"}[1]);
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
        foo.appendObj(array[0]);
        foo.appendObj(array[1]);
        foo.appendObj((array[(2)]));
        foo.appendObj((array[(3)]));

        (foo).appendObj(array[0]);
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

        foo.appendString(strings[0]);
        foo.appendString(strings[1]);
        foo.appendString((String) objs[0]);
        foo.appendString(((String) (objs[(1)])));

        foo.appendString(((String) (creation[(0)])));
        foo.appendString(((String) (creation[(1)])));
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

        foo.appendObj(objs[(int) indexes[0]]);
        foo.appendObj(objs[((int) (indexes)[(1)])]);
    }

    // // can't cast array of a type to another
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

        foo.appendObj(chars[1]);
        foo.appendObj(chars[2]);

        foo.appendObj((chars[(0)]));
        foo.appendObj((chars[(1)]));
        foo.appendObj((chars[(2)]));
        foo.appendObj((chars[(3)]));
        foo.appendObj((chars[(4)]));
    }

    public void arrayIndexIsChar(final Foo foo) {
        String[] cities = {"foo", "bar", "baz"};

        char[] indexes = {'\1', ('\2'), '\0'};

        foo.appendString(cities[indexes['\0']]);
        foo.appendString(cities[indexes['\1']]);
        foo.appendString(cities[(indexes[('\2')])]);
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

        foo.appendObj(objs[0]);
        foo.appendObj(objs[1]);
        foo.appendObj(objs[2]);

        objs = new Object[] {new String("barx"), (new File("foox")),
                (new HashMap<String, LocalTime>())};

        foo.appendObj(objs[0]);
        foo.appendObj(objs[1]);
        foo.appendObj((objs[(2)]));
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

        foo.appendObj(objs[new int[] {0, 1, 2}[0]]);
        foo.appendObj(objs[new int[] {0, 1, 2}[1]]);
        foo.appendObj(objs[new int[] {0, 1, 2}[2]]);

        objs = new Object[] {new String("barx"), (new File("foox")),
                (new HashMap<String, LocalTime>())};

        foo.appendObj(objs[new int[] {0, 1, 2}[0]]);
        foo.appendObj(objs[new int[] {0, ((1)), 2}[1]]);
        foo.appendObj(objs[((new int[] {(0), (1), (2)})[(2)])]);
    }

    /**
     * STEPIN - add cast to var initializer. For new String("bar") i.e. array[1]
     * uknit generates String var but mockito expects Object.
     *
     * @param foo
     */
    public void arrayNameIsClassInstanceCreation(final Foo foo) {
        foo.appendObj(new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[0]);
        foo.appendObj(new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[1]);
        foo.appendObj(new Object[] {new File("foo"), new String("bar"),
                new ArrayList<LocalDate>()}[2]);
    }

    // access array whose items are conditional
    public void accessConditional(final Foo foo) {
        boolean flg = false;
        String[] cities = {flg ? "foo" : "bar", flg ? "bar" : "foo"};

        foo.appendString(cities[0]);
        foo.appendString(cities[1]);

        flg = true;
        cities = new String[] {((flg) ? ("foox") : "barx"),
                flg ? "barx" : "foox"};
        foo.appendString(cities[0]);
        foo.appendString(cities[1]);
    }

    public void indexIsconditional(final Foo foo) {

        String[] cities = {"foo", "bar"};
        int[] indexes = {1, 0};

        boolean flg = false;
        foo.appendString(cities[flg ? 1 : 0]);

        flg = true;
        foo.appendString(cities[indexes[flg ? indexes[0] : indexes[1]]]);

        flg = false;
        foo.appendString(
                (cities)[((indexes)[((flg) ? (indexes)[(0)] : indexes[(1)])])]);
    }

    public void arrayExpressionIsConditional(final Foo foo) {
        boolean flg = false;
        String[] cities = {"foo", "bar"};
        String[] states = {"foox", "barx"};

        int[] indexes = {1, 0};

        foo.appendObj(flg ? cities : states[indexes[1]]);
        foo.appendString((((flg) ? (cities) : (states))[((indexes)[(0)])]));

        flg = true;
        foo.appendString((flg ? cities : states)[flg ? 0 : 1]);
    }

    int findex = 0;
    String[] fcities = {"ffoo", "fbar", "fbaz"};
    String fa = "ffoo";
    String fb = "fbar";
    String fc = "fbaz";
    String[] arrayOfFields = {fa, fb, fc};

    public void arrayIsField(final Foo foo) {
        int index = 0;
        foo.appendString(fcities[index]);

        index = 1;
        foo.appendString(fcities[index]);

        (index) = 2;
        foo.appendString(((fcities)[(index)]));

        index = 0;
        foo.appendString(((fcities)[(index)]));
    }

    public void arrayIsFieldOfFields(final Foo foo) {
        int index = 0;
        foo.appendString(arrayOfFields[index]);

        index = 1;
        foo.appendString(arrayOfFields[index]);

        (index) = 2;
        foo.appendString(((arrayOfFields)[(index)]));

        index = 1;
        foo.appendString(((arrayOfFields)[(index)]));
    }

    public void arrayItemIsField(final Foo foo) {
        String[] cities = {fa, fb, fc};

        int index = 0;
        foo.appendString(cities[index]);

        index = 1;
        foo.appendString(cities[index]);

        (index) = 2;
        foo.appendString(((cities)[(index)]));

        index = 1;
        foo.appendString(((cities)[(index)]));
    }

    public void accessIndexIsField(final Foo foo) {
        String[] cities = {"foo", "bar", "baz"};

        foo.appendString(cities[findex]);

        findex = 1;
        foo.appendString(cities[findex]);

        (findex) = 2;
        foo.appendString(((cities)[(findex)]));

        findex = 1;
        foo.appendString(((cities)[(findex)]));
    }

    /**
     * TODO L - array access value is obtained from field array instead of
     * local.
     *
     * @param foo
     */
    public void arrayItemAndIndexHidesField(final Foo foo) {
        String fa = "foo";
        String fb = "bar";
        String fc = "baz";
        String[] fcities = {fa, fb, fc};

        int findex = 0;
        foo.appendString(fcities[findex]);

        findex = 1;
        foo.appendString(fcities[findex]);

        (findex) = 2;
        foo.appendString(((fcities)[(findex)]));

        findex = 1;
        foo.appendString(((fcities)[(findex)]));
    }

    /*
     * Array is field access
     *
     * STEPIN - change box from mock to spy
     */
    public void arrayIsFieldAccess(final Foo foo, final Box box) {
        foo.appendString((box).items[1]);
    }

    /**
     * assign field access array to local var
     *
     * STEPIN - change cities[0] var initialization and change box to spy.
     *
     * @param foo
     * @param box
     */
    public void localArrayIsFieldAccess(final Foo foo, final Box box) {
        String[] cities = (box).items;
        foo.appendString(cities[0]);
    }

    /*
     * array items are fields access
     *
     * STEPIN - change box to spy.
     */
    public void arrayItemIsFieldAccess(final Foo foo, final Box box) {
        String[] cities = {(box).items[0], (box).items[1]};
        foo.appendString(cities[0]);
    }

    /*
     * array access index is field access
     *
     * STEPIN - change box to spy.
     *
     * TODO N - can a mock used via FieldAccess be a spy
     */
    public void indexIsFieldAccess(final Foo foo, final Box box) {
        foo.appendString((box).items[(box).id]);
    }

    /*
     * Array item is infix.
     *
     * STEPIN - uknit can't evaluate value, step in to correct the test.
     */
    public void arrayItemIsInfix(final Foo foo) {
        int i = 0;
        int[] codes = {i + 1, (i - 1)};

        foo.appendObj(codes[0]);
        foo.appendObj(codes[1]);
    }

    /*
     * Array access index is infix.
     *
     * STEPIN - uknit can't evaluate value, step in to correct the test.
     *
     */
    public void arrayIndexIsInfix(final Foo foo) {
        int i = 0;
        String[] cities = {"foo", "bar"};

        foo.appendString(cities[i + 1]);
        foo.appendString(cities['\0' + 1]);
        foo.appendString(cities[i == 1 ? 0 : 1]);

        foo.appendString(cities[((i) + (1))]);
    }

    /*
     * Array item is instanceof.
     *
     * STEPIN - change file var type to Object.
     *
     */
    public void arrayItemIsInstanceOf(final Foo foo) {
        Object bar = new File("foo");
        boolean[] codes = {foo instanceof Foo, bar instanceof String};

        foo.appendObj(codes[0]);
        foo.appendObj(codes[1]);
    }

    /*
     * array item is lambda
     *
     * TODO L - enable capture for lambda
     */
    public void arrayItemIsLambda(final Foo foo) {
        @SuppressWarnings("rawtypes")
        Function[] codes = {(a) -> a.toString(), (b) -> b.toString()};

        foo.appendObj(codes[0]);
        foo.appendObj(codes[1]);
    }

    /*
     * array is from invoke.
     *
     * STEPIN - each invoke returns separate array instead of single array.
     */
    public void arrayIsInvoke(final Foo foo, final Box box) {
        foo.appendString(box.getItems()[0]);
        foo.appendString(box.getItems()[1]);
        foo.appendString((box.getItems())[(2)]);
    }

    // array var is invoke
    public void arrayVarIsInvoke(final Foo foo, final Box box) {
        String[] items = box.getItems();
        foo.appendString(items[0]);
        foo.appendString(items[1]);
        foo.appendString((items)[(2)]);
    }

    // array item is invoke
    public void arrayItemIsInvoke(final Foo foo) {
        String[] codes = {foo.lang(), foo.cntry(), (foo.name())};

        foo.appendString(codes[0]);
        foo.appendString((codes)[1]);
        foo.appendString(codes[2]);
    }

    public void arrayAccessInvoke(final Foo foo, final File f1, final File f2) {
        File[] files = {f1, f2};
        foo.appendString(files[0].getAbsolutePath());
        foo.appendString(files[0].getAbsolutePath());
        foo.appendString(((files[(0)]).getAbsolutePath()));
        foo.appendString(files[1].getAbsolutePath());
    }

    // array access index is invoke
    public void arrayIndexIsInvoke(final Foo foo, final Box box) {
        String[] cities = {"foo", "bar", "baz"};

        foo.appendString(cities[box.getId()]);
        foo.appendString((cities)[box.getId()]);
        foo.appendString(cities[((box).getId())]);
    }

    public void arrayIndexIsInvokeItemReassigned(final Foo foo) {
        String[] array = {"foo", "bar"};
        foo.appendString(array[foo.index()]);
        array[1] = "barx";
        foo.appendString(array[((foo).index())]);
    }

    // array exp is static call
    public void arrayIsStaticInvoke(final Foo foo) {
        foo.appendString(StaticBox.getItems()[0]);
        foo.appendString(StaticBox.getItems()[1]);
        foo.appendString(StaticBox.getItems()[2]);
        foo.appendString((StaticBox.getItems())[(2)]);
    }

    // array item is static call
    public void arrayItemIsStaticInvoke(final Foo foo, final Box box) {
        String[] cities = {String.valueOf("foo"), String.valueOf("bar"),
                (String.valueOf("bar"))};

        foo.appendString(cities[0]);
        foo.appendString(cities[1]);
        foo.appendString(cities[2]);
        foo.appendString(cities[(2)]);
    }

    /*
     * array index is static call.
     *
     * STEPIN - uknit can't evaluate index exp value, so initialize vars with
     * proper value
     */
    public void arrayIndexIsStaticInvoke(final Foo foo, final Box box) {
        String[] cities = {"foo", "bar", "baz"};

        foo.appendString(cities[Integer.valueOf("0")]);
        foo.appendString(cities[Integer.valueOf("1")]);
        foo.appendString(cities[Integer.valueOf("2")]);
        foo.appendString(cities[(Integer.valueOf(("2")))]);
    }

    public void arrayAccessInStaticInvoke(final Foo foo) {
        String[] cities = {"foo", "foox", "baz"};
        int a = 1;
        int b = 1;
        int c = 2;
        foo.appendString(Foo.valueOf(cities[a]));
        foo.appendString((Foo.valueOf((cities[(a)]))));
        foo.appendString((Foo.valueOf((cities[(b)]))));
        foo.appendString(Foo.valueOf(cities[c]));
    }

    // null array, access not allowed
    // public void arrayIsNullLiteral(final Foo foo) {
    // String[] codes = null;
    //
    // foo.append(codes[0]);
    // }

    // array item is null
    public void arrayItemIsNullLiteral(final Foo foo) {
        String[] codes = {null, null, "baz"};

        foo.appendString(codes[0]);
        foo.appendString((codes)[1]);
        foo.appendString((codes)[(2)]);
    }

    // array item is number literal
    public void arrayItemIsNumberLiteral(final Foo foo) {
        int[] ints = {1, 2, 3};
        short[] shorts = {11, 12, 13};
        long[] longs = {21, 22L, 23L};
        float[] floats = {31f, 32f, 33f};
        double[] doubles = {41.11d, 42.22d, 43.33};

        foo.appendObj(ints[0]);
        foo.appendObj((ints)[1]);
        foo.appendObj((ints)[(2)]);

        foo.appendObj(shorts[0]);
        foo.appendObj((shorts)[1]);
        foo.appendObj((shorts)[(2)]);

        foo.appendObj(longs[0]);
        foo.appendObj((longs)[1]);
        foo.appendObj((longs)[(2)]);

        foo.appendObj(floats[0]);
        foo.appendObj((floats)[1]);
        foo.appendObj((floats)[(2)]);

        foo.appendObj(doubles[0]);
        foo.appendObj((doubles)[1]);
        foo.appendObj((doubles)[(2)]);
    }

    /*
     * Postfix and Prefix expressions are not allowed in ArrayInitializer and
     * ArrayAccess index.
     */

    /*
     * Array is QualifedName
     *
     * STEPIN - change box from mock to spy
     */
    public void arrayIsQName(final Foo foo, final Box box) {
        foo.appendString(box.items[1]);

        foo.appendString((box.items)[1]);
    }

    /*
     * assign QName array to local var
     *
     * STEPIN - change cities[0] var initialization and change box to spy.
     */
    public void localArrayIsQName(final Foo foo, final Box box) {
        String[] cities = box.items;
        String[] items = (box.items);
        foo.appendString(cities[0]);
        foo.appendString(items[0]);
    }

    /*
     * array item is QName
     *
     * STEPIN - change box to spy and set proper value for var which (uknit
     * can't evaluate vars)
     */
    public void arrayItemIsQName(final Foo foo, final Box box) {
        String[] cities = {box.items[0], ((box.items)[(1)])};
        foo.appendString(cities[0]);
        foo.appendString(cities[1]);
    }

    /*
     * array access index is QName
     *
     * STEPIN - change box to spy.
     *
     * TODO N - can a mock used via QName be a spy
     */
    public void indexIsQName(final Foo foo, final Box box) {
        foo.appendString(box.items[box.id]);

        foo.appendString(((box.items)[(box.id)]));
    }

    String[] tDevices = {"foo", "bar", "baz"};
    int[] tIndexes = {0, 1, 2};

    // array exp with this
    public void arrayExpHasThis(final Foo foo) {
        foo.appendString(this.tDevices[0]);
        foo.appendString(this.tDevices[1]);
        foo.appendString(this.tDevices[2]);
    }

    // array item with this
    public void arrayItemsHasThis(final Foo foo) {
        String[] names = {this.tDevices[0], this.tDevices[1], this.tDevices[2]};
        foo.appendString(names[0]);
        foo.appendString(names[1]);
        foo.appendString(names[2]);
    }

    // array index with this
    public void arrayIndexHasThis(final Foo foo) {
        foo.appendString(this.tDevices[this.tIndexes[0]]);
        foo.appendString(this.tDevices[this.tIndexes[1]]);
        foo.appendString(this.tDevices[this.tIndexes[2]]);
    }

    // array exp has type literal
    public void arrayExpHasTypeLiteral(final Foo foo) {
        foo.appendObj(String.class.getDeclaredMethods()[0]);
        foo.appendObj(Integer.class.getDeclaredMethods()[0]);
        foo.appendObj(File.class.getDeclaredMethods()[0]);
    }

    // array item is type literal
    public void arrayItemIsTypeLiteral(final Foo foo) {
        Class<?>[] clzs = {String.class, Integer.class, File.class};
        foo.appendObj(clzs[0]);
        foo.appendObj(clzs[1]);
        foo.appendObj(clzs[2]);
        foo.appendObj(((clzs)[(2)]));
    }

    // array index has type literal
    public void arrayIndexHasTypeLiteral(final Foo foo) {
        Class<?>[] clzs = {String.class, Integer.class, File.class};
        foo.appendObj(clzs[String.class.getModifiers() - 16]);
        foo.appendObj(clzs[Integer.class.getModifiers() - 16]);
        foo.appendObj(clzs[File.class.getModifiers()]);
    }
}
