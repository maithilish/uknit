package org.codetab.uknit.itest.reassign;

import org.codetab.uknit.itest.reassign.Model.Foo;

/**
 * Update reassigned vars in when and verify stmts.
 *
 * @author Maithilish
 *
 */
public class WhenVerify {

    public void reassignWhenVerify(final Foo foo) {
        String name = "foo";
        name = foo.format(name); // when
        foo.append(name); // verify
    }

    public void reassignVerifyWhen(final Foo foo) {
        String name = "foo";
        foo.append(name); // verify
        name = foo.format(name); // when
    }

    public void reassignOnceWhenVerify(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        foo.append(name);
        name = "foo2";
        name = foo.format(name);
        foo.append(name);
    }

    public void reassignOnceVerifyWhen(final Foo foo) {
        String name = "foo";
        foo.append(name);
        name = foo.format(name);
        name = "foo2";
        foo.append(name);
        name = foo.format(name);
    }

    public void reassignTwiceWhenVerify(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        foo.append(name);
        name = "foo2";
        name = foo.format(name);
        foo.append(name);
        name = "foo3";
        name = foo.format(name);
        foo.append(name);
    }

    public void reassignTwiceVerifyWhen(final Foo foo) {
        String name = "foo";
        foo.append(name);
        name = foo.format(name);

        name = "foo2";
        foo.append(name);
        name = foo.format(name);

        name = "foo3";
        foo.append(name);
        name = foo.format(name);
    }

    public void reassignTwiceTwoArgsWhenVerify(final Foo foo) {
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
    }

    public void reassignTwiceTwoArgsVerifyWhen(final Foo foo) {
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
    }

    public void multiVarWhenVerify(final Foo foo) {

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
    }

    public void multiVarVerifyWhen(final Foo foo) {

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
    }
}
