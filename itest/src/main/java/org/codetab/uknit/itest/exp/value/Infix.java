package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

/**
 * In many tests separate verify is created if extended exp is wrapped in
 * parenthesize. Ex: <code>
 *  foo.appendString("baz" + "foo" + a);
 *  foo.appendString("baz" + ("foo" + a));
 *  </code>
 *
 * For some statements the extra parenthesize is mandatory, hence uKnit retains
 * it for many cases which creates two verifies for above example instead of one
 * with times(2). To retain or discard the parenthesize in decided in in
 * core.make.exp.srv.SafeExpSetter.
 *
 * Infix exp may returns true or false. uKnit can't evaluate values of infix so
 * fix verify in such tests.
 *
 * @author Maithilish
 *
 */
class Infix {

    public void rightExpIsArrayAccess(final Foo foo) {
        int[] codes = {10, 12, 14};
        foo.appendInt(1 - codes[0]);
        foo.appendInt(1 - codes[1]);
        foo.appendInt((1) - (codes[(0)]));
        foo.appendInt((((1)) - (((codes)[((0))]))));
    }

    public void leftExpIsArrayAccess(final Foo foo) {
        int[] codes = {10, 12, 14};
        foo.appendInt(codes[0] - 1);
        foo.appendInt(codes[1] - 1);
        foo.appendInt((codes[(0)]) - (1));
        foo.appendInt(((((codes)[((0))])) - (1)));
    }

    public void extendedExpHasArrayAccess(final Foo foo) {
        int[] codes = {10, 12, 14};
        foo.appendInt(1 - 1 - codes[0]);
        foo.appendInt(1 - 1 - codes[1]);
        foo.appendInt((1) - (1) - (codes[(0)]));
        foo.appendInt((((1)) - ((1)) - (((codes)[((0))]))));
    }

    public void rightExpIsArrayCreation(final Foo foo) {
        foo.appendInt(1 - new int[] {5, 6, 7}[0]);
        foo.appendInt(1 - new int[] {5, 6, 7}[1]);
        foo.appendInt((1) - (new int[] {(5), (6), (7)}[(0)]));
        foo.appendInt((((1)) - ((new int[] {((5)), ((6)), ((7))}[((0))]))));
    }

    public void leftExpIsArrayCreation(final Foo foo) {
        foo.appendInt(new int[] {5, 6, 7}[0] - 1);
        foo.appendInt(new int[] {5, 6, 7}[1] - 1);
        foo.appendInt((new int[] {(5), (6), (7)}[(0)]) - (1));
        foo.appendInt((((new int[] {((5)), ((6)), ((7))}[((0))])) - (1)));
    }

    public void extendedExpHasArrayCreation(final Foo foo) {
        foo.appendInt(1 - 1 - new int[] {5, 6, 7}[0]);
        foo.appendInt(1 - 1 - new int[] {5, 6, 7}[1]);
        foo.appendInt((1) - (1) - (new int[] {(5), (6), (7)}[(0)]));
        foo.appendInt(
                (((1)) - ((1)) - (((new int[] {((5)), ((6)), ((7))}[((0))])))));
    }

    public void rightExpIsBooleanLiteral(final Foo foo) {
        boolean flag = false;
        foo.appendBoolean(flag == true);
        foo.appendBoolean(flag != true);
        foo.appendBoolean((flag) == (true));
        foo.appendBoolean((((flag)) == ((true))));
    }

