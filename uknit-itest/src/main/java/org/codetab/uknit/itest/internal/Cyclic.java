package org.codetab.uknit.itest.internal;

/**
 * Test IM calling the caller.
 *
 * TODO L - analyze a real test with some when and verify including cyclic
 * break.
 *
 * @author Maithilish
 *
 */
public class Cyclic {

    public void imCallsImCallerA() {
        imc1(5);
    }

    private void imc1(int c) {
        c--;
        imc2(c);
    }

    private void imc2(int c) {
        c--;
        if (c > 0) {
            imc1(c);
        }
    }

    public void imCallsImCallerB() {
        imc11(5);
    }

    private void imc11(final int c) {
        imc12(c);
    }

    private void imc12(final int c) {
        imc13(c);
    }

    private void imc13(int c) {
        c--;
        if (c > 0) {
            imc11(c);
        }
    }

    public void imCallsBaseC(int c) {
        c--;
        imc21(c);
    }

    private void imc21(int c) {
        c--;
        if (c > 0) {
            imCallsBaseC(c);
        }
    }

    public void imCallsBaseD(int c) {
        c--;
        imc31(c);
    }

    private void imc31(final int c) {
        imc32(c);
    }

    private void imc32(int c) {
        c--;
        if (c > 0) {
            imCallsBaseD(c);
        }
    }
}
