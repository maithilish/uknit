package org.codetab.uknit.itest.exp.value;

import java.io.File;
import java.util.function.Function;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

class Conditional {

    /*
     * STEPIN - uknit doesn't evaluate value, update times() for verify
     * name.equals().
     *
     */
    public void expIsArrayAccess(final Foo foo) {
        boolean[] codes = {true, false};
        foo.appendString(codes[0] ? "foo" : "bar");
        foo.appendString(codes[1] ? "foo" : "bar");
        foo.appendString((codes[(1)]) ? ("foo") : ("bar"));

        String[] names = {"foo", "bar"};
        foo.appendString(names[0].equals("foo") ? "yes" : "no");
        foo.appendString(names[1].equals("foo") ? "yes" : "no");
        foo.appendString((names[(1)].equals("foo")) ? ("yes") : "no");
    }

    public void choiceIsArrayAccess(final Foo foo) {
        boolean code = true;
        String[] names = {"foo", "bar"};
        foo.appendString(code ? names[0] : names[1]);
        foo.appendString((code) ? (names[0]) : names[1]);

        code = false;
        foo.appendString(code ? names[0] : names[1]);
        foo.appendString((code) ? (names[(0)]) : (names[(1)]));
    }

    /*
     * STEPIN - uknit doesn't evaluate value, update times() for verify
     * name.equals().
     *
     */
    public void expIsArrayCreation(final Foo foo) {
        foo.appendString(new boolean[] {true, false}[0] ? "foo" : "bar");
        foo.appendString(new boolean[] {true, false}[1] ? "foo" : "bar");
        foo.appendString(
                (new boolean[] {true, false}[(1)]) ? ("foo") : ("bar"));

        foo.appendString(
                new String[] {"foo", "bar"}[0].equals("foo") ? "yes" : "no");
        foo.appendString(
                new String[] {"foo", "bar"}[1].equals("foo") ? "yes" : "no");
        foo.appendString(
                (new String[] {"foo", "bar"}[(1)].equals("foo")) ? ("yes")
                        : "no");
    }

    public void choiceIsArrayCreation(final Foo foo) {
        boolean code = true;
        foo.appendString(code ? new String[] {"foo", "bar"}[0]
                : new String[] {"foo", "bar"}[1]);
        foo.appendString((code) ? (new String[] {"foo", "bar"}[0])
                : new String[] {"foo", "bar"}[1]);

        code = false;
        foo.appendString(code ? new String[] {"foo", "bar"}[0]
                : new String[] {"foo", "bar"}[1]);
        foo.appendString((code) ? (new String[] {"foo", "bar"}[(0)])
                : (new String[] {"foo", "bar"}[(1)]));
    }

    @SuppressWarnings("unused")
    public void expIsBooleanLiteral(final Foo foo) {
        foo.appendString(true ? "foo" : "bar");
        foo.appendString(false ? "foo" : "bar");
        foo.appendString((false) ? ("foo") : "bar");
    }

    public void choiceIsBooleanLiteral(final Foo foo) {
        boolean flag = true;
        foo.appendObj(flag ? false : true);
        foo.appendObj((flag) ? (false) : true);
    }

    public void expIsCast(final Foo foo) {
        Object flag = true;
        foo.appendString((boolean) flag ? "foo" : "bar");
        foo.appendString(((boolean) (flag)) ? ("foo") : ("bar"));
    }

    public void choiceIsCast(final Foo foo) {
        boolean flag = true;
        Object a = "foo";
        Object b = "foo";
        foo.appendString(flag ? (String) a : (String) b);
        foo.appendString((flag) ? ((String) (a)) : (String) (b));
    }

    public void expIsCharLiteral(final Foo foo) {
        char ch = 'a';
        // not char literal but infix with char
        foo.appendString('a' == ch ? "foo" : "bar");
        foo.appendString('b' == ch ? "foo" : "bar");
        foo.appendString((('a') == (ch)) ? ("foo") : "bar");
    }

    public void choiceIsCharLiteral(final Foo foo) {
        boolean flag = true;
        foo.appendObj(flag ? 'a' : 'b');
        foo.appendObj((flag) ? ('a') : ('b'));
    }