    public void leftExpIsBooleanLiteral(final Foo foo) {
        boolean flag = false;
        foo.appendBoolean(true == flag);
        foo.appendBoolean(true == flag);
        foo.appendBoolean((true) == (flag));
        foo.appendBoolean((((true)) == (((flag)))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     */
    public void extendedExpHasBooleanLiteral(final Foo foo) {
        boolean flag = false;
        foo.appendBoolean(true == flag == true);
        foo.appendBoolean(true == flag != true);
        foo.appendBoolean((true) == (flag) == (true));
        foo.appendBoolean((((true)) == (((flag)) == ((true)))));
    }

    public void rightExpIsCast(final Foo foo) {
        Object a = Integer.valueOf(1);
        Object b = Integer.valueOf(2);
        foo.appendInt(11 - (Integer) a);
        foo.appendInt(22 - (Integer) b);
        foo.appendInt((11) - ((Integer) (a)));
        foo.appendInt((((11)) - (((Integer) (a)))));
    }

    public void leftExpIsCast(final Foo foo) {
        Object a = Integer.valueOf(1);
        Object b = Integer.valueOf(2);
        foo.appendInt((Integer) a - 11);
        foo.appendInt((Integer) b - 22);
        foo.appendInt(((Integer) (a)) - (11));
        foo.appendInt(((((Integer) (a))) - ((11))));
    }

    public void extendedExpHasCast(final Foo foo) {
        Object a = Integer.valueOf(1);
        Object b = Integer.valueOf(2);
        foo.appendInt(11 - (Integer) a - 1);
        foo.appendInt(22 - (Integer) b - 1);
        foo.appendInt((11) - ((Integer) (a)) - (1));
        foo.appendInt((((11)) - (((Integer) (a))) - ((1))));
    }

    public void rightExpIsChar(final Foo foo) {
        foo.appendInt(1 - 'a');
        foo.appendInt(1 - 'b');
        foo.appendInt((1) - ('a'));
        foo.appendInt((((1)) - (('a'))));
    }

    public void leftExpIsChar(final Foo foo) {
        foo.appendInt('a' - 1);
        foo.appendInt('b' - 1);
        foo.appendInt(('a') - (1));
        foo.appendInt(((('a')) - ((1))));
    }

    public void extendedExpHasChar(final Foo foo) {
        foo.appendInt(1 - 'a' - 'b');
        foo.appendInt(1 - 'b' - 'c');
        foo.appendInt(1 - ('a') - ('b'));
        foo.appendInt(((1) - ((('b')) - (('b')))));
    }

    @SuppressWarnings("deprecation")
    public void rightExpIsClassInstance(final Foo foo) {
        foo.appendInt(11 - new Integer(1));
        foo.appendInt(21 - new Integer(2));
        foo.appendInt((11) - (new Integer((1))));
        foo.appendInt((((11)) - ((new Integer((1))))));
    }

    @SuppressWarnings("deprecation")
    public void leftExpIsClassInstance(final Foo foo) {
        foo.appendInt(new Integer(1) - 11);
        foo.appendInt(new Integer(2) - 21);
        foo.appendInt((new Integer((1))) - (11));
        foo.appendInt((((new Integer((1)))) - ((11))));
    }

    @SuppressWarnings("deprecation")
    public void extendedExpHasClassInstance(final Foo foo) {
        foo.appendInt(11 - new Integer(1) - 11);
        foo.appendInt(21 - new Integer(2) - 21);
        foo.appendInt((11) - (new Integer((1))) - (11));
        foo.appendInt((((11)) - ((new Integer((1)))) - ((11))));
    }

    public void rightExpIsConditional(final Foo foo) {
        int a = 0;
        foo.appendInt(1 + (a < 1 ? 1 : 0));
        foo.appendInt(1 + (a > 1 ? 1 : 0));
        foo.appendInt((1) + ((a) < (1) ? (1) : (0)));
        foo.appendInt((((1)) + ((((a)) < ((1)) ? ((1)) : ((0))))));
    }

    public void leftExpIsConditional(final Foo foo) {
        int a = 0;
        foo.appendInt((a < 1 ? 1 : 0) + 1);
        foo.appendInt((a > 1 ? 1 : 0) + 1);
        foo.appendInt(((a) < (1) ? (1) : (0)) + (1));
        foo.appendInt((((((a)) < ((1)) ? ((1)) : ((0)))) + ((1))));
    }

    public void extendedExpHasConditional(final Foo foo) {
        int a = 0;
        foo.appendInt(1 + (a < 1 ? 1 : 0) + 1);
        foo.appendInt(1 + (a > 1 ? 1 : 0) + 1);
        foo.appendInt((1) + ((a) < (1) ? (1) : (0)) + (1));
        foo.appendInt((((1)) + ((((a)) < ((1)) ? ((1)) : ((0)))) + ((1))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     */
    public void rightExpIsMethodRef(final Foo foo) {
        // method ref can't be exp of infix
        foo.appendInt(11 + foo.valueOf("1", Integer::valueOf));
        foo.appendInt(21 + foo.valueOf("2", Integer::valueOf));
        foo.appendInt((11) + (foo.valueOf(("1"), (Integer::valueOf))));
        foo.appendInt((((11)) + (foo.valueOf((("1")), ((Integer::valueOf))))));
    }

    /**
     * STEPIN - fix verifies.
     *
     * @param foo
     */
    public void leftExpIsMethodRef(final Foo foo) {
        // method ref can't be exp of infix
        foo.appendInt(foo.valueOf("1", Integer::valueOf) + 11);
        foo.appendInt(foo.valueOf("2", Integer::valueOf) + 21);
        foo.appendInt((foo.valueOf(("1"), (Integer::valueOf))) + (11));
        foo.appendInt(((foo.valueOf((("1")), ((Integer::valueOf)))) + ((11))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     */
    public void extendedExpIsMethodRef(final Foo foo) {
        // method ref can't be exp of infix
        foo.appendInt(1 + foo.valueOf("1", Integer::valueOf) + 11);
        foo.appendInt(1 + foo.valueOf("2", Integer::valueOf) + 21);
        foo.appendInt((1) + (foo.valueOf(("1"), (Integer::valueOf))) + (11));
        foo.appendInt((((1)) + (foo.valueOf((("1")), ((Integer::valueOf))))
                + ((11))));
    }

    public void rightExpIsFieldAccess(final Foo foo, final Box box) {
        foo.appendInt(1 - (box).id);
        foo.appendInt(2 - (box).id);
        foo.appendInt((1) - ((box).id));
        foo.appendInt(((((1)) - ((((box).id))))));
    }

    public void leftExpIsFieldAccess(final Foo foo, final Box box) {
        foo.appendInt((box).id - 1);
        foo.appendInt((box).id - 2);
        foo.appendInt(((box).id) - (1));
        foo.appendInt(((((((box).id))) - ((1)))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     * @param box
     */
    public void extendedExpHasFieldAccess(final Foo foo, final Box box) {
        foo.appendInt(1 - 1 - (box).id);
        foo.appendInt(2 - (1 - (box).id));
        foo.appendInt(1 - ((1) - ((box).id)));
        foo.appendInt(1 - (((1)) - ((((box).id)))));
    }

    public void rightExpIsInfix(final Foo foo) {
        int a = -10;
        foo.appendInt(1 - a + 1);
        foo.appendInt(1 - a + 2);
        foo.appendInt((1) - (a + 1));
        foo.appendInt((((1)) - ((((a) + (1))))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     */
    public void leftExpIsInfix(final Foo foo) {
        int a = -10;
        foo.appendInt(a + 1 - 1);
        foo.appendInt(a + 1 - 2);
        foo.appendInt((a + 1) - (1));
        foo.appendInt((((((a) + (1)))) - ((1))));
    }

    public void extendedExpHasInfix(final Foo foo) {
        int a = -10;
        foo.appendInt(1 - 1 - a + 1);
        foo.appendInt(1 - 1 - a + 2);
        foo.appendInt((1) - (1) - (a) + (1));
        foo.appendInt(((1)) - ((1)) - ((a)) + ((1)));
    }

    /**
     * STEPIN - change obj type from Foo to Object
     *
     * @param foo
     */
    public void rightExpIsInstanceof(final Foo foo) {
        Object obj = foo;
        foo.appendBoolean(true == obj instanceof String);
        foo.appendBoolean(true != obj instanceof String);
        foo.appendBoolean((true) == (obj instanceof String));
        foo.appendBoolean(((true) == (((obj) instanceof String))));
    }

    /**
     * STEPIN - change obj type from Foo to Object
     *
     * @param foo
     */
    public void leftExpIsInstanceof(final Foo foo) {
        Object obj = foo;
        foo.appendBoolean(obj instanceof String == true);
        foo.appendBoolean(obj instanceof String != true);
        foo.appendBoolean((obj instanceof String) == (true));
        foo.appendBoolean(((((obj) instanceof String)) == (true)));
    }

    /**
     * STEPIN - change obj type from Foo to Object
     *
     * @param foo
     */
    public void extendedExpHasInstanceof(final Foo foo) {
        Object obj = foo;
        foo.appendBoolean(true == (true == obj instanceof String));
        foo.appendBoolean(true == (true != obj instanceof String));
        foo.appendBoolean((true) == ((true) == (obj instanceof String)));
        foo.appendBoolean(
                ((true) == (((true) == (((obj) instanceof String))))));
    }

    /**
     * STEPIN - fix verifies
     *
     * @param foo
     * @param box
     */
    public void rightExpIsMi(final Foo foo, final Box box) {
        foo.appendInt(1 + box.getId());
        foo.appendInt(2 + box.getId());
        foo.appendInt(1 + (box).getId());
        foo.appendInt((1) + ((box).getId()));
        foo.appendInt((((1)) + ((((box)).getId()))));
    }

    /**
     * STEPIN - fix verifies
     *
     * @param foo
     * @param box
     */
    public void leftExpIsMi(final Foo foo, final Box box) {
        foo.appendInt(box.getId() + 1);
        foo.appendInt(box.getId() + 2);
        foo.appendInt((box).getId() + 1);
        foo.appendInt(((box).getId()) + (1));
        foo.appendInt((((((box)).getId())) + ((1))));
    }

    /**
     * STEPIN - fix verifies
     *
     * @param foo
     * @param box
     */
    public void exdendedExpHasMi(final Foo foo, final Box box) {
        foo.appendInt(1 + box.getId() + 1);
        foo.appendInt(1 + box.getId() + 2);
        foo.appendInt(1 + (box).getId() + 1);
        foo.appendInt((1) + ((box).getId()) + (1));
        foo.appendInt((((1)) + ((((box)).getId())) + ((1))));
    }

    public void rightExpIsNull(final Foo foo, final Box box) {
        foo.appendBoolean(box == null);
        foo.appendBoolean(box != null);
        foo.appendBoolean((box) == (null));
        foo.appendBoolean(((((box))) == ((null))));
    }

    public void leftExpIsNull(final Foo foo, final Box box) {
        foo.appendBoolean(null == box);
        foo.appendBoolean(null != box);
        foo.appendBoolean((null) == (box));
        foo.appendBoolean((((null)) == (((box)))));
    }

    public void extendedExpHasNull(final Foo foo, final Box box) {
        foo.appendBoolean(true == (null == box));
        foo.appendBoolean(true == (null != box));
        foo.appendBoolean(true == ((null) == (box)));
        foo.appendBoolean(true == (((null)) == (((box)))));
    }

    /**
     * STEPIN - unknit can't evaluate value of a++ and verifies are merged.
     * Unmerge verifies.
     *
     * @param foo
     */
    public void rightExpIsPostfix(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(1 + a++);
        foo.appendInt(1 + b++);
        foo.appendInt((1) + (a++));
        foo.appendInt((((1)) + (((a++)))));
    }

    /**
     * STEPIN - unknit can't evaluate value of a++ and verifies are merged.
     * Unmerge verifies.
     *
     * @param foo
     */
    public void leftExpIsPostfix(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(a++ + 1);
        foo.appendInt(b++ + 1);
        foo.appendInt((a++) + (1));
        foo.appendInt(((((a++))) + ((1))));
    }

    /**
     * STEPIN - unknit can't evaluate value of a++ and verifies are merged.
     * Unmerge verifies.
     *
     * @param foo
     */
    public void extendedExpHasPostfix(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(1 + 1 + a++);
        foo.appendInt(1 + 1 + b++);
        foo.appendInt(1 + (1 + a++));
        foo.appendInt((1) + ((1) + (a++)));
        foo.appendInt((((1)) + (((1)) + (((a++))))));
    }

    /**
     * STEPIN - unknit can't evaluate value of --a and verifies are merged.
     * Unmerge verifies.
     *
     * @param foo
     */
    public void rightExpIsPrefix(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(1 + --a);
        foo.appendInt(1 + --b);
        foo.appendInt((1) + (--a));
        foo.appendInt((((1)) + (((--a)))));
    }

    /**
     * STEPIN - unknit can't evaluate value of --a and verifies are merged.
     * Unmerge verifies.
     *
     * @param foo
     */
    public void leftExpIsPrefix(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(--a + 1);
        foo.appendInt(--b + 1);
        foo.appendInt((--a) + (1));
        foo.appendInt(((((--a))) + ((1))));
    }

    /**
     * STEPIN - unknit can't evaluate value of --a and verifies are merged.
     * Unmerge verifies.
     *
     * @param foo
     */
    public void extendedExpHasPrefix(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(1 + 1 + --a);
        foo.appendInt(1 + 1 + --b);
        foo.appendInt(1 + (1 + --a));
        foo.appendInt((1) + ((1) + (--a)));
        foo.appendInt((((1)) + (((1)) + (((--a))))));
    }

    public void rightExpIsQName(final Foo foo, final Box box) {
        foo.appendInt(1 - box.id);
        foo.appendInt(2 - box.id);
        foo.appendInt((1) - (box.id));
        foo.appendInt(((((1)) - (((box.id))))));
    }

    public void leftExpIsQName(final Foo foo, final Box box) {
        foo.appendInt(box.id - 1);
        foo.appendInt(box.id - 2);
        foo.appendInt((box.id) - (1));
        foo.appendInt((((((box.id))) - ((1)))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     * @param box
     */
    public void extendedExpHasQName(final Foo foo, final Box box) {
        foo.appendInt(1 - 1 - box.id);
        foo.appendInt(2 - (1 - box.id));
        foo.appendInt(1 - ((1) - (box.id)));
        foo.appendInt(1 - (((1)) - (((box.id)))));
    }

    public void rightExpIsSimpleName(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(1 + a);
        foo.appendInt(1 + b);
        foo.appendInt((1) + (a));
        foo.appendInt((((1)) + (((a)))));
    }

    public void leftExpIsSimpleName(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(a + 1);
        foo.appendInt(b + 1);
        foo.appendInt((a) + (1));
        foo.appendInt(((((a))) + ((1))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     * @param box
     */
    public void extendedExpHasSimpleName(final Foo foo) {
        int a = 10;
        int b = -10;
        foo.appendInt(1 + 1 + a);
        foo.appendInt(1 + 1 + b);
        foo.appendInt(1 + (1 + a));
        foo.appendInt((1) + ((1) + (a)));
        foo.appendInt((((1)) + (((1)) + (((a))))));
    }

    public void rightExpIsString(final Foo foo) {
        String a = "foo";
        String b = "bar";
        foo.appendString(a + "foo");
        foo.appendString(b + "foo");
        foo.appendString((a) + ("foo"));
        foo.appendString(((((a))) + (("foo"))));
    }

    public void leftExpIsString(final Foo foo) {
        String a = "foo";
        String b = "bar";
        foo.appendString("foo" + a);
        foo.appendString("foo" + b);
        foo.appendString(("foo") + (a));
        foo.appendString(((("foo")) + (((a)))));
    }

    /**
     * STEPIN - see class java doc.
     *
     * @param foo
     */
    public void extendedExpHasString(final Foo foo) {
        String a = "foo";
        String b = "bar";
        foo.appendString("baz" + "foo" + a);
        foo.appendString("baz" + "foo" + b);
        foo.appendString("baz" + ("foo" + a));
        foo.appendString(("baz") + (("foo") + (a)));
        foo.appendString(((("baz")) + ((("foo")) + (((a))))));
    }

    String name = "baz";

    public void rightExpIsThis(final Foo foo) {
        foo.appendString("foo" + this.name);
        foo.appendString("bar" + this.name);
        foo.appendString(("foo") + (this.name));
        foo.appendString(((("foo")) + (((this.name)))));
    }

    public void leftExpIsThis(final Foo foo) {
        foo.appendString(this.name + "foo");
        foo.appendString(this.name + "bar");
        foo.appendString((this.name) + ("foo"));
        foo.appendString(((((this.name))) + (("foo"))));
    }

    public void extendedExpHasThis(final Foo foo) {
        foo.appendString(true + ("foo" + this.name));
        foo.appendString(true + ("bar" + this.name));
        foo.appendString(true + (("foo") + (this.name)));
        foo.appendString(true + ((("foo")) + (((this.name)))));
    }

    public void rightExpIsClz(final Foo foo) {
        Class<?> clz = foo.getClass();
        foo.appendBoolean(clz == Integer.class);
        foo.appendBoolean(clz != Integer.class);
        foo.appendBoolean((clz) == (Integer.class));
        foo.appendBoolean(((((clz))) == ((Integer.class))));
    }

    public void leftExpIsClz(final Foo foo) {
        Class<?> clz = foo.getClass();
        foo.appendBoolean(Integer.class == clz);
        foo.appendBoolean(Integer.class != clz);
        foo.appendBoolean((Integer.class) == (clz));
        foo.appendBoolean((((Integer.class)) == (((clz)))));
    }

    public void extendedExpHasClz(final Foo foo) {
        Class<?> clz = foo.getClass();
        foo.appendBoolean(true == (Integer.class == clz));
        foo.appendBoolean(true == (Integer.class != clz));
        foo.appendBoolean(true == ((Integer.class) == (clz)));
        foo.appendBoolean(true == (((Integer.class)) == (((clz)))));
    }
}
