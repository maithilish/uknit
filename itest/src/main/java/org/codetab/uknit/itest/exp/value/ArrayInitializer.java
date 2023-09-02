package org.codetab.uknit.itest.exp.value;

import java.time.LocalDate;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

/**
 * Test various types of expressions in an ArrayInitializer.
 *
 * ArrayInitializer: { [ Expression { , Expression} [ , ]] }
 *
 * @author Maithilish
 *
 */
public class ArrayInitializer {

    public void expIsArrayAccess(final Foo foo) {
        String[] names = {"foo", "bar"};
        String[] names2 = {"baz", "zoo"};
        foo.appendObj(new String[] {names[0], names[1]});
        foo.appendObj(new String[] {names2[0], names2[1]});
        foo.appendObj(new String[] {(names[(0)]), (names[(1)])});
        foo.appendObj((new String[] {((names[((0))])), ((names[((1))]))}));

    }

    // STEPIN - direct array verify fails, wrap eq() to the method arg.
    public void expIsArrayCreation(final Foo foo) {
        foo.appendObj(
                new String[][] {new String[] {"foo"}, new String[] {"bar"}});
        foo.appendObj(
                new String[][] {new String[] {"baz"}, new String[] {"zoo"}});
        foo.appendObj(new String[][] {(new String[] {("foo")}),
                (new String[] {("bar")})});
        foo.appendObj((new String[][] {((new String[] {(("foo"))})),
                ((new String[] {("bar")}))}));
    }

    public void expIsBoolean(final Foo foo) {
        foo.appendObj(new Boolean[] {true, false});
        foo.appendObj(new Boolean[] {false, true});
        foo.appendObj(new Boolean[] {(true), (false)});
        foo.appendObj((new Boolean[] {((true)), ((false))}));
    }

    public void expIsCast(final Foo foo) {
        Object a = "foo";
        Object b = "bar";
        foo.appendObj(new String[] {(String) a, (String) b});
        foo.appendObj(new String[] {(String) b, (String) a});
        foo.appendObj(new String[] {((String) (a)), ((String) (b))});
        foo.appendObj((new String[] {(((String) ((a)))), (((String) ((b))))}));
    }

    public void expIsCharacterLiteral(final Foo foo) {
        foo.appendObj(new char[] {'a', 'b'});
        foo.appendObj(new char[] {'b', 'a'});
        foo.appendObj(new char[] {('a'), ('b')});
        foo.appendObj((new char[] {(('a')), (('b'))}));
    }

    public void expIsClassInstanceCreation(final Foo foo) {
        foo.appendObj(new String[] {new String("foo"), new String("bar")});
        foo.appendObj(new String[] {new String("bar"), new String("foo")});
        foo.appendObj(
                new String[] {(new String(("foo"))), (new String(("bar")))});
        foo.appendObj((new String[] {((new String((("foo"))))),
                ((new String((("bar")))))}));
    }

    public void expIsConditional(final Foo foo) {
        int a = 1;
        foo.appendObj(
                new String[] {a > 1 ? new String("foo") : new String("bar")});
        foo.appendObj(
                new String[] {a > 0 ? new String("baz") : new String("zoo")});
        foo.appendObj(new String[] {
                (a) > (1) ? (new String("foo")) : (new String("bar"))});
        foo.appendObj((new String[] {((a)) > ((1)) ? ((new String("foo")))
                : ((new String("bar")))}));

    }

    /**
     * TODO - try to improve the test
     *
     * STEPIN - fix verifies
     *
     * @param foo
     */
    public void expIsMethodRef(final Foo foo) {
        foo.appendObj(new int[] {foo.valueOf("1", Integer::valueOf)});
        foo.appendObj(new int[] {foo.valueOf("2", Integer::valueOf)});
        foo.appendObj(new int[] {(foo).valueOf(("1"), (Integer::valueOf))});
        foo.appendObj(
                new int[] {(((foo)).valueOf((("1")), ((Integer::valueOf))))});
    }

    // STEPIN - initialize fields
    public void expIsFieldAccess(final Foo foo, final Box box) {
        foo.appendObj(new int[] {(box).id, (box).id2});
        foo.appendObj(new int[] {(box).id2, (box).id});
        foo.appendObj(new int[] {((box).id), ((box).id2)});
        foo.appendObj((new int[] {(((box).id)), (((box).id2))}));
    }

    public void expIsInfix(final Foo foo) {
        foo.appendObj(new int[] {1 + 1, 1 + 12});
        foo.appendObj(new int[] {1 + 12, 1 + 1});
        foo.appendObj(new int[] {(1 + 1), (1 + 12)});
        foo.appendObj((new int[] {((1 + 1)), ((1 + 12))}));
    }

