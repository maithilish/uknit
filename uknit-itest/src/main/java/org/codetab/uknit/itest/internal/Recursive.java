package org.codetab.uknit.itest.internal;

/**
 *
 * TODO L - Recursive call cycle is simply broke in
 * MethodMakers.processMethod(). Try to improve tests of recursive call.
 *
 * @author Maithilish
 *
 */
public class Recursive {

    public void callRecursiveSelf(int c) {
        c--;
        if (c > 0) {
            callRecursiveSelf(c);
        }
    }

    public void callRecursiveInternal() {
        recursiveInternal(2);
    }

    private void recursiveInternal(int c) {
        c--;
        if (c > 0) {
            recursiveInternal(c);
        }
    }
}
