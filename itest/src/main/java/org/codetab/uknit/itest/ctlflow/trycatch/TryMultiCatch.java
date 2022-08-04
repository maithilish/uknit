package org.codetab.uknit.itest.ctlflow.trycatch;

import org.codetab.uknit.itest.model.Duck;

public class TryMultiCatch {

    public String tryMultiCatchFoo(final Duck duck) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException | IllegalArgumentException e) {
            duck.swim("multi catch");
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryCatchMultiCatchFoo(final Duck duck) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
        } catch (IllegalAccessError | IllegalArgumentException e) {
            duck.swim("multi catch");
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryMultiCatchPlusIfFoo(final Duck duck,
            final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException | IllegalArgumentException e) {
            duck.swim("multi catch");
        } finally {
            duck.swim("finally");
        }
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryMultiCatchIfPlusIfFoo(final Duck duck,
            final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException | IllegalArgumentException e) {
            duck.swim("multi catch");
            if (canSwim) {
                duck.swim("multi catch if");
            } else {
                duck.swim("multi catch else");
            }
        } finally {
            duck.swim("finally");
        }

        duck.swim("end");
        return duck.toString();
    }
}
