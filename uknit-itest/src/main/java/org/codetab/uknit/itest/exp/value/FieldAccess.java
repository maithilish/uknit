package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

class FieldAccess {

    /*
     * STEPIN - fix verifies
     *
     * TODO L - Two boxes[0] points to same value but two infer vars box and
     * box2, are created. Either one var should be created (impacts many tests!)
     * or box2 = box with a varName patch.
     */
    public void expIsArrayAccess(final Foo foo) {
        Box[] boxes = {new Box("foo"), new Box("bar")};
        foo.appendObj((boxes[0]).name);
        foo.appendObj((boxes[1]).name);
        foo.appendObj((((boxes[(0)])).name));
    }

    /*
     * STEPIN - fix verifies
     */
    public void expIsArrayCreation(final Foo foo) {
        foo.appendObj((new Box[] {new Box("foo"), new Box("bar")}[0]).name);
        foo.appendObj((new Box[] {new Box("foo"), new Box("bar")}[1]).name);
        foo.appendObj(
                (((new Box[] {new Box("foo"), new Box("bar")}[(0)])).name));
    }

    public void expIsCast(final Foo foo, final Object box) {
        foo.appendObj(((Box) box).name);
        foo.appendObj(((((Box) box)).name));
    }

    public void expIsClassInstanceCreation(final Foo foo, final Object box) {
        foo.appendObj((new Box()).name);
        foo.appendObj((((new Box())).name));
    }

    /*
     * STEPIN - make box spy and initialize box field with new Box("foo")
     */
    public void expIsFieldAccess(final Foo foo, final Box box) {
        foo.appendObj(((box).box).name);
        foo.appendObj(((((box).box)).name));
    }

    // infix, instanceof, method ref, lambda field access not possible

    /*
     * STEPIN - fix verify as foo.getBox() may return same or different instance
     */
    public void expIsMi(final Foo foo) {
        foo.appendObj((foo.getBox()).name);
        foo.appendObj((((foo.getBox())).name));
    }

    // null literal, number literal, postfix, prefix, string literal and class
    // literal field access not possible

    /*
     * STEPIN - make box spy and initialize box field with new Box("foo")
     */
    public void expIsQName(final Foo foo, final Box box) {
        foo.appendObj((box.box).name);
        foo.appendObj(((box.box).name));
    }

    /*
     * STEPIN - make box spy
     */
    public void expIsSimpleName(final Foo foo, final Box box) {
        foo.appendObj((box).name);
        foo.appendObj(((box).name));
    }

    Box tbox = new Box("foo");

    public void expIsThisExp(final Foo foo) {
        foo.appendObj((tbox).name);
        foo.appendObj(((tbox).name));
    }

}
