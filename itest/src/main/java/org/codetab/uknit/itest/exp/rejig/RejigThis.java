package org.codetab.uknit.itest.exp.rejig;

import java.util.function.Supplier;

import org.codetab.uknit.itest.exp.rejig.Model.Box;
import org.codetab.uknit.itest.exp.rejig.Model.Foo;

/**
 * Test whether this exp is modified to CUT name in output.
 *
 * @author Maithilish
 *
 */
public class RejigThis {

    // STEPIN - fix verifies
    public void thisInArrayCreation(final Foo foo) {
        Object[] array = new Object[] {this, (this), ((this)), "foo"};
        foo.appendObj(array[0]);
        foo.appendObj(array[1]);
        foo.appendObj(array[2]);
        foo.appendObj(array[3]);

        foo.appendObj(new Object[] {"foo"});
        foo.appendObj(new Object[] {this, this, this, "bar"});
        foo.appendObj(new Object[] {(this), (this), ((this)), "bar"});
    }

    public void thisInArrayInitializer(final Foo foo) {
        Object[] array = {this, (this), ((this)), "foo"};
        foo.appendObj(array[0]);
        foo.appendObj(array[1]);
        foo.appendObj(array[2]);
        foo.appendObj(array[3]);
    }

    // STEPIN - fix verifies
    public void thisInClassInstanceCreation(final Foo foo) {
        Box box = new Box(this);
        foo.appendBox(box);
        foo.appendBox(new Box(this));
        foo.appendBox(new Box((this)));
        foo.appendBox((new Box(((this)))));
    }

    int fcode = 0;

    public void thisInConditionalExp(final Foo foo) {
        foo.appendObj(this.fcode);
        foo.appendObj(this.fcode > 1 ? this : "bar");
        foo.appendObj(this.fcode > 1 ? "foo" : this);
        foo.appendObj((this).fcode > 1 ? ("foo") : (this));
        foo.appendObj((((this).fcode) > (1)) ? (("foo")) : ((this)));
    }

    public void thisInConditionalAnswer(final Foo foo) {
        int a = 0;
        Object code = a > 1 ? this : "bar";
        foo.appendObj(code);
        foo.appendObj(a > 1 ? this : "bar");
        foo.appendObj(a > 1 ? "foo" : this);
        foo.appendObj(a > 1 ? ("foo") : (this));
        foo.appendObj((a > 1) ? (("foo")) : ((this)));
    }

    private Integer getValue(final Supplier<Integer> func) {
        return func.get();
    }

    private Integer getId() {
        return 10;
    }

    public void thisInMethodRef(final Foo foo) {
        foo.appendObj(this.getValue(this::getId));
    }

    public void thisInFieldAccess(final Foo foo, final Box box) {
        foo.appendObj(this.fcode);
        foo.appendObj((this).fcode);
        foo.appendObj(((this).fcode));
    }

    RejigThis o1;
    RejigThis o2;

    // STEPIN - fix verify count
    public void thisInInfix(final Foo foo) {
        foo.appendObj(this == o1);
        foo.appendObj(this == o2);
        foo.appendObj((this) == (o1));
        foo.appendObj(((this) == ((o2))));

        foo.appendObj(o1 == this);
        foo.appendObj(o2 == this);
        foo.appendObj(o1 == (this));
        foo.appendObj(((o1) == (this)));
    }

    /*
     * STEPIN - fix verifies
     *
     * TODO L - verifies merge is not consistent, fix it.
     *
     */
    public void thisFieldAccessInInfix(final Foo foo) {
        foo.appendObj(this.fcode + 1);
        foo.appendObj(this.fcode + 2);
        foo.appendObj((this).fcode + 1);
        foo.appendObj(((this).fcode) + 1);

        foo.appendObj(1 + this.fcode);
        foo.appendObj(2 + this.fcode);
        foo.appendObj((1) + (this).fcode);
        foo.appendObj(((1) + (this).fcode));
    }

    public void thisInLambda(final Foo foo) {
        foo.appendObj(this.getValue(() -> getId()));
    }

    public void thisInInstanceof(final Foo foo) {
        foo.appendObj(this instanceof RejigThis);
        foo.appendObj((this) instanceof RejigThis);
        foo.appendObj((((this)) instanceof RejigThis));
    }

    // STEPIN - change var values to 10, fix verifies times.
    public void thisInMIExp(final Foo foo, final Box box) {
        foo.appendObj(this.getId());
        foo.appendObj((this).getId());
        foo.appendObj((((this)).getId()));
    }

    public void thisInMIArg(final Foo foo, final Box box) {
        Object obj1 = box.strip(this);
        Object obj2 = (box).strip((this));
        foo.appendObj(obj1);
        foo.appendObj(obj2);
        foo.appendObj(((obj1)));
    }

    // STEPIN - fix verifies and times.
    public void thisFieldAccessInPostfix(final Foo foo) {
        int c = this.fcode++;
        foo.appendInt(c);

        foo.appendInt(this.fcode++);
        foo.appendInt(this.fcode--);
        foo.appendInt(((this).fcode++));
    }

    // STEPIN - fix verifies and times.
    public void thisFieldAccessInPrefix(final Foo foo) {
        int c = ++this.fcode;
        foo.appendInt(c);

        foo.appendInt(++this.fcode);
        foo.appendInt(++this.fcode);
        foo.appendInt((++(this).fcode));
    }

    public void thisInthisExpression(final Foo foo) {
        foo.appendObj(RejigThis.this);
        foo.appendObj((RejigThis.this));
    }

}