    // STEPIN - change b type to Object
    public void expIsInstanceof(final Foo foo) {
        Object a = "foo";
        Object b = "bar";
        foo.appendObj(
                new boolean[] {a instanceof String, b instanceof LocalDate});
        foo.appendObj(
                new boolean[] {b instanceof LocalDate, a instanceof String});
        foo.appendObj(new boolean[] {(a instanceof String),
                (b instanceof LocalDate)});
        foo.appendObj((new boolean[] {((a instanceof String)),
                ((b instanceof LocalDate))}));
    }

    /**
     * TODO - try to improve the test
     *
     * STEPIN - fix verifies
     *
     * @param foo
     */
    public void expIsLambda(final Foo foo) {
        foo.appendObj(new int[] {foo.valueOf("1", a -> Integer.valueOf(a))});
        foo.appendObj(new int[] {foo.valueOf("2", a -> Integer.valueOf(a))});
        foo.appendObj(
                new int[] {(foo).valueOf(("1"), (a -> Integer.valueOf(a)))});
        foo.appendObj(new int[] {
                (((foo)).valueOf((("1")), ((a -> Integer.valueOf(a)))))});
    }

    // STEPIN - change infer var init values
    public void expIsMi(final Foo foo, final Box box) {
        foo.appendObj(new Number[] {box.getId(), box.getFid()});
        foo.appendObj(new Number[] {box.getFid(), box.getId()});
        foo.appendObj(new Number[] {(box.getId()), (box.getFid())});
        foo.appendObj((new Number[] {((box.getId())), ((box.getFid()))}));
    }

    public void expIsNull(final Foo foo) {
        foo.appendObj(new String[] {null, null});
        foo.appendObj(new String[] {null, null, null});
        foo.appendObj(new String[] {(null), (null)});
        foo.appendObj((new String[] {((null)), ((null))}));
    }

    public void expIsNumberLiteral(final Foo foo) {
        foo.appendObj(new Number[] {1, 11});
        foo.appendObj(new Number[] {11, 1});
        foo.appendObj(new Number[] {(1), (11)});
        foo.appendObj((new Number[] {((1)), ((11))}));
    }

    // STEPIN - uKnit can't evaluate postfix value, fix verifies
    public void expIsPostfix(final Foo foo) {
        int a = 1;
        int b = 11;
        foo.appendObj(new Number[] {a++, b++});
        foo.appendObj(new Number[] {b++, a++});
        foo.appendObj(new Number[] {(a++), (b++)});
        foo.appendObj((new Number[] {((a++)), ((b++))}));
    }

    // STEPIN - uKnit can't evaluate prefix value, fix verifies
    public void expIsPrefix(final Foo foo) {
        int a = 1;
        int b = 11;
        foo.appendObj(new Number[] {++a, ++b});
        foo.appendObj(new Number[] {++b, ++a});
        foo.appendObj(new Number[] {(++a), (++b)});
        foo.appendObj((new Number[] {((++a)), ((++b))}));
    }

    // STEPIN - initialize fields
    public void expIsQName(final Foo foo, final Box box) {
        foo.appendObj(new int[] {box.id, box.id2});
        foo.appendObj(new int[] {box.id2, box.id});
        foo.appendObj(new int[] {(box.id), (box.id2)});
        foo.appendObj((new int[] {((box.id)), ((box.id2))}));
    }

    public void expIsSimpleName(final Foo foo) {
        int a = 1;
        int b = 11;
        foo.appendObj(new Number[] {a, b});
        foo.appendObj(new Number[] {b, a});
        foo.appendObj(new Number[] {(a), (b)});
        foo.appendObj((new Number[] {((a)), ((b))}));
    }

    public void expIsStringLiteral(final Foo foo) {
        foo.appendObj(new String[] {"foo", "bar"});
        foo.appendObj(new String[] {"baz", "zoo"});
        foo.appendObj(new String[] {("foo"), ("bar")});
        foo.appendObj((new String[] {(("foo")), (("bar"))}));
    }

    int fa = 1;
    int fb = 11;

    public void expIsThisExp(final Foo foo) {
        foo.appendObj(new Number[] {this.fa, this.fb});
        foo.appendObj(new Number[] {this.fb, this.fa});
        foo.appendObj(new Number[] {(this.fa), (this.fb)});
        foo.appendObj((new Number[] {((this.fa)), ((this.fb))}));
    }

    public void expIsTypeLiteral(final Foo foo) {
        foo.appendObj(new Class[] {String.class, Integer.class, void.class});
        foo.appendObj(new Class[] {Integer.class, String.class, void.class});
        foo.appendObj(
                new Class[] {(String.class), (Integer.class), (void.class)});
        foo.appendObj((new Class[] {((String.class)), ((Integer.class)),
                ((void.class))}));
    }
}
