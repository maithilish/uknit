package org.codetab.uknit.itest.reassign;

import org.codetab.uknit.itest.reassign.Model.Foo;

public class IsUsed {

    public String nullLiteral(final Foo foo) {
        String name = null;
        name = foo.format(name);
        foo.append(name);
        return name;
    }

    public String nullLiteralNoReassign(final Foo foo) {
        String name = null;
        foo.append(name);
        return name;
    }

    public String stringLiteral(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        foo.append(name);
        return name;
    }

    public String stringLiteralNoReassign(final Foo foo) {
        String name = "foo";
        foo.append(name);
        return name;
    }

    public String stringLiteralDiscardedByCreate(final Foo foo) {
        String name = "foo";
        name = new String("bar"); // discards the above
        foo.append(name);
        return name;
    }

    public String stringLiteralThenCreate(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        name = new String("bar");
        foo.append(name);
        return name;
    }

    /**
     * TODO L - Even though name is not used and discarded it is reassigned as
     * name2 in second stmt. No reassign happens when name is null. In other
     * cases it may be valid when stmt (as in next method) which may come also
     * crop between any two reassign anywhere. It is difficult know whether
     * discard or retain such whens. Reassign or discard is decided in
     * visit.Assignor$Reassigns.isReassign().
     *
     * @param foo
     * @return
     */
    public String stringLiteralDiscardedByInfix(final Foo foo) {
        String name = "foo";
        name = "bar" + "baz"; // discards the above
        foo.append(name);
        return name;
    }

    public String stringLiteralDiscardedByInfix2(final Foo foo) {
        String name = foo.format("foo");
        name = "bar" + "baz"; // discards the above
        foo.append(name);
        return name;
    }

    public String stringLiteralThenInfix(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        name = "bar" + "baz";
        foo.append(name);
        return name;
    }

    public String stringLiteralDiscardedByCreateInfix(final Foo foo) {
        String name = "foo";
        name = new String("bar" + "baz"); // discards the above
        foo.append(name);
        return name;
    }

    public String stringLiteralThenCreateInfix(final Foo foo) {
        String name = "foo";
        name = foo.format(name);
        name = new String("bar" + "baz");
        foo.append(name);
        return name;
    }

}
