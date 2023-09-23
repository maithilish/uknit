package org.codetab.uknit.itest.exp.value;

import java.io.File;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

class ClassInstanceCreation {

    public void argIsArrayAccess(final Foo foo) {
        String[] cities = {"foo", "bar"};
        foo.appendObj(new Box(cities[0]));
        foo.appendObj(new Box(cities[1]));
        foo.appendObj((new Box((cities[(0)]))));

        foo.appendObj(new Box(cities[0], foo));
        foo.appendObj(new Box(cities[1], foo));
        foo.appendObj((new Box((cities[(0)]), foo)));
    }

    public void argIsArrayCreation(final Foo foo) {
        foo.appendObj(new Box(new String[] {"foo", "bar"}));
        foo.appendObj(new Box(new String[] {"foox", "barx"}));
        foo.appendObj((new Box(new String[] {("foo"), ("bar")})));

        String[] cities = {"fooxx", "barxx"};
        foo.appendObj(new Box(cities));
    }

    public void argIsCast(final Foo foo) {
        Object name = "foo";
        Object city = "bar";
        foo.appendBox(new Box((String) name));
        foo.appendBox(new Box((String) city));
        foo.appendBox((new Box(((String) (name)))));
    }

    @SuppressWarnings("deprecation")
    public void argIsChar(final Foo foo) {
        foo.appendObj(new Character('a'));
        foo.appendObj(new Character('b'));
        foo.appendObj((new Character(('a'))));
    }

    public void argIsClassInstanceCreation(final Foo foo) {
        foo.appendFile(new File(new String("foo")));
        foo.appendFile(new File(new String("bar")));
        foo.appendFile((new File((new String(("foo"))))));
    }

    public void argIsConditional(final Foo foo) {
        boolean flag = false;
        foo.appendFile(new File(flag ? "foo" : "bar"));
        foo.appendFile(new File(flag ? "bar" : "foo"));
        foo.appendFile((new File((flag) ? "foo" : "bar")));
    }

    /*
     * SEEK OPINION - difficult to test
     */
    public void argIsMethodRef(final Foo foo) {
        foo.appendBox(new Box("foo", String::toUpperCase));
        foo.appendBox(new Box("foo", String::toLowerCase));
        foo.appendBox(new Box("foo", String::toUpperCase));
    }

    /*
     * STEPIN - change box as spy instead of mock
     */
    public void argIsFieldAccess(final Foo foo, final Box box) {
        foo.appendObj(new File((box).name));
        foo.appendObj(new File((box).name));
        foo.appendObj((new File(((box).name))));
    }

    public void argIsInfix(final Foo foo, final Box box) {
        int code = 1;
        foo.appendObj(new Box(code > 0));
        foo.appendObj(new Box(code > 1));
        foo.appendObj((new Box(((code) > (0)))));
    }

    /*
     * STEPIN - change name type from String to Object
     */
    public void argIsInstanceof(final Foo foo, final Box box) {
        Object name = "foo";
        foo.appendObj(new Box(name instanceof String));
        foo.appendObj(new Box(name instanceof Foo));
        foo.appendObj((new Box(((name) instanceof String))));
    }

    /*
     * SEEK OPINION - difficult to test
     */
    public void argIsLambda(final Foo foo, final Box box) {
        String name = "foo";
        foo.appendObj(new Box(name, s -> s.toUpperCase()));
        foo.appendObj(new Box(name, s -> s.toLowerCase()));
        foo.appendObj((new Box((name), (s -> s.toUpperCase()))));
    }

    public void argIsMi(final Foo foo, final Box box) {
        foo.appendObj(new Box(foo.cntry()));
        foo.appendObj(new Box(foo.lang()));
        foo.appendObj((new Box(((foo).cntry()))));

        foo.appendObj(new Box(foo.lang(), foo.cntry()));
    }

    public void argIsNullLiteral(final Foo foo, final Box box) {
        foo.appendObj(new Box(null, null, null));
        foo.appendObj(new Box("foo", null, "baz"));
        foo.appendObj((new Box((null), (null), null)));
    }

    public void argIsNumberLiteral(final Foo foo, final Box box) {
        foo.appendObj(new Box(1));
        foo.appendObj(new Box(2L));
        foo.appendObj(new Box(3.3d));
        foo.appendObj(new Box(4.4f));
    }

    /*
     * STEPIN - uknit can't evaluate, fix verify
     */
    public void argIsPostfix(final Foo foo, final Box box) {
        int a = 0;
        int b = 10;
        foo.appendObj(new Box(a++));
        foo.appendObj(new Box(b++));
        foo.appendObj((new Box((a++))));
    }

    /*
     * STEPIN - uknit can't evaluate, fix verify
     */
    public void argIsPrefix(final Foo foo, final Box box) {
        int a = 0;
        int b = 10;
        foo.appendObj(new Box(--a));
        foo.appendObj(new Box(--b));
        foo.appendObj((new Box((--a))));
    }

    /*
     * STEPIN - change box as spy instead of mock
     */
    public void argIsQName(final Foo foo, final Box box) {
        foo.appendObj(new File(box.name));
        foo.appendObj(new File(box.name));
        foo.appendObj((new File((box.name))));
    }

    public void argIsSimpleName(final Foo foo, final Box box) {
        String a = "foo";
        String b = "bar";
        foo.appendObj(new File(a));
        foo.appendObj(new File(b));
        foo.appendObj((new File((a))));
    }

    String tfoo = "foo";
    String tbar = "bar";

    public void argIsThisExp(final Foo foo, final Box box) {
        foo.appendObj(new File(this.tfoo));
        foo.appendObj(new File(this.tbar));
        foo.appendObj((new File((this.tfoo))));
    }

    public void argIsTypeLiteral(final Foo foo, final Box box) {
        foo.appendObj(new Box(String.class));
        foo.appendObj(new Box(File.class));
        foo.appendObj((new Box((String.class))));
    }
}
