package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

/**
 * Test various types of expressions in an ArrayCreation.
 *
 * <code>
 * ArrayCreation:
 *    new PrimitiveType [ Expression ] { [ Expression ] } { [ ] }
 *    new TypeName [ < Type { , Type } > ]
 *        [ Expression ] { [ Expression ] } { [ ] }
 *  ...
 * </code>
 *
 * @author Maithilish
 *
 */
public class ArrayCreation {

    public void expIsArrayAccess(final Foo foo) {
        int[] indexes = {1, 2};
        int[] ints = (new int[indexes[(0)]]);
        int[] codes = (new int[indexes[(1)]]);
        foo.appendObj(ints);
        foo.appendObj(codes);
        foo.appendObj((ints));
    }

    public void expIsArrayAccess2(final Foo foo) {
        int[] indexes = {1, 2};
        foo.appendObj(new int[indexes[(0)]]);
        foo.appendObj(new int[indexes[(1)]]);
        foo.appendObj((new int[indexes[(0)]]));
    }

    public void expIsArrayCreation(final Foo foo) {
        foo.appendObj(new int[new int[] {1}[0]]);
        foo.appendObj(new int[new int[] {2}[0]]);
        foo.appendObj(new int[(new int[] {(1)}[(0)])]);
        foo.appendObj((new int[((new int[] {((1))}[((0))]))]));
    }

    public void expIsCast(final Foo foo) {
        Object[] indexes = {1, 2};
        foo.appendObj(new int[(int) indexes[(0)]]);
        foo.appendObj(new int[(int) indexes[(1)]]);
        foo.appendObj((new int[((int) indexes[(0)])]));
    }

    public void expIsCharLiteral(final Foo foo) {
        foo.appendObj(new int['a']);
        foo.appendObj(new int['b']);
        foo.appendObj((new int[('a')]));
    }

    @SuppressWarnings("deprecation")
    public void expIsClassInstanceCreation(final Foo foo) {
        foo.appendObj(new String[new Integer(1)]);
        foo.appendObj(new String[new Integer(2)]);
        foo.appendObj((new String[(new Integer(1))]));
    }

    public void expIsConditional(final Foo foo) {
        int code = 1;
        foo.appendObj(new String[code > 0 ? 1 : 0]);
        foo.appendObj(new String[code > 1 ? 1 : 0]);
        foo.appendObj(new String[(code) > (0) ? (1) : (0)]);
        foo.appendObj((new String[((code)) > ((0)) ? ((1)) : ((0))]));
    }

    public void expIsMethodRef(final Foo foo) {
        foo.appendObj(new String[foo.valueOf("1", Integer::valueOf)]);
        foo.appendObj(new String[foo.valueOf("2", Integer::valueOf)]);
        foo.appendObj(new String[(foo.valueOf(("1"), (Integer::valueOf)))]);
        foo.appendObj(
                (new String[((foo.valueOf((("1")), ((Integer::valueOf)))))]));
    }

    public void expIsFieldAccess(final Foo foo, final Box box) {
        box.id2 = 11;
        foo.appendObj(new String[(box).id]);
        foo.appendObj(new String[(box).id2]);
        foo.appendObj(new String[((box).id)]);
        foo.appendObj((new String[(((box).id))]));
    }

    public void expIsInfix(final Foo foo) {
        foo.appendObj(new String[1 + 1]);
        foo.appendObj(new String[2 + 2]);
        foo.appendObj(new String[(1 + 1)]);
        foo.appendObj((new String[((1 + 1))]));
    }

    public void expIsLambda(final Foo foo) {
        foo.appendObj(new String[foo.valueOf("1", i -> Integer.valueOf(i))]);
        foo.appendObj(new String[foo.valueOf("2", i -> Integer.valueOf(i))]);
        foo.appendObj(
                new String[(foo.valueOf(("1"), (i -> Integer.valueOf(i))))]);
        foo.appendObj((new String[((foo.valueOf((("1")),
                ((i -> Integer.valueOf(i))))))]));
    }

    // STEPIN - change infer var values.
    public void expIsMI(final Foo foo, final Box box, final Box box2) {
        foo.appendObj(new String[box.getId()]);
        foo.appendObj(new String[box2.getId()]);
        foo.appendObj(new String[((box).getId())]);
        foo.appendObj((new String[(((((box))).getId()))]));
    }

    public void expIsNumberLiteral(final Foo foo) {
        foo.appendObj(new String[1]);
        foo.appendObj(new String[2]);
        foo.appendObj(new String[(1)]);
        foo.appendObj((new String[((1))]));
    }

    // STEPIN - uKnit can't evaluate value of postfix. Fix verifies.
    public void expIsPostfix(final Foo foo) {
        int a = 1;
        int b = 11;
        foo.appendObj(new String[a++]);
        foo.appendObj(new String[b++]);
        foo.appendObj(new String[(a++)]);
        foo.appendObj((new String[((a++))]));
    }

    // STEPIN - uKnit can't evaluate value of postfix. Fix verifies.
    public void expIsPrefix(final Foo foo) {
        int a = 1;
        int b = 11;
        foo.appendObj(new String[++a]);
        foo.appendObj(new String[++b]);
        foo.appendObj(new String[(++a)]);
        foo.appendObj((new String[((++a))]));
    }

    public void expIsQName(final Foo foo, final Box box) {
        box.id2 = 11;
        foo.appendObj(new String[box.id]);
        foo.appendObj(new String[box.id2]);
        foo.appendObj(new String[(box.id)]);
        foo.appendObj((new String[((box.id))]));
    }

    public void expIsSimpleName(final Foo foo, final Box box) {
        int a = 1;
        int b = 11;
        foo.appendObj(new String[a]);
        foo.appendObj(new String[b]);
        foo.appendObj(new String[(a)]);
        foo.appendObj((new String[((a))]));
    }

    int fa = 1;
    int fb = 11;

    public void expIsThis(final Foo foo, final Box box) {
        foo.appendObj(new String[this.fa]);
        foo.appendObj(new String[this.fb]);
        foo.appendObj(new String[(this.fa)]);
        foo.appendObj((new String[((this.fa))]));
    }
}
