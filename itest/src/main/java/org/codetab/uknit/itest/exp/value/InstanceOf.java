package org.codetab.uknit.itest.exp.value;

import java.time.LocalDate;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Dog;
import org.codetab.uknit.itest.exp.value.Model.Foo;
import org.codetab.uknit.itest.exp.value.Model.Pet;

class InstanceOf {

    // STEPIN - change array var to Object.
    public void leftExpIsArrayAccess(final Foo foo) {
        Object[] objs = {"foo", LocalDate.now()};
        foo.appendBoolean(objs[0] instanceof String);
        foo.appendBoolean(objs[1] instanceof String);
        foo.appendBoolean((objs[(0)]) instanceof String);
        foo.appendBoolean(((((objs[((0))])) instanceof String)));
    }

    // STEPIN - change array var to Object.
    public void leftExpIsArrayCreation(final Foo foo) {
        foo.appendBoolean(
                new Object[] {"foo", LocalDate.now()}[0] instanceof String);
        foo.appendBoolean(
                new Object[] {"foo", LocalDate.now()}[1] instanceof String);
        foo.appendBoolean(
                (new Object[] {"foo", LocalDate.now()}[(0)]) instanceof String);
        foo.appendBoolean(((((new Object[] {("foo"),
                (LocalDate.now())}[((0))])) instanceof String)));
    }

    // STEPIN - fix verify count
    public void leftExpIsCast(final Foo foo) {
        Object dog = new Dog();
        Object pet = new Pet();
        foo.appendBoolean((Dog) dog instanceof Pet);
        foo.appendBoolean((Pet) pet instanceof Pet);
        foo.appendBoolean((Dog) (dog) instanceof Pet);
        foo.appendBoolean((((Dog) ((dog))) instanceof Pet));
    }

    // STEPIN - fix verify count
    public void leftExpIsClassInstanceCreation(final Foo foo) {
        foo.appendBoolean(new Dog() instanceof Dog);
        foo.appendBoolean(new Pet() instanceof Pet);
        foo.appendBoolean((new Dog()) instanceof Dog);
        foo.appendBoolean((((new Dog())) instanceof Dog));
    }

    // STEPIN - fix verify count
    public void leftExpIsConditional(final Foo foo) {
        int count = 0;
        foo.appendBoolean(
                (count > 0 ? LocalDate.now() : new Dog()) instanceof Dog);
        foo.appendBoolean(
                (count == 0 ? new Dog() : LocalDate.now()) instanceof Dog);
        foo.appendBoolean(((count) > (0) ? (LocalDate.now())
                : (new Dog())) instanceof Dog);
        foo.appendBoolean(((((count) > (0) ? (LocalDate.now())
                : (new Dog()))) instanceof Dog));
    }

    // STEPIN - fix verify count
    public void leftExpIsMethodRef(final Foo foo) {
        foo.appendBoolean(
                foo.valueOf("1", Integer::valueOf) instanceof Integer);
        foo.appendBoolean(
                foo.valueOf("2", Integer::valueOf) instanceof Integer);
        foo.appendBoolean(
                foo.valueOf(("1"), (Integer::valueOf)) instanceof Integer);
        foo.appendBoolean(
                (foo.valueOf((("1")), (Integer::valueOf)) instanceof Integer));
    }

    // STEPIN - fix verify count
    public void leftExpIsFieldAccess(final Foo foo, final Box box) {
        foo.appendBoolean((box).iid instanceof Integer);
        foo.appendBoolean((box).iid instanceof Double);
        foo.appendBoolean(((box).iid) instanceof Integer);
        foo.appendBoolean(((((box).iid)) instanceof Integer));
    }

    // STEPIN - fix verify count
    public void leftExpIsInfix(final Foo foo, final Box box) {
        String a = "foo";
        String b = "bar";
        foo.appendBoolean(a + b instanceof String);
        foo.appendBoolean(a + b instanceof CharSequence);
        foo.appendBoolean((a) + (b) instanceof String);
        foo.appendBoolean((((a) + (b)) instanceof String));
    }

    // STEPIN - fix verify count
    public void leftExpIsLambda(final Foo foo) {
        foo.appendBoolean(
                foo.valueOf("1", s -> Integer.valueOf(s)) instanceof Integer);
        foo.appendBoolean(
                foo.valueOf("2", s -> Integer.valueOf(s)) instanceof Integer);
        foo.appendBoolean(foo.valueOf(("1"),
                (s -> Integer.valueOf(s))) instanceof Integer);
        foo.appendBoolean((foo.valueOf((("1")),
                (s -> Integer.valueOf(s))) instanceof Integer));
    }

    // STEPIN - fix verifies
    public void leftExpIsMI(final Foo foo) {
        foo.appendBoolean(foo.getObj() instanceof String);
        foo.appendBoolean(foo.getObj() instanceof LocalDate);
        foo.appendBoolean(((foo).getObj()) instanceof String);
        foo.appendBoolean((((((foo)).getObj())) instanceof String));
    }

    // STEPIN - fix verify count
    public void leftExpIsPostfix(final Foo foo) {
        Integer a = Integer.valueOf(5);
        Integer b = Integer.valueOf(5);
        foo.appendBoolean(a++ instanceof Integer);
        foo.appendBoolean(b++ instanceof Integer);
        foo.appendBoolean((a++) instanceof Integer);
        foo.appendBoolean((((a++)) instanceof Integer));
    }

    // STEPIN - fix verify count
    public void leftExpIsPrefix(final Foo foo) {
        Integer a = Integer.valueOf(5);
        Integer b = Integer.valueOf(5);
        foo.appendBoolean(++a instanceof Integer);
        foo.appendBoolean(++b instanceof Integer);
        foo.appendBoolean((++a) instanceof Integer);
        foo.appendBoolean((((++a)) instanceof Integer));
    }

    // STEPIN - fix verify count
    public void leftExpIsQName(final Foo foo, final Box box) {
        foo.appendBoolean(box.iid instanceof Integer);
        foo.appendBoolean(box.iid instanceof Double);
        foo.appendBoolean((box.iid) instanceof Integer);
        foo.appendBoolean((((box.iid)) instanceof Integer));
    }

    // STEPIN - change b type to Object
    public void leftExpIsSimpleName(final Foo foo) {
        Integer a = Integer.valueOf(1);
        Object b = Integer.valueOf(1);
        foo.appendBoolean(a instanceof Integer);
        foo.appendBoolean(b instanceof Double);
        foo.appendBoolean((a) instanceof Integer);
        foo.appendBoolean((((a)) instanceof Integer));
    }

    // STEPIN - fix verify count
    public void leftExpIsStringLiteral(final Foo foo) {
        foo.appendBoolean("foo" instanceof String);
        foo.appendBoolean("bar" instanceof String);
        foo.appendBoolean(("foo") instanceof String);
        foo.appendBoolean(((("foo")) instanceof String));
    }

    String bar = "bar";
    String baz = "baz";

    // STEPIN - fix verify count
    public void leftExpIsThis(final Foo foo) {
        foo.appendBoolean(this.bar instanceof String);
        foo.appendBoolean(this.baz instanceof String);
        foo.appendBoolean((this.bar) instanceof String);
        foo.appendBoolean((((this.bar)) instanceof String));
    }

    // STEPIN - fix verify count
    public void leftExpIsTypeLiteral(final Foo foo) {
        foo.appendBoolean(String.class instanceof Class);
        foo.appendBoolean(LocalDate.class instanceof Class);
        foo.appendBoolean((String.class) instanceof Class);
        foo.appendBoolean((((String.class)) instanceof Class));
    }
}
