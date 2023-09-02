package org.codetab.uknit.itest.exp.value;

import org.codetab.uknit.itest.exp.value.Model.Box;
import org.codetab.uknit.itest.exp.value.Model.Foo;

public class Postfix {

    // STEPIN - uKnit can't evaluate postfix exp, fix verifies
    public void expIsArrayAccess(final Foo foo) {
        int[] ints = {1, 11};
        foo.appendInt(ints[0]++);
        foo.appendInt(ints[1]++);
        foo.appendInt(ints[(0)]++);
        foo.appendInt((((ints[((0))]++))));
    }

    public void expIsArrayCreation(final Foo foo) {
        foo.appendInt(new int[] {1, 11}[0]++);
        foo.appendInt(new int[] {1, 11}[1]++);
        foo.appendInt(new int[] {(1), (11)}[(0)]++);
        foo.appendInt((new int[] {(1), (11)}[(0)]++));
    }

    // STEPIN - uKnit can't evaluate postfix exp, fix verifies
    public void expIsCast(final Foo foo) {
        int a = 1;
        int b = 11;
        foo.appendString(String.valueOf((Integer) a++));
        foo.appendString(String.valueOf((Integer) b++));
        foo.appendString(String.valueOf(((Integer) a++)));
        foo.appendString((String.valueOf((((Integer) a++)))));
    }

    // ClassInstanceCreation can't be exp of postfix
    // Conditional exp can't be exp of postfix

    // STEPIN - uKnit can't evaluate postfix exp, fix verifies
    public void expIsFieldAccess(final Foo foo, final Box box) {
        foo.appendInt((box).id++);
        foo.appendObj((box).fid++);
        foo.appendInt(((box).id++));
        foo.appendInt((((box).id++)));
    }

    // Infix, MI, NullLiteral, NumberLiteral, Postfix, Prefix exp can't be exp
    // of postfix

    // STEPIN - uKnit can't evaluate postfix exp, fix verifies
    public void expIsQName(final Foo foo, final Box box) {
        foo.appendInt(box.id++);
        foo.appendObj(box.fid++);
        foo.appendInt((box.id++));
        foo.appendInt(((box.id++)));
    }

    // STEPIN - uKnit can't evaluate postfix exp, fix verifies
    public void expIsSimpleName(final Foo foo) {
        int a = 1;
        int b = 11;
        foo.appendInt(a++);
        foo.appendObj(b++);
        foo.appendInt((a++));
        foo.appendInt(((a++)));
    }

    int fa = 1;
    int fb = 11;

    // STEPIN - uKnit can't evaluate postfix exp, fix verifies
    public void expIsThis(final Foo foo) {
        foo.appendInt(this.fa++);
        foo.appendObj(this.fb++);
        foo.appendInt((this.fa++));
        foo.appendInt(((this.fa++)));
    }
}