    @SuppressWarnings("deprecation")
    public void expIsInstanceCreation(final Foo foo) {
        foo.appendString(new Boolean(true) ? "foo" : "bar");
        foo.appendString(new Boolean(false) ? "foo" : "bar");
        foo.appendString((new Boolean(false)) ? ("foo") : ("bar"));
    }

    public void choiceIsInstanceCreation(final Foo foo) {
        boolean flag = true;
        foo.appendObj(flag ? new String("foo") : new String("bar"));
        foo.appendObj((flag) ? (new String("foo")) : new String("bar"));
    }

    public void expIsConditional(final Foo foo) {
        boolean flag = true;
        foo.appendString((flag ? true : false) ? "foo" : "bar");
        foo.appendString((flag ? false : true) ? "foo" : "bar");
        foo.appendString(((flag) ? (true) : (false)) ? ("foo") : "bar");
    }

    public void choiceIsConditional(final Foo foo) {
        boolean flag = true;
        boolean code = false;
        foo.appendString(flag ? (code ? "foo" : "bar") : "bar");
        foo.appendString((flag) ? ((code) ? ("foo") : "bar") : ("bar"));
    }

    /*
     * SEEK OPINION - is Method Ref possible in conditional exp.
     */
    public void expIsMethodRef(final Foo foo) {
        // not exactly method ref
        foo.appendString(
                ((Function<String, Boolean>) Boolean::valueOf).apply("true")
                        ? "foo"
                        : "bar");
        foo.appendString(
                ((Function<String, Boolean>) Boolean::valueOf).apply("false")
                        ? "foo"
                        : "bar");
    }

    public void choiceIsMethodRef(final Foo foo) {
        // not exactly method ref
        boolean flag = true;
        foo.appendString(flag
                ? ((Function<String, String>) String::toUpperCase).apply("foo")
                : "bar");
    }

    public void expIsFieldAccess(final Foo foo, final Box box) {
        foo.appendString((box).done ? "foo" : "bar");
        foo.appendString(((box).done) ? ("foo") : "bar");
    }

    public void choiceIsFieldAccess(final Foo foo, final Box box) {
        boolean flag = true;
        foo.appendObj(flag ? (box).id : (box).done);
        foo.appendObj((flag) ? ((box).id) : ((box).done));
    }

    public void expIsInfix(final Foo foo) {
        int code = 2;
        foo.appendString(code > 1 ? "foo" : "bar");
        foo.appendString(code < 1 ? "foo" : "bar");
        foo.appendString((code > 1) ? ("foo") : "bar");
    }

    public void choiceIsInfix(final Foo foo) {
        boolean flag = true;
        int code = 2;
        foo.appendObj(flag ? code > 1 : code > 2);
        foo.appendObj((flag) ? (code) > 1 : (code > 2));
    }

    /*
     * STEPIN - change type of o from String to Object
     */
    public void expIsInstanceof(final Foo foo) {
        Object o = "foo";
        foo.appendString(o instanceof String ? "foo" : "bar");
        foo.appendString(o instanceof Integer ? "foo" : "bar");
        foo.appendString((o instanceof String) ? ("foo") : ("bar"));
    }

    /*
     * STEPIN - change type of o from String to Object
     */
    public void choiceIsInstanceof(final Foo foo) {
        boolean flag = true;
        Object o = "foo";
        foo.appendObj(flag ? o instanceof String : o instanceof Integer);
        foo.appendObj((flag) ? (o instanceof String) : (o instanceof Integer));
    }

    /*
     * SEEK OPINION - is Lambda possible in conditional exp.
     */

    public void expIsMi(final Foo foo) {
        foo.appendString(foo.isDone() ? "foo" : "bar");
        foo.appendString(foo.isDone() ? "bar" : "foo");
        foo.appendString(((foo).isDone()) ? ("foo") : ("bar"));
    }

    public void choiceIsMi(final Foo foo) {
        boolean flag = true;
        foo.appendObj(flag ? foo.cntry() : foo.lang());
        foo.appendObj((flag) ? (foo.cntry()) : (foo.lang()));
    }

