package org.codetab.uknit.itest.imc.reassign;

import org.codetab.uknit.itest.imc.reassign.Model.Foo;

/**
 * Update reassigned vars in when and verify stmts.
 *
 * @author Maithilish
 *
 */
class WhenVerifyConflict {

    public String callReassignWhenVerify(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignWhenVerify(foo);
        return name;
    }

    private String reassignWhenVerify(final Foo foo) {
        String name = "foo";
        name = foo.format(name); // when
        foo.append(name); // verify
        return name;
    }

    public String callReassignVerifyWhen(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignVerifyWhen(foo);
        return name;
    }

    private String reassignVerifyWhen(final Foo foo) {
        String name = "foo";
        foo.append(name); // verify
        name = foo.format(name); // when
        return name;
    }

    public String callReassignOnceWhenVerify(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignOnceWhenVerify(foo);
        return name;
    }

    private String reassignOnceWhenVerify(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        foo.append(name);
        name = "foo2";
        name = foo.format(name);
        foo.append(name);
        return name;
    }

    public String callReassignOnceVerifyWhen(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignOnceVerifyWhen(foo);
        return name;
    }

    private String reassignOnceVerifyWhen(final Foo foo) {
        String name = "foo";
        foo.append(name);
        name = foo.format(name);
        name = "foo2";
        foo.append(name);
        name = foo.format(name);
        return name;
    }

    public String callReassignTwiceWhenVerify(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignTwiceWhenVerify(foo);
        return name;
    }

    private String reassignTwiceWhenVerify(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        foo.append(name);
        name = "foo2";
        name = foo.format(name);
        foo.append(name);
        name = "foo3";
        name = foo.format(name);
        foo.append(name);
        return name;
    }

    public String callReassignTwiceVerifyWhen(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignTwiceVerifyWhen(foo);
        return name;
    }

    private String reassignTwiceVerifyWhen(final Foo foo) {
        String name = "foo";
        foo.append(name);
        name = foo.format(name);

        name = "foo2";
        foo.append(name);
        name = foo.format(name);

        name = "foo3";
        foo.append(name);
        name = foo.format(name);
        return name;
    }

    public String callReassignTwiceTwoArgsWhenVerify(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignTwiceTwoArgsWhenVerify(foo);
        return name;
    }

    private String reassignTwiceTwoArgsWhenVerify(final Foo foo) {
        String name = "foo";
        String city = "bar";
        name = foo.format(name, city);
        foo.append(name, city);
        name = "foo2";
        city = "bar2";
        name = foo.format(name, city);
        foo.append(name, city);
        name = "foo3";
        city = "bar3";
        name = foo.format(name, city);
        foo.append(name, city);
        return name;
    }

    public String callReassignTwiceTwoArgsVerifyWhen(final Foo foo) {
        String name = "zoo";
        name = "zoox";
        name = reassignTwiceTwoArgsVerifyWhen(foo);
        return name;
    }

    private String reassignTwiceTwoArgsVerifyWhen(final Foo foo) {
        String name = "foo";
        String city = "bar";
        foo.append(name, city);
        name = foo.format(name, city);

        name = "foo2";
        city = "bar2";
        foo.append(name, city);
        name = foo.format(name, city);

        name = "foo3";
        city = "bar3";
        foo.append(name, city);
        name = foo.format(name, city);
        return name;
    }

    public String callMultiVarWhenVerify(final Foo foo) {
        String city = "zoo";
        city = "zoox";
        city = multiVarWhenVerify(foo);
        return city;
    }

    private String multiVarWhenVerify(final Foo foo) {

        String name = "foo";
        name = foo.format(name);
        foo.append(name);

        String street = "boo";
        street = foo.format(street);
        foo.append(street);
        street = "boo2";
        street = foo.format(street);
        foo.append(street);

        String city = "bar";
        city = foo.format(city);
        foo.append(city);
        city = "bar2";
        city = foo.format(city);
        foo.append(city);
        city = "baz3";
        city = foo.format(city);
        foo.append(city);
        city = "baz4";
        city = foo.format(city);
        foo.append(city);
        city = "baz5";
        city = foo.format(city);
        foo.append(city);

        return city;
    }

    public String callMultiVarVerifyWhen(final Foo foo) {
        String city = "zoo";
        city = "zoox";
        city = multiVarVerifyWhen(foo);
        return city;
    }

    private String multiVarVerifyWhen(final Foo foo) {

        String name = "foo";
        foo.append(name);
        name = foo.format(name);

        String street = "boo";
        foo.append(street);
        street = foo.format(street);
        street = "boo2";
        foo.append(street);
        street = foo.format(street);

        String city = "bar";
        foo.append(city);
        city = foo.format(city);
        city = "bar2";
        foo.append(city);
        city = foo.format(city);
        city = "baz3";
        foo.append(city);
        city = foo.format(city);
        city = "baz4";
        foo.append(city);
        city = foo.format(city);
        city = "baz5";
        foo.append(city);
        city = foo.format(city);

        return city;
    }
}
