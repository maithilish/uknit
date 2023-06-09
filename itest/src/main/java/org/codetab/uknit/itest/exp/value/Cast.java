package org.codetab.uknit.itest.exp.value;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Dog;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.codetab.uknit.itest.exp.value.Model.Pet;
import org.codetab.uknit.itest.exp.value.Model.Pitbull;

class Cast {

    // cast exp is ArrayAccess
    public void expIsArrayAccess(final Foo foo) {
        Object[] cities = {"foo", "bar", "baz"};

        foo.appendString((String) cities[0]);
        foo.appendString((String) cities[1]);
        foo.appendString(((String) cities[(1)]));
        foo.appendString(((String) (cities)[(2)]));

        cities[1] = "barx";

        foo.appendString((String) cities[1]);
    }

    /*
     * cast exp is ArrayCreation
     *
     * runtime error - can't cast Object{} to String[]
     */
    public void expIsArrayCreation(final Foo foo) {
        foo.appendStringArray((String[]) new Object[] {"foo", "bar"});
        foo.appendStringArray(((String[]) (new Object[] {"foo", "bar"})));
        foo.appendStringArray(((String[]) (new Object[] {("foo"), ("bar")})));
    }

    public void expIsBooleanLiteral(final Foo foo) {
        Object t = true;
        Object f = false;
        foo.appendBoolean((Boolean) t);
        foo.appendBoolean((Boolean) f);
        foo.appendBoolean(((Boolean) (f)));
    }

    // cast exp is cast exp
    public void expIsCastExp(final Foo foo, final Object dog) {
        foo.appendPitbull((Pitbull) ((Pet) dog));
        foo.appendPitbull(((Pitbull) (((Pet) dog))));
    }

    public void expIsCharacter(final Foo foo) {
        Object a = 'a';
        Object b = 'b';
        foo.appendCharacter((Character) a);
        foo.appendCharacter((Character) b);
        foo.appendCharacter(((Character) (b)));
    }

    /*
     * runtime error - can't cast Dog to Pitbull
     */
    public void expIsNewInstanceCreation(final Foo foo) {
        foo.appendPitbull((Pitbull) new Dog());
        foo.appendPitbull(((Pitbull) new Dog()));
    }

    public void expIsConditional(final Foo foo) {
        boolean flg = true;
        foo.appendObj(flg ? (Integer) 2 : (Float) 2f);
        foo.appendObj(flg ? (Integer) 3 : (Float) 3f);
        foo.appendObj(flg ? ((Integer) 3) : ((Float) (3f)));
    }

    public void expIsFieldAccess(final Foo foo, final Box box) {
        foo.appendString((String) (box).obj);
        foo.appendString(((String) (box).obj));
        foo.appendString(((String) ((box).obj)));
    }

    public void expIsMethodRef(final Foo foo) {
        foo.appendString(
                ((Function<String, String>) String::toLowerCase).apply("BAR"));
        foo.appendString(((BiFunction<String, String, String>) String::concat)
                .apply("foo", "bar"));
    }

    public void expIsNullLiteral(final Foo foo) {
        foo.appendString((String) null);
        foo.appendString(((String) (null)));
    }

    public void expIsNumberLiteral(final Foo foo) {
        foo.appendString(String.valueOf((Integer) 1));
        foo.appendString(String.valueOf((Integer) 2));
        foo.appendString(String.valueOf(((Integer) (2))));
    }

    /*
     * STEPIN - uknit can't evaluate values, split the merged verifies
     */
    public void expIsPostfix(final Foo foo) {
        int a = 5;
        int b = 6;
        foo.appendString(String.valueOf((Integer) a++));
        foo.appendString(String.valueOf((Integer) b++));
        foo.appendString(String.valueOf(((Integer) (b++))));
    }

    /*
     * STEPIN - uknit can't evaluate values, split the merged verifies
     */
    public void expIsPrefix(final Foo foo) {
        int a = 5;
        int b = 8;
        foo.appendString(String.valueOf((Integer) (--a)));
        foo.appendString(String.valueOf((Integer) (--b)));
        foo.appendString(String.valueOf(((Integer) ((--b)))));
    }

    public void expIsQName(final Foo foo, final Box box) {
        foo.appendString((String) box.obj);
        foo.appendString(((String) box.obj));
        foo.appendString(((String) (box.obj)));
    }

    public void expIsSimpleName(final Foo foo) {
        Object o = "foo";
        foo.appendString((String) o);
        foo.appendString(((String) o));
        foo.appendString(((String) (o)));
    }

    // can't cast string literal

    Object tobj = "foo";

    /*
     * STEPIN - remove mock field
     */
    public void expIsThisExp(final Foo foo) {
        foo.appendString((String) this.tobj);
        foo.appendString(((String) this.tobj));
        foo.appendString(((String) (this).tobj));
    }

    // can't cast type literal
}
