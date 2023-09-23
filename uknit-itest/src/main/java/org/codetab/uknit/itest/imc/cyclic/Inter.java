package org.codetab.uknit.itest.imc.cyclic;

import org.codetab.uknit.itest.imc.cyclic.Model.Foo;

/**
 * Test calls from one path to another.
 *
 * @author Maithilish
 *
 */
public class Inter {

    boolean end = false;

    public void callInterNoCyclic11(final Foo foo) {
        imc12(foo);
        imc16(foo);
    }

    // branch 1
    private void imc12(final Foo foo) {
        foo.append("imc12 before");
        imc13(foo);
        foo.append("imc12 after");
        foo.format("imc12 after");
    }

    private void imc13(final Foo foo) {
        foo.append("imc13 before");
        imc14(foo);
        foo.append("imc13 after");
        foo.format("imc13 after");
    }

    private void imc14(final Foo foo) {
        foo.append("imc14 before");
        imc15(foo);
        foo.append("imc14 after");
        foo.format("imc14 after");
    }

    private void imc15(final Foo foo) {
        foo.append("imc15");
    }

    // branch 2
    private void imc16(final Foo foo) {
        foo.append("imc16 before");
        imc17(foo);
        foo.append("imc16 after");
        foo.format("imc16 after");
    }

    // call a node in branch 1
    private void imc17(final Foo foo) {
        foo.append("imc17 before");
        imc14(foo);
        foo.append("imc17 after");
        foo.format("imc17 after");
    }

    /**
     * STEPIN - The call imc23() in imc25() is cyclic and not processed even
     * though the cyclic breaks only in imc24(). This results in verify times()
     * mismatch. The call imc23() from imc27() becomes cyclic only after imc23()
     * is called again by imc25() even though it breaks before calling imc25()
     * in imc24(). In both cases, user has to step in and update the times
     * count.
     *
     * TODO L - try to fix the error after implementing the ctl flow path for IM
     * (if ever).
     *
     * @param foo
     */
    public void callInterCyclic21(final Foo foo) {
        imc22(foo);
        imc26(foo);
    }

    // branch 1
    private void imc22(final Foo foo) {
        foo.append("imc22 before");
        imc23(foo);
        foo.append("imc22 after");
        foo.format("imc22 after");
    }

    private void imc23(final Foo foo) {
        foo.append("imc23 before");
        imc24(foo);
        foo.append("imc23 after");
        foo.format("imc23 after");
    }

    private void imc24(final Foo foo) {
        foo.append("imc24 before");
        if (!end) {
            imc25(foo);
        }
        foo.append("imc24 after");
        foo.format("imc24 after");
    }

    private void imc25(final Foo foo) {
        foo.append("imc25 before");
        end = true;
        imc23(foo);
        foo.append("imc25 after");
        foo.format("imc25 after");
    }

    // branch 2
    private void imc26(final Foo foo) {
        foo.append("imc26 before");
        imc27(foo);
        foo.append("imc26 after");
        foo.format("imc26 after");
    }

    // call a node in branch 1
    private void imc27(final Foo foo) {
        foo.append("imc27 before");
        end = true;
        imc23(foo);
        foo.append("imc27 after");
        foo.format("imc27 after");
    }

    // zigzag - path1 calls path2 and vice versa.
    public void callZigZagCyclic21(final Foo foo) {
        imc32(foo);
        imc36(foo);
    }

    // branch 1
    private void imc32(final Foo foo) {
        foo.append("imc32 before");
        imc33(foo);
        foo.append("imc32 after");
        foo.format("imc32 after");
    }

    private void imc33(final Foo foo) {
        foo.append("imc33 before");
        if (!end) {
            imc36(foo);
        }
        foo.append("imc33 after");
        foo.format("imc33 after");
    }

    // branch 2
    private void imc36(final Foo foo) {
        foo.append("imc36 before");
        imc37(foo);
        foo.append("imc36 after");
        foo.format("imc36 after");
    }

    // call a node in branch 1
    private void imc37(final Foo foo) {
        foo.append("imc37 before");
        end = true;
        imc32(foo);
        foo.append("imc37 after");
        foo.format("imc37 after");
    }
}