    // exp of conditional can't be null literal

    public void choiceIsNullLiteral(final Foo foo) {
        foo.appendObj(foo.isDone() ? "foo" : null);
        foo.appendObj(foo.isDone() ? null : "bar");
        foo.appendObj((foo.isDone()) ? (null) : "bar");
    }

    // exp of conditional can't be number literal

    public void choiceIsNumberLiteral(final Foo foo) {
        foo.appendObj(foo.isDone() ? 1 : 2);
        foo.appendObj(foo.isDone() ? 1L : 2L);
        foo.appendObj(foo.isDone() ? 11.1f : 11.2f);
        foo.appendObj(foo.isDone() ? 21.1d : 21.2d);
        foo.appendObj((foo.isDone()) ? (21.1d) : (21.2d));
    }

    // exp of conditional can't be postfix and prefix

    /*
     * STEPIN - uknit can't evaluate values, fix verify
     */
    public void choiceIsPostfix(final Foo foo) {
        int a = 11;
        int b = 21;
        foo.appendObj(foo.isDone() ? a++ : b++);
        foo.appendObj((foo).isDone() ? (a++) : (b++));
    }

    /*
     * STEPIN - uknit can't evaluate values, fix verify
     */
    public void choiceIsPrefix(final Foo foo) {
        int a = 11;
        int b = 21;
        foo.appendObj(foo.isDone() ? --a : --b);
        foo.appendObj((foo).isDone() ? (--a) : (--b));
    }

    public void expIsQName(final Foo foo, final Box box) {
        foo.appendString(box.done ? "foo" : "bar");
        foo.appendString((box.done) ? ("foo") : ("bar"));
    }

    public void choiceIsQName(final Foo foo, final Box box) {
        boolean flag = true;
        foo.appendObj(flag ? box.id : box.done);
        foo.appendObj((flag) ? (box.id) : (box.done));
    }

    public void expIsSimpleName(final Foo foo) {
        boolean flag = false;
        foo.appendString(flag ? "foo" : "bar");
        foo.appendString((flag) ? ("foo") : "bar");
    }

    public void choiceIsSimpleName(final Foo foo) {
        boolean flag = false;
        String a = "foo";
        String b = "bar";
        foo.appendString(flag ? a : b);
        foo.appendString((flag) ? a : (b));
    }

    public void expIsParamVar(final Foo foo, final boolean flag) {
        boolean flag1 = flag;
        foo.appendString(flag1 ? "foo" : "bar");
        foo.appendString((flag1) ? ("foo") : ("bar"));
    }

    public void expIsParam(final Foo foo, final boolean flag) {
        foo.appendString(flag ? "foo" : "bar");
        foo.appendString((flag) ? ("foo") : "bar");
    }

    public void choiceIsParamVar(final Foo foo, final File f1, final File f2) {
        boolean flag = false;
        File a = f1;
        File b = f2;
        foo.appendObj(flag ? a : b);
        foo.appendObj((flag) ? a : (b));
    }

    public void choiceIsParam(final Foo foo, final File f1, final File f2) {
        boolean flag = false;
        foo.appendObj(flag ? f1 : f2);
        foo.appendObj((flag) ? (f1) : f2);
    }

    // exp of conditional can't be string literal, choice tested many times
    // above

    boolean fflag = true;
    String ffoo = "foo";
    String fbar = "bar";

    public void expIsThis(final Foo foo) {
        foo.appendString(this.fflag ? "foo" : "bar");
        foo.appendString(((this).fflag) ? ("foo") : "bar");
    }

    public void choiceIsThis(final Foo foo) {
        foo.appendString(this.fflag ? this.ffoo : this.fbar);
        foo.appendString(((this).fflag) ? ((this).ffoo) : this.fbar);
    }

    // exp of conditional can't be type literal

    public void choiceIsTypeLiteral(final Foo foo) {
        foo.appendObj(foo.isDone() ? String.class : Integer.class);
        foo.appendObj(((foo).isDone()) ? (String.class) : Integer.class);
    }
}
